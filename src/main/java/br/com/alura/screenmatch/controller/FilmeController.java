package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.domain.filme.DadosAlteracaoFilme;
import br.com.alura.screenmatch.domain.filme.DadosCadastroFilme;
import br.com.alura.screenmatch.domain.filme.Filme;
import br.com.alura.screenmatch.domain.filme.FilmeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

    private static final String REDIRECT_FILMES = "redirect:/filmes";

    @Autowired
    private FilmeRepository repository;

    @GetMapping("/formulario")
    public String carregaPaginaFormulario(Long id, Model model) {
        if (id != null) {
            var filme = repository.getReferenceById(id);
            model.addAttribute("filme", filme);
        }
        return REDIRECT_FILMES;
    }

    @GetMapping
    public String carregaPaginaListagem(Model model) {
        model.addAttribute("lista", repository.findAll());
        return "filmes/listagem";
    }

    @PostMapping
    @Transactional
    public String cadastrarFilmes(DadosCadastroFilme dados) {
        var filme = new Filme(dados);
        repository.save(filme);
        return REDIRECT_FILMES;
    }

    @PutMapping
    @Transactional
    public String alterarFilmes(DadosAlteracaoFilme dados) {
        var filme = repository.getReferenceById(dados.id());
        filme.atualizaDados(dados);
        return REDIRECT_FILMES;
    }

    @DeleteMapping
    @Transactional
    public String removeFilme(Long id) {
        repository.deleteById(id);
        return REDIRECT_FILMES;
    }
}
