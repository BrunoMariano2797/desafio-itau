package com.myproject.desafio_itau.controller;

import com.myproject.desafio_itau.dto.TransferenciaRequest;
import com.myproject.desafio_itau.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transferencia")
@Tag(name = "Transferências", description = "Operações de transferência entre contas")
public class TransferenciaController {

    private final ContaService service;

    public TransferenciaController(ContaService service) {
        this.service = service;
    }

    @Operation(summary = "Transferir valores entre contas", description = "Realiza uma transferência de valores entre a conta de origem e destino.")
    @ApiResponse(responseCode = "204", description = "Transferência realizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou saldo insuficiente")
    @ApiResponse(responseCode = "404", description = "Conta de origem ou destino não encontrada")
    @PostMapping
    public ResponseEntity<Void> transferir(
            @Valid @RequestBody @Schema(description = "Dados da transferência") TransferenciaRequest request) {
        service.transferir(request.getIdOrigem(), request.getIdDestino(), request.getValor());
        return ResponseEntity.noContent().build();
    }
}
