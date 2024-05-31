package com.task.care.dto;

import java.util.List;

import lombok.Data;

@Data
public class CarDTO {

	private String manufacturer;
	private String modelName;
	private int productionYear;
	private Boolean available;
	private List<Long> categories;
	private Long id;
	
	public CarDTO() {}
	
	public CarDTO(String manufacturer, String modelName, int productionYear) {
		this.manufacturer = manufacturer;
		this.modelName = modelName;
		this.productionYear = productionYear;
	}
	
	public CarDTO(String manufacturer, String modelName, int productionYear, boolean available) {
		this.manufacturer = manufacturer;
		this.modelName = modelName;
		this.productionYear = productionYear;
		this.available = available;
	}
}
