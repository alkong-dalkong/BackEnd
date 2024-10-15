package alkong_dalkong.backend.Physical.service;

import alkong_dalkong.backend.Physical.dto.response.PhysicalResponseDto;
import alkong_dalkong.backend.Physical.entity.PhysicalInfo;
import alkong_dalkong.backend.Physical.entity.WeightInfo;
import alkong_dalkong.backend.Physical.repository.PhysicalInfoRepository;
import alkong_dalkong.backend.Physical.util.AgeRangeUtils;
import alkong_dalkong.backend.Physical.util.ApiWeightData;
import alkong_dalkong.backend.Physical.util.ApiWeightDataWrapper;
import alkong_dalkong.backend.User.Domain.Gender;
import alkong_dalkong.backend.User.Domain.User;
import alkong_dalkong.backend.User.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhysicalService {

    @Autowired
    private PhysicalInfoRepository physicalInfoRepository;

    @Autowired
    private UserRepository userRepository;

    public PhysicalResponseDto getPhysicalInfo(Long userId, String period) throws IOException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("해당 유저를 찾을 수 없습니다: " + userId);
        }
        User user = userOpt.get();

        // 유저의 PhysicalInfo 찾기 (없으면 생성)
        Optional<PhysicalInfo> physicalInfoOpt = physicalInfoRepository.findByUserUserId(userId);
        PhysicalInfo physicalInfo;
        if (physicalInfoOpt.isEmpty()) {
            physicalInfo = new PhysicalInfo();
            physicalInfo.setUser(user);
            physicalInfo = physicalInfoRepository.save(physicalInfo);
        } else {
            physicalInfo = physicalInfoOpt.get();
        }

        float apiAvgWeight = getApiAvgWeight(user.getGender(), user.getBirth());

        // 오늘 날짜의 데이터를 확인 -> 없으면 null 반환
        Optional<WeightInfo> todayWeightOpt = physicalInfo.getWeightInfoList() != null ?
                physicalInfo.getWeightInfoList().stream()
                        .filter(weight -> weight.getCreatedAt().equals(LocalDate.now()))
                        .findFirst() : Optional.empty();

        PhysicalResponseDto.Weight weightDto = null;
        if (todayWeightOpt.isPresent()) {
            WeightInfo todayWeight = todayWeightOpt.get();
            weightDto = PhysicalResponseDto.Weight.builder()
                    .weightId(todayWeight.getWeightId())
                    .weight(todayWeight.getWeight())
                    .build();
        }

        // 주기(period)에 따라 체중 데이터의 평균 계산
        List<WeightInfo> weightInfoList = physicalInfo.getWeightInfoList() != null ? physicalInfo.getWeightInfoList() : Collections.emptyList();
        List<PhysicalResponseDto.WeightInfoDto> weightInfoDtoList = calculateAverageWeight(weightInfoList, period);

        // 건강 리포트 생성 (체중 정보가 있을 때만 계산)
        PhysicalResponseDto.HealthReport healthReport = null;
        if (todayWeightOpt.isPresent()) {
            // 주별 그룹화된 weightInfoList를 넘김
            healthReport = createHealthReport(todayWeightOpt.get().getWeight(), apiAvgWeight, calculateAverageWeight(weightInfoList, "weekly"));
        }

        return PhysicalResponseDto.builder()
                .physicalId(physicalInfo.getPhysicalId())
                .weight(weightDto)
                .weightInfo(weightInfoDtoList)
                .healthReport(healthReport)
                .build();
    }

    // 주기(period)에 따라 주별 또는 월별 평균 체중 계산
    private List<PhysicalResponseDto.WeightInfoDto> calculateAverageWeight(List<WeightInfo> weightInfoList, String period) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        if (period.equals("weekly")) {
            return weightInfoList.stream()
                    .collect(Collectors.groupingBy(this::getWeekOfMonthAndYear)) // 년-월-주차로 그룹화
                    .entrySet().stream()
                    .map(entry -> new PhysicalResponseDto.WeightInfoDto(
                            calculateAverage(entry.getValue()), // 평균 계산
                            entry.getKey()  // 년-월-주차 정보
                    ))
                    .sorted((a, b) -> b.getAvgDate().compareTo(a.getAvgDate())) // 년-월-주차 내림차순 정렬
                    .collect(Collectors.toList());
        } else if (period.equals("monthly")) {
            return weightInfoList.stream()
                    .collect(Collectors.groupingBy(weight -> weight.getCreatedAt().withDayOfMonth(1))) // 월별 그룹화
                    .entrySet().stream()
                    .map(entry -> new PhysicalResponseDto.WeightInfoDto(
                            calculateAverage(entry.getValue()), // 평균 계산
                            entry.getKey().format(formatter)
                    ))
                    .sorted((a, b) -> b.getAvgDate().compareTo(a.getAvgDate())) // 년-월 내림차순 정렬
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();  // weightInfoList가 없으면 빈 리스트 반환
    }

    // 리스트 내의 체중 정보 평균 계산
    private float calculateAverage(List<WeightInfo> weightInfos) {
        return (float) weightInfos.stream().mapToDouble(WeightInfo::getWeight).average().orElse(0);
    }

    // WeightInfo 객체의 주(week) 계산후 문자열 조합 후 반환
    private String getWeekOfMonthAndYear(WeightInfo weightInfo) {
        LocalDate date = weightInfo.getCreatedAt();
        // 해당 달의 몇 번째 주인지 계산 (1부터 시작)
        int weekOfMonth = (date.getDayOfMonth() - 1) / 7 + 1;
        // 년, 월, 주차 정보를 문자열로 반환
        return String.format("%d-%02d-W%d", date.getYear(), date.getMonthValue(), weekOfMonth);
    }

    // 통계 데이터를 사용하여 유저의 평균 체중 가져오기
    private float getApiAvgWeight(Gender gender, LocalDate birthDate) throws IOException {
        int userAge = AgeRangeUtils.calculateAge(birthDate, LocalDate.now());
        ObjectMapper objectMapper = new ObjectMapper();

        // 리소스 파일을 ClassPathResource를 통해 읽음
        ClassPathResource resource = new ClassPathResource("weight_data.json");

        // InputStream으로 리소스 파일 읽기
        try (InputStream inputStream = resource.getInputStream()) {
            // ApiWeightDataWrapper 클래스로 역직렬화
            ApiWeightDataWrapper weightDataWrapper = objectMapper.readValue(inputStream, ApiWeightDataWrapper.class);

            List<ApiWeightData> weightData = weightDataWrapper.getData();

            return weightData.stream()
                    .filter(data -> data.getGender().equals(gender.toString()))
                    .filter(data -> userAge >= data.getMinAge() && userAge <= data.getMaxAge())
                    .findFirst()
                    .map(ApiWeightData::getApiAvgWeight)
                    .orElseThrow(() -> new IllegalArgumentException("유저의 성별과 나이에 맞는 평균 체중 정보를 가지고 있지 않습니다."));
        }
    }

    // 건강 리포트 생성
    private PhysicalResponseDto.HealthReport createHealthReport(float todayWeight, float apiAvgWeight, List<PhysicalResponseDto.WeightInfoDto> weightInfoList) {
        // 오늘 기준으로 지난주의 날짜를 계산
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        int oneWeekAgoWeekOfMonth = (oneWeekAgo.getDayOfMonth() - 1) / 7 + 1;
        String lastWeek = oneWeekAgo.getYear() + "-" + oneWeekAgo.getMonthValue() + "-W" + oneWeekAgoWeekOfMonth;

        // 지난 주 주차에 해당하는 데이터 필터링하여 해당 주차의 평균 체중을 가져옴
        Optional<Float> lastWeekAvgWeightOpt = weightInfoList.stream()
                .filter(weightInfo -> weightInfo.getAvgDate().equals(lastWeek))
                .map(PhysicalResponseDto.WeightInfoDto::getAvgWeight)
                .findFirst();

        // 지난주 평균 체중이 없으면 차이를 -1로 설정
        float lastWeekAvgWeightDiff = lastWeekAvgWeightOpt
                .map(lastWeekAvgWeight -> todayWeight - lastWeekAvgWeight) // 지난 주 평균 체중과 오늘 체중 차이 계산
                .orElse(-1.0f); // 지난 주 데이터가 없으면 -1로 설정

        return PhysicalResponseDto.HealthReport.builder()
                .apiAvgWeight(apiAvgWeight)
                .diffWeight(todayWeight - apiAvgWeight)
                .lastweekWeight(lastWeekAvgWeightDiff)
                .build();
    }
}
