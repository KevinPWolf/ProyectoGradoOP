package com.proyecto.server.Dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class datos implements Serializable{
	private static final long serialVersionUID = 4L;
	@JsonProperty("nombre")
	private String nombre;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@JsonProperty("penalizacion")
	private String penalizacion;
	
	@JsonProperty("confiabilidad")
	private String confiabilidad;
	
	@JsonProperty("numero")
	private String numero;
	public String getPenalizacion() {
		return penalizacion;
	}
	public void setPenalizacion(String penalizacion) {
		this.penalizacion = penalizacion;
	}
	public String getConfiabilidad() {
		return confiabilidad;
	}
	public void setConfiabilidad(String confiabilidad) {
		this.confiabilidad = confiabilidad;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

}
