package org.iftm.gerenciadorveterinarios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

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

    // Parte 2 - Exercicio A5
    @Test
    @Order(1)
    public void deveCadastrarAnimalComoInternado() {

        // ARRANGE
        Animal animal = new Animal(
                1,
                "Rex",
                "Cachorro",
                5,
                false);

        Animal animalSalvo = new Animal(
                1,
                "Rex",
                "Cachorro",
                5,
                true);

        // MOCK
        when(repository.save(any())).thenReturn(animalSalvo);

        // ACT
        Animal resultado = service.cadastrar(animal);

        // ASSERT
        assertTrue(resultado.isInternado());

        // VERIFY
        verify(repository).save(any());
    }

    @Test
    @Order(2)
    public void deveLancarExcecao_quandoEspecieForInvalida() {

        // ARRANGE
        Animal animal = new Animal(
                1,
                "Rex",
                "Dinossauro",
                5,
                false);

        // ACT + ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrar(animal);
        });

        // VERIFY
        verify(repository, never()).save(any());
    }

    @Test
    @Order(3)
    public void deveDarAltaParaAnimalInternado() {

        // ARRANGE
        Integer id = 1;

        Animal animal = new Animal(
                id,
                "Rex",
                "Cachorro",
                5,
                true);

        // MOCK
        when(repository.findById(id)).thenReturn(Optional.of(animal));
        
        // MOCK
        when(repository.save(any())).thenReturn(animal);

        // ACT
        service.darAlta(id);

        // ASSERT
        assertFalse(animal.isInternado());

        // VERIFY
        verify(repository).save(animal);
    }
}
