package com.plaxa.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
