package com.task.care;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.task.care.dto.CarDTO;
import com.task.care.model.Car;
import com.task.care.model.QCar;
import com.task.care.model.QCarCategory;
import com.task.care.repository.CarRepositoryImpl;
import com.task.care.service.CarService;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarenationApplicationTests {

	@InjectMocks
	private CarService carService;

	@Mock
	private CarRepositoryImpl carRepository;

	@Mock
    private JPAQueryFactory queryFactory;

    @Mock
    private JPAQuery<Car> jpaQuery;
    
    @Autowired
    TestEntityManager testEntityManager;
 
    EntityManager em;
    
    @BeforeEach
    void init() {
    	 em = testEntityManager.getEntityManager();
    }
    
	@Test
	@DisplayName("자동차 등록 테스트")
	public void testRegisterCar() {
		CarDTO carDTO = new CarDTO();
		carDTO.setManufacturer("현대");
		carDTO.setModelName("코나");
		carDTO.setProductionYear(2022);
		carDTO.setCategories(List.of(1L, 2L));

		Car savedCar = new Car();
		savedCar.setId(1L);
		savedCar.setManufacturer("현대");
		savedCar.setModelName("코나");
		savedCar.setProductionYear(2022);

		when(carRepository.registerCar(any())).thenReturn(savedCar);
		Car registeredCar = carRepository.registerCar(carDTO);

		assertNotNull(registeredCar);
		assertEquals(1L, registeredCar.getId());
		assertEquals("현대", registeredCar.getManufacturer());
		assertEquals("코나", registeredCar.getModelName());
		assertEquals(2022, registeredCar.getProductionYear());
		
	}

	@Test
	@DisplayName("자동차 대여 가능여부 조회 테스트")
	public void testIsCarAvailable() {
		JPAQuery<QCar> query = new JPAQuery<>(em);
		QCar qCar = QCar.car;
		boolean result = query.select(qCar.available).from(qCar).where(qCar.id.eq(1L)).fetchOne();
		assertEquals(result, true);
	}
	
	@Test
	@DisplayName("카테고리별 조회 테스트")
	public void testGetCarsByCategory() {
		QCar qCar = QCar.car;
		JPAQuery<QCar> query = new JPAQuery<>(em);
		QCarCategory qCarCategory = QCarCategory.carCategory;
		
		query.select(qCar).from(qCar).join(qCar.carCategories, qCarCategory)
				.where(qCarCategory.category.id.eq(1L)).fetch();
	}
	
	@Test
	@DisplayName("전체조회 조회 테스트")
	public void testGetCars() {
		QCar qCar = QCar.car;
		JPAQuery<QCar> query = new JPAQuery<>(em);
		
		query.select(qCar).from(qCar).fetch();
	}
	
    
    @Test
    @DisplayName("자동차 정보 수정 테스트")
	public void testUpdateCar() {
		CarDTO carDTO = new CarDTO();
		carDTO.setManufacturer("현대");
		carDTO.setModelName("코나");
		carDTO.setProductionYear(2022);
		carDTO.setCategories(List.of(1L, 2L));

		Car savedCar = new Car();
		savedCar.setId(1L);
		savedCar.setManufacturer("현대");
		savedCar.setModelName("코나");
		savedCar.setProductionYear(2022);

		when(carRepository.updateCar(any())).thenReturn(savedCar);
		Car registeredCar = carRepository.updateCar(carDTO);

		assertNotNull(registeredCar);
		assertEquals(1L, registeredCar.getId());
		assertEquals("현대", registeredCar.getManufacturer());
		assertEquals("코나", registeredCar.getModelName());
		assertEquals(2022, registeredCar.getProductionYear());
	}
}
