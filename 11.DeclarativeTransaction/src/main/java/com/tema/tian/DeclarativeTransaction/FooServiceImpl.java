package com.tema.tian.DeclarativeTransaction;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FooServiceImpl implements FooService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void insertRecord() {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('AAA')");
    }

    @Override
    @Transactional(rollbackFor = RollbackException.class)
    public void insertThenRollBack() throws RollbackException {
       jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('BBB')");
       throw new RollbackException();
    }

    //隐式事务 直接commit 没有Rollback
    @Override
//    @Transactional(rollbackFor = RollbackException.class)
    public void invokeInsertThenRollback() throws RollbackException {
       insertThenRollBack();
//        ((FooService) (AopContext.currentProxy())).insertThenRollBack();
    }
}
