package com.julianocli.springstudy.Biblioteca;

import com.julianocli.springstudy.Livros.LivrosRepository;
import com.julianocli.springstudy.User.UserModel;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/biblioteca")
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    @PostMapping("/emprestimo/{idUsuario}/{idLivro}")
    public ResponseEntity<String> postEmprestimo(@PathVariable Long idUsuario,@PathVariable Long idLivro) {
        bibliotecaService.realizarEmprestimo(idUsuario, idLivro);
        return ResponseEntity.ok("Emprestimo realizado com sucesso");
    }
    @PostMapping("/devolucao/{idUsuario}/{idLivro}")
    public ResponseEntity<String> postDevolucao(@PathVariable Long idUsuario,@PathVariable Long idLivro) {
        bibliotecaService.realizarDevolucao(idUsuario, idLivro);
        return ResponseEntity.ok("Devolução realizada com sucesso");
    }
}
