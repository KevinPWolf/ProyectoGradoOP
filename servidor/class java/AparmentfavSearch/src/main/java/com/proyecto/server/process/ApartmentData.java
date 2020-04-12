package com.proyecto.server.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.proyecto.server.Dto.Request;


public class ApartmentData implements Processor {

	public void process(Exchange exchange) throws Exception {
		Request persona= exchange.getIn().getBody(Request.class);
		exchange.setProperty("email",persona.getFavorito().getEmail());
		exchange.setProperty("inmueble",persona.getFavorito().getInmueble());

	}

}