package com.proyecto.server.process;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.server.Dto.comentario;
import com.proyecto.server.Dto.comentarios;
import com.proyecto.server.Dto.response;
import com.proyecto.server.Dto.response2;

import java.util.ArrayList;
import java.util.List;
import org.apache.camel.Exchange;

import org.apache.camel.Processor;


public class DataHandler implements Processor {


	public void process(Exchange exchange) throws Exception {
		int bandera = (int) exchange.getIn().getHeader("bandera");
		if(bandera==0) {
			response responseDTO = new response();
			comentarios comentarios=new comentarios();
			
			Counter count=(Counter) exchange.getIn().getHeader("counter");
			//inmueble [] inmueble = new inmueble [6];
			List<comentario> comentario =new ArrayList<comentario>();
			
			 List<List> map= (List<List>) exchange.getIn().getBody();
			for(int i=0; i<count.getCount();i++) {
				comentario inmu=new comentario();
				inmu.setID((String) map.get(i).get(0));
				inmu.setContexto((String) map.get(i).get(1));
				inmu.setFecha((String) map.get(i).get(2));
				inmu.setEmisor((String) map.get(i).get(3));
				inmu.setPuntuacion((String) map.get(i).get(4));
				comentario.add(inmu);
			}
			comentarios.setComentario(comentario);
			comentarios.setTotal(""+count.getCount());
			
			responseDTO.setComentarios(comentarios);


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

			exchange.getIn().setBody(responseDTO, response2.class);
		}	
	}
}