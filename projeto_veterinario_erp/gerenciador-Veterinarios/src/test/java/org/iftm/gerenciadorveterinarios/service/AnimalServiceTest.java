package org.iftm.gerenciadorveterinarios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.iftm.gerenciadorveterinarios.entities.Animal;
import org.iftm.gerenciadorveterinarios.repositories.AnimalRepository;
import org.iftm.gerenciadorveterinarios.servicies.AnimalService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    private AnimalRepository repository;

    @InjectMocks
    private AnimalService service;

    //Parte 2 - Exercicio A5
    @Test
    @Order(1)
    public void deveCadastrarAnimalComoInternado() {

        // ARRANGE
        Animal animal =
                new Animal(
                        1,
                        "Rex",
                        "Cachorro",
                        5,
                        false);

        Animal animalSalvo =
                new Animal(
                        1,
                        "Rex",
                        "Cachorro",
                        5,
                        true);

        when(repository.save(any())).thenReturn(animalSalvo);

        // ACT
        Animal resultado = service.cadastrar(animal);

        // ASSERT
        assertTrue(resultado.isInternado());

        // VERIFY
        verify(repository).save(any());
    }
}
