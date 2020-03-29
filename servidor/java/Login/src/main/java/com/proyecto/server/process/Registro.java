package com.proyecto.server.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.proyecto.server.Dto.Request;


public class Registro implements Processor {

	public void process(Exchange exchange) throws Exception {
	
		Request persona= exchange.getIn().getBody(Request.class);
		
		
		exchange.setProperty("email",persona.getPersona().getEmail());
		exchange.setProperty("password",persona.getPersona().getPassword());
		exchange.setProperty("name",persona.getPersona().getName());
		exchange.setProperty("phone",persona.getPersona().getPhone());

	}

}