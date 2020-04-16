package com.proyecto.server.Dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class inmueble implements Serializable{
	private static final long serialVersionUID = 4L;
	@JsonProperty("nombre")
	private String nombre;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@JsonProperty("id")
	private String id;
	
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}

	@JsonProperty("precio")
	private String precio;
	
	@JsonProperty("estado_inmueble")
	private String estado_inmueble;
	
	@JsonProperty("estado")
	private String estado;
	
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getEstado_inmueble() {
		return estado_inmueble;
	}
	public void setEstado_inmueble(String estado_inmueble) {
		this.estado_inmueble = estado_inmueble;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	@JsonProperty("barrio")
	private String barrio;
	
	public String getBarrio() {
		return barrio;
	}
	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}
	
	@JsonProperty("tipo")
	private String tipo;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
