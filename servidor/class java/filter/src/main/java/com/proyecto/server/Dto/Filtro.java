package com.proyecto.server.Dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class Filtro implements Serializable{
	private static final long serialVersionUID = 4L;
	
	
	@JsonProperty("tipo")
	private String tipo;
	
	@JsonProperty("precio")
	private String precio;
	
	@JsonProperty("precio_mayor")
	private String precio_mayor;
	
	@JsonProperty("estado")
	private String estado;
	
	@JsonProperty("estado_inmueble")
	private String estado_inmueble;
	
	@JsonProperty("barrio")
	private String barrio;

	@JsonProperty("localidad")
	private String localidad;

	@JsonProperty("numero")
	private String numero;

	
	
	
	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstado_inmueble() {
		return estado_inmueble;
	}

	public void setEstado_inmueble(String estado_inmueble) {
		this.estado_inmueble = estado_inmueble;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
	public String getPrecio_mayor() {
		return precio_mayor;
	}

	public void setPrecio_mayor(String precio_mayor) {
		this.precio_mayor = precio_mayor;
	}
}
