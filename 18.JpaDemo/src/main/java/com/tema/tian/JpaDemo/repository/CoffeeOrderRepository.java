package com.tema.tian.JpaDemo.repository;

import com.tema.tian.JpaDemo.model.CoffeeOrder;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeOrderRepository extends CrudRepository<CoffeeOrder, Long> {
}
