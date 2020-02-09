package com.proyecto.server.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.proyecto.server.dto.Flight;
import com.proyecto.server.dto.Header;
import com.proyecto.server.dto.RequestDTO;


/**
 * 
 * @author Assert Solutions S.A.S <info@assertsolutions.com>
 *         <br/>
 *         Date: 9/04/2018 9:17:11 a.m.
 *
 */
public class registerTest extends CamelSpringTestSupport {

    private static final String PROPERTIES_FILE_DIR = "src/test/resources/";
    private static Properties testProperties = new Properties();
    
    @Test
    public void testRoute() throws Exception {
	final String fromRoute = "direct:fromRoute";

	context.getRouteDefinition("ROUTE-REGISTER").adviceWith(context, new AdviceWithRouteBuilder() {
	    @Override
	    public void configure() throws Exception {
		replaceFromWith(fromRoute);
		weaveAddLast().log("Finishing the unit test of the route ").to("mock://endroute");
	    }
	});
	context.start();
	// Agregamos un mock endpoint
	MockEndpoint mockEndpoint = getMockEndpoint("mock://endroute");

	Header header = new Header();
	header.setIdApp("SS010101");
	Flight flight = new Flight();
	flight.setDayOrigin("13/01/2020");
	List<String> listStrings = new ArrayList<String>();
	listStrings.add("8382");
	flight.setFlightNumber(listStrings);

	RequestDTO request = new RequestDTO();
	request.setHeader(header);
	request.setFlight(flight);

	template.sendBody(fromRoute, request);
    }
    /**
     * Carga del archivo de propiedades para los Test Unitarios
     * 
     * @throws Exception
     */
    @BeforeClass
    public static void init() throws Exception {
	testProperties.load(registerTest.class.getResourceAsStream("/server.register.properties"));
    }
    
    @BeforeClass
    public static void setUpProperties() throws Exception {
	System.setProperty("karaf.home", PROPERTIES_FILE_DIR);
	System.setProperty("project.artifactId", "Test-Maven-Artifact");
    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
	return new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml",
		"META-INF/spring/properties-beans.xml");
    }

}
