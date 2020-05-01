package com.proyecto.server.process;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.server.Dto.inmueble;
import com.proyecto.server.Dto.inmuebles;
import com.proyecto.server.Dto.response;
import com.proyecto.server.Dto.response2;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;

import org.apache.camel.Processor;
import org.springframework.stereotype.Component;


public class ResponseHandler implements Processor {


	public void process(Exchange exchange) throws Exception {
		int bandera = (int) exchange.getIn().getHeader("bandera");
		if(bandera==0) {
			response responseDTO = new response();
			inmuebles inmuebles=new inmuebles();
			
			Counter count=(Counter) exchange.getIn().getHeader("counter");
			String cont=(String) exchange.getIn().getHeader("contb");
			//inmueble [] inmueble = new inmueble [6];
			List<inmueble> inmueble =new ArrayList<inmueble>();
			
			 List<List> map= (List<List>) exchange.getIn().getBody();
			for(int i=0; i<count.getCount();i++) {
				inmueble inmu=new inmueble();
				inmu.setID((String) map.get(i).get(0));
				inmu.setNombre((String) map.get(i).get(1));
				inmu.setPrecio((String) map.get(i).get(2));
				inmu.setTipo((String) map.get(i).get(3));
				inmu.setEstado_inmueble((String) map.get(i).get(4));
				inmu.setEstado((String) map.get(i).get(5));
				inmu.setBarrio((String) map.get(i).get(6));
				System.out.println("jaja un pigmeo");
				System.out.println("jaja como alf "+(String) map.get(i).get(7));
				inmu.setGreen((String) map.get(i).get(7));
				inmu.setBlue((String) map.get(i).get(8));
				inmu.setRed((String) map.get(i).get(9));
				inmu.setAlpha((String) map.get(i).get(10));
				inmueble.add(inmu);
			}

			inmuebles.setInmueble(inmueble);

			responseDTO.setInmuebles(inmuebles);
			responseDTO.setTotal(cont);
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonResponse = objectMapper.writeValueAsString(responseDTO);

			exchange.setProperty("jsonResponse", jsonResponse);

			exchange.getIn().setHeader("CamelAcceptContentType", "application/json; charset=UTF-8");
			exchange.getIn().setHeader("Content-Type", "application/json; charset=UTF-8");

			exchange.getIn().setBody(responseDTO, response.class);
		}else {
			response2 responseDTO = new response2();

			responseDTO.setError((String) exchange.getIn().getBody());

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonResponse = objectMapper.writeValueAsString(responseDTO);

			exchange.setProperty("jsonResponse", jsonResponse);

			exchange.getIn().setHeader("CamelAcceptContentType", "application/json; charset=UTF-8");
			exchange.getIn().setHeader("Content-Type", "application/json; charset=UTF-8");

			exchange.getIn().setBody(responseDTO, response.class);
		}	
	}
}