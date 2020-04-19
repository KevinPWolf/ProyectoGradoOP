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




public class EliminarInmueble
{
  private static final Logger LOG = Logger.getLogger("UpdateLog");
  //@BeanInject("dsRegister")
  //private DataSource dataSource;
  
  @BeanInject("dataSource")
  private DataSource dataSource;
  
  
  private  String sqlsearch,sqlsearch0,sqlsearch01,sqlsearch1,sqlsearch2,sqlsearch3,sqlsearch4;
  

 
  public void process(Exchange exchange) throws Exception {
	  
	  this.sqlsearch="SELECT * FROM Inmueble WHERE estado='vendido'";
	  this.sqlsearch0="select DATE_ADD('fecha',INTERVAL 30 MINUTE)";
	  
	  
	  
	  
	   this.sqlsearch01="DELETE FROM Inmueble WHERE ID=numero";
	   this.sqlsearch1="DELETE FROM inmueblexpersona WHERE ID_INMUEBLE=numero";
	   this.sqlsearch2="DELETE FROM comentarios WHERE ID_inmueble=numero";
	   this.sqlsearch3="DELETE FROM Pisos WHERE ID_inmueble=numero";
	   this.sqlsearch4="DELETE FROM fotoxinmueble WHERE ID_INMUEBLE=numero";
	   

	   
   
	   conexion();
	}
  public void conexion()
    throws Exception
  {
    Connection connection = null;
    Statement statement =null;
    ResultSet resultSet = null;
    ResultSet resultSet2 = null;
    try
    {
      connection = getConnection();
      statement  =  connection.createStatement(); 
      
      
      resultSet = statement.executeQuery(this.sqlsearch);   
      int resul=cont(resultSet);
      if(resul>0) { 
    	  resultSet = statement.executeQuery(this.sqlsearch); 
    	  List<List> map = new ArrayList<List>();
	   	  while(resultSet.next()) {
	   		String fecha=resultSet.getString("fecha_registro");
	   		String numerot =resultSet.getString("ID");
	   		this.sqlsearch0 = this.sqlsearch0.replaceAll("fecha", fecha);
	   		resultSet2 = statement.executeQuery(this.sqlsearch0);
	   		String temp="DATE_ADD('fecha',INTERVAL 30 MINUTE)";
	   		temp = temp.replaceAll("fecha", fecha);
	   		String fecha2=resultSet2.getString(temp);
	   		int pull= fecha2.compareTo(fecha);
	       if(pull>0){
	    	   resultSet2 = statement.executeQuery("select NOW() FROM DUAL");
	    	   fecha=resultSet2.getString("NOW()");
	    	   pull= fecha.compareTo(fecha2);
	    	   if(pull>=0){
	    		   
	    		    this.sqlsearch1 = this.sqlsearch1.replaceAll("numero", numerot);
	    		    this.sqlsearch2 = this.sqlsearch2.replaceAll("numero", numerot);
	    		    this.sqlsearch3 = this.sqlsearch3.replaceAll("numero", numerot);
	    		    this.sqlsearch4 = this.sqlsearch4.replaceAll("numero", numerot);
	    		    this.sqlsearch01 = this.sqlsearch01.replaceAll("numero", numerot);
	    		    statement.executeUpdate(this.sqlsearch1);
	    		      statement.executeUpdate(this.sqlsearch2);
	    		      statement.executeUpdate(this.sqlsearch3);
	    		      statement.executeUpdate(this.sqlsearch4);
	    		      statement.executeUpdate(this.sqlsearch01);
	    		      System.out.println("elimino inmueble "+numerot);
	    		    
	    	   }else {
	    		   System.out.println("aun no se elimina");
	    	   }

	       }else{
	           System.out.println("esta mal");
	       }
	   	  }
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
