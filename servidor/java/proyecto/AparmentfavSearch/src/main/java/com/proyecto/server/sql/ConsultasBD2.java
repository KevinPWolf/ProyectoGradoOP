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



public class ConsultasBD2
{
  private static final Logger LOG = Logger.getLogger("FavoriteDataLog");

  
  @BeanInject("dataSource")
  private DataSource dataSource;
  private  String sqlsearch, sqlsearch2,sqlsearch3;
  Counter count=new Counter();
  List<List> map = new ArrayList<List>();

  public void process(Exchange exchange) throws Exception {
	  int bandera=0;
	  this.sqlsearch = "SELECT ID FROM Persona WHERE correo = 'usuario'"; 
	  this.sqlsearch2= "SELECT inmueblexpersona.ID_INMUEBLE, Inmueble.nombre_Inmueble, Inmueble.precio, Inmueble.Tipo , Inmueble.estado_inmueble, Inmueble.estado, Inmueble.barrio FROM inmueblexpersona INNER JOIN Inmueble ON Inmueble.ID = inmueblexpersona.ID_INMUEBLE WHERE inmueblexpersona.ID_PERSONA = 'persoid'";
	    
	    this.sqlsearch = this.sqlsearch.replaceAll("usuario", (String) exchange.getIn().getHeader("email"));
	    
		   this.sqlsearch3="SELECT green, blue, red, alpha FROM fotos where ID_INMUEBLE='idein'";
  
	    
	   bandera=conexion(bandera);
	  	exchange.setProperty("bandera",bandera);
	  	exchange.getIn().setBody(this.map);
	  	exchange.setProperty("counter",count);
	}
  
  
  public int conexion(int bandera)
    throws Exception
  {
    Connection connection = null;
    Statement statement =null;
    ResultSet resultSet = null;
    ResultSet resultSet2 = null;
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
	   	 this.sqlsearch2 = this.sqlsearch2.replaceAll("persoid",id_persona );
	   	  
	   	 
	   	resultSet = statement.executeQuery(this.sqlsearch2);   
	      resul=cont(resultSet); 
	     count.setCount(resul);
	      if(resul>0) { 
	    	  resultSet = statement.executeQuery(this.sqlsearch2); 
	    	  List<List> map = new ArrayList<List>();
		   	  while(resultSet.next()) {
		   		List<String> data = new ArrayList<String>();
		   		data.add(resultSet.getString("ID_INMUEBLE"));
		   		data.add(resultSet.getString("nombre_Inmueble"));
		   		data.add(resultSet.getString("precio"));
		   		data.add(resultSet.getString("Tipo"));
		   		data.add(resultSet.getString("estado_inmueble"));
		   		data.add(resultSet.getString("estado"));
		   		data.add(resultSet.getString("barrio"));
		   		this.sqlsearch3 = this.sqlsearch3.replaceAll("idein", resultSet.getString("ID"));
			   	  resultSet2 = statement.executeQuery(this.sqlsearch3);
			   	while(resultSet2.next()) {
			   		data.add(resultSet2.getString("green"));
			   		data.add(resultSet2.getString("blue"));
			   		data.add(resultSet2.getString("red"));
			   		data.add(resultSet2.getString("alpha"));
			   	}
		   		map.add(data);
		   	  }
		   this.map=map;
	   	 

         LOG.info("inmuebles mostrados");
         return bandera=0;
      }
	      else { 
			   LOG.info("no existe apartamentos favoritos");
		       return bandera=1;
		   }
	      }
      else { 
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
