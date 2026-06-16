package org.iftm.gerenciadorveterinarios.service;

import static org.junit.jupiter.api.Assertions.*;

import org.iftm.gerenciadorveterinarios.entities.Animal;
import org.iftm.gerenciadorveterinarios.servicies.AnimalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AnimalServiceTeste_VanessaPains {

    @Autowired
    private AnimalService service;

    //teste real para validar o cadastro de um animal como internado, usando a service real e o banco de teste, sem mocks.
    @Test
    @Order(1)
    @DisplayName("deve cadastrar um animal como internado quando a espécie for válida")
    public void deveCadastrarAnimalComoInternado() {
        // ARRANGE
        Animal animal = new Animal(
                null,
                "Rex",
                "Cachorro",
                5,
                false);

        // ACT
        Animal resultado = service.cadastrar(animal);

        // ASSERT
        assertNotNull(resultado, "O resultado não deve ser nulo ao cadastrar animal válido.");
        assertTrue(resultado.isInternado(), "O animal deve ser marcado como internado após o cadastro.");
        assertEquals("Cachorro", resultado.getEspecie());
        assertNotNull(resultado.getId(), "O animal salvo deve receber um ID gerado pelo banco.");
    }

    //teste real para validar o comportamento de erro ao tentar cadastrar um animal com espécie inválida, usando a service real e o banco de teste, sem mocks.
    @Test
    @Order(2)
    @DisplayName("deve lançar exceção quando a espécie do animal for inválida")
    public void deveLancarExcecao_quandoEspecieForInvalida() {
        // ARRANGE
        Animal animal = new Animal(
                null,
                "Rex",
                "Dinossauro",
                5,
                false);

        // ACT + ASSERT
        assertThrows(IllegalArgumentException.class, () -> service.cadastrar(animal));
    }

    //teste real para validar o comportamento de erro ao tentar dar alta para um animal que não está internado, usando a service real e o banco de teste, sem mocks.    
    @Test
    @Order(3)
    @DisplayName("deve dar alta para animal internado quando o id existir")
    public void deveDarAltaParaAnimalInternado() {
        // ARRANGE
        Animal animal = new Animal(
                null,
                "Rex",
                "Cachorro",
                5,
                false);

        Animal animalSalvo = service.cadastrar(animal);

        // ACT
        Animal resultado = service.darAlta(animalSalvo.getId());

        // ASSERT
        assertNotNull(resultado, "O resultado não deve ser nulo ao dar alta para animal existente.");
        assertFalse(resultado.isInternado(), "O animal deve ser desinternado ao receber alta.");
        assertEquals(animalSalvo.getId(), resultado.getId());
    }

    //teste real para validar o comportamento de erro ao tentar dar alta para um animal que não existe, usando a service real e o banco de teste, sem mocks.
    @Test
    @Order(4)
    @DisplayName("deve lançar exceção quando não encontrar animal para dar alta")
    public void deveLancarExcecao_quandoAnimalNaoExistirAoDarAlta() {
        // ACT + ASSERT
        assertThrows(RuntimeException.class, () -> service.darAlta(999));
    }
}
