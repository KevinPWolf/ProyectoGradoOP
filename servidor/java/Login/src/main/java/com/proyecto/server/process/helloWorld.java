package com.proyecto.server.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.proyecto.server.Dto.prueba;

public class helloWorld implements Processor {

	public void process(Exchange exchange) throws Exception {


		prueba prueba= exchange.getIn().getBody(prueba.class);
		String msg=prueba.getIdApp();
		exchange.getOut().setBody("Hello World " + msg);
	}

}