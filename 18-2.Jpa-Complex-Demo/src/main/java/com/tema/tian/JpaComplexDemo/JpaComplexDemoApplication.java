package com.tema.tian.JpaComplexDemo;

import com.tema.tian.JpaComplexDemo.model.Coffee;
import com.tema.tian.JpaComplexDemo.model.CoffeeOrder;
import com.tema.tian.JpaComplexDemo.model.OrderState;
import com.tema.tian.JpaComplexDemo.responsitory.CoffeeOrderRepository;
import com.tema.tian.JpaComplexDemo.responsitory.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
@EnableTransactionManagement
@EnableJpaRepositories
public class JpaComplexDemoApplication implements ApplicationRunner {

	@Autowired
	private CoffeeRepository coffeeRepository;

	@Autowired
	private CoffeeOrderRepository coffeeOrderRepository;

	public static void main(String[] args) {
		SpringApplication.run(JpaComplexDemoApplication.class, args);
	}

	@Transactional
	@Override
	public void run(ApplicationArguments args) throws Exception {
        initOrders();
        findOrders();
	}

	private void initOrders() {
		Coffee latte = Coffee.builder().name("latte").price(Money.of(CurrencyUnit.of("CNY"), 390)).build();
		coffeeRepository.save(latte);
		log.warn("Coffee:{}", latte);

		Coffee espresso = Coffee.builder().name("esptpresso").price(Money.of(CurrencyUnit.of("CNY"), 30)).build();
		coffeeRepository.save(espresso);
		log.warn("Coffee: {}", espresso);

		CoffeeOrder order = CoffeeOrder.builder().customer("Tany").items(Collections.singletonList(latte)).state(OrderState.INIT).build();
		coffeeOrderRepository.save(order);
		log.warn("Order:{}", order);

		order = CoffeeOrder.builder().customer("Harry").items(Arrays.asList(espresso, latte)).state(OrderState.INIT).build();
		coffeeOrderRepository.save(order);
		log.warn("Order:{}", order);
	}

	private void  findOrders() {
        coffeeRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).forEach(c -> log.error("Loading {}", c));

		List<CoffeeOrder> list = coffeeOrderRepository.findTop3ByOrderByUpdateTimeDescIdAsc();
		log.error("findTop3ByOrderByUpdateTimeDescIdAsc:{}", getJoinedOrderId(list));

		list = coffeeOrderRepository.findByCustomerOrderById("Tany");
		log.error("findByCustomerOrderById:{}", getJoinedOrderId(list));

		list.forEach( o -> {
			log.error("Order:{}", o.getId());
			o.getItems().forEach(
					i -> log.error("Item {}", i)
			);
		});

		list = coffeeOrderRepository.findByItems_Name("latte");
		log.error("findByItems_Name:{}",getJoinedOrderId(list));
	}

	private String getJoinedOrderId(List<CoffeeOrder> list) {
		return list.stream().map(o -> o.getId().toString()).collect(Collectors.joining(","));
	}
}
