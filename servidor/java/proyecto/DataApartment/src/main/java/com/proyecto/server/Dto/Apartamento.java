package com.proyecto.server.Dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class Apartamento implements Serializable{
	private static final long serialVersionUID = 5L;
	@JsonProperty("id")
	private String id;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}