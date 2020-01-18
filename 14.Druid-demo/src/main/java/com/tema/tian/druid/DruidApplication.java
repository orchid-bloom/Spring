package com.tema.tian.druid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j
@EnableTransactionManagement(proxyTargetClass = true)
public class DruidApplication implements CommandLineRunner {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private FooService fooService;

	public static void main(String[] args) {
		SpringApplication.run(DruidApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
       log.info(dataSource.toString());

       new Thread(()->fooService.selectedForUpdate()).start();
       new Thread(()->fooService.selectedForUpdate()).start();
	}
}
