package com.proyecto.server.sql;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.camel.BeanInject;
import org.apache.camel.Exchange;
import org.apache.camel.PropertyInject;
import org.apache.log4j.Logger;



public class ConsultasBD
{
  private static final Logger LOG = Logger.getLogger("RegisterLog");
  //@BeanInject("dsRegister")
  //private DataSource dataSource;
  
  @BeanInject("dataSource")
  private DataSource dataSource;
  private String sqlUpdate;

  public void process(Exchange exchange) throws Exception {
	  
	  this.sqlUpdate="UPDATE Persona SET islogging = '0' WHERE correo = 'usuario'";
	  
	    String email = (String) exchange.getIn().getHeader("email");

	   this.sqlUpdate = this.sqlUpdate.replaceAll("usuario", email);
	     
	  	conexion(email);
	}
  public void conexion(String username)
    throws Exception
  {
    Connection connection = null;
    Statement statement =null;
    try
    {
      connection = getConnection();
      statement  =  connection.createStatement();
      statement.executeUpdate(this.sqlUpdate);   
    }
    finally
    {
      closeCon(connection, statement);
    }
  }
  
  private Connection getConnection()
    throws Exception
  {
    Connection conn = null;
    try
    {
      conn = this.dataSource.getConnection();
    }
    catch (SQLException e)
    {
      LOG.error("Se presento un error al obtener la Conexion con: " + this.dataSource, e);
      throw e;
    }
    return conn;
  }
  
  public void closeCon(Connection cn, Statement ps)
  {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Closing connection: " + this.dataSource);
    }
    if (ps != null) {
      try
      {
        ps.close();
      }
      catch (SQLException e)
      {
        LOG.error("Error al cerrar la consulta. ", e);
      }
    }
    if (cn != null) {
      try
      {
        cn.close();
      }
      catch (SQLException e)
      {
        LOG.error("Error al cerrar la conexion. ", e);
      }
    }
  }
}
