package com.proyecto.server.process;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;


public class mail  implements Processor {

	
	@Autowired
	private Configuration config;
	
	@Autowired
	private JavaMailSender sender;
	
	
	@Override
	public void process(Exchange exchange) throws Exception {

        String email = (String) exchange.getIn().getHeader("correo");
        String password = (String) exchange.getIn().getHeader("pass");
        try {

        	MimeMessage message = sender.createMimeMessage();
            
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
    				StandardCharsets.UTF_8.name());

    		

    		Template t = config.getTemplate("email-template.ftl");
    		Map<String, Object> model = new HashMap<>();
    		model.put("nombreApp", "Viewer Realm");
    		model.put("nombreProy", "REALIDAD VIRTUAL INTERACTIVA ENFOCADO A INMOBILIARIAS MEDIANTE DISPOSITIVOS MÓVILES");
    		model.put("mensaje",(String) exchange.getIn().getBody());
    		model.put("correo",email);
    		model.put("pass",password);
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t,model);

			helper.setTo(email);
			helper.setText(html, true);
			helper.setSubject("cambio de contraseña");
			helper.setFrom("ViewerRealmReal@gmail.com");
			sender.send(message);
			
 
        } catch (MessagingException | IOException | TemplateException  e) {
            throw new RuntimeException(e);
        }
	}
}
