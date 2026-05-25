package org.iftm.gerenciadorveterinarios.servicies;

import java.util.List;
import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.repositories.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VeterinarioService {
    @Autowired
    private VeterinarioRepository repositorio;

    @Transactional(readOnly = true)
    public List<Veterinario> buscaVeterinariosComParteNome(String nome) {
        return repositorio.findByNomeContains(nome);
    }

    @Transactional(readOnly = true)
    public Optional<Veterinario> buscaVeterinariosPeloId(Integer id) {
        return repositorio.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Veterinario> buscaTodosVeterinarios() {
        return repositorio.findAll();
    }

    @Transactional
    public Veterinario salvar(Veterinario veterinario) {
        return repositorio.save(veterinario);
    }

    @Transactional
    public void apagar(Veterinario veterinario) {
        repositorio.delete(veterinario);
    }

    //Parte 1 - Exercicio A5
    //test - order (5) - apagar por id
    @Transactional
    public void apagarPorId(Integer id) {

        //busca no findbyid
        Optional<Veterinario> veterinario = repositorio.findById(id);

        //vrifica se existe, se não lanca o erro com throw
        if (veterinario.isEmpty()) {
            throw new RuntimeException(
                    "Veterinario não encontrado.");
        }

        //se nao deu erro vai aparar
        repositorio.delete(veterinario.get());
    }
}
