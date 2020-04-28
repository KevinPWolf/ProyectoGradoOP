package com.proyecto.server.Dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class response implements Serializable{
	private static final long serialVersionUID = 3L;
	
	@JsonProperty("comentarios")
	private comentarios comentarios;

	public comentarios getComentarios() {
		return comentarios;
	}

	public void setComentarios(comentarios comentarios) {
		this.comentarios = comentarios;
	}

}