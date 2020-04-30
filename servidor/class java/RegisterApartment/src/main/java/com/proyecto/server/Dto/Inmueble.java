package com.proyecto.server.Dto;

import java.io.Serializable;
import java.util.List;

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
	
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public List<pisos> getPisos() {
		return pisos;
	}


	public void setPisos(List<pisos> pisos) {
		this.pisos = pisos;
	}
	
	
	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public Imagen getFoto() {
		return foto;
	}

	public void setFoto(Imagen foto) {
		this.foto = foto;
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
	
	@JsonProperty("localidad")
	private String localidad;

	@JsonProperty("ancho")
	private String ancho;
	
	@JsonProperty("largo")
	private String largo;
	
	@JsonProperty("informacion_extra")
	private String informacion_extra;
	
	@JsonProperty("correo")
	private String correo;
	
	@JsonProperty("pisos")
	private List<pisos> pisos;
	
	@JsonProperty("foto")
	private Imagen foto;
}