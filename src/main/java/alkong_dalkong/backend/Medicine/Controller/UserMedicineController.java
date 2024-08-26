package alkong_dalkong.backend.Medicine.Controller;

import alkong_dalkong.backend.Medicine.DTO.Response.UserTotalMedicineResponse;
import alkong_dalkong.backend.Medicine.Domain.MedicineRelation;
import alkong_dalkong.backend.Medicine.Service.MedicineRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserMedicineController {
    private final MedicineRelationService medicineRelationService;

    @GetMapping("/medicine/{medicine_user_id}/total_medicine_info")
    public List<UserTotalMedicineResponse> UserTotalMedicine(@PathVariable("medicine_user_id") Long user_id){
        List<MedicineRelation> medicineRelationList = medicineRelationService.FindAllUserMedicine(user_id);

        // UserTotalMedicineResponse 생성
        List<UserTotalMedicineResponse> resultList = new ArrayList<>();
        for(MedicineRelation medicineRelation : medicineRelationList){
            // 약을 복용하는 요일 리스트
            List<DayOfWeek> weekList = medicineRelation.possibleWeek();
            // 약을 복용하는 시간 리스트
            List<LocalTime> timeList = medicineRelation.takenTime();

            UserTotalMedicineResponse response =
                    new UserTotalMedicineResponse(medicineRelation.getMedicine().getId(),
                            medicineRelation.getMedicine().getMedicineName(), weekList, timeList,
                            medicineRelation.getDosage(), medicineRelation.getMedicineTakenType()
                            ,medicineRelation.getMedicineMemo());
            resultList.add(response);
        }

        return resultList;
    }


    @DeleteMapping("/medicine/{medicine_user_id}/{medicine_id}/delete")
    public int MedicineDelete(@PathVariable("medicine_user_id") Long user_id,
                              @PathVariable("medicine_id") Long medicine_id){

        medicineRelationService.removeMedicineRelation(user_id, medicine_id);
        return 0;
    }
}
