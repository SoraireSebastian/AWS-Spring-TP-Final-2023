package com.javatechie.aws.sns;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication (exclude = {ContextStackAutoConfiguration.class, ContextRegionProviderAutoConfiguration.class})
@RestController
public class SpringbootAwsSnsExampleApplication {

    @Autowired
    private AmazonSNSClient snsClient;

    String TOPIC_ARN="" ;

	@GetMapping("/suscripcion/{email}")
	public String addSubscription(@PathVariable String email) {
		SubscribeRequest request = new SubscribeRequest(TOPIC_ARN, "email", email);
		snsClient.subscribe(request);
		return "La solicitud de suscripción está pendiente. Para confirmar la suscripción, revisa tu correo electrónico: " + email;
	}

	 @GetMapping("/enviarnotificacion")
	public String publishMessageToTopic(){
		 PublishRequest publishRequest=new PublishRequest(TOPIC_ARN,buildEmailBody(),"Notification: Network connectivity issue");
		 snsClient.publish(publishRequest);
		 return "Notificacion enviada correctamente !!";
	}



	private String buildEmailBody(){
		return "Estimado Cliente ,\n" +
				"\n" +
				"\n" +
				"Coneccion Fallida."+"\n"+
				"No se puede acceder a todos los servidores del centro de datos de HIA. ¡Los APU estan trabajando en ello! \n" +
				"La notificación se enviará tan pronto como se resuelva el problema. Si tiene alguna pregunta sobre este mensaje, no dude en comunicarse con el equipo de soporte de servicios de APU";
	}

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAwsSnsExampleApplication.class, args);
    }

}
