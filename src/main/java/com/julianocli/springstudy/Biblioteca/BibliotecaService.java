package com.julianocli.springstudy.Biblioteca;

import com.julianocli.springstudy.Livros.LivrosModel;
import com.julianocli.springstudy.Livros.LivrosRepository;
import com.julianocli.springstudy.User.UserModel;
import com.julianocli.springstudy.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BibliotecaService {

    private final UserRepository userRepository;
    private final LivrosRepository livrosRepository;

    @Transactional
    public void realizarEmprestimo(Long idUsuario, Long idLivro){
        UserModel userModel = userRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Busca o livro pelo ID; se não existir, interrompe o fluxo disparando uma exceção "Livro não encontrado"
        LivrosModel livrosModel = livrosRepository.findById(idLivro).orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        if (livrosModel.getUser() != null) {
            throw new RuntimeException("O livro já está emprestado.");
        }

        if (userModel.getLivros().size() >= 3) {
            throw new RuntimeException("O usuário já possui 3 livros emprestados.");
        }

        userModel.getLivros().add(livrosModel);
        livrosModel.setUser(userModel);
    }

    @Transactional
    public void realizarDevolucao(Long idUsuario, Long idLivro){
        UserModel userModel = userRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        LivrosModel livrosModel = livrosRepository.findById(idLivro).orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        if  (livrosModel.getUser() == null) {
            throw new RuntimeException("Livro disponível para empréstimo");
        }

        if  (!livrosModel.getUser().getId().equals(idUsuario)) {
            throw new RuntimeException("Livro não consta como emprestado a este usuário");
        }

        userModel.getLivros().remove(livrosModel);
        livrosModel.setUser(null);
    }

}
