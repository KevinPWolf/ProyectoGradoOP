package com.proyecto.server.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
public class RestServices {

	public RestServices() {
	}

	@GET
	@Path("/employees/{name}/")
	public String getCustomer(@PathParam("name") String name) {
		return "Welcome " + name;
	}

}