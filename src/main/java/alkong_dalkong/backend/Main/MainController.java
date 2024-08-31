package alkong_dalkong.backend.Main;

import alkong_dalkong.backend.Main.MainInfo.CurrentMedicineInfo;
import alkong_dalkong.backend.Medicine.Domain.MedicineRelation;
import alkong_dalkong.backend.Medicine.Service.MedicineRecordService;
import alkong_dalkong.backend.Medicine.Service.MedicineRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final MedicineRelationService medicineRelationService;
    private final MedicineRecordService medicineRecordService;

    @GetMapping("/main/{user_id}/{local_date}")
    public ResponseEntity<?> userMain(@PathVariable("user_id") Long user_id,
                                      @PathVariable("local_date") LocalDate date){
        try{
            UserMainResponseDto data = new UserMainResponseDto();
            // 의료 정보

            // 약 정보
            // 사용자가 복용하는 모든 약
            List<MedicineRelation> medicineRelationList = medicineRelationService.FindAllUserMedicine(user_id);
            for (MedicineRelation medicineRelation : medicineRelationList){
                // date 복용하는 약인 경우
                if (medicineRecordService.FindAllDateByMedicine(medicineRelation.getId()).contains(date)){
                    CurrentMedicineInfo medicineInfo= new CurrentMedicineInfo();
                    // 약 이름
                    medicineInfo.setMedicineName(medicineRelation.getMedicine().getMedicineName());
                    // 약 복용 시간
                    medicineInfo.setTimes(medicineRelation.takenTime());
                    // 약 요일
                    medicineInfo.setWeekList(medicineRelation.possibleWeek());

                    // UserMainResponseDto List 약 정보 추가
                    data.getCurrentMedicineInfo().add(medicineInfo);
                }
            }

            return ResponseEntity.ok().body(Map.of("code", 200, "data", data));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("code", 500, "error", "예상치 못한 오류 발생"));
        }

    }
}
