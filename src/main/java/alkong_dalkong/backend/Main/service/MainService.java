package alkong_dalkong.backend.Main.service;

import alkong_dalkong.backend.Main.dto.response.MainResponseDto;
import alkong_dalkong.backend.Medical.entity.MedicalInfo;
import alkong_dalkong.backend.Medical.repository.MedicalInfoRepository;
import alkong_dalkong.backend.Medicine.Domain.MedicineRelation;
import alkong_dalkong.backend.Medicine.Service.MedicineRecordService;
import alkong_dalkong.backend.Medicine.Service.MedicineRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MainService {

    @Autowired
    private MedicalInfoRepository medicalInfoRepository;

    @Autowired
    private MedicineRelationService medicineRelationService;

    @Autowired
    private MedicineRecordService medicineRecordService;

    public MainResponseDto getMainInfo(Long userId, LocalDateTime localDate) {
        /////////////
        /* 진료 정보 */
        /////////////

        // 가장 최근 지난 병원 내원 정보
        Optional<MedicalInfo> recentMedicalInfoOpt = medicalInfoRepository.findTopByUserUserIdAndHospitalDateLessThanEqualOrderByHospitalDateDesc(userId, localDate);
        MainResponseDto.RecentMedicalInfo recentMedicalInfo = null;

        if (recentMedicalInfoOpt.isPresent()) {
            MedicalInfo recentMedicalInfoEntity = recentMedicalInfoOpt.get();
            recentMedicalInfo = new MainResponseDto.RecentMedicalInfo(
                    recentMedicalInfoEntity.getHospitalName(),
                    recentMedicalInfoEntity.getHospitalDate()
            );
        }

        // 가장 가까운 병원 내원 일정
        Optional<MedicalInfo> upcomingMedicalInfoOpt = medicalInfoRepository.findTopByUserUserIdAndHospitalDateGreaterThanEqualOrderByHospitalDateAsc(userId, localDate);
        MainResponseDto.UpcomingMedicalInfo upcomingMedicalInfo = null;

        if (upcomingMedicalInfoOpt.isPresent()) {
            MedicalInfo upcomingMedicalInfoEntity = upcomingMedicalInfoOpt.get();
            upcomingMedicalInfo = new MainResponseDto.UpcomingMedicalInfo(
                    upcomingMedicalInfoEntity.getHospitalName(),
                    upcomingMedicalInfoEntity.getHospitalDate(),
                    upcomingMedicalInfoEntity.getMedicalPart()
            );
        }

        /* 약 정보 */
        List<MainResponseDto.CurrentMedicineInfo> currentMedicineInfoList = getCurrentMedicineInfoList(userId, localDate);

        return new MainResponseDto(upcomingMedicalInfo, recentMedicalInfo, currentMedicineInfoList);
    }

    ////////////////
    /* 약 정보 조회 */
    ////////////////
    private List<MainResponseDto.CurrentMedicineInfo> getCurrentMedicineInfoList(Long userId, LocalDateTime localDate) {
        List<MainResponseDto.CurrentMedicineInfo> currentMedicineInfoList = new ArrayList<>();

        // 사용자가 복용하는 모든 약 정보 가져오기
        List<MedicineRelation> medicineRelationList = medicineRelationService.FindAllUserMedicine(userId);
        for (MedicineRelation medicineRelation : medicineRelationList) {
            // 무제한 기간 약의 복용 정보가 없는 경우 (1달 기록 생성)
            LocalDate date = localDate.toLocalDate();
            if (medicineRelation.getTakenEndDate().equals(LocalDate.of(9999, 12, 31))) {
                medicineRelationService.addMedicineInfinite(medicineRelation, date);
            }

            // 현재 날짜에 복용하는 약인지 확인
            if (medicineRecordService.FindAllDateByMedicine(medicineRelation.getId()).contains(localDate.toLocalDate())) {
                MainResponseDto.CurrentMedicineInfo medicineInfo = new MainResponseDto.CurrentMedicineInfo(
                        medicineRelation.getMedicine().getMedicineName(),
                        medicineRelation.takenTime(),
                        medicineRelation.possibleWeek()
                );
                // currentMedicineInfo 리스트에 추가
                currentMedicineInfoList.add(medicineInfo);
            }
        }

        return currentMedicineInfoList;
    }
}