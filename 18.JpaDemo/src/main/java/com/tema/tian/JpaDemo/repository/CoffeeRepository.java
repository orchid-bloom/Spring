package com.tema.tian.JpaDemo.repository;

import com.tema.tian.JpaDemo.model.Coffee;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeRepository extends CrudRepository<Coffee, Long> {

}
