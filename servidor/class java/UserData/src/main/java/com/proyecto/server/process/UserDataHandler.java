package com.proyecto.server.process;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.server.Dto.datos;
import com.proyecto.server.Dto.response;


import org.apache.camel.Exchange;

import org.apache.camel.Processor;
import org.springframework.stereotype.Component;


public class UserDataHandler implements Processor {


	public void process(Exchange exchange) throws Exception {
		int bandera = (int) exchange.getIn().getHeader("bandera");
		if(bandera==1) {
			response responseDTO = new response();
			datos dato=new datos();
			dato.setNombre((String) exchange.getIn().getHeader("nombre"));
			dato.setPenalizacion((String) exchange.getIn().getHeader("penalizacion"));
			dato.setConfiabilidad((String) exchange.getIn().getHeader("confiabilidad"));
			dato.setNumero((String) exchange.getIn().getHeader("numero"));
			responseDTO.setDato(dato);


			ObjectMapper objectMapper = new ObjectMapper();
			String jsonResponse = objectMapper.writeValueAsString(responseDTO);

			exchange.setProperty("jsonResponse", jsonResponse);

			exchange.getIn().setHeader("CamelAcceptContentType", "application/json; charset=UTF-8");
			exchange.getIn().setHeader("Content-Type", "application/json; charset=UTF-8");

			exchange.getIn().setBody(responseDTO, response.class);
		}else {

			response responseDTO = new response();
			datos dato=new datos();
			dato.setNombre((String) exchange.getIn().getBody());
			dato.setPenalizacion((String) exchange.getIn().getBody());
			dato.setConfiabilidad((String) exchange.getIn().getBody());
			dato.setNumero((String) exchange.getIn().getBody());
			responseDTO.setDato(dato);


			ObjectMapper objectMapper = new ObjectMapper();
			String jsonResponse = objectMapper.writeValueAsString(responseDTO);

			exchange.setProperty("jsonResponse", jsonResponse);

			exchange.getIn().setHeader("CamelAcceptContentType", "application/json; charset=UTF-8");
			exchange.getIn().setHeader("Content-Type", "application/json; charset=UTF-8");

			exchange.getIn().setBody(responseDTO, response.class);
		}	
	}
}