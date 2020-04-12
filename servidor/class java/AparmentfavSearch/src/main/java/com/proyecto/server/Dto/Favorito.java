package com.proyecto.server.Dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class Favorito implements Serializable{
	private static final long serialVersionUID = 5L;
	@JsonProperty("inmueble")
	private String inmueble;

	@JsonProperty("email")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInmueble() {
		return this.inmueble;
	}

	public void setInmueble(String inmueble) {
		this.inmueble = inmueble;
	}
}