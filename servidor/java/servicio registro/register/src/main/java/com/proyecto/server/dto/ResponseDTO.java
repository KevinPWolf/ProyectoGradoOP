package com.proyecto.server.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;

@JsonAutoDetect
@JsonSerialize
public class ResponseDTO implements Serializable {
	private static final long serialVersionUID = -928421079621243775L;
	@JsonProperty("result")
	private Result result;
	@JsonProperty("error")
	private Error error;

	public Result getResult() {
		return this.result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Error getError() {
		return this.error;
	}

	public void setError(Error error) {
		this.error = error;
	}
}
	