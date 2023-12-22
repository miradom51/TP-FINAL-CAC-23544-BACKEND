package com.cac.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class GlobalObjectMapper {

	private static ObjectMapper mapper;
	private static GlobalObjectMapper instance;

	private GlobalObjectMapper() {
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

	public static synchronized GlobalObjectMapper getInstance() {
		if (instance == null) {
			System.out.println("no existe instancia");
			instance = new GlobalObjectMapper();
		}
		return instance;
	}

	public ObjectMapper getObjectMapper() {
		return mapper;
	}
}