package alkong_dalkong.backend.Medicine.Controller;

import alkong_dalkong.backend.Medicine.DTO.*;
import alkong_dalkong.backend.Medicine.DTO.Request.MedicineUpdateTakenRequest;
import alkong_dalkong.backend.Medicine.DTO.Response.MedicineDateResponse;
import alkong_dalkong.backend.Medicine.DTO.Response.MedicineUpdateTakenResponse;
import alkong_dalkong.backend.Medicine.Domain.MedicineRecord;
import alkong_dalkong.backend.Medicine.Domain.MedicineRelation;
import alkong_dalkong.backend.Medicine.Service.MedicineRecordService;
import alkong_dalkong.backend.Medicine.Service.MedicineRelationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class MedicineDateController {
    private final MedicineRelationService medicineRelationService;
    private final MedicineRecordService medicineRecordService;

    @GetMapping("/medicine/{medicine_user_id}/{local_date}/taken_info")
    public MedicineDateResponse MedicineDate(@PathVariable("medicine_user_id") Long user_id,
                                             @PathVariable("local_date")LocalDate date){
        MedicineDateResponse medicineDateResponse = new MedicineDateResponse();
        // 사용자가 복용하는 모든 약
        List<MedicineRelation> medicineRelationList = medicineRelationService.FindAllUserMedicine(user_id);

        for(MedicineRelation medicineRelation : medicineRelationList){
            // 무제한 기간 약의 복용 정보가 없는 경우 (1달 기록 생성)
            if(medicineRelation.getTakenEndDate().equals(LocalDate.of(9999, 12, 31))){
                medicineRelationService.addMedicineInfinite(medicineRelation, date);
            }

            // 특정 날짜에 복용하는 약
            if(medicineRecordService.FindAllDateByMedicine(medicineRelation.getId()).contains(date)){
                MedicineDateDto medicineDateDto = new MedicineDateDto();
                medicineDateDto.setMedicineId(medicineRelation.getMedicine().getId());
                medicineDateDto.setMedicineName(medicineRelation.getMedicine().getMedicineName());
                medicineDateDto.setMedicineDosage(medicineRelation.getDosage());
                medicineDateDto.setMedicineTakenType(medicineRelation.getMedicineTakenType());

                // 약 복용 정보
                List<LocalTime> timeList = medicineRelation.takenTime();
                MedicineRecord medicineRecord = medicineRecordService.dateMedicineRecord(date, medicineRelation.getId());
                medicineDateDto.setMedicineRecordId(medicineRecord.getId());

                for(int i = 0; i < timeList.size(); i++){
                    MedicineDateTakenInfo medicineDateTakenInfo = new MedicineDateTakenInfo();
                    medicineDateTakenInfo.setMedicine_id(medicineRelation.getMedicine().getId());
                    medicineDateTakenInfo.setIndex(i);
                    switch(i){
                        case 0 -> {
                            medicineDateTakenInfo.setTaken(medicineRecord.getBreakfast());
                            medicineDateResponse.addMedicineTakenInfo(medicineRelation.getMedicineBreakfast(), medicineDateTakenInfo);
                        }
                        case 1 -> {
                            medicineDateTakenInfo.setTaken(medicineRecord.getLunch());
                            medicineDateResponse.addMedicineTakenInfo(medicineRelation.getMedicineLunch(), medicineDateTakenInfo);
                        }
                        case 2 -> {
                            medicineDateTakenInfo.setTaken(medicineRecord.getDinner());
                            medicineDateResponse.addMedicineTakenInfo(medicineRelation.getMedicineDinner(), medicineDateTakenInfo);
                        }
                    }
                }
                medicineDateResponse.getMedicineDateDtoList().add(medicineDateDto);
            }
        }
        return medicineDateResponse;
    }

    @PatchMapping("/medicine/{medicine_record_id}/taken")
    public MedicineUpdateTakenResponse MedicineUpdateTaken(@PathVariable("medicine_record_id") Long medicine_record_id,
                                                           @RequestBody @Valid MedicineUpdateTakenRequest request){
        medicineRecordService.updateMedicineTaken(medicine_record_id, request.getTimeNum(), request.getTakenNum());

        return new MedicineUpdateTakenResponse(medicine_record_id);
    }
}