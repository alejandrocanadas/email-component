package com.example.mail.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionCompraDTO {
    private String correoCliente;
    private String estado;  
    private Double monto;
}
