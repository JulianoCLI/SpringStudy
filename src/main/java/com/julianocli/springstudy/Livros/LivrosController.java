package com.julianocli.springstudy.Livros;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/livros")
public class LivrosController {

    private final LivrosRepository livrosRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LivrosModel postLivros(@RequestBody LivrosModel livrosModel) {
        return livrosRepository.save(livrosModel);
    }

    @GetMapping
    public ResponseEntity<List<LivrosModel>> getLivros() {
        List<LivrosModel> livrosModels = livrosRepository.findAll();
        return livrosModels.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(livrosModels);
    }

}
