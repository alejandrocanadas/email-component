package com.example.mail.listener;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.mail.config.RabbitConfig;
import com.example.mail.models.EmailDTO;
import com.example.mail.services.EmailServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PagosListener {
    private final EmailServiceImplementation emailService;
    private final ObjectMapper mapper = new ObjectMapper();

    public PagosListener(EmailServiceImplementation emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void recibirMensaje(String json) {
        try {
            System.out.println("JSON recibido desde Pagos: " + json);

            Map<String, Object> datos = mapper.readValue(json, Map.class);

            String correo = datos.get("correo").toString();
            String nombre = datos.get("nombre").toString();
            String estado = datos.get("estado").toString();
            String total = datos.get("total").toString();

            String mensajeHTML = String.format(
                    "Hola %s, tu transacción fue <strong>%s</strong>. <br/>" +
                            "El valor total fue: <strong>$%s</strong>.",
                    nombre, estado, total);

            EmailDTO emailDTO = new EmailDTO(
                    correo,
                    "Resultado de tu pago",
                    mensajeHTML,
                    "no-reply@pagos.com");

            emailService.sendEmail(emailDTO);

            System.out.println("Correo enviado correctamente a " + correo);

        } catch (Exception e) {
            System.out.println("Error procesando mensaje de pagos");
            e.printStackTrace();
        }
    }
}
