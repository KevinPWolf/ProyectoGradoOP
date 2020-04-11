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
  private  String sqlsearch;
  private  String sqlsearch2;
  private String nombre,penalizacion,confiabilidad,numero;
  public void process(Exchange exchange) throws Exception {
	  int bandera=0;
	   this.sqlsearch="SELECT * FROM Persona WHERE correo = 'usuario'";
	    String email = (String) exchange.getIn().getHeader("email");
		   this.sqlsearch2="SELECT pe.nombre, pe.penalizacion, pe.confiabilidad , te.numero FROM Persona pe, telefonos te WHERE pe.correo = '"+email+"' AND pe.ID = te.ID_PERSONA";
	   this.sqlsearch = this.sqlsearch.replaceAll("usuario", email);
	     
	   bandera=conexion(email,bandera);
	  	exchange.setProperty("bandera",bandera);
	  	exchange.setProperty("nombre",this.nombre);
	  	exchange.setProperty("penalizacion",this.penalizacion);
	  	exchange.setProperty("confiabilidad",this.confiabilidad);
	  	exchange.setProperty("numero",this.numero);
	}
  public int conexion(String username ,int bandera)
    throws Exception
  {
    Connection connection = null;
    Statement statement =null;
    ResultSet resultSet = null;
    int islogging;
    try
    {
      connection = getConnection();
      statement  =  connection.createStatement();
      resultSet = statement.executeQuery(this.sqlsearch);   
      int resul=cont(resultSet);
     
      if(resul>0) { 
    	  resultSet = statement.executeQuery(this.sqlsearch); 
	   	  while(resultSet.next()) {
	   		islogging =resultSet.getInt("islogging");
	   		if(islogging==1) {
	   			resultSet = statement.executeQuery(this.sqlsearch2); 
	   			 resul=cont(resultSet);
	   			if(resul>0) { 
	   				resultSet = statement.executeQuery(this.sqlsearch2); 
	   		   	  while(resultSet.next()) {
	   		   		  nombre =resultSet.getString("nombre");
	   		   		  penalizacion =resultSet.getString("penalizacion");
	   		   		  confiabilidad =resultSet.getString("confiabilidad");
	   		   		  numero =resultSet.getString("numero");
	   		   	  }
	   		   	  return bandera=1;
	   			}else {
	   				LOG.info("no existe en la BD");
		   			return bandera=2;
	   			}
	   		}else {
	   			LOG.info("usuario actualmente no esta ingresado");
	   			return bandera=3;
	   		}
	   	  }
      }else { 
		   LOG.info("no existe");
	       return bandera=4;
	   }
    }
    finally
    {
      closeCon(connection, statement,resultSet);
    }
    return 6;
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
