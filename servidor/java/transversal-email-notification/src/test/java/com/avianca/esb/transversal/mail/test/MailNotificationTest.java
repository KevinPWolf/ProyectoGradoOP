package com.avianca.esb.transversal.mail.test;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class MailNotificationTest extends CamelSpringTestSupport {

    private String getMockEndpoint() {
        return "mock:finish?assertPeriod=5000";
    }

    @Test
    public void testRoute() throws Exception {
        MockEndpoint mockEndpoint = getMockEndpoint(getMockEndpoint());
        mockEndpoint.expectedMinimumMessageCount(0);

        Map params = new HashMap<>();
        params.put("from","notificacionesesb@avianca.com");
        params.put("to","jcastro@assertsolutions.com");
        params.put("subject","This is a testing mail");

        context.getShutdownStrategy().setTimeout(600);
        context.getRouteDefinition("ROUTE-JMS-TO-MAIL").adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                weaveAddLast().to(getMockEndpoint());
            }
        });
        template.sendBody("direct-vm:emailNotificationRoute",params);

        mockEndpoint.assertIsSatisfied();
    }


	@Override
	protected ClassPathXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml");
	}

}
