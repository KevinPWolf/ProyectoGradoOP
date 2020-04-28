package com.proyecto.server.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.proyecto.server.Dto.Request2;
import com.proyecto.server.Dto.Request3;


public class CorreoData3 implements Processor {

	public void process(Exchange exchange) throws Exception {
		Request3 persona= exchange.getIn().getBody(Request3.class);
		exchange.setProperty("correo",persona.getEmisor());
		exchange.setProperty("id",persona.getID_inmueble());
		exchange.setProperty("contexto",persona.getContexto());
	}

}