package com.nilesh.jawarkar.learn.javaee8.entity;

public enum EngineType {
	PETROL, DIESEL, ELECTRIC, UNKNOWN
}

//-- UNKNOWN - Added for demo purpose - Domo of exception mapper.
//-- If we get this as engine type CarManufacturer call will throw InvalidEngineException.
//-- Now to manage this exception in rest-webservice, we will implement Exception mapper.