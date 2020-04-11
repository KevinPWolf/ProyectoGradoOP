package com.proyecto.server.Dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class Grupo implements Serializable{
	private static final long serialVersionUID = 5L;
	@JsonProperty("numero")
	private String numero;

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
}