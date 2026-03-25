package com.dare;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConsultaController {

    // 🔥 ROTA PRINCIPAL (resolve teu 404)
    @GetMapping("/")
    public String home() {
        return "🚀 Sistema DARE online!";
    }

    // 🔥 CONSULTA (usa teu Selenium)
    @PostMapping("/consulta")
    public List<String> consultar(@RequestBody List<String> codigos) {
        return Main.consultarLista(codigos);
    }

    // 🔥 STATUS DA EXECUÇÃO
    @GetMapping("/status")
    public StatusConsulta status() {
        return Main.status;
    }
}