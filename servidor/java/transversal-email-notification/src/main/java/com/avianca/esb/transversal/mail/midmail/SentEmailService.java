package com.avianca.esb.transversal.mail.midmail;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("")
public abstract interface SentEmailService
{
  @POST
  @Path("/sentEmail/")
  @Consumes({"application/json; charset=UTF-8"})
  @Produces({"application/json; charset=UTF-8", "*/*"})
  public abstract ResponseSentMessage sentEmail(RequestSentMessage paramRequestSentMessage);
}


/* Location:              D:\USUARIOS\ASSERT\Downloads\transversal-mail-notification-1.0.3.1.jar!\com\avianca\esb\transversal\mail\midmail\SentEmailService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */