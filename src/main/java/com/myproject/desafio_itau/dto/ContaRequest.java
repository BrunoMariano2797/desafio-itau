package com.myproject.desafio_itau.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ContaRequest {
    @NotBlank
    private String nome;

    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    private String cpf;

    @DecimalMin(value = "0.00", inclusive = true, message = "Saldo inicial deve ser ≥ 0")
    private BigDecimal saldoInicial;

    // Getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
}
