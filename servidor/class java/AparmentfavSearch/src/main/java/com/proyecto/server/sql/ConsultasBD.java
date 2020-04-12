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



public class ConsultasBD
{
  private static final Logger LOG = Logger.getLogger("FavoriteDataLog");
  //@BeanInject("dsRegister")
  //private DataSource dataSource;
  
  @BeanInject("dataSource")
  private DataSource dataSource;
  private  String sqlinsert, sqlsearch;


  public void process(Exchange exchange) throws Exception {
	  int bandera=0;
	  this.sqlinsert="INSERT INTO inmueblexpersona(ID_INMUEBLE, ID_PERSONA) VALUE('inmuid','perid')";
	  this.sqlsearch = "SELECT ID FROM Persona WHERE correo = 'usuario'"; 
	  String id_inmueble = (String) exchange.getIn().getHeader("inmueble");
	   
	    
	    this.sqlsearch = this.sqlsearch.replaceAll("usuario", (String) exchange.getIn().getHeader("email"));
	    
	      
	    
	   bandera=conexion(bandera,id_inmueble);
	  	exchange.setProperty("bandera",bandera);
	}
  public int conexion(int bandera,String id_inmueble)
    throws Exception
  {
    Connection connection = null;
    Statement statement =null;
    ResultSet resultSet = null;
    String id_persona="";
    try
    {
      connection = getConnection();
      statement  =  connection.createStatement();
      resultSet = statement.executeQuery(this.sqlsearch);   
      int resul=cont(resultSet);

      if(resul>0) { 
    	  resultSet = statement.executeQuery(this.sqlsearch); 
 
	   	  while(resultSet.next()) {
	   		id_persona=resultSet.getString("ID");	
	   	  }
	   	 this.sqlinsert = this.sqlinsert.replaceAll("inmuid", id_inmueble);
	   	this.sqlinsert = this.sqlinsert.replaceAll("perid", id_persona);
         statement.executeUpdate(this.sqlinsert);
         LOG.info("inmueble agregado");
         return bandera=0;
      }else { 
		   LOG.info("no existe ese usuario");
	       return bandera=1;
	   }
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
