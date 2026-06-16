package org.iftm.gerenciadorveterinarios.repositories;

import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Integer>{

    Optional<Animal> findById(Integer id);
}
