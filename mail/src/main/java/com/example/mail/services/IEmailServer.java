package com.example.mail.services;

import com.example.mail.models.EmailDTO;

import jakarta.mail.MessagingException;

public interface IEmailServer {
    void sendEmail(EmailDTO emailDTO) throws MessagingException;
}
