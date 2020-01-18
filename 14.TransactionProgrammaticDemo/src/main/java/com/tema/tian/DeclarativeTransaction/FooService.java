package com.tema.tian.DeclarativeTransaction;

public interface FooService {
    void insertThenRollBack() throws RollbackException;
    void invokeInsertThenRollback();
}
