# Ruta transversal de notificaciones de Correo Electronico.
La ruta transversal de notificaciones permite simplificar la forma en la que las rutas pueden enviar mensajes via email
manteniendo un formato estandarizado.Utiliza colas, camel-mail, y plantillas tipo velocity.

### Invocacion de la ruta por medio de direct-vm
Se puede realizar la invocacion por medio de un endpoint producer utilizando el componente direct-vm 
con el nombre emailNotificationRoute

    <to uri="direct-vm:emailNotificationRoute" pattern="InOnly"/>

### Invocacion de la ruta por medio de AMQ
Se puede realizar la invocacion enviando el mensaje directamente a la cola parametrizada.

    <to uri="activemq:queue:TRANSVERSAL.MAIL.NOTIFICATION?jmsMessageType=Map"/>

En ambos casos de debe construir un mensaje en el body tipo Map, la ruta de notificaciones lee el mensaje de la cola, 
realiza una validacion de los campos requeridos y envia el mensaje via correo electronico.

### Invocacion de la ruta a traves del web service
Se puede realizar la invocación de la ruta por medio de un web service expuesto con la siguiente estructura de entrada:

		{
		"idAplication":555,
		"messageTemplate":"465484654",
		"mail":[
			{
				"destinatario":"pepito@micorreo.com",
				"parametros":[
					[
					"asunto",
					"Prueba Adjuntos Notificaciones"
					],
					[
					"NombreServicio",
					"Prueba Hola Mundo"
					],
					[
					"TipoServicio",
					"Transversal Mail Notification"
					],
					[
					"DescripcionError",
					"Esta es una notificacion de pruebas"
					]
				],
				"adjuntos":[
					"ejemplo1.xml",
					"ejemplo2.xls"
				]
			}
		]
		}

Los parametros idAplication,messageTemplate, destinatario, asunto son obligatorios, los demas parametros son opcionales
y pueden agregarse en caso de que una plantilla los requiera.

### Body de entrada de los endpoint AMQ y Direct-vm

El mensaje (Body) de entrada debe ser de tipo `Map<String,String>` con las siguientes claves:

| campo            | Obligatoriedad | Tipo de dato | Descripcion                                                                          |
|------------------|----------------|--------------|--------------------------------------------------------------------------------------|
| from             | requerido      | String       | Direccion email del remitente                                                        |
| to               | requerido      | String       | Direccion email del destinatario                                                     |
| subject          | requerido      | String       | Asunto del correo                                                                    |
| template         | opcional       | String       | Nombre de la plantilla, si esta nulo o vacio se toma la plantilla generica (generic) |
| NombreServicio   | opcional       | String       | Campo descriptivo del servicio                                                       |
| TipoServicio     | opcional       | String       | Campo descriptivo del servicio                                                       |
| DescripcionError | opcional       | String       | Descripcion de la incidencia                                                         |

### Plantillas de correo electronico
Las plantillas de correo electronico son archivos tipo _Velocity_ que se cargan en el perfil de fabric8, estos archivos 
se pueden estructurar con tags html. El tipo de contenido del email es html.


Para añadir nuevas plantillas de correo a la ruta, se crea una plantilla tipo *Velocity* dentro del perfil.
Luego de esto se agrega el nombre de la plantilla en la propiedad `successfactor.mail.strTemplateList` del archivo
de propiedades de la ruta que contiene la lista de plantillas separadas por comas.
Por ultimo se refresca el perfil para actualizar los cambios.

### propiedades del mapa adicionales
Para cargar propiedades adicionales unicamente deben añadirse dentro del mapa del body que se envia a la
cola, luego pueden usarse como propiedades de mapa en las plantillas _Velocity_. Las propiedades pueden invocarse 
desde las plantillas usando la sintaxis correspondiente, por ejemplo:

    <p>propiedad1: ${body["propiedad1"]}</p>
    <p>propiedad2: ${body["propiedad2"]}</p>
    <p>propiedadN: ${body["propiedadn"]}</p>


### Processor de camel de ejemplo para generar el Mapa

Para la creacion del body de tipo Map hacia el endpoint direct-vm o AMQ puede emplearse el siguiente Processor de ejemplo:


    import java.util.HashMap;
    import java.util.Map;
	import org.apache.camel.Exchange;
	import org.apache.camel.Processor;
	import org.springframework.stereotype.Component;
	
	@Component
	public class PrepareMailingNotificationProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Map<String, Object> map = new HashMap<>();
		// set required fields
		map.put("from", String.valueOf(exchange.getProperty("mailFrom")));
		map.put("to", String.valueOf(exchange.getProperty("mailTo")));
		map.put("subject", String.valueOf(exchange.getProperty("mailSubject")));
		map.put("template", String.valueOf(exchange.getProperty("mailTemplate")));
		// set optional fields
		map.put("NombreServicio", String.valueOf(exchange.getIn().getHeader("NombreServicio")));
		map.put("TipoServicio", String.valueOf(exchange.getIn().getHeader("TipoServicio")));
		map.put("DescripcionError", String.valueOf(exchange.getIn().getHeader("DescripcionError")));
		exchange.getIn().setBody(map, Map.class);
		}
	}
	

### Soporte de archivos Adjuntos
Desde la version 1.0.4 se pueden enviar archivos adjuntos en los correos electronicos. Las siguientes claves se establecen
en el mapa para enviar un archivo adjunto.

| campo                           | Obligatoriedad | Tipo de dato | Descripcion                                                                                                                |
|---------------------------------|----------------|--------------|----------------------------------------------------------------------------------------------------------------------------|
| attachment.\[# Adjunto\].name     | requerido      | String       | Nombre del archivo adjunto                                                                                                 |
| attachment.\[# Adjunto\].mimeType | requerido      | String       | Tipo de archivo segun el mimeType [mimeTypes](https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types) |
| attachment.\[# Adjunto\].content  | requerido      | byte[]       | Array de Bytes en formato *Base64* que representa el contenido del archivo                                                   |
  
  
Donde \[# Adjunto\] representa el numero del adjunto, para enviar varios archivos adjuntos las claves/valores pueden ser:
 
* attachment.1.name=Avianca.pdf
* attachment.1.content=[B@5ad58361,
* attachment.1.mimeType=application/pdf
* attachment.2.name=Avianca.zip
* attachment.2.content=[B@5ad58361,
* attachment.2.mimeType=application/zip
* attachment.n.name=...
* attachment.n.content=...
* attachment.n.mimeType=...

