package alkong_dalkong.backend.Physical.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ApiWeightDataWrapper {

    private Metadata metadata;
    private List<ApiWeightData> data;

    @Data
    public static class Metadata {
        private String statisticsSource;
        private AgeRanges ageRanges;

        @Data
        public static class AgeRanges {
            @JsonProperty("6~18")
            private String range6To18;

            @JsonProperty("19~69")
            private String range19To69;

            @JsonProperty("70+")
            private String range70Plus;
        }
    }
}