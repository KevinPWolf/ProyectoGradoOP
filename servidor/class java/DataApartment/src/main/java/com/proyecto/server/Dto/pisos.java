package com.proyecto.server.Dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class pisos implements Serializable{
	private static final long serialVersionUID = 4L;
	
	
	@JsonProperty("paredes")
	private String paredes;
	
	@JsonProperty("habitaciones")
	private String habitaciones;
	
	@JsonProperty("muebles")
	private String muebles;
	
	@JsonProperty("texturas")
	private String texturas;
	
	@JsonProperty("posiciones_muebles")
	private String posiciones_muebles;

	@JsonProperty("piso")
	private String piso;
	
	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
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

	public String getTexturas() {
		return texturas;
	}

	public void setTexturas(String texturas) {
		this.texturas = texturas;
	}

	public String getPosiciones_muebles() {
		return posiciones_muebles;
	}

	public void setPosiciones_muebles(String posiciones_muebles) {
		this.posiciones_muebles = posiciones_muebles;
	}
	
}
