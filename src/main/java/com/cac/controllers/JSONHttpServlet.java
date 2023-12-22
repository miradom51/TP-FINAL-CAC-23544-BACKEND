package com.cac.controllers;


import java.util.stream.Collectors;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class JSONHttpServlet extends HttpServlet {

	private static final long serialVersionUID = 8081441726189855730L;
	
	
	public void toJSON(Object obj, HttpServletResponse response) {
		
		try {
			response.getWriter().print(GlobalObjectMapper.getInstance().getObjectMapper().writeValueAsString(obj));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Object fromJSON(Class<?> clazz, HttpServletRequest request,HttpServletResponse response) {
		
		try {
			String jsonBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			return GlobalObjectMapper.getInstance().getObjectMapper().readValue(jsonBody, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}