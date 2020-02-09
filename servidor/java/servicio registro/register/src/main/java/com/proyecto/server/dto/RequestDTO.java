package com.proyecto.server.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;

@JsonAutoDetect
@JsonSerialize
public class RequestDTO implements Serializable {
	private static final long serialVersionUID = 6872117570224011584L;
	@JsonProperty("header")
	private Header header;
	@JsonProperty("flight")
	private Flight flight;

	public Header getHeader() {
		return this.header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Flight getFlight() {
		return this.flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}
}
