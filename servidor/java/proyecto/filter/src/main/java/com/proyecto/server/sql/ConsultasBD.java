package com.proyecto.server.sql;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.camel.BeanInject;
import org.apache.camel.Exchange;
import org.apache.camel.PropertyInject;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.server.Dto.Filtro;
import com.proyecto.server.process.Counter;



public class ConsultasBD
{
  private static final Logger LOG = Logger.getLogger("filterApartmentLog");

  
  @BeanInject("dataSource")
  private DataSource dataSource;
  
  private  String sqlsearch1, sqlsearch;
  Counter count=new Counter();
  List<List> map = new ArrayList<List>();
  private int numero, numero2;
  public void process(Exchange exchange) throws Exception {
	  int bandera=0;
	   this.sqlsearch1="SELECT inmu.ID,inmu.nombre_Inmueble,inmu.precio,inmu.Tipo,inmu.estado_inmueble,inmu.estado,inmu.barrio,inmu.direccion FROM (select ID,nombre_Inmueble,precio,Tipo,estado_inmueble,estado,barrio,direccion FROM Inmueble where Tipo=tieru AND estado=esteru AND estado_inmueble=estinmeru AND barrio=bareru AND localidad=locaeru AND precio BETWEEN cezro AND enormegrgr ORDER BY fecha_registro DESC LIMIT number) inmu"
	   		+ " WHERE inmu.direccion NOT IN (SELECT DISTINCT inm.direccion FROM (select ID,nombre_Inmueble,precio,Tipo,estado_inmueble,estado,barrio,direccion FROM Inmueble where Tipo=tieru AND estado=esteru AND estado_inmueble=estinmeru AND barrio=bareru AND localidad=locaeru AND precio BETWEEN cezro AND enormegrgr  ORDER BY fecha_registro DESC LIMIT numo) inm)";
	 
	   this.sqlsearch="SELECT COUNT(total.barrio) FROM (SELECT inmu.ID,inmu.nombre_Inmueble,inmu.precio,inmu.Tipo,inmu.estado_inmueble,inmu.estado,inmu.barrio,inmu.direccion FROM (select ID,nombre_Inmueble,precio,Tipo,estado_inmueble,estado,barrio,direccion FROM Inmueble where Tipo=tieru AND estado=esteru "
	   		+ "AND estado_inmueble=estinmeru AND barrio=bareru AND localidad=locaeru AND precio BETWEEN cezro AND enormegrgr ORDER BY fecha_registro DESC) inmu) total";
	   
	    String tipo = (String) exchange.getIn().getHeader("tipo");
	    String estado = (String) exchange.getIn().getHeader("estado");
	    String estado_inmueble = (String) exchange.getIn().getHeader("estado_inmueble");
	    String precio = (String) exchange.getIn().getHeader("precio");
	    String precio_mayor = (String) exchange.getIn().getHeader("precio_mayor");
	    String barrio = (String) exchange.getIn().getHeader("barrio");
	    String localidad= (String) exchange.getIn().getHeader("localidad");
	    
	    String numerot = (String) exchange.getIn().getHeader("numero");
		   
	    numero=Integer.parseInt(numerot);
	    numero=numero*10;
	    numero2=numero-10;
	    
	    	
	    this.sqlsearch1 = this.sqlsearch1.replaceAll("number", Integer.toString(numero));
	    this.sqlsearch1 = this.sqlsearch1.replaceAll("numo", Integer.toString(numero2));
	      
        
       // String phone = (String) exchange.getIn().getHeader("phone");
	  	bandera=conexion(bandera,tipo, estado,estado_inmueble,precio,precio_mayor,barrio,localidad);
	  	exchange.setProperty("bandera",bandera);
	  	exchange.setProperty("contb",contb);
	  	exchange.getIn().setBody(this.map);
	  	exchange.setProperty("counter",count);
	}
  
