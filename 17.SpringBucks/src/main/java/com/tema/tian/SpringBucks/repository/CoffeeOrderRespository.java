package com.tema.tian.SpringBucks.repository;

import com.tema.tian.SpringBucks.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRespository extends JpaRepository<CoffeeOrder, Long> {
}
