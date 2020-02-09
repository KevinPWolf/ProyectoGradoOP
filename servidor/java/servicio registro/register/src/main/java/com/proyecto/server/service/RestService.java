package com.proyecto.server.service;

import com.proyecto.server.dto.RequestDTO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public abstract interface RestService
{
  @POST
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public abstract Response getMethod(RequestDTO paramRequestDTO);
}
