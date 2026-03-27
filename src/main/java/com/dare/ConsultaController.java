package com.dare;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConsultaController {

    // 🔥 CONSULTA
    @PostMapping("/consultar")
    public List<String> consultar(@RequestBody List<String> codigos) {
        return Main.consultarLista(codigos);
    }

    // 🔥 STATUS
    @GetMapping("/status")
    public StatusConsulta status() {
        return Main.status;
    }
}