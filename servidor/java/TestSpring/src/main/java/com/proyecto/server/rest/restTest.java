package com.proyecto.server.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.proyecto.server.Dto.prueba;


 
@Path("/")
public abstract interface restTest
{
  @POST
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public abstract Response getMethod(prueba paramRequestDTO);
}