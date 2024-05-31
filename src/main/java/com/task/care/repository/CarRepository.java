package com.task.care.repository;

import java.util.List;

import com.task.care.dto.CarDTO;
import com.task.care.model.Car;

public interface CarRepository {

	Car registerCar(CarDTO param);

	boolean isCarAvailable(Long id) throws Exception;

	List<Car> getCarsByCategory(Long id);

	List<CarDTO> getCars();
	
	Car updateCar(CarDTO param);
}
