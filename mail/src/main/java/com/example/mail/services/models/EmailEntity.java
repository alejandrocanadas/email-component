package com.example.mail.services.models;

import java.time.LocalDateTime;

import org.springframework.stereotype.Indexed;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "email_logs")
public class EmailEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinatario;
    private String asunto;

    @Column(length = 5000)
    private String mensaje;

    private String remitente;
    private LocalDateTime fechaEnvio;
    private boolean exito;
    private String errorMensaje;
    
    public EmailEntity() {}

    public EmailEntity(String destinatario, String asunto, String mensaje, String remitente, Boolean exito, String errorMensaje) {
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.remitente = remitente;
        this.fechaEnvio = LocalDateTime.now();
        this.exito = exito;
        this.errorMensaje = errorMensaje;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getErrorMensaje() {
        return errorMensaje;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public String getRemitente() {
        return remitente;
    }

    
    public void setErrorMensaje(String errorMensaje) {
        this.errorMensaje = errorMensaje;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }
}
