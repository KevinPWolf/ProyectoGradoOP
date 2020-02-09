package com.proyecto.server.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("getAbordados")
public class GetAbordados implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		String body = String.valueOf(exchange.getIn().getBody());
		int indexIni = body.indexOf("<DCSLST_GetPassengerListReply>");
		int indexFIn = body.indexOf("]]></GetPassengerListResult>");
		String finalBody = body.substring(indexIni,indexFIn);
		
		exchange.getIn().setBody(finalBody);
		
	}

}
