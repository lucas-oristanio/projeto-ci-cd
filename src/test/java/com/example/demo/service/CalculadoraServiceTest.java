package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculadoraServiceTest {
    private final CalculadoraService service = new CalculadoraService();

    @Test
    void deveDividirCorretamente() {
        double resultado = service.dividir(10, 2);
        assertEquals(5.0, resultado);
    }

    @Test
    void deveLancarExcecaoAoDividirPorZero() {
        ResponseStatusException excecao = assertThrows(
                ResponseStatusException.class,
                () -> service.dividir(10, 0)
        );
        assertEquals("400 BAD_REQUEST \"Divisão por zero não permitida\"", excecao.getMessage());
    }
}
