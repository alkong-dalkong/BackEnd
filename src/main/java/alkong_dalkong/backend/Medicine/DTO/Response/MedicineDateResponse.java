package alkong_dalkong.backend.Medicine.DTO.Response;

import alkong_dalkong.backend.Medicine.DTO.MedicineDateDto;
import alkong_dalkong.backend.Medicine.DTO.MedicineDateTakenInfo;
import lombok.Data;

import java.time.LocalTime;
import java.util.*;

@Data
public class MedicineDateResponse {
    private List<MedicineDateDto> medicineDateDtoList = new ArrayList<>();
    private Map<LocalTime, List<MedicineDateTakenInfo>> medicineTakenInfo = new TreeMap<>();

    public void addMedicineTakenInfo(LocalTime time, MedicineDateTakenInfo medicineDateTakenInfo) {
        // time 키가 존재하지 않으면 새 리스트 생성
        medicineTakenInfo.computeIfAbsent(time, k -> new ArrayList<>()).add(medicineDateTakenInfo);
    }
}
