package com.proyecto.server.prueba;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class proceso implements Processor{


	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("entro aca");
	}
}
