package com.rrtx.simulationclient.service;

public interface IHttpClient<T, Y, U, I> {
    T get(Y y, U u, I i);
    T post(Y y,U u, I i);
}