package com.proyecto.server.Dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class Request implements Serializable{
	private static final long serialVersionUID = 1L;

	
	@JsonProperty("filtro")
	private Filtro filtro;


	public Filtro getFiltro() {
		return filtro;
	}


	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}
	
	

}
