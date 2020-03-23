package com.proyecto.server.Dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonAutoDetect
@JsonSerialize
public class Request implements Serializable{
	private static final long serialVersionUID = 3L;
	@JsonProperty("person")
	private Persona person;


	public Persona getPerson() {
		return this.person;
	}


	public void setPerson(Persona person) {
		this.person = person;
	}

}
