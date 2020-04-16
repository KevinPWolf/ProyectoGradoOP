package com.proyecto.server.Dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class inmuebles implements Serializable{
	private static final long serialVersionUID = 4L;
	
	
	@JsonProperty("inmueble")
	private List<inmueble> inmueble;


	public List<inmueble> getInmueble() {
		return inmueble;
	}


	public void setInmueble(List<inmueble> inmueble) {
		this.inmueble = inmueble;
	}
	

}
