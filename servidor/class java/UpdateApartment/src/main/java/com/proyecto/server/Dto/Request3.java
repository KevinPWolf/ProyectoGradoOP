package com.proyecto.server.Dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class Request3 implements Serializable{
	private static final long serialVersionUID = 1L;

	@JsonProperty("emisor")
	private String emisor;

	@JsonProperty("ID_inmueble")
	private String ID_inmueble;

	@JsonProperty("contexto")
	private String contexto;

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public String getID_inmueble() {
		return ID_inmueble;
	}

	public void setID_inmueble(String iD_inmueble) {
		ID_inmueble = iD_inmueble;
	}

	public String getContexto() {
		return contexto;
	}

	public void setContexto(String contexto) {
		this.contexto = contexto;
	}

	

}
