package com.dare;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class ConsultaController {

    @PostMapping("/consultar")
    public Object consultar(@RequestBody List<String> codigos) {

        try {

            return Main.consultarLista(codigos);

        } catch (Exception e) {

            e.printStackTrace();

            return Map.of(
                    "erro", true,
                    "mensagem", "Erro interno no servidor");

        }
    }

    @GetMapping("/status")
    public StatusConsulta status() {

        return Main.status;

    }
}