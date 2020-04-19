package com.proyecto.server.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.proyecto.server.Dto.Request2;


public class CorreoData implements Processor {

	public void process(Exchange exchange) throws Exception {
		Request2 persona= exchange.getIn().getBody(Request2.class);
		exchange.setProperty("correo",persona.getCorreo());
	}

}