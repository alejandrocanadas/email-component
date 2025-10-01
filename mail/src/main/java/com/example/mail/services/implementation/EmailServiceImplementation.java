package com.example.mail.services.implementation;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import com.example.mail.services.models.EmailDTO;

// import io.micrometer.observation.Observation.Context;
import org.thymeleaf.context.Context;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import com.example.mail.services.IEmailServer;

@Service
public class EmailServiceImplementation implements IEmailServer{
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImplementation(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }



    @Override
    public void sendEmail(EmailDTO emailDTO) throws MessagingException {
        // Lógica para enviar el correo electrónico usando javaMailSender y templateEngine
        try{
            MimeMessage mensaje = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(emailDTO.getDestinatario());
            helper.setSubject(emailDTO.getAsunto());

            Context context = new Context();
            context.setVariable("mensaje", emailDTO.getMensaje());
            String htmlContent = templateEngine.process("email", context);

            helper.setText(htmlContent, true);
            javaMailSender.send(mensaje);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw e; // Re-lanzar la excepción para que pueda ser manejada por el controlador
        }
    }
}
