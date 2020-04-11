package com.proyecto.server.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.proyecto.server.Dto.Request;


public class Registro implements Processor {

	public void process(Exchange exchange) throws Exception {
	
		Request inmueble= exchange.getIn().getBody(Request.class);
		
		
		exchange.setProperty("nombre",inmueble.getInmueble().getNombre());
		exchange.setProperty("tipo",inmueble.getInmueble().getTipo());
		exchange.setProperty("estado",inmueble.getInmueble().getEstado());
		exchange.setProperty("estado_inmueble",inmueble.getInmueble().getEstado_inmueble());
		exchange.setProperty("precio",inmueble.getInmueble().getPrecio());
		exchange.setProperty("direccion",inmueble.getInmueble().getDireccion());
		exchange.setProperty("barrio",inmueble.getInmueble().getBarrio());
		exchange.setProperty("ancho",inmueble.getInmueble().getAncho());
		exchange.setProperty("largo",inmueble.getInmueble().getLargo());
		exchange.setProperty("informacion_extra",inmueble.getInmueble().getInformacion_extra());
		exchange.setProperty("paredes",inmueble.getInmueble().getParedes());
		exchange.setProperty("habitaciones",inmueble.getInmueble().getHabitaciones());
		exchange.setProperty("muebles",inmueble.getInmueble().getMuebles());
		exchange.setProperty("correo",inmueble.getInmueble().getCorreo());
		exchange.setProperty("pisos",inmueble.getInmueble().getPisos());
	}

}