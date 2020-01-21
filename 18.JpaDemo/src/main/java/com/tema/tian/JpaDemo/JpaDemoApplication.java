package com.tema.tian.JpaDemo;

import com.tema.tian.JpaDemo.model.Coffee;
import com.tema.tian.JpaDemo.model.CoffeeOrder;
import com.tema.tian.JpaDemo.repository.CoffeeOrderRepository;
import com.tema.tian.JpaDemo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@Slf4j
@EnableJpaRepositories
public class JpaDemoApplication implements ApplicationRunner {

	@Autowired
	private CoffeeOrderRepository coffeeOrderRepository;
	@Autowired
	private CoffeeRepository coffeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
        initOrders();
	}

	private void initOrders() {
		Coffee espresso = Coffee.builder().name("espresso")
				                .price(Money.of(CurrencyUnit.of("CNY"), 20))
				                .build();
		coffeeRepository.save(espresso);
		log.error("Coffee:{}", espresso);
		Coffee latte = Coffee.builder().name("latte")
				             .price(Money.of(CurrencyUnit.of("CNY"), 30))
				             .build();
		coffeeRepository.save(latte);
		log.error("Coffee: {}", latte);

		CoffeeOrder order = CoffeeOrder.builder()
				                       .customer("Tony")
				                       .items(Collections.singletonList(espresso))
				                       .state(0)
				                       .build();
		coffeeOrderRepository.save(order);
		log.error("Order:{}",order);

		order = CoffeeOrder.builder()
				           .customer("Harry")
				           .items(Arrays.asList(espresso, latte))
				           .state(0)
				           .build();

		coffeeOrderRepository.save(order);
		log.error("Order:{}", order);
	}
}
