package alkong_dalkong.backend.Physical.service;

import alkong_dalkong.backend.Physical.dto.response.PhysicalResponseDto;
import alkong_dalkong.backend.Physical.entity.PhysicalInfo;
import alkong_dalkong.backend.Physical.entity.WeightInfo;
import alkong_dalkong.backend.Physical.repository.PhysicalInfoRepository;
import alkong_dalkong.backend.User.Domain.User;
import alkong_dalkong.backend.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhysicalService {

    @Autowired
    private PhysicalInfoRepository physicalInfoRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String WEIGHT_DATA_PATH = "src/main/resources/weight_data.json";

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

        // 체중 정보가 있는지 확인
        Optional<WeightInfo> latestWeightOpt = physicalInfo.getWeightInfoList() != null ?
                physicalInfo.getWeightInfoList().stream().findFirst() : Optional.empty();

        PhysicalResponseDto.Weight weightDto = null;
        if (latestWeightOpt.isPresent()) {
            WeightInfo latestWeight = latestWeightOpt.get();
            weightDto = PhysicalResponseDto.Weight.builder()
                    .weightId(latestWeight.getWeightId())
                    .weight(latestWeight.getWeight())
                    .build();
        }

        // 주기(period)에 따라 체중 데이터의 평균 계산
        List<WeightInfo> weightInfoList = physicalInfo.getWeightInfoList() != null ? physicalInfo.getWeightInfoList() : Collections.emptyList();
        List<PhysicalResponseDto.WeightInfoDto> weightInfoDtoList = calculateAverageWeight(weightInfoList, period);

        // 건강 리포트 생성 (체중 정보가 있을 때만 계산)
        PhysicalResponseDto.HealthReport healthReport = null;
        if (latestWeightOpt.isPresent()) {
            // 주별 그룹화된 weightInfoList를 넘김
            healthReport = createHealthReport(latestWeightOpt.get().getWeight(), apiAvgWeight, calculateAverageWeight(weightInfoList, "weekly"));
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

}
