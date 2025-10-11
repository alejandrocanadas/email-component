package com.example.mail.services.implementation;

import java.time.LocalDateTime;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import com.example.mail.repository.EmailRepository;
import com.example.mail.services.IEmailServer;
import com.example.mail.services.models.EmailDTO;
import com.example.mail.services.models.EmailEntity;

// import io.micrometer.observation.Observation.Context;
import org.thymeleaf.context.Context;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImplementation implements IEmailServer{
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final EmailRepository emailRepository;

    public EmailServiceImplementation(JavaMailSender javaMailSender, TemplateEngine templateEngine, EmailRepository emailRepository) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.emailRepository = emailRepository;
    }

    @Override
    public void sendEmail(EmailDTO emailDTO) throws MessagingException {
        EmailEntity log = new EmailEntity();
        log.setDestinatario(emailDTO.getDestinatario());
        log.setAsunto(emailDTO.getAsunto());
        log.setMensaje(emailDTO.getMensaje());
        log.setRemitente(emailDTO.getFrom());
        log.setFechaEnvio(LocalDateTime.now());



        // Lógica para enviar el correo electrónico usando javaMailSender y templateEngine
        try{
            MimeMessage mensaje = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(emailDTO.getDestinatario());
            helper.setSubject(emailDTO.getAsunto());
            
            // Usar el campo from si está disponible, sino usar el email configurado
            if (emailDTO.getFrom() != null && !emailDTO.getFrom().isEmpty()) {
                helper.setFrom(emailDTO.getFrom());
            }

            Context context = new Context();
            context.setVariable("mensaje", emailDTO.getMensaje());
            String htmlContent = templateEngine.process("email", context);

            helper.setText(htmlContent, true);
            javaMailSender.send(mensaje);
            log.setExito(true); // Marcar como exitoso si no hay excepción
        } catch (MessagingException e) {
            log.setExito(false);
            log.setErrorMensaje(e.getMessage());
            e.printStackTrace();
            throw e; // Re-lanzar la excepción para que pueda ser manejada por el controlador
        } finally {
            emailRepository.save(log);
        }
    }
}
