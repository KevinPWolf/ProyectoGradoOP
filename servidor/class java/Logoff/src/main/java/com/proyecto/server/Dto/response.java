package com.proyecto.server.Dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class response implements Serializable{
	private static final long serialVersionUID = 2L;
	@JsonProperty("respuesta")
	private String respuesta;
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}


}
