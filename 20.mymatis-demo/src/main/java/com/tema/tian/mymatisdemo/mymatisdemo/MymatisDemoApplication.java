package com.tema.tian.mymatisdemo.mymatisdemo;

import com.tema.tian.mymatisdemo.mymatisdemo.mapper.CoffeeMapper;
import com.tema.tian.mymatisdemo.mymatisdemo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@MapperScan("com.tema.tian.mymatisdemo.mymatisdemo.mapper")
public class MymatisDemoApplication implements ApplicationRunner {

	@Autowired
	private CoffeeMapper coffeeMapper;

	public static void main(String[] args) {
		SpringApplication.run(MymatisDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Coffee c = Coffee.builder().name("espresso")
				.price(Money.of(CurrencyUnit.of("CNY"), 20.0)).build();
		int count = coffeeMapper.save(c);
		log.error("Save {} Coffee:{}", count, c);

		c = Coffee.builder().name("latte").price(Money.of(CurrencyUnit.of("CNY"), 25.0)).build();
		count = coffeeMapper.save(c);
		log.error("Save {} Coffee:{}", count, c);

		c = coffeeMapper.findById(c.getId());
		log.error("Find Coffee: {}", c);
	}
}
