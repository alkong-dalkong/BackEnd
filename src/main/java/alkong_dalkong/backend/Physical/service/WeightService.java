package alkong_dalkong.backend.Physical.service;

import alkong_dalkong.backend.Physical.dto.request.PhysicalRequestDto;
import alkong_dalkong.backend.Physical.dto.request.PhysicalUpdateRequestDto;
import alkong_dalkong.backend.Physical.entity.PhysicalInfo;
import alkong_dalkong.backend.Physical.entity.WeightInfo;
import alkong_dalkong.backend.Physical.repository.PhysicalInfoRepository;
import alkong_dalkong.backend.Physical.repository.WeightInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeightService {

    @Autowired
    private PhysicalInfoRepository physicalInfoRepository;

    @Autowired
    private WeightInfoRepository weightInfoRepository;

    // 체중 정보 추가
    public Long addWeight(PhysicalRequestDto requestDto) {
        Optional<PhysicalInfo> physicalInfoOpt = physicalInfoRepository.findById(requestDto.getPhysicalId());
        if (physicalInfoOpt.isEmpty()) {
            throw new IllegalArgumentException("해당 유저의 건강 정보가 존재하지 않습니다: " + requestDto.getPhysicalId());
        }

        PhysicalInfo physicalInfo = physicalInfoOpt.get();

        WeightInfo weightInfo = new WeightInfo();
        weightInfo.setWeight(requestDto.getWeight());
        weightInfo.setCreatedAt(requestDto.getCreatedAt());
        weightInfo.setPhysicalInfo(physicalInfo);

        return weightInfoRepository.save(weightInfo).getWeightId();
    }

    // 체중 정보 수정
    public Long updateWeight(Long weightId, PhysicalUpdateRequestDto requestDto) {
        Optional<WeightInfo> weightInfoOpt = weightInfoRepository.findById(weightId);
        if (weightInfoOpt.isEmpty()) {
            throw new IllegalArgumentException("해당 체중 정보를 찾을 수 없습니다: " + weightId);
        }

        WeightInfo weightInfo = weightInfoOpt.get();
        weightInfo.setWeight(requestDto.getWeight());
        weightInfo.setCreatedAt(requestDto.getCreatedAt());

        return weightInfoRepository.save(weightInfo).getWeightId();
    }
}
