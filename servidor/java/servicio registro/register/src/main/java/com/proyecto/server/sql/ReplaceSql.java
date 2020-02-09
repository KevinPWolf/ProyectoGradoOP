package com.proyecto.server.sql;

import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.PropertyInject;

public class ReplaceSql implements Processor {
	@PropertyInject("{{OPAIN.queryRest}}")
	private String query;

	public void process(Exchange exchange) throws Exception {
		String date = (String) exchange.getIn().getHeader("dayOrigin");
		List<String> flight = (List) exchange.getIn().getHeader("flightNumber");
		String concat1 = "";
		for (String concat : flight) {
			concat1 = "'" + concat + "'," + concat1;
		}
		concat1 = concat1.substring(0, concat1.length() - 1).trim();
		String queryF = this.query.replaceAll(":date:", date);
		String queryFinal = queryF.replaceAll(":listflight:", concat1);
		exchange.getIn().setHeader("query", queryFinal);
		exchange.getIn().setHeader("CamelSqlQuery", queryFinal);
	}
}
