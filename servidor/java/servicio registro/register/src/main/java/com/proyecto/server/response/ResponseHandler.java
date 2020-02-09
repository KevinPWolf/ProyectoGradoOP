package com.proyecto.server.response;

import com.proyecto.server.dto.Error;
import com.proyecto.server.dto.ResponseDTO;
import com.proyecto.server.dto.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;
import org.apache.camel.BeanInject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandler implements Processor {
	@BeanInject("properties")
	private Properties properties;

	public void process(Exchange exchange) throws Exception {
		String success = (String) exchange.getProperty("success");
		String code = (String) exchange.getProperty("code");
		String message = (String) exchange.getProperty("message");

		Result result = new Result();
		result.setSuccess(success);

		Error error = new Error();
		error.setCode(code);
		error.setMessage(message);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setResult(result);
		responseDTO.setError(error);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonResponse = objectMapper.writeValueAsString(responseDTO);

		exchange.setProperty("jsonResponse", jsonResponse);

		exchange.getIn().setHeader("CamelAcceptContentType", "application/json; charset=UTF-8");
		exchange.getIn().setHeader("Content-Type", "application/json; charset=UTF-8");

		exchange.getIn().setBody(responseDTO, ResponseDTO.class);
	}
}
