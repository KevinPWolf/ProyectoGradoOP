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
  private String sqlUpdate;
  private  String sqlsearch2;
  private String sqlUpdate2;
  
  public void process(Exchange exchange) throws Exception {
	  
	  int bandera=0;
	   this.sqlsearch="SELECT * FROM Persona WHERE correo = 'usuario'";
	  this.sqlUpdate="UPDATE Persona SET islogging = '1' WHERE correo = 'usuario'";
	  
	    String email = (String) exchange.getIn().getHeader("email");
	    String password = (String) exchange.getIn().getHeader("password");

	   this.sqlsearch = this.sqlsearch.replaceAll("usuario", email);
	    
	    MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        password=sb.toString();
        this.sqlUpdate = this.sqlUpdate.replaceAll("usuario", email);       
	  	bandera=conexion(email,password,bandera);
	  	exchange.setProperty("bandera",bandera);
	}
  public int conexion(String username,String password,int bandera)
    throws Exception
  {
    Connection connection = null;
    Statement statement =null;
    ResultSet resultSet = null;
    int islogging;
    String contrase=null;
    try
    {
      connection = getConnection();
      statement  =  connection.createStatement();
      resultSet = statement.executeQuery(this.sqlsearch);    
      int resul=cont(resultSet);
      System.out.println(resul); 
      if(resul>0) { 
    	  resultSet = statement.executeQuery(this.sqlsearch); 
	   	  while(resultSet.next()) {
	   		islogging =resultSet.getInt("islogging");
	   		contrase=resultSet.getString("contrasena");
	   		if(islogging==0 || islogging==1 ) {
	   			if(password.equals(contrase)) {
	   		     statement.executeUpdate(this.sqlUpdate);
	   				LOG.info("login-success");
	   				return bandera=3;	
	   			}else {
	   				LOG.info("usuario o contraseña incorrectos");
	   				return bandera=1;
	   			}
	   		}else {
	   			LOG.info("usuario actualmente ingresado");
	   			return bandera=2;	
	   		}
	   	  }  
	   }else { 
		   LOG.info("usuario o contraseña incorrectos");
	       return bandera=1;
	   }
    }
    finally
    {
      closeCon(connection, statement, resultSet);
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
