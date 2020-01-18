package com.tema.tian.DeclarativeTransaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class FooServiceImpl implements FooService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FooService fooService;

    @Override
    @Transactional(rollbackFor = RollbackException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertThenRollBack() throws RollbackException {
       jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('BBB')");
       throw new RollbackException();
    }

    @Override
    @Transactional(rollbackFor = RollbackException.class)
    public void invokeInsertThenRollback() {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('AAA')");
        try {
            fooService.insertThenRollBack();
        } catch (RollbackException e) {
            log.error("RollbackException",e);
        }
//        throw new RuntimeException();
    }
}
