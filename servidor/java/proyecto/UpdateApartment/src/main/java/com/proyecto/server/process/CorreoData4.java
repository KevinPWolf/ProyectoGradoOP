package com.proyecto.server.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.proyecto.server.Dto.Request2;
import com.proyecto.server.Dto.Request3;
import com.proyecto.server.Dto.Request4;


public class CorreoData4 implements Processor {

	public void process(Exchange exchange) throws Exception {
		Request4 persona= exchange.getIn().getBody(Request4.class);
		exchange.setProperty("puntuacion",persona.getPuntuacion());
		exchange.setProperty("correo",persona.getCorreo());
		exchange.setProperty("id_inmueble",persona.getId_inmueble());
	}

}