  public HashMap<String, String> permu(String tipo, String estado,  String estado_inmueble,String precio,String precio_mayor , String barrio, String localidad) {
	  HashMap<String, String> hmap = new HashMap<String, String>();
	  HashMap<String, String> hmap2 = new HashMap<String, String>();
	  
	  hmap.put("Tipo",tipo);
      hmap.put("estado",estado);
      hmap.put("estado_inmueble",estado_inmueble);
      hmap.put("precio",precio);
      hmap.put("precio_mayor",precio_mayor);
      hmap.put("barrio",barrio);
      hmap.put("localidad",localidad);
      
      Set set = hmap.entrySet();
      Iterator iterator = set.iterator();
      System.out.println(tipo+" "+estado+" "+estado_inmueble+" "+precio+" "+precio_mayor+" "+barrio+" "+localidad+" ");
      
      
      while(iterator.hasNext()) {
          Map.Entry mentry = (Map.Entry)iterator.next();
          if(mentry.getValue()==null || mentry.getValue()=="") {
        	  if(mentry.getKey()=="precio") {
        		  String temp=(String) mentry.getKey();
            	  String temp2="0";
            	  hmap2.put(temp, temp2);
        	  }else {
        		  if(mentry.getKey()=="precio_mayor") {
        			  String temp=(String) mentry.getKey();
                	  String temp2="9999999999999";
                	  hmap2.put(temp, temp2);
        		  }else {
        			  String temp=(String) mentry.getKey();
                	  String temp2="ANY(SELECT "+temp+" IN('*') FROM DUAL)";
                	  hmap2.put(temp,temp2); 
        		  }  
        	  } 
          }else {
        	  hmap2.put((String)mentry.getKey(),"'"+(String)mentry.getValue()+"'"); 
          }
       }
      return hmap2;
  }
  String contb="";
  public int conexion(int bandera,String tipo, String estado,  String estado_inmueble,String precio,String precio_mayor, String barrio, String localidad)
    throws Exception
  {
    Connection connection = null;
    Statement statement =null;
    ResultSet resultSet = null;
    String ID=null;
    try
    {
      connection = getConnection();
      statement  =  connection.createStatement();
      HashMap<String, String> hmap =permu(tipo,estado,estado_inmueble,precio,precio_mayor,barrio,localidad);
      
      Set set = hmap.entrySet();
      Iterator iterator = set.iterator();
      
      while(iterator.hasNext()) {
         Map.Entry mentry = (Map.Entry)iterator.next();
         switch ((String)mentry.getKey()) {
	         case "Tipo":
	        	 this.sqlsearch1=this.sqlsearch1.replaceAll("tieru", (String) mentry.getValue());
	        	 this.sqlsearch=this.sqlsearch.replaceAll("tieru", (String) mentry.getValue());
	           break;
	         case "estado":
	        	 this.sqlsearch1=this.sqlsearch1.replaceAll("esteru", (String) mentry.getValue());
	        	 this.sqlsearch=this.sqlsearch.replaceAll("esteru", (String) mentry.getValue());
	           break;
	         case "estado_inmueble":
	        	 this.sqlsearch1=this.sqlsearch1.replaceAll("estinmeru", (String) mentry.getValue());
	        	 this.sqlsearch=this.sqlsearch.replaceAll("estinmeru", (String) mentry.getValue());
	           break;
	         case "precio":
	        	 this.sqlsearch1=this.sqlsearch1.replaceAll("cezro", (String) mentry.getValue());
	        	 this.sqlsearch=this.sqlsearch.replaceAll("cezro", (String) mentry.getValue());
	           break;
	         case "precio_mayor":
	        	 this.sqlsearch1=this.sqlsearch1.replaceAll("enormegrgr", (String) mentry.getValue());
	        	 this.sqlsearch=this.sqlsearch.replaceAll("enormegrgr", (String) mentry.getValue());
	           break;
	         case "barrio":
	        	 this.sqlsearch1=this.sqlsearch1.replaceAll("bareru", (String) mentry.getValue());
	        	 this.sqlsearch=this.sqlsearch.replaceAll("bareru", (String) mentry.getValue());
	           break;
	         case "localidad":
	        	 this.sqlsearch1=this.sqlsearch1.replaceAll("locaeru", (String) mentry.getValue());
	        	 this.sqlsearch=this.sqlsearch.replaceAll("locaeru", (String) mentry.getValue());
	           break;
	       }
   
      }
      resultSet = statement.executeQuery(this.sqlsearch1);   
      int resul=cont(resultSet);
     
     count.setCount(resul);
     
      if(resul>0) { 
    	  resultSet = statement.executeQuery(this.sqlsearch1); 
    	  List<List> map = new ArrayList<List>();
	   	  while(resultSet.next()) {
	   		List<String> data = new ArrayList<String>();
	   		data.add(resultSet.getString("ID"));
	   		data.add(resultSet.getString("nombre_Inmueble"));
	   		data.add(resultSet.getString("precio"));
	   		data.add(resultSet.getString("Tipo"));
	   		data.add(resultSet.getString("estado_inmueble"));
	   		data.add(resultSet.getString("estado"));
	   		data.add(resultSet.getString("barrio"));
	   		map.add(data);
	   	  }
	   this.map=map;
	   resultSet = statement.executeQuery(this.sqlsearch);
	   
	   while(resultSet.next()) {		
		   contb=resultSet.getString("COUNT(total.barrio)");
	   }
	     
	   return bandera=0;
      }else { 
		   LOG.info("no existen inmuebles");
		   contb="0";
	       return bandera=1;
	   }
    }
    finally
    {
      closeCon(connection, statement, resultSet);
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
