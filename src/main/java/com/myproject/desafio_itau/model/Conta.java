package com.myproject.desafio_itau.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Conta {
    private final String id;
    private final String nome;
    private final String cpf;
    private BigDecimal saldo;

    public Conta(String nome, String cpf, BigDecimal saldoInicial) {
        this.id = UUID.randomUUID().toString();
        this.nome = Objects.requireNonNull(nome, "nome é obrigatório");
        this.cpf = Objects.requireNonNull(cpf, "cpf é obrigatório");
        this.saldo = saldoInicial != null ? saldoInicial : BigDecimal.ZERO;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public synchronized BigDecimal getSaldo() {
        return saldo;
    }

    public synchronized void creditar(BigDecimal valor) {
        Objects.requireNonNull(valor);
        this.saldo = this.saldo.add(valor);
    }

    public synchronized void debitar(BigDecimal valor) {
        Objects.requireNonNull(valor, "valor não pode ser nulo");
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("valor deve ser positivo");
        }
        if (this.saldo.compareTo(valor) < 0) {
            throw new IllegalStateException("saldo insuficiente");
        }
        this.saldo = this.saldo.subtract(valor);
    }

}
