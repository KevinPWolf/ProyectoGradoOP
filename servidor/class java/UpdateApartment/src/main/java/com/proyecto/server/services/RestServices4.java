package com.proyecto.server.services;

import javax.ws.rs.Path;
import com.proyecto.server.Dto.Request4;

import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
@Path("/")
public interface RestServices4 {

	//@GET
	//@Path("pruebaget/{name}/")
	//String getCustomer(@PathParam("name") String name);
	@POST
	@Path("/")
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public abstract Response getMethod(Request4 paramRequestDTO);
}