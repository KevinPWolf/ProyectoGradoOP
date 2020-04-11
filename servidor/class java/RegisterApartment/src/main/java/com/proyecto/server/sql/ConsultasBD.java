package com.proyecto.server.sql;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.Statement;
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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.server.Dto.pisos;



public class ConsultasBD
{
  private static final Logger LOG = Logger.getLogger("RegisterApartmentLog");

  
  @BeanInject("dataSource")
  private DataSource dataSource;
  
  private  String sqlsearch1;
  private  String sqlsearch;
  private String sqlinsert;
  private  String sqlsearch2;
  private String sqlinsert2;
  
  public void process(Exchange exchange) throws Exception {
	  int bandera=0;
	   this.sqlsearch1="SELECT ID FROM Persona WHERE correo = 'email'";
	   this.sqlsearch="SELECT direccion FROM Inmueble WHERE direccion = 'address'";
	  this.sqlinsert="INSERT INTO Inmueble(ancho,largo, estado_inmueble, precio, estado, barrio, direccion,nombre_inmueble,informacion_extra,Tipo,ID_VENDEDOR) VALUE('width','depth', 'state_property','price','state','neighborhood','address','name','inext','type','correo')";
	  this.sqlsearch2="SELECT ID FROM Inmueble WHERE direccion = 'address'";
	  this.sqlinsert2="INSERT INTO Pisos(paredes,habitaciones,muebles,texturas,posiciones_muebles,ID_inmueble) VALUE('walls','rooms','furniture','texg','posfur','Ifuri')";
	  
	    String nombre = (String) exchange.getIn().getHeader("nombre");
	    String tipo = (String) exchange.getIn().getHeader("tipo");
	    String estado = (String) exchange.getIn().getHeader("estado");
	    String estado_inmueble = (String) exchange.getIn().getHeader("estado_inmueble");
	    String precio = (String) exchange.getIn().getHeader("precio");
	    String direccion = (String) exchange.getIn().getHeader("direccion");
	    String barrio = (String) exchange.getIn().getHeader("barrio");
	    String ancho = (String) exchange.getIn().getHeader("ancho");
	    String largo = (String) exchange.getIn().getHeader("largo");
	    String informacion_extra = (String) exchange.getIn().getHeader("informacion_extra");
	    String correo = (String) exchange.getIn().getHeader("correo");

		List<pisos> piso =(List<pisos>) exchange.getIn().getHeader("pisos");
	    this.sqlsearch = this.sqlsearch.replaceAll("address", direccion);
	   //System.out.println("el piso "+piso);
	    
	  
	    
        this.sqlinsert = this.sqlinsert.replaceAll("width", ancho);
        this.sqlinsert = this.sqlinsert.replaceAll("depth", largo);
        this.sqlinsert = this.sqlinsert.replaceAll("state_property", estado_inmueble);
        this.sqlinsert = this.sqlinsert.replaceAll("price", precio); 
        this.sqlinsert = this.sqlinsert.replaceAll("state", estado);
        this.sqlinsert = this.sqlinsert.replaceAll("neighborhood", barrio);
        this.sqlinsert = this.sqlinsert.replaceAll("address", direccion);  
        this.sqlinsert = this.sqlinsert.replaceAll("name", nombre); 
        this.sqlinsert = this.sqlinsert.replaceAll("inext", informacion_extra);
        this.sqlinsert = this.sqlinsert.replaceAll("type", tipo);
        
        
        this.sqlsearch1=this.sqlsearch1.replaceAll("email", correo);
        
        
        this.sqlsearch2 = this.sqlsearch2.replaceAll("address", direccion);
                
        
       // String phone = (String) exchange.getIn().getHeader("phone");
	  	bandera=conexion(bandera,piso);
	  	exchange.setProperty("bandera",bandera);
	}
  public int conexion(int bandera,List<pisos> pisos)
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
      resultSet = statement.executeQuery(this.sqlsearch1);    
      while(resultSet.next()) {
		  ID =resultSet.getString("ID");  
	  } 
      this.sqlinsert = this.sqlinsert.replaceAll("correo", ID); 
      resultSet = statement.executeQuery(this.sqlsearch); 
      int cont=cont(resultSet);
      if(cont>0) {
    	  LOG.info("inmueble ya registrado");
    	 return bandera=1;
      }else {
    	  resultSet = statement.executeQuery(this.sqlsearch2);
    	   cont=cont(resultSet);
    	  if(cont>0) {
    		  
    	  }else {
    		  statement.executeUpdate(this.sqlinsert);
    	  }
           resultSet = statement.executeQuery(this.sqlsearch2);       
    	  while(resultSet.next()) {
    		  ID =resultSet.getString("ID");  
    	  } 
    	  
    	  for(int i=0;i<pisos.size();i++) {  
    		  String sqlinsert3= this.sqlinsert2;
    		  sqlinsert3 = sqlinsert3.replaceAll("walls", pisos.get(i).getParedes());
              sqlinsert3 = sqlinsert3.replaceAll("rooms", pisos.get(i).getHabitaciones());
              sqlinsert3 = sqlinsert3.replaceAll("furniture", pisos.get(i).getMuebles());
              sqlinsert3 = sqlinsert3.replaceAll("texg", pisos.get(i).getTexturas());
              sqlinsert3 = sqlinsert3.replaceAll("posfur", pisos.get(i).getPosiciones_muebles());
              sqlinsert3 = sqlinsert3.replaceAll("Ifuri", ID);
              statement.executeUpdate(sqlinsert3);
               
    	  }
          LOG.info("Inmueble registrado");
         return bandera=0;
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
