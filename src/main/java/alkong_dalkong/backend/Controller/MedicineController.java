package alkong_dalkong.backend.Controller;

import alkong_dalkong.backend.DTO.AddNewMedicineRequest;
import alkong_dalkong.backend.DTO.AddNewMedicineResponse;
import alkong_dalkong.backend.DTO.MedicineInfoResponse;
import alkong_dalkong.backend.Domain.Medicine.Enum.MedicineAlarm;
import alkong_dalkong.backend.Domain.Medicine.Medicine;
import alkong_dalkong.backend.Domain.Medicine.MedicineRelation;
import alkong_dalkong.backend.Domain.Medicine.MedicineUser;
import alkong_dalkong.backend.Service.Medicine.MedicineRecordService;
import alkong_dalkong.backend.Service.Medicine.MedicineRelationService;
import alkong_dalkong.backend.Service.Medicine.MedicineService;
import alkong_dalkong.backend.Service.Medicine.MedicineUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MedicineController {
    private final MedicineService medicineService;
    private final MedicineRelationService medicineRelationService;
    private final MedicineUserService medicineUserService;
    private final MedicineRecordService medicineRecordService;

    @PostMapping("/medicine/{medicine_user_id}/add")
    public AddNewMedicineResponse addNewMedicine(@PathVariable("medicine_user_id") Long user_id,
                                                @RequestBody @Valid AddNewMedicineRequest request){
        // user 검색
        Optional<MedicineUser> findUser = medicineUserService.findUserById(user_id);
        MedicineUser user = findUser.orElseThrow(()->new IllegalStateException("USER ID가 존재하지 않습니다."));

        // 약 저장
        Medicine medicine = Medicine.createMedicine(request.getMedicine_name());
        medicineService.saveMedicine(medicine);

        // 약 정보 저장
        MedicineRelation medicineRelation = MedicineRelation.createMedicineRelation(user, medicine,
                request.getMedicine_times(), request.getMedicine_dosage(),
                request.getMedicine_taken_time(), request.getMedicine_memo(), request.getMedicine_alarm(), request.getMedicine_week());
        medicineRelationService.saveMedicineRelation(medicineRelation);

        // 약을 복용해야 하는 모든 날짜를 계산
        List<LocalDate> possibleList = new ArrayList<>();
        for (LocalDate date = request.getMedicine_start();
             !date.isAfter(request.getMedicine_end()); date = date.plusDays(1)) {
            if(request.getMedicine_week().contains(date.getDayOfWeek())){
                possibleList.add(date);
            }
        }

        // 약 기록 정보 저장
        medicineRelationService.createNewMedicine(medicineRelation, possibleList);

        return new AddNewMedicineResponse(medicine.getId());
    }


    @GetMapping("/mdicine/{medicine_user_id}/{medicine_id}/medicine_info")
    public MedicineInfoResponse MedicineInfo(@PathVariable("medicine_user_id") Long user_id,
                                             @PathVariable("medicine_id") Long medicine_id){
        // 사용자가 복용하는 약
        MedicineRelation medicineRelation = medicineRelationService.FindUserMedicine(user_id, medicine_id);

        // 약을 복용하는 요일 리스트
        List<DayOfWeek> weekList = medicineRelation.possibleWeek();
        // 약을 복용하는 시간 리스트
        List<LocalTime> timeList = medicineRelation.takenTime();
        // 약을 복용하는 날짜 리스트
        List<LocalDate> dateList = medicineRecordService.FindAllDateByMedicine(medicineRelation.getId());

        int agree_num;
        if(medicineRelation.getMedicineAlarm().equals(MedicineAlarm.AGREE)){
            agree_num = 1;
        }else{
            agree_num=  0;
        }
        return new MedicineInfoResponse(medicineRelation.getMedicine().getMedicineName(), weekList,
                medicineRelation.getMedicineTimes(),
                timeList, dateList, medicineRelation.getDosage(),
                medicineRelation.getMedicineMemo(), agree_num);
    }

}
