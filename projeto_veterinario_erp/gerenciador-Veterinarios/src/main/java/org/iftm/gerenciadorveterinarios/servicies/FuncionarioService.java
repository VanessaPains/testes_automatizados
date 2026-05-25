package org.iftm.gerenciadorveterinarios.servicies;

import org.iftm.gerenciadorveterinarios.entities.Funcionario;
import org.iftm.gerenciadorveterinarios.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    public Funcionario cadastrar(Funcionario funcionario) {

        funcionario.setEmFerias(false);

        return repository.save(funcionario);
    }
}
