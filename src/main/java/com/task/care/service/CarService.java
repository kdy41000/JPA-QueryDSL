package com.task.care.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.care.dto.CarDTO;
import com.task.care.model.Car;
import com.task.care.repository.CarRepository;

@Service
public class CarService {

	@Autowired
	CarRepository repository;
	
	public Car registerCar(CarDTO param) {
		return repository.registerCar(param);
	}
	
	public boolean isCarAvailable(Long id) throws Exception {
		return repository.isCarAvailable(id);
	}
	
	public List<Car> getCarsByCategory(Long id) {
        return repository.getCarsByCategory(id);
    }

    public List<CarDTO> getCars() {
        return repository.getCars();
    }
    
    public Car updateCar(CarDTO param) {
    	return repository.updateCar(param);
    }

    
}
