/*    */ package com.avianca.esb.transversal.mail.exception;
/*    */ 
/*    */ import com.avianca.esb.transversal.mail.midmail.ResponseSentMessage;
/*    */ import com.fasterxml.jackson.databind.JsonMappingException;
/*    */ import javax.ws.rs.core.Response;
/*    */ import javax.ws.rs.core.Response.ResponseBuilder;
/*    */ import javax.ws.rs.ext.ExceptionMapper;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AtributoJSONExceptionMapper
/*    */   implements ExceptionMapper<JsonMappingException>
/*    */ {
/* 16 */   private static final Logger logger = LoggerFactory.getLogger("mailLog");
/*    */   
/*    */   public Response toResponse(JsonMappingException e)
/*    */   {
/* 20 */     logger.error("Error en el request JsonMappingException", e);
/* 21 */     ResponseSentMessage response = new ResponseSentMessage();
/* 22 */     response.setIdTransaction(0);
/* 23 */     response.setMessageResult(e + e.getCause().toString());
/* 24 */     return Response.ok()
/* 25 */       .entity(response).type("application/json").encoding("UTF-8")
/* 26 */       .header("SCodigo", "400")
/* 27 */       .header("BOperacionExitosa", "false")
/* 28 */       .header("SMensaje", "error en el request JsonMappingException")
/* 29 */       .header("SMensajeTecnico", e + e.getCause().toString())
/* 30 */       .build();
/*    */   }
/*    */ }


/* Location:              D:\USUARIOS\ASSERT\Downloads\transversal-mail-notification-1.0.3.1.jar!\com\avianca\esb\transversal\mail\exception\AtributoJSONExceptionMapper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */