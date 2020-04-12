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


public class ApartmentDataHandler2 implements Processor {


	public void process(Exchange exchange) throws Exception {
		int bandera = (int) exchange.getIn().getHeader("bandera");
		if(bandera==0) {
			response responseDTO = new response();
			inmuebles inmuebles=new inmuebles();
			System.out.println("entro aca");
			Counter count=(Counter) exchange.getIn().getHeader("counter");
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
				inmueble.add(inmu);
			}

			inmuebles.setInmueble(inmueble);

			responseDTO.setInmuebles(inmuebles);

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonResponse = objectMapper.writeValueAsString(responseDTO);

			exchange.setProperty("jsonResponse", jsonResponse);

			exchange.getIn().setHeader("CamelAcceptContentType", "application/json; charset=UTF-8");
			exchange.getIn().setHeader("Content-Type", "application/json; charset=UTF-8");

			exchange.getIn().setBody(responseDTO, response.class);
		}else {
			response2 responseDTO = new response2();

			responseDTO.setRespuesta((String) exchange.getIn().getBody());

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonResponse = objectMapper.writeValueAsString(responseDTO);

			exchange.setProperty("jsonResponse", jsonResponse);

			exchange.getIn().setHeader("CamelAcceptContentType", "application/json; charset=UTF-8");
			exchange.getIn().setHeader("Content-Type", "application/json; charset=UTF-8");

			exchange.getIn().setBody(responseDTO, response.class);
		}	
	}
}