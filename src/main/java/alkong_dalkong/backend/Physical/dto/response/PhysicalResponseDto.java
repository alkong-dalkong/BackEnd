package alkong_dalkong.backend.Physical.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PhysicalResponseDto {

    private Long physicalId;
    private Weight weight;
    private List<WeightInfoDto> weightInfo;
    private HealthReport healthReport;

    @Data
    @AllArgsConstructor
    @Builder
    public static class Weight {
        private Long weightId;
        private float weight;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class WeightInfoDto {
        private float avgWeight;
        private String avgDate; // yyyy-MM 형식 / 주차별일땐 yyyy-MM-Wx 형식
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class HealthReport {
        private float apiAvgWeight;
        private float diffWeight;
        private float lastweekWeight;
    }
}