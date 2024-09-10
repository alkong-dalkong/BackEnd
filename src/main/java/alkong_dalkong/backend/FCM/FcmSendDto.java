package alkong_dalkong.backend.FCM;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 모바일에서 전달 받는 객체
public class FcmSendDto {
    private String token;

    private String title;

    private String body;

    @Builder(toBuilder = true)
    public FcmSendDto(String token, String title, String body) {
        this.token = token;
        this.title = title;
        this.body = body;
    }
}
