package com.laurynas.kacinauskas.revolut.dao;

interface Dao<T, ID> {

    T getById(ID id);

}
