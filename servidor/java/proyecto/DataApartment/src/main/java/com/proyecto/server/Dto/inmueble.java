package com.proyecto.server.Dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class inmueble implements Serializable{
	private static final long serialVersionUID = 4L;

	@JsonProperty("ancho")
	private String ancho;
	
	@JsonProperty("largo")
	private String largo;
	
	@JsonProperty("estado_inmueble")
	private String estado_inmueble;
	
	@JsonProperty("precio")
	private String precio;
	
	@JsonProperty("estado")
	private String estado;
	
	@JsonProperty("barrio")
	private String barrio;

	@JsonProperty("direccion")
	private String direccion;
	
	@JsonProperty("nombre_inmueble")
	private String nombre_inmueble;
	
	@JsonProperty("informacion_extra")
	private String informacion_extra;
	
	@JsonProperty("tipo")
	private String tipo;
	
	@JsonProperty("telefono")
	private String telefono;
	
	public String getAncho() {
		return ancho;
	}


	public void setAncho(String ancho) {
		this.ancho = ancho;
	}


	public String getLargo() {
		return largo;
	}


	public void setLargo(String largo) {
		this.largo = largo;
	}


	public String getEstado_inmueble() {
		return estado_inmueble;
	}


	public void setEstado_inmueble(String estado_inmueble) {
		this.estado_inmueble = estado_inmueble;
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


	public String getBarrio() {
		return barrio;
	}


	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public String getNombre_inmueble() {
		return nombre_inmueble;
	}


	public void setNombre_inmueble(String nombre_inmueble) {
		this.nombre_inmueble = nombre_inmueble;
	}


	public String getInformacion_extra() {
		return informacion_extra;
	}


	public void setInformacion_extra(String informacion_extra) {
		this.informacion_extra = informacion_extra;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	@JsonProperty("pisos")
	private List<pisos> pisos;


	public List<pisos> getPisos() {
		return pisos;
	}


	public void setPisos(List<pisos> pisos) {
		this.pisos = pisos;
	}
}
