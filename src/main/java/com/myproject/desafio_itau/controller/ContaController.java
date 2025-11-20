package com.myproject.desafio_itau.controller;

import com.myproject.desafio_itau.dto.*;
import com.myproject.desafio_itau.model.Conta;
import com.myproject.desafio_itau.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/contas")
@Tag(name = "Contas", description = "Operações relacionadas a contas bancárias")
public class ContaController {

    private final ContaService service;

    public ContaController(ContaService service) {
        this.service = service;
    }

    @Operation(summary = "Criar nova conta", description = "Cria uma conta bancária com nome, CPF e saldo inicial.")
    @ApiResponse(responseCode = "200", description = "Conta criada com sucesso", content = @Content(schema = @Schema(implementation = ContaResponse.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @PostMapping
    public ResponseEntity<ContaResponse> criarConta(@Valid @RequestBody ContaRequest request) {
        Conta conta = service.criarConta(request.getNome(), request.getCpf(), request.getSaldoInicial());
        return ResponseEntity.ok(new ContaResponse(conta.getId()));
    }

    @Operation(summary = "Consultar saldo", description = "Retorna o saldo atual da conta informada pelo ID")
    @ApiResponse(responseCode = "200", description = "Saldo retornado com sucesso")
    @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    @GetMapping("/{id}/saldo")
    public ResponseEntity<BigDecimal> consultarSaldo(@PathVariable String id) {
        BigDecimal saldo = service.consultarSaldo(id);
        return ResponseEntity.ok(saldo);
    }
}
