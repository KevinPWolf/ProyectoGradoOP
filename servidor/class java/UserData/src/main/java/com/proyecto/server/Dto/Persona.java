package com.proyecto.server.Dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class Persona implements Serializable{
	private static final long serialVersionUID = 5L;
	@JsonProperty("email")
	private String email;

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}