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
  @BeanInject("dsRegister")
  private DataSource dataSource;

  private  String sqlsearch;
  private String sqlinsert;
  private  String sqlsearch2;
  private String sqlinsert2;
  
  public void process(Exchange exchange) throws Exception {
	  
	   this.sqlsearch="SELECT correo FROM Persona WHERE correo = 'usuario'";
	  this.sqlinsert="INSERT INTO Persona(nombre,correo, contrasena, penalizacion, confiabilidad, islogging, registro) VALUE('name','user', 'password','0','0','0','date')";
	  this.sqlsearch2="SELECT ID FROM Persona WHERE correo = 'usuario'";
	  this.sqlinsert2="INSERT INTO telefonos(numero,ID_PERSONA) VALUE('phone','IDE')";
	  
	    String email = (String) exchange.getIn().getHeader("email");
	    String password = (String) exchange.getIn().getHeader("password");
	    String name = (String) exchange.getIn().getHeader("name");
	    String date = (String) exchange.getIn().getHeader("hora");
	   this.sqlsearch = this.sqlsearch.replaceAll("usuario", email);
	   
	    
	    MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        password=sb.toString();
	    
        this.sqlinsert = this.sqlinsert.replaceAll("name", name);
        this.sqlinsert = this.sqlinsert.replaceAll("user", email);
        this.sqlinsert = this.sqlinsert.replaceAll("password", password);
        this.sqlinsert = this.sqlinsert.replaceAll("date", date);        
        this.sqlsearch2 = this.sqlsearch2.replaceAll("usuario", email);
        
        
        String phone = (String) exchange.getIn().getHeader("phone");
	  	conexion(email,phone);
	  	
	}
  public void conexion(String username,String phone)
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
      resultSet = statement.executeQuery(this.sqlsearch);    

      if(cont(resultSet)>0) {
    	  LOG.info("usuario ya existe");
      }else {
          statement.executeUpdate(this.sqlinsert);
           resultSet = statement.executeQuery(this.sqlsearch2);       
    	  while(resultSet.next()) {
    		  ID =resultSet.getString("ID");  
    	  } 
          this.sqlinsert2 = this.sqlinsert2.replaceAll("phone", phone);
          this.sqlinsert2 = this.sqlinsert2.replaceAll("IDE", ID);
          statement.executeUpdate(this.sqlinsert2);
          LOG.info("usuario registrado");
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
    	//conn = DriverManager.getConnection("jdbc:mysql://fdb19.awardspace.net:3306/3296648_viewerrealm", "3296648_viewerrealm", "123456789an");
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
