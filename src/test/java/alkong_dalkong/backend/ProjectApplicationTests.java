package alkong_dalkong.backend;

import alkong_dalkong.backend.Domain.Medicine.*;
import alkong_dalkong.backend.Domain.Medicine.Enum.MedicineTaken;
import alkong_dalkong.backend.Service.Medicine.MedicineUserService;
import alkong_dalkong.backend.Service.Medicine.MedicineRecordService;
import alkong_dalkong.backend.Service.Medicine.MedicineRelationService;
import alkong_dalkong.backend.Service.Medicine.MedicineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

	}

}
