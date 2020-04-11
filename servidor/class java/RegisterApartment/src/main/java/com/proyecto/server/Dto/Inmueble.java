package com.proyecto.server.Dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class Inmueble implements Serializable{
	private static final long serialVersionUID = 5L;
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

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

	public String getInformacion_extra() {
		return informacion_extra;
	}

	public void setInformacion_extra(String informacion_extra) {
		this.informacion_extra = informacion_extra;
	}

	public String getParedes() {
		return paredes;
	}

	public void setParedes(String paredes) {
		this.paredes = paredes;
	}

	public String getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(String habitaciones) {
		this.habitaciones = habitaciones;
	}

	public String getMuebles() {
		return muebles;
	}

	public void setMuebles(String muebles) {
		this.muebles = muebles;
	}
	
	@JsonProperty("nombre")
	private String nombre;
	
	@JsonProperty("tipo")
	private String tipo;

	@JsonProperty("estado")
	private String estado;
	
	@JsonProperty("estado_inmueble")
	private String estado_inmueble;

	@JsonProperty("precio")
	private String precio;
	
	@JsonProperty("direccion")
	private String direccion;
	
	@JsonProperty("barrio")
	private String barrio;
	
	@JsonProperty("ancho")
	private String ancho;
	
	@JsonProperty("largo")
	private String largo;
	
	@JsonProperty("informacion_extra")
	private String informacion_extra;
	
	@JsonProperty("paredes")
	private String paredes;
	
	@JsonProperty("habitaciones")
	private String habitaciones;
	
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	@JsonProperty("muebles")
	private String muebles;
	
	@JsonProperty("correo")
	private String correo;
	
	@JsonProperty("pisos")
	private String pisos;


	public String getPisos() {
		return pisos;
	}

	public void setPisos(String pisos) {
		this.pisos = pisos;
	}
}