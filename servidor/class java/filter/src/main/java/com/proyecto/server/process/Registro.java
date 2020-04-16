package com.proyecto.server.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.proyecto.server.Dto.Request;


public class Registro implements Processor {

	public void process(Exchange exchange) throws Exception {
	
		Request filtro= exchange.getIn().getBody(Request.class);
		
		
		exchange.setProperty("tipo",filtro.getFiltro().getTipo());
		exchange.setProperty("precio",filtro.getFiltro().getPrecio());
		exchange.setProperty("precio_mayor",filtro.getFiltro().getPrecio_mayor());
		exchange.setProperty("estado",filtro.getFiltro().getEstado());
		exchange.setProperty("estado_inmueble",filtro.getFiltro().getEstado_inmueble());
		exchange.setProperty("barrio",filtro.getFiltro().getBarrio());
		exchange.setProperty("localidad",filtro.getFiltro().getLocalidad());
		exchange.setProperty("numero",filtro.getFiltro().getNumero());
		
	}

}