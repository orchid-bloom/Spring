package com.tema.tian.SpringBucks;

import com.tema.tian.SpringBucks.model.Coffee;
import com.tema.tian.SpringBucks.model.CoffeeOrder;
import com.tema.tian.SpringBucks.model.OrderState;
import com.tema.tian.SpringBucks.repository.CoffeeOrderRespository;
import com.tema.tian.SpringBucks.repository.CoffeeRepository;
import com.tema.tian.SpringBucks.service.CoffeeOrderService;
import com.tema.tian.SpringBucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@SpringBootApplication
@EnableTransactionManagement
@EnableJdbcRepositories
@Slf4j
public class SpringBucksApplication implements CommandLineRunner {

	@Autowired
	private CoffeeRepository coffeeRepository;
	@Autowired
	CoffeeService coffeeService;
	@Autowired
	CoffeeOrderService orderService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBucksApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("All Coffee: {}", coffeeRepository.findAll());

		Optional<Coffee> latte = coffeeService.finOneCoffee("Latte");
		if (latte.isPresent()) {
			CoffeeOrder order = orderService.createOrder("Li Lei", latte.get());
			log.info("Update INIT to PAID: {}", orderService.updateState(order, OrderState.PAID));
			log.info("Update PAID to INIT: {}", orderService.updateState(order,OrderState.INIT));
		}
	}
}
