package com.julianocli.springstudy;

import org.springframework.web.bind.annotation.*;

@RestController //Recebe requisições HTTP e devolve a resposta direto para o cliente.
@RequestMapping // Define a URL e o métode HTTP para chegar aqui.
public class Controller {
    // Controladores são intermediários entre a View e o Model.

    @GetMapping("/boasvindas")
    public String boasVindas () {
        return  "Boas vindas";
    }
}
