package com.myproject.desafio_itau.service;

import com.myproject.desafio_itau.model.Conta;
import com.myproject.desafio_itau.repository.ContaRepository;
import com.myproject.desafio_itau.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ContaService {

    private final ContaRepository repo;
    // locks por conta para sincronização na JVM
    private final ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    public ContaService(ContaRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Conta criarConta(String nome, String cpf, BigDecimal saldoInicial) {
        Objects.requireNonNull(nome, "nome é obrigatório");
        Objects.requireNonNull(cpf, "cpf é obrigatório");
        Objects.requireNonNull(saldoInicial, "saldoInicial é obrigatório");
        if (saldoInicial.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("saldoInicial não pode ser negativo");
        }
        Conta c = new Conta(nome, cpf, saldoInicial);
        return repo.save(c);
    }

    @Transactional(readOnly = true)
    public BigDecimal consultarSaldo(String id) {
        Objects.requireNonNull(id, "id é obrigatório");
        Conta c = repo.findById(id).orElseThrow(() -> new NotFoundException("Conta não encontrada"));
        return c.getSaldo();
    }

    @Transactional
    public void transferir(String idOrigem, String idDestino, BigDecimal valor) {
        Objects.requireNonNull(idOrigem, "idOrigem é obrigatório");
        Objects.requireNonNull(idDestino, "idDestino é obrigatório");
        Objects.requireNonNull(valor, "valor é obrigatório");
        if (idOrigem.equals(idDestino)) {
            throw new BadRequestException("idOrigem e idDestino devem ser diferentes");
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("valor deve ser positivo");
        }

        // obter locks em ordem consistente para evitar deadlock
        String firstId = idOrigem.compareTo(idDestino) < 0 ? idOrigem : idDestino;
        String secondId = firstId.equals(idOrigem) ? idDestino : idOrigem;

        ReentrantLock firstLock = locks.computeIfAbsent(firstId, k -> new ReentrantLock());
        ReentrantLock secondLock = locks.computeIfAbsent(secondId, k -> new ReentrantLock());

        firstLock.lock();
        try {
            secondLock.lock();
            try {
                Conta origem = repo.findById(idOrigem)
                        .orElseThrow(() -> new NotFoundException("Conta origem não encontrada"));
                Conta destino = repo.findById(idDestino)
                        .orElseThrow(() -> new NotFoundException("Conta destino não encontrada"));

                if (origem.getSaldo().compareTo(valor) < 0) {
                    throw new BadRequestException("Saldo insuficiente");
                }

                origem.debitar(valor);
                destino.creditar(valor);

                repo.save(origem);
                repo.save(destino);
            } finally {
                secondLock.unlock();
            }
        } finally {
            firstLock.unlock();
        }
    }
}
