package com.task.care.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.task.care.dto.CarDTO;
import com.task.care.model.Car;
import com.task.care.model.CarCategory;
import com.task.care.model.Category;
import com.task.care.model.QCar;
import com.task.care.model.QCarCategory;
import com.task.care.model.QCategory;
import com.querydsl.core.types.Projections;

import jakarta.persistence.EntityManager;

@Repository
public class CarRepositoryImpl implements CarRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Autowired
	private final EntityManager entityManager;

	public CarRepositoryImpl(JPAQueryFactory jpaQueryFactory, EntityManager entityManager) {
		this.jpaQueryFactory = jpaQueryFactory;
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public Car registerCar(CarDTO param) {
		Car car = new Car(param.getManufacturer(), param.getModelName(), param.getProductionYear(), true);
		entityManager.persist(car);

		QCategory qCategory = QCategory.category;
		List<Category> categories = jpaQueryFactory.selectFrom(qCategory).where(qCategory.id.in(param.getCategories()))
				.fetch();

		for (Category category : categories) {
			CarCategory carCategory = new CarCategory(car, category);
			entityManager.persist(carCategory);
		}
		return car;
	}

	@Override
	public boolean isCarAvailable(Long id) throws Exception {
		QCar qCar = QCar.car;
		Boolean available = jpaQueryFactory.select(qCar.available).from(qCar).where(qCar.id.eq(id)).fetchOne();

		if (available == null) {
			throw new Exception("Car not found");
		}
		return available;
	}

	@Override
	public List<Car> getCarsByCategory(Long id) {
		QCar qCar = QCar.car;
		QCarCategory qCarCategory = QCarCategory.carCategory;
		return jpaQueryFactory.selectFrom(qCar).join(qCar.carCategories, qCarCategory)
				.where(qCarCategory.category.id.eq(id)).fetch();
	}

	@Override
	public List<CarDTO> getCars() {
		QCar qCar = QCar.car;
		return jpaQueryFactory
				.select(Projections.constructor(CarDTO.class, qCar.manufacturer, qCar.modelName, qCar.productionYear))
				.from(qCar).fetch();
	}

	@Override
	@Transactional
	public Car updateCar(CarDTO param) {
		QCarCategory qCarCategory = QCarCategory.carCategory;
		QCar qCar = QCar.car;
		QCategory qCategory = QCategory.category;
		
		Car car = jpaQueryFactory.selectFrom(qCar)
				        .where(qCar.id.eq(param.getId()))
				        .fetchOne();
		
		car.setManufacturer(param.getManufacturer());
		car.setModelName(param.getModelName());
		car.setProductionYear(param.getProductionYear());
		car.setAvailable(param.getAvailable());
		
		entityManager.persist(car);

		jpaQueryFactory.delete(qCarCategory)
				.where(qCarCategory.car.eq(JPAExpressions.selectFrom(qCar).where(qCar.id.eq(param.getId())))).execute();

		List<Category> categories = jpaQueryFactory.selectFrom(qCategory).where(qCategory.id.in(param.getCategories()))
				.fetch();
		
		for (Category category : categories) {
			CarCategory carCategory = new CarCategory(car, category);
			entityManager.persist(carCategory);
		}
		return car;
	}

}