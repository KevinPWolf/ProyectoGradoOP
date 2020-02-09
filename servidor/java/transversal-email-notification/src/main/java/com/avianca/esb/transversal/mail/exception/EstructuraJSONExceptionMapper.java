/*    */ package com.avianca.esb.transversal.mail.exception;
/*    */ 
/*    */ import com.avianca.esb.transversal.mail.midmail.ResponseSentMessage;
/*    */ import com.fasterxml.jackson.core.JsonParseException;
/*    */ import javax.ws.rs.core.Response;
/*    */ import javax.ws.rs.core.Response.ResponseBuilder;
/*    */ import javax.ws.rs.ext.ExceptionMapper;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EstructuraJSONExceptionMapper
/*    */   implements ExceptionMapper<JsonParseException>
/*    */ {
/* 17 */   private static final Logger logger = LoggerFactory.getLogger("mailLog");
/*    */   
/*    */   public Response toResponse(JsonParseException e)
/*    */   {
/* 21 */     logger.error("Error en el request JsonParseException", e);
/* 22 */     ResponseSentMessage response = new ResponseSentMessage();
/* 23 */     response.setIdTransaction(0);
/* 24 */     response.setMessageResult(e + e.getCause().toString());
/* 25 */     return Response.ok()
/* 26 */       .entity(response).type("application/json").encoding("UTF-8")
/* 27 */       .header("SCodigo", "400")
/* 28 */       .header("BOperacionExitosa", "false")
/* 29 */       .header("SMensaje", "error en el request JsonParseException")
/* 30 */       .header("SMensajeTecnico", e + e.getCause().toString())
/* 31 */       .build();
/*    */   }
/*    */ }


/* Location:              D:\USUARIOS\ASSERT\Downloads\transversal-mail-notification-1.0.3.1.jar!\com\avianca\esb\transversal\mail\exception\EstructuraJSONExceptionMapper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */