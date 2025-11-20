package com.myproject.desafio_itau.repository;

import com.myproject.desafio_itau.model.Conta;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;
import java.util.Collection;

@Repository
public class ContaRepository {
    private final ConcurrentHashMap<String, Conta> map = new ConcurrentHashMap<>();

    public Conta save(Conta conta) {
        map.put(conta.getId(), conta);
        return conta;
    }

    public Optional<Conta> findById(String id) {
        return Optional.ofNullable(map.get(id));
    }

    public Collection<Conta> findAll() {
        return map.values();
    }

    public boolean existsById(String id) {
        return map.containsKey(id);
    }

    public Optional<Conta> findByCpf(String cpf) {
        return map.values().stream()
                .filter(c -> c.getCpf() != null && c.getCpf().equals(cpf))
                .findFirst();
    }

    public void deleteById(String id) {
        map.remove(id);
    }

    // Apenas para facilitar testes locais/integration tests
    public void clear() {
        map.clear();
    }
}

