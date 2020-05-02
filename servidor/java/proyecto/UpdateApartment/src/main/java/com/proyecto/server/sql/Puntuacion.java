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




public class Puntuacion
{
  private static final Logger LOG = Logger.getLogger("UpdateLog");
  //@BeanInject("dsRegister")
  //private DataSource dataSource;
  
  @BeanInject("dataSource")
  private DataSource dataSource;
  
  
  private  String sqlsearch0,sqlsearch,sqlsearch2,sqlsearch3,sqlsearch31,sqlsearch4,sqlsearch5;
  
  Counter count=new Counter();
 
  public void process(Exchange exchange) throws Exception {
	  int bandera=0;
	   this.sqlsearch0="SELECT puntuacion  FROM comentarios WHERE emisor='address' and ID_inmueble='inmuid'";
	   this.sqlsearch="UPDATE comentarios SET puntuacion='point' WHERE emisor='address' and ID_inmueble='inmuid'";
	   this.sqlsearch2="SELECT puntuacion  FROM comentarios WHERE ID_inmueble='inmuid'";
	   this.sqlsearch3="UPDATE Inmueble SET confiabilidad='point' WHERE ID='inmuid'";
	   this.sqlsearch31="SELECT ID_VENDEDOR  FROM Inmueble WHERE ID='inmuid'";
	   this.sqlsearch4="SELECT confiabilidad  FROM Inmueble WHERE ID_VENDEDOR='inmuid'";
	   this.sqlsearch5="UPDATE Persona SET confiabilidad='point' WHERE ID='inmuid'";

	    String correo = (String) exchange.getIn().getHeader("correo");
	    String puntuacion = (String) exchange.getIn().getHeader("puntuacion");
	    String id = (String) exchange.getIn().getHeader("id_inmueble");
	    
	    
	    this.sqlsearch0 = this.sqlsearch0.replaceAll("address", correo);
	    this.sqlsearch0 = this.sqlsearch0.replaceAll("inmuid", id);
	    
	    this.sqlsearch = this.sqlsearch.replaceAll("address", correo);
	    this.sqlsearch = this.sqlsearch.replaceAll("point", puntuacion);
	    this.sqlsearch = this.sqlsearch.replaceAll("inmuid", id);
	    
	    
	    this.sqlsearch2 = this.sqlsearch2.replaceAll("inmuid", id);
	    
	    this.sqlsearch3 = this.sqlsearch3.replaceAll("inmuid", id);
	    this.sqlsearch31=this.sqlsearch31.replaceAll("inmuid", id);
	   bandera=conexion(bandera);
	   exchange.setProperty("bandera",bandera);
	}
  public int  conexion(int bandera)
    throws Exception
  {
    Connection connection = null;
    Statement statement =null;
    ResultSet resultSet = null;
    try
    {
      connection = getConnection();
      statement  =  connection.createStatement();
  
      resultSet = statement.executeQuery(this.sqlsearch0);   
      int resul=cont(resultSet);
     count.setCount(resul);
      if(!(resul>0)) { 
    	  statement.executeUpdate(this.sqlsearch);
          
          resultSet = statement.executeQuery(this.sqlsearch2);   
           resul=cont(resultSet);
         count.setCount(resul);
          if(resul>0) { 
        	  resultSet = statement.executeQuery(this.sqlsearch2); 
        	  int puntos=0;
    	   	  while(resultSet.next()) {
    	   		  puntos+=resultSet.getInt("puntuacion");
    	   	  }
    	   	  puntos=puntos/count.getCount();
    	   this.sqlsearch3 = this.sqlsearch3.replaceAll("point", Integer.toString(puntos));
    	   statement.executeUpdate(this.sqlsearch3); 
    	   resultSet = statement.executeQuery(this.sqlsearch31); 
    	   String idven="";
    	   while(resultSet.next()) {
    		   idven=resultSet.getString("ID_VENDEDOR");
    	   }
    	   this.sqlsearch4=this.sqlsearch4.replaceAll("inmuid", idven);
    	   resultSet = statement.executeQuery(this.sqlsearch4);   
    	   resul=cont(resultSet);
    	   count.setCount(resul);
    	   resultSet = statement.executeQuery(this.sqlsearch4); 
     	  puntos=0;
    	   	  while(resultSet.next()) {
    	   		  puntos+=resultSet.getInt("confiabilidad");
    	   	  }
    	   	  puntos=puntos/count.getCount();
    		 this.sqlsearch5 = this.sqlsearch5.replaceAll("inmuid", idven);
    		this.sqlsearch5 = this.sqlsearch5.replaceAll("point", Integer.toString(puntos));
    		statement.executeUpdate(this.sqlsearch5);
    		 return bandera=0;
    	   
          }else { 
    		   LOG.info("error raro");
    		   return bandera=1;
    	   }
      }
      return bandera=1;
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
