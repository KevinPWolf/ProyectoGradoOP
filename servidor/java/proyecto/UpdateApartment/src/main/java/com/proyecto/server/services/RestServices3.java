package com.proyecto.server.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.proyecto.server.Dto.Request;
import com.proyecto.server.Dto.Request2;
import com.proyecto.server.Dto.Request3;

import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
@Path("/")
public interface RestServices3 {

	//@GET
	//@Path("pruebaget/{name}/")
	//String getCustomer(@PathParam("name") String name);
	@POST
	@Path("/")
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public abstract Response getMethod(Request3 paramRequestDTO);
}