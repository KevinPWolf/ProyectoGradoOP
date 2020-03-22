package com.proyecto.server.Dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class prueba implements Serializable{
	private static final long serialVersionUID = 1L;
	@JsonProperty("idApp")
	private String idApp;

	public String getIdApp() {
		return this.idApp;
	}

	public void setIdApp(String idApp) {
		this.idApp = idApp;
	}
}
