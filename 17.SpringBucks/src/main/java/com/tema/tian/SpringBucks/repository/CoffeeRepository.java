package com.tema.tian.SpringBucks.repository;

import com.tema.tian.SpringBucks.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
