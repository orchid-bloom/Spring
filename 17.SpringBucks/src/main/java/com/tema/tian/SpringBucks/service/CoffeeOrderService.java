package com.tema.tian.SpringBucks.service;

import com.tema.tian.SpringBucks.model.Coffee;
import com.tema.tian.SpringBucks.model.CoffeeOrder;
import com.tema.tian.SpringBucks.model.OrderState;
import com.tema.tian.SpringBucks.repository.CoffeeOrderRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@Service
@Transactional
public class CoffeeOrderService {

    @Autowired
    private CoffeeOrderRespository coffeeOrderRespository;

    public CoffeeOrder createOrder(String customer, Coffee...coffees) {
        CoffeeOrder order = CoffeeOrder.builder()
                .customer(customer)
                .items(new ArrayList<>(Arrays.asList(coffees)))
                .state(OrderState.INIT)
                .build();
        CoffeeOrder saved = coffeeOrderRespository.save(order);
        log.info("New order:{}",saved);
        return saved;
    }

    public boolean updateState(CoffeeOrder order, OrderState state) {
        if (state.compareTo(order.getState()) <= 0) {
            log.warn("Wrong state order :{},{}",state, order.getState());
            return false;
        }
        order.setState(state);
        coffeeOrderRespository.save(order);
        log.info("Update order: {}", order);
        return true;
    }
}
