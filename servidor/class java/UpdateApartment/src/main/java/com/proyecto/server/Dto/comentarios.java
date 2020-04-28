package com.proyecto.server.Dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class comentarios implements Serializable{
	private static final long serialVersionUID = 4L;
	
	
	@JsonProperty("comentario")
	private List<comentario> comentario;


	public List<comentario> getComentario() {
		return comentario;
	}


	public void setComentario(List<comentario> comentario) {
		this.comentario = comentario;
	}

	@JsonProperty("total")
	private String total;


	public String getTotal() {
		return total;
	}


	public void setTotal(String total) {
		this.total = total;
	}

	

}
