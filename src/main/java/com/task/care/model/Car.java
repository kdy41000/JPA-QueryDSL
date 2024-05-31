package com.task.care.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String manufacturer;
	private String modelName;
	private int productionYear;
	private Boolean available;

	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CarCategory> carCategories = new HashSet<>();

	// Constructors, Getters, and Setters

	public Car() {
	}

	public Car(String manufacturer, String modelName, int productionYear, boolean available) {
		this.manufacturer = manufacturer;
		this.modelName = modelName;
		this.productionYear = productionYear;
		this.available = available;
	}

}