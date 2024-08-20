package alkong_dalkong.backend.Controller;

import alkong_dalkong.backend.DTO.MedicineDateResponse;
import alkong_dalkong.backend.DTO.MedicineUpdateTakenRequest;
import alkong_dalkong.backend.DTO.MedicineUpdateTakenResponse;
import alkong_dalkong.backend.Domain.Medicine.MedicineRecord;
import alkong_dalkong.backend.Domain.Medicine.MedicineRelation;
import alkong_dalkong.backend.Service.Medicine.MedicineRecordService;
import alkong_dalkong.backend.Service.Medicine.MedicineRelationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MedicineDateController {
    private final MedicineRelationService medicineRelationService;
    private final MedicineRecordService medicineRecordService;

    @GetMapping("/medicine/{medicine_user_id}/{local_date}/taken_info")
    public List<MedicineDateResponse> MedicineDate(@PathVariable("medicine_user_id") Long user_id,
                                                   @PathVariable("local_date")LocalDate date){
        List<MedicineDateResponse> resultList = new ArrayList<>();
        // 사용자가 복용하는 모든 약
        List<MedicineRelation> medicineRelationList = medicineRelationService.FindAllUserMedicine(user_id);

        for(MedicineRelation medicineRelation : medicineRelationList){
            // 특정 날짜에 복용하는 약
            if(medicineRecordService.FindAllDateByMedicine(medicineRelation.getId()).contains(date)){
                MedicineDateResponse medicineDateResponse = new MedicineDateResponse();
                medicineDateResponse.setMedicine_id(medicineRelation.getMedicine().getId());
                medicineDateResponse.setMedicine_name(medicineRelation.getMedicine().getMedicineName());
                medicineDateResponse.setMedicine_dosage(medicineRelation.getDosage());

                // 약 복용 정보
                List<LocalTime> timeList = medicineRelation.takenTime();
                MedicineRecord medicineRecord = medicineRecordService.dateMedicineRecord(date, medicineRelation.getId());
                medicineDateResponse.setMedicine_record_id(medicineRecord.getId());

                for(int i = 0; i < timeList.size(); i++){
                    if(i == 0){
                        medicineDateResponse.getMedicine_taken_info().put(timeList.get(i), medicineRecord.getBreakfast());
                    }
                    if(i == 1){
                        medicineDateResponse.getMedicine_taken_info().put(timeList.get(i), medicineRecord.getLunch());
                    }
                    if(i == 2){
                        medicineDateResponse.getMedicine_taken_info().put(timeList.get(i), medicineRecord.getDinner());
                    }
                }

                resultList.add(medicineDateResponse);
            }
        }
        return resultList;
    }

    @PatchMapping("/medicine/{medicine_record_id}/taken")
    public MedicineUpdateTakenResponse MedicineUpdateTaken(@PathVariable("medicine_record_id") Long medicine_record_id,
                                                           @RequestBody @Valid MedicineUpdateTakenRequest request){
        medicineRecordService.updateMedicineTaken(medicine_record_id, request.getTimeNum(), request.getTakenNum());

        return new MedicineUpdateTakenResponse();
    }
}


