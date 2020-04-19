package com.proyecto.server.process;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.proyecto.server.Dto.response2;

import java.util.ArrayList;
import java.util.List;
import org.apache.camel.Exchange;

import org.apache.camel.Processor;


public class ApartmentDataHandler implements Processor {


	public void process(Exchange exchange) throws Exception {
		
			response2 responseDTO = new response2();
			responseDTO.setRespuesta((String) exchange.getIn().getBody());



			ObjectMapper objectMapper = new ObjectMapper();
			String jsonResponse = objectMapper.writeValueAsString(responseDTO);

			exchange.setProperty("jsonResponse", jsonResponse);

			exchange.getIn().setHeader("CamelAcceptContentType", "application/json; charset=UTF-8");
			exchange.getIn().setHeader("Content-Type", "application/json; charset=UTF-8");

			exchange.getIn().setBody(responseDTO, response2.class);
			
	}
}