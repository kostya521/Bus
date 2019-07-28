package com.goeuro.challenges.busroutesservice.services;

public interface DataMapper<T, R> {

  R mapData(T inputData);
}
