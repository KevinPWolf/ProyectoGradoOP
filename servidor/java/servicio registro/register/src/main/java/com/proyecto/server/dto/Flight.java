package com.proyecto.server.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;

@JsonAutoDetect
@JsonSerialize
public class Flight
{
  @JsonProperty("dayOrigin")
  private String dayOrigin;
  @JsonProperty("flightNumber")
  private List<String> flightNumber;
  
  public String getDayOrigin()
  {
    return this.dayOrigin;
  }
  
  public void setDayOrigin(String dayOrigin)
  {
    this.dayOrigin = dayOrigin;
  }
  
  public List<String> getFlightNumber()
  {
    return this.flightNumber;
  }
  
  public void setFlightNumber(List<String> flightNumber)
  {
    this.flightNumber = flightNumber;
  }
}
