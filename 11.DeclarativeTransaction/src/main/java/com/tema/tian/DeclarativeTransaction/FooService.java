package com.tema.tian.DeclarativeTransaction;

public interface FooService {
    void insertRecord();
    void insertThenRollBack() throws RollbackException;
    void invokeInsertThenRollback() throws RollbackException;
}
