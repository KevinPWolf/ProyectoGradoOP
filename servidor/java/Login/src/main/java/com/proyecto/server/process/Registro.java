package com.proyecto.server.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.proyecto.server.Dto.Persona;
import com.proyecto.server.Dto.Request;

public class Registro implements Processor {

	public void process(Exchange exchange) throws Exception {

		Request req=exchange.getIn().getBody(Request.class);
		
		//Persona persona= exchange.getIn().getBody(Persona.class);
		//String msg=persona.getIdApp();
		exchange.setProperty("email",req.getPerson().getEmail());
		exchange.setProperty("password",req.getPerson().getPassword());
		exchange.setProperty("name",req.getPerson().getName());
		exchange.setProperty("phone",req.getPerson().getPhone());
		//exchange.getOut().setBody(persona);
	}

}