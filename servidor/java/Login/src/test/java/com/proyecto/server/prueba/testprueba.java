package com.proyecto.server.prueba;



import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import com.proyecto.server.Dto.Persona;
import com.proyecto.server.Dto.Request;



//public class testprueba extends CamelTestSupport{

public class testprueba extends CamelSpringTestSupport{ 	
		
	    @Test
	    public  void testP() throws Exception {
			// TODO Auto-generated method stub	    	
	    	context.getRouteDefinition("myRoute").adviceWith(context, new AdviceWithRouteBuilder() {
				@Override
				public void configure() throws Exception {
					replaceFromWith("direct:inicio"); 
					weaveAddLast().to("mock://endRoute");
					
				}
			});
			context.start();
			Persona person = new Persona();
			person.setEmail("pepinosamk@gmail.com");
			//person.setEmail("kwolfp12@gmail.com");
			person.setPassword("rata");
			person.setName("pepinosa");
			person.setPhone("589647567");
			
			Request request = new Request();
			request.setPerson(person);
						
			template.sendBody("direct:inicio", request);
		}
	    
	    @Override
	    protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/applicationContext.xml");
	    }
	    
	    
	    @BeforeClass
	   	public static void setUpDatasourceJNDI() throws Exception {
	   		try {//
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
	   			ds.setUrl("jdbc:mysql://remotemysql.com:3306/Ah7yd8cjwB");
	   			//ds.setUsername("3296648_viewerrealm");
	   			ds.setUsername("Ah7yd8cjwB");
	   			//ds.setPassword("123456789an");
	   			ds.setPassword("IRuHkHIOCG");

	   			builder.bind("osgi:service/jdbc/RegisterBD", ds);
	   			builder.activate();	
	   		} catch (NamingException ex) {
	   		}
	   	}
	    
}
