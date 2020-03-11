package com.proyecto.server.test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import com.proyecto.server.dto.RequestDTO;
import com.proyecto.server.dto.Person;


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
	
	Person person = new Person();
	person.setEmail("pepinosamk@gmail.com");
	//person.setEmail("kwolfp12@gmail.com");
	person.setPassword("rata");
	person.setName("pepinosa");
	person.setPhone("589647567");
	
	RequestDTO request = new RequestDTO();
	request.setPerson(person);
	
	
//	byte[] byter = Files.readAllBytes(Paths.get(PROPERTIES_FILE_DIR + "/data/request"));

	//template.sendBody(fromRoute, new String(byter));
	template.sendBody(fromRoute, request);
    }
    /**
     * Carga del archivo de propiedades para los Test Unitarios
     * 
     * @throws Exception
     */
    
    @BeforeClass
   	public static void setUpDatasourceJNDI() throws Exception {
   		try {
   			// Create initial context
   			System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
   			System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
   			SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();

   			// Construct DataSource
   			BasicDataSource ds = new BasicDataSource();
   			//ds.setDriverClassName("com.mysql.cj.jdbc.Driver");

   			//ds.setDriverClassName("com.mysql.jdbc.Driver");
   			ds.setDriverClassName("org.gjt.mm.mysql.Driver");
   			
   			//ds.setUrl("jdbc:mysql://fdb19.awardspace.net:3306/3296648_viewerrealm?useSSL=false");
   			//ds.setUrl("jdbc:mysql://fdb19.awardspace.net:3306/3296648_viewerrealm");
   			ds.setUrl("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7322058");
   			//ds.setUsername("3296648_viewerrealm");
   			ds.setUsername("sql7322058");
   			//ds.setPassword("123456789an");
   			ds.setPassword("NGJcUgj2Aj");

   			builder.bind("osgi:service/jdbc/RegisterBD", ds);
   			builder.activate();	
   		} catch (NamingException ex) {
   		}
   	}
    
    
    
    
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
