package org.iftm.gerenciadorveterinarios.repositories;

import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    Funcionario save(Funcionario funcionario);

    Optional<Funcionario> findById(Integer id);
}
