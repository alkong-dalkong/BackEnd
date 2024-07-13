package alkong_dalkong.backend;

import alkong_dalkong.backend.Domain.Medicine.*;
import alkong_dalkong.backend.Service.Medicine.MedicineUserService;
import alkong_dalkong.backend.Service.Medicine.MedicineRecordService;
import alkong_dalkong.backend.Service.Medicine.MedicineRelationService;
import alkong_dalkong.backend.Service.Medicine.MedicineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Time;

@SpringBootTest
class ProjectApplicationTests {

	@Autowired
	private MedicineService medicineservice;
	@Autowired
	private MedicineUserService medicineUserService;
	@Autowired
	private MedicineRecordService medicineRecordService;
	@Autowired
	private MedicineRelationService medicineRelationService;

	@Test
	void contextLoads() {
		Medicine M1 = new Medicine();
		M1.setMedicineName("Medicine1");
		M1.setMedicineMemo("감기약");
		Medicine M2 = new Medicine();
		M2.setMedicineName("Medicine2");
		M2.setMedicineMemo("소화제");
		Medicine M3 = new Medicine();
		M3.setMedicineName("Medicine3");
		M3.setMedicineMemo("두통약");

		medicineservice.saveMedicine(M1);
		medicineservice.saveMedicine(M2);
		medicineservice.saveMedicine(M3);

		MedicineUser MI1 = new MedicineUser();
		MedicineUser MI2 = new MedicineUser();
		MedicineUser MI3 = new MedicineUser();
		medicineUserService.saveMedicineInfo(MI1);
		medicineUserService.saveMedicineInfo(MI2);
		medicineUserService.saveMedicineInfo(MI3);

		MedicineRelation MR1 = new MedicineRelation();
		MR1.setMedicine(M2);
		MR1.setMedicineUser(MI3);
		MR1.setMedicineTimes(3);
		MR1.setDosage(1);
		MR1.setMedicineBreakfast(Time.valueOf("08:00:00"));
		MR1.setMedicineLunch(Time.valueOf("12:00:00"));
		MR1.setMedicineDinner(Time.valueOf("19:00:00"));
		medicineRelationService.saveMedicineRelation(MR1);

		MedicineRecord MRC = new MedicineRecord();
		MRC.setTakenDate(Date.valueOf("2024-07-09"));
		MRC.setMedicineDate(MR1);
		MRC.setBreakfast(MedicineTaken.NOT_TAKEN);
		MRC.setLunch(MedicineTaken.NOT_YET);
		MRC.setDinner(MedicineTaken.NOT_YET);
		medicineRecordService.saveMedicineRecord(MRC);

	}

}
