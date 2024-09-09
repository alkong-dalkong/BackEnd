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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

        Optional<PhysicalInfo> physicalInfoOpt = physicalInfoRepository.findByUserUserId(userId);
        PhysicalInfo physicalInfo = physicalInfoOpt.get();

        float apiAvgWeight = getApiAvgWeight(user.getGender(), user.getBirth());

        Optional<WeightInfo> latestWeightOpt =
                physicalInfo.getWeightInfoList().stream().findFirst();

        PhysicalResponseDto.Weight weightDto = null;
        if (latestWeightOpt.isPresent()) {
            WeightInfo latestWeight = latestWeightOpt.get();
            weightDto = PhysicalResponseDto.Weight.builder()
                    .weightId(latestWeight.getWeightId())
                    .weight(latestWeight.getWeight())
                    .build();
        }

        // 주기(period)에 따라 체중 데이터의 평균 계산
        List<PhysicalResponseDto.WeightInfoDto> weightInfoDtoList = calculateAverageWeight(physicalInfo.getWeightInfoList(), period);

        // 건강 리포트 생성 (체중 정보가 있을 때만 계산)
        PhysicalResponseDto.HealthReport healthReport = null;
        if (latestWeightOpt.isPresent()) {
            // 주별 그룹화된 weightInfoList를 넘김
            healthReport = createHealthReport(latestWeightOpt.get().getWeight(), apiAvgWeight, calculateAverageWeight(physicalInfo.getWeightInfoList(), "weekly"));
        }

        return PhysicalResponseDto.builder()
                .physicalId(physicalInfo.getPhysicalId())
                .weight(weightDto)
                .weightInfo(weightInfoDtoList)
                .healthReport(healthReport)
                .build();
    }
}
