package com.proyecto.server.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;

@JsonAutoDetect
@JsonSerialize
public class RequestDTO implements Serializable {
	private static final long serialVersionUID = 6872117570224011584L;
	@JsonProperty("person")
	private Person person;


	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
