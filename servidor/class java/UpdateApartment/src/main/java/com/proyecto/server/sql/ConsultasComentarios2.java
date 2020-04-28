package com.proyecto.server.sql;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.camel.BeanInject;
import org.apache.camel.Exchange;
import org.apache.camel.PropertyInject;
import org.apache.log4j.Logger;

import com.proyecto.server.process.Counter;




public class ConsultasComentarios2
{
  private static final Logger LOG = Logger.getLogger("UpdateLog");
  //@BeanInject("dsRegister")
  //private DataSource dataSource;
  
  @BeanInject("dataSource")
  private DataSource dataSource;
  
  
  private  String sqlinsert;
  

  public void process(Exchange exchange) throws Exception {
	  
	  this.sqlinsert="INSERT INTO comentarios(contexto,emisor,ID_inmueble) VALUE('mensaje','correo', 'identificador')";	    
	  String id = (String) exchange.getIn().getHeader("id");
	  String contexto = (String) exchange.getIn().getHeader("contexto");
	  String emisor = (String) exchange.getIn().getHeader("correo");
	  
	    this.sqlinsert = this.sqlinsert.replaceAll("mensaje", contexto);
	    this.sqlinsert = this.sqlinsert.replaceAll("correo", emisor);
	    this.sqlinsert = this.sqlinsert.replaceAll("identificador", id);

	    conexion();
	}
  public void conexion()
		    throws Exception
		  {
		    Connection connection = null;
		    Statement statement =null;
		    ResultSet resultSet = null;
		    try
		    {
		      connection = getConnection();
		      statement  =  connection.createStatement();
		      statement.executeUpdate(this.sqlinsert);   
		      
		      
		    }
		    finally
		    {
		      closeCon(connection, statement,resultSet);
		    }
		  }
  
  private int cont(ResultSet rs) throws SQLException {
	  int i = 0;
	  while(rs.next()) {
	      i++;
	  } 
	  return i;
  }
  
  private ResultSet executeSQL(Connection cn, PreparedStatement ps)
    throws Exception
  {
    ResultSet rs = null;
    try
    {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Ejecutando Consulta: ");
      }
      rs = ps.executeQuery();
    }
    catch (Exception e)
    {
      LOG.error("Se presento un error al ejecutar la consulta: ", e);
      throw e;
    }
    return rs;
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
  
  public void closeCon(Connection cn, Statement ps, ResultSet rs)
  {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Closing connection: " + this.dataSource);
    }
    if (rs != null) {
      try
      {
        rs.close();
      }
      catch (SQLException e)
      {
        LOG.error("Error al cerrar la set de resultados. ", e);
      }
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
