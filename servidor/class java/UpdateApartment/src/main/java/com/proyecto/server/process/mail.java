package com.proyecto.server.process;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class mail  implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        String email = (String) exchange.getIn().getHeader("correo");
        String password = (String) exchange.getIn().getHeader("pass");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("ViewerRealmReal@gmail.com", "ViewerRealm69");
                        //return new PasswordAuthentication("afsolanoc95@gmail.com", "95102508387a");

                    }
                });
       
        try {
 
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ViewerRealmReal@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("cambio de constraseña");
            message.setText((String) exchange.getIn().getBody()
            		+ " su usuario es "+email+ " contraseña: "+password);
            Transport.send(message);
 
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
	}
}
