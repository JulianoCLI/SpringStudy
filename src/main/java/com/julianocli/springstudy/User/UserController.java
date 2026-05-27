package com.julianocli.springstudy.User;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController //Recebe requisições HTTP e devolve a resposta direto para o cliente.
@RequestMapping("/users") // Define a URL e o métode HTTP para chegar aqui.
public class UserController { // Controladores são intermediários entre a View e o Model.

    private final UserRepository userRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel postUser(@RequestBody UserModel userModel) {
        return userRepository.save(userModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable Long id){
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
