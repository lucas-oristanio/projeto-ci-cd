package com.example.demo.controller;

import com.example.demo.service.CalculadoraService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculadoraController.class)
public class CalculadoraControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CalculadoraService calculadoraService;

    @TestConfiguration
    static class CalculadoraTestConfig {
        @Bean
        public CalculadoraService calculadoraService() {
            return Mockito.mock(CalculadoraService.class);
        }
    }

    @Test
    void deveRetornarResultadoDivisao() throws Exception {
        when(calculadoraService.dividir(10.0, 2.0)).thenReturn(5.0);

        mockMvc.perform((RequestBuilder) post("/divisao/10/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("5.0"));
    }

    @Test
    void deveRetornarBadRequestQuandoDividirPorZero() throws Exception {
        when(calculadoraService.dividir(10.0, 0.0))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.BAD_REQUEST, "Divisão por zero não permitida"
                ));

        mockMvc.perform((RequestBuilder) post("/divisao/10/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Divisão por zero não permitida"));
    }
}
