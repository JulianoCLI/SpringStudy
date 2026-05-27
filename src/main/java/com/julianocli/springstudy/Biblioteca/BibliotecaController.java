package com.julianocli.springstudy.Biblioteca;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/biblioteca")
public class BibliotecaController {
    // TODO: Injetar BibliotecaService e expor os endpoints de empréstimo e devolução
}
