package com.proyecto.server.Dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class Requestf implements Serializable{
	private static final long serialVersionUID = 1L;

	@JsonProperty("apartamento")
	private Apartamento apartamento;
	
	@JsonProperty("correo")
	private String correo;

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Apartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}
}
