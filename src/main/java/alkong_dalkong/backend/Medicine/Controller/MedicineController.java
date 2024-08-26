package alkong_dalkong.backend.Medicine.Controller;

import alkong_dalkong.backend.Medicine.DTO.Request.AddNewMedicineRequest;
import alkong_dalkong.backend.Medicine.DTO.Response.AddNewMedicineResponse;
import alkong_dalkong.backend.Medicine.DTO.Request.MedicineEditRequest;
import alkong_dalkong.backend.Medicine.DTO.Response.MedicineInfoResponse;
import alkong_dalkong.backend.Medicine.Domain.Medicine;
import alkong_dalkong.backend.Medicine.Domain.MedicineRelation;
import alkong_dalkong.backend.Medicine.Domain.MedicineUser;
import alkong_dalkong.backend.Medicine.Service.MedicineRecordService;
import alkong_dalkong.backend.Medicine.Service.MedicineRelationService;
import alkong_dalkong.backend.Medicine.Service.MedicineService;
import alkong_dalkong.backend.Medicine.Service.MedicineUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
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
        Medicine medicine = Medicine.createMedicine(request.getMedicineName());
        medicineService.saveMedicine(medicine);

        // 약 정보 저장
        MedicineRelation medicineRelation = MedicineRelation.createMedicineRelation(user, medicine,
                request.getMedicineTimes(), request.getMedicineDosage(), request.getMedicineTakenType(),
                request.getMedicineTakenTimeList(), request.getMedicineEnd(),
                request.getMedicineMemo(), request.getMedicineAlarm(), request.getMedicineWeek());
        medicineRelationService.saveMedicineRelation(medicineRelation);

        // 복용 하는 모든 리스트
        List<LocalDate> possibleList =
                medicineRelationService.countAllDates(request.getMedicineStart(), request.getMedicineEnd(), request.getMedicineWeek());

        // 약 기록 정보 저장
        medicineRelationService.createNewMedicine(medicineRelation, possibleList);

        return new AddNewMedicineResponse(medicine.getId());
    }

    @GetMapping("/medicine/{medicine_user_id}/{medicine_id}/medicine_info")
    public MedicineInfoResponse MedicineInfo(@PathVariable("medicine_user_id") Long user_id,
                                             @PathVariable("medicine_id") Long medicine_id){
        // 사용자가 복용하는 약
        MedicineRelation medicineRelation = medicineRelationService.FindUserMedicine(user_id, medicine_id);

        // 약을 복용하는 요일 리스트
        List<DayOfWeek> weekList = medicineRelation.possibleWeek();
        // 약을 복용하는 시간 리스트
        List<LocalTime> timeList = medicineRelation.takenTime();

        return new MedicineInfoResponse(medicineRelation.getMedicine().getMedicineName(), weekList,
                medicineRelation.getMedicineTimes(),
                timeList, medicineRelation.getTakenEndDate(), medicineRelation.getDosage(),
                medicineRelation.getMedicineTakenType(),
                medicineRelation.getMedicineMemo(), medicineRelation.getMedicineAlarm());
    }

    @PutMapping("/medicine/{medicine_user_id}/{medicine_id}/medicine_info/edit")
    public Long MedicineEdit(@PathVariable("medicine_user_id") Long user_id,
                                             @PathVariable("medicine_id") Long medicine_id,
                                             @RequestBody @Valid MedicineEditRequest request){
        MedicineRelation medicineRelation = medicineRelationService.FindUserMedicine(user_id, medicine_id);
        // 기존 약 기록 전부 삭제
        medicineRecordService.removeMedicineRecord(medicineRelation);

        medicineRelation.changeMedicineRelation(request.getMedicineTimes(), request.getMedicineDosage(), request.getMedicineTakenType(),
                request.getMedicineTakenTimeList(), request.getMedicineEnd(), request.getMedicineMemo(), request.getMedicineAlarm(), request.getMedicineWeek());

        // 복용 하는 모든 리스트
        List<LocalDate> possibleList =
                medicineRelationService.countAllDates(request.getMedicineStart(), request.getMedicineEnd(), request.getMedicineWeek());

        // 약 기록 정보 저장
        medicineRelationService.createNewMedicine(medicineRelation, possibleList);

        return medicineRelation.getId();
    }
}
