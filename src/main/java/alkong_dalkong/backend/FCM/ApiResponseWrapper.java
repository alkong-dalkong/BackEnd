package alkong_dalkong.backend.FCM;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponseWrapper<T> {
    private T result;
    private String resultCode;
    private String resultMsg;
}
