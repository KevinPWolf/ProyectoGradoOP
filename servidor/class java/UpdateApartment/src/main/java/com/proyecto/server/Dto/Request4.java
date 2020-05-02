package com.proyecto.server.Dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class Request4 implements Serializable{
	private static final long serialVersionUID = 1L;

	@JsonProperty("puntuacion")
	private String puntuacion;

	@JsonProperty("correo")
	private String correo;

	@JsonProperty("id_inmueble")
	private String id_inmueble;

	public String getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(String puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getId_inmueble() {
		return id_inmueble;
	}

	public void setId_inmueble(String id_inmueble) {
		this.id_inmueble = id_inmueble;
	}

	

}
