package com.proyecto.server.Dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class response implements Serializable{
	private static final long serialVersionUID = 3L;
	
	@JsonProperty("inmueble")
	private inmueble inmueble;
	public inmueble getInmueble() {
		return inmueble;
	}
	public void setInmueble(inmueble inmueble) {
		this.inmueble = inmueble;
	}
}