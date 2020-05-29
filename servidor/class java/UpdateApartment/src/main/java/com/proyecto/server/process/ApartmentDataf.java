package com.proyecto.server.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.proyecto.server.Dto.Request;
import com.proyecto.server.Dto.Requestf;


public class ApartmentDataf implements Processor {

	public void process(Exchange exchange) throws Exception {
		Requestf persona= exchange.getIn().getBody(Requestf.class);
		exchange.setProperty("id",persona.getApartamento().getId());
		exchange.setProperty("correo",persona.getCorreo());
	}

}