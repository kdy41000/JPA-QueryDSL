package com.task.care.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.care.dto.CarDTO;
import com.task.care.dto.ResponseDTO;
import com.task.care.model.Car;
import com.task.care.service.CarService;

import lombok.extern.slf4j.Slf4j;

/**
 * 자동차 관련 Controller
 * @author Dong Young Kim
 */
@Slf4j
@RestController
@RequestMapping("/api/cars")
public class CarController {

	@Autowired
	private CarService service;

	/**
	 * 자동차 등록 API 
	 * @param CarDTO 
	 * @return ResponseDTO<Car>
	 */
	@PostMapping("/register")
	public ResponseEntity<ResponseDTO<Car>> registerCar(@RequestBody CarDTO param) {
		Car car;
		try {
			car = service.registerCar(param);
			return ResponseEntity.ok(ResponseDTO.success(car));
		} catch (Exception e) {
			log.error("[ERROR] :: registerCar {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ResponseDTO.failure("Failed to registerCar: " + e.getMessage()));
		}
	}

	/**
	 * 자동차 대여 가능 여부 조회 API 
	 * @param id 
	 * @return ResponseDTO<Boolean>
	 */
	@GetMapping("/available")
	public ResponseEntity<ResponseDTO<Boolean>> isCarAvailable(@RequestParam("id") Long id) {
		System.out.println("id:" + id);
		try {
			boolean available = service.isCarAvailable(id);
			return ResponseEntity.ok(new ResponseDTO<>(true, "fetched success", available));
		} catch (Exception e) {
			log.error("[ERROR] :: isCarAvailable {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDTO<>(false, "Failed to isCarAvailable: " + e.getMessage(), null));
		}
	}

	/**
	 * 자동차 카테고리 별 조회 API 
	 * @param id 
	 * @return ResponseDTO<List<Car>>
	 */
	@GetMapping("/category")
	public ResponseEntity<ResponseDTO<List<Car>>> getCarsByCategory(@RequestParam("id") Long id) {
		try {
			List<Car> cars = service.getCarsByCategory(id);
			return ResponseEntity.ok(new ResponseDTO<>(true, "Cars fetched successfully", cars));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDTO<>(false, "Failed to getCarsByCategory: " + e.getMessage(), null));
		}
	}

	/**
	 * 자동차 제조사, 모델명, 생산년도 통합 조회 API 
	 * @return ResponseDTO<List<CarDTO>>
	 */
	@GetMapping("/search")
	public ResponseEntity<ResponseDTO<List<CarDTO>>> getCars() {
		try {
			List<CarDTO> cars = service.getCars();
			return ResponseEntity.ok(new ResponseDTO<>(true, "Cars fetched successfully", cars));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDTO<>(false, "Failed to getCars: " + e.getMessage(), null));
		}
	}

	/**
	 * 자동차 수정 API
	 * @param CarDTO
	 * @return ResponseDTO<Car>
	 */
	@PostMapping("/update")
	public ResponseEntity<ResponseDTO<Car>> updateCar(@RequestBody CarDTO param) {
		Car car;
		try {
			car = service.updateCar(param);
			return ResponseEntity.ok(ResponseDTO.success(car));
		} catch (Exception e) {
			log.error("[ERROR] :: updateCar {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ResponseDTO.failure("Failed to updateCar: " + e.getMessage()));
		}
	}
}
