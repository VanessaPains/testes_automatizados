package org.iftm.selenium_webdriver.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.iftm.selenium_webdriver.model.Veterinario;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class VeterinarioService {

    private final Map<Long, Veterinario> veterinarios = new LinkedHashMap<>();
    private long proximoId = 1;

    @PostConstruct
    public void inicializarDados() {
        salvar(new Veterinario(null, "Conceição Evaristo", "conceicao@gmail.com", "pequenos", "3500.0"));
        salvar(new Veterinario(null, "Erica Queiroz Pinto", "erica@gmail.com", "grandes", "4500.0"));
    }

    public List<Veterinario> listar(String pesquisa) {
        if (pesquisa == null || pesquisa.isBlank()) {
            return new ArrayList<>(veterinarios.values());
        }

        String termo = pesquisa.toLowerCase().trim();
        List<Veterinario> resultado = new ArrayList<>();
        for (Veterinario veterinario : veterinarios.values()) {
            if (veterinario.getNome().toLowerCase().contains(termo)
                    || veterinario.getEmail().toLowerCase().contains(termo)
                    || veterinario.getEspecialidade().toLowerCase().contains(termo)) {
                resultado.add(veterinario);
            }
        }
        return resultado;
    }

    public void salvar(Veterinario veterinario) {
        if (veterinario.getId() == null) {
            veterinario.setId(proximoId++);
        }
        veterinarios.put(veterinario.getId(), veterinario);
    }

    public Optional<Veterinario> obterPorId(Long id) {
        return Optional.ofNullable(veterinarios.get(id));
    }

    public void excluir(Long id) {
        veterinarios.remove(id);
    }
}
