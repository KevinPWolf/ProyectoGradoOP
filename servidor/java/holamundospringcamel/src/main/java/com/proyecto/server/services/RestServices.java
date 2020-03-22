package com.proyecto.server.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
public interface RestServices {



	@GET
	@Path("/{name}/")
	String getCustomer(@PathParam("name") String name);

}