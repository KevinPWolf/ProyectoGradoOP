package com.proyecto.server.process;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.server.Dto.pisos;
import com.proyecto.server.Dto.inmueble;
import com.proyecto.server.Dto.response;
import com.proyecto.server.Dto.response2;

import java.util.ArrayList;
import java.util.List;
import org.apache.camel.Exchange;

import org.apache.camel.Processor;


public class ApartmentDataHandler implements Processor {


	public void process(Exchange exchange) throws Exception {
		int bandera = (int) exchange.getIn().getHeader("bandera");
		if(bandera==0) {
			response responseDTO = new response();
			inmueble inmueble=new inmueble();
			
			Counter count=(Counter) exchange.getIn().getHeader("counter");
			//inmueble [] inmueble = new inmueble [6];
			List<pisos> pisos =new ArrayList<pisos>();
			
			 List<List> map= (List<List>) exchange.getIn().getBody();
			for(int i=0; i<count.getCount();i++) {
				pisos piso=new pisos();
				piso.setParedes((String) map.get(i).get(11));
				piso.setHabitaciones((String) map.get(i).get(12));
				piso.setMuebles((String) map.get(i).get(13));
				piso.setTexturas((String) map.get(i).get(14));
				piso.setPosiciones_muebles((String) map.get(i).get(15));
				pisos.add(piso);
			}
			
			inmueble.setPisos(pisos);
			inmueble.setAncho((String) map.get(0).get(0));
			inmueble.setLargo((String) map.get(0).get(1));
			inmueble.setEstado_inmueble((String) map.get(0).get(2));
			inmueble.setPrecio((String) map.get(0).get(3));
			inmueble.setEstado((String) map.get(0).get(4));
			inmueble.setBarrio((String) map.get(0).get(5));
			inmueble.setDireccion((String) map.get(0).get(6));
			inmueble.setNombre_inmueble((String) map.get(0).get(7));
			inmueble.setInformacion_extra((String) map.get(0).get(8));
			inmueble.setTipo((String) map.get(0).get(9));
			inmueble.setTelefono((String) map.get(0).get(10));
			inmueble.setGreen((String) map.get(0).get(16));
			inmueble.setBlue((String) map.get(0).get(17));
			inmueble.setRed((String) map.get(0).get(18));
			inmueble.setAlpha((String) map.get(0).get(19));
			responseDTO.setInmueble(inmueble);


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