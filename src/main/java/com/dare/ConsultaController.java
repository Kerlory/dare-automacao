package com.dare;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ConsultaController {

    @PostMapping("/consultar")
    public List<String> consultar(@RequestBody List<String> codigos) {
        return Main.consultarLista(codigos);
    }

}
