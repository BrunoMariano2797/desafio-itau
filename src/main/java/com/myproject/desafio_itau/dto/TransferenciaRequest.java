package com.myproject.desafio_itau.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class TransferenciaRequest {
    @NotBlank
    private String idOrigem;

    @NotBlank
    private String idDestino;

    @DecimalMin(value = "0.01", inclusive = true, message = "Valor deve ser positivo")
    private BigDecimal valor;

    // Getters e setters
    public String getIdOrigem() {
        return idOrigem;
    }

    public void setIdOrigem(String idOrigem) {
        this.idOrigem = idOrigem;
    }

    public String getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(String idDestino) {
        this.idDestino = idDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
