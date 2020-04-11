package com.proyecto.server.process;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.proyecto.server.Dto.response;


import org.apache.camel.Exchange;

import org.apache.camel.Processor;
import org.springframework.stereotype.Component;


public class LogoffHandler implements Processor {


	public void process(Exchange exchange) throws Exception {
		String response = (String) exchange.getIn().getBody();

		response responseDTO = new response();
		responseDTO.setRespuesta(response);;


		ObjectMapper objectMapper = new ObjectMapper();
		String jsonResponse = objectMapper.writeValueAsString(responseDTO);

		exchange.setProperty("jsonResponse", jsonResponse);

		exchange.getIn().setHeader("CamelAcceptContentType", "application/json; charset=UTF-8");
		exchange.getIn().setHeader("Content-Type", "application/json; charset=UTF-8");

		exchange.getIn().setBody(responseDTO, response.class);
	}
}