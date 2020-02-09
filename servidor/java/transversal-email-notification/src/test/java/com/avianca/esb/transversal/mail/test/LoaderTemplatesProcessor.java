package com.avianca.esb.transversal.mail.test;

import org.apache.camel.Exchange;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean que se encarga de cargar las templates usando el URL Handler profile de fabric
 */
public class LoaderTemplatesProcessor {
	private Map<String,URL> cacheMap;

    /**
     * Instantiates a new Loader templates processor.
     *
     * @param strTemplates listado de plantillas separadas por coma
     * @throws Exception the exception
     */
    public LoaderTemplatesProcessor(String strTemplates) throws Exception{
		String[] arrTemplates = strTemplates.split(",");
		cacheMap = new HashMap<>();
		for(String templateName : arrTemplates){
			cacheMap.put(templateName, new URL("file:src/main/fabric8/" + templateName + ".vm"));
		}
	}

    /**
     * Carga una plantilla en el header CamelVelocityTemplate
     *
     * @param ex exchange con el header template de la plantilla a cargar
     */
    public void loadTemplate(Exchange ex){
		String nameTemplate = ex.getIn().getHeader("template", String.class);
		URL urlTemplate = cacheMap.get(nameTemplate);
		ex.getIn().setHeader("CamelVelocityTemplate", urlTemplate);
	}
}
