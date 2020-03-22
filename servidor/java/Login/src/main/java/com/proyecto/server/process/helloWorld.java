package com.proyecto.server.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.proyecto.server.Dto.prueba;

public class helloWorld implements Processor {

	public void process(Exchange exchange) throws Exception {

		// Get input from exchange
		//String msg = exchange.getIn().getBody(String.class);
		// set output in exchange
		//System.out.println("aca aca  body "+ exchange.getIn().getBody());
		System.out.println("aca aca  body "+ exchange.getIn().getHeader("hola"));
		prueba prueba= exchange.getIn().getBody(prueba.class);
		//String msg=exchange.getIn().getHeader("hola",String.class);
		String msg=prueba.getIdApp();
		exchange.getOut().setBody("Hello World " + msg);
	}

}