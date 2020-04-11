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
  private static final Logger LOG = Logger.getLogger("AparmentDataLog");
  //@BeanInject("dsRegister")
  //private DataSource dataSource;
  
  @BeanInject("dataSource")
  private DataSource dataSource;
  
  
  private  String sqlsearch;
  
  private int numero, numero2;
  Counter count=new Counter();
  List<List> map = new ArrayList<List>();
  public void process(Exchange exchange) throws Exception {
	  int bandera=0;
	   this.sqlsearch="SELECT Inmueble.ancho, Inmueble.largo, Inmueble.estado_inmueble, Inmueble.precio, Inmueble.estado, Inmueble.barrio, Inmueble.direccion, Inmueble.nombre_Inmueble, Inmueble.informacion_extra, Inmueble.Tipo, telefonos.numero, Pisos.paredes, Pisos.habitaciones, Pisos.muebles, Pisos.texturas, Pisos.posiciones_muebles FROM Inmueble INNER JOIN telefonos ON Inmueble.ID_VENDEDOR = telefonos.ID_PERSONA INNER JOIN Pisos ON Inmueble.ID = Pisos.ID_inmueble where Inmueble.ID=numinid";
	    String numerot = (String) exchange.getIn().getHeader("id");
	   
	    this.sqlsearch = this.sqlsearch.replaceAll("numinid", numerot);

	   
	    
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
    try
    {
      connection = getConnection();
      statement  =  connection.createStatement();
      resultSet = statement.executeQuery(this.sqlsearch);   
      int resul=cont(resultSet);
     
     count.setCount(resul);
     
      if(resul>0) { 
    	  resultSet = statement.executeQuery(this.sqlsearch); 
    	  List<List> map = new ArrayList<List>();
	   	  while(resultSet.next()) {
	   		List<String> data = new ArrayList<String>();
	   		data.add(resultSet.getString("ancho"));
	   		data.add(resultSet.getString("largo"));
	   		data.add(resultSet.getString("estado_inmueble"));
	   		data.add(resultSet.getString("precio"));
	   		data.add(resultSet.getString("estado"));
	   		data.add(resultSet.getString("barrio"));
	   		data.add(resultSet.getString("direccion"));
	   		data.add(resultSet.getString("nombre_Inmueble"));
	   		data.add(resultSet.getString("informacion_extra"));
	   		data.add(resultSet.getString("Tipo"));
	   		data.add(resultSet.getString("numero"));
	   		data.add(resultSet.getString("paredes"));
	   		data.add(resultSet.getString("habitaciones"));
	   		data.add(resultSet.getString("muebles"));
	   		data.add(resultSet.getString("texturas"));
	   		data.add(resultSet.getString("posiciones_muebles"));
	   		map.add(data);
	   	  }
	   this.map=map;
	   return bandera=0;
      }else { 
		   LOG.info("no existe informacion de inmueble");
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
