package org.iftm.gerenciadorveterinarios.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@TestMethodOrder(OrderAnnotation.class) // define a forma de ordenar os métodos
public class VeterinarioRepositoryTest {

    @Autowired
    private VeterinarioRepository repository;

    // ====================== 5º CICLO ===================

    // teste 3 - excluir um veterinário com a busca pelo nome
    // Erro no: sem erro, pois tem método delete() do JpaRepository, e tem id 2 no banco.
    // Motivo: já tem método delete()/apagar no repositório e tem id 2 no banco.
    // Solução: ja tem método delete() e tem id 2 no banco e não deve ser encontrado apo exclusao
    @Test
    @Order(16)
    public void deveRetornarVeterinario_quandoNaoDeveEncontrarPorNomeAaposExclusao() {
        // Arrange
        int idExistente = 2;// id existente no banco de dados

        var veterinario = repository.findById(idExistente).get();// buscar o veterinário com id 2 no banco em memória (H2)

        String nome = veterinario.getNome();// guardar o NOME do veterinário para validar depois

        // Act
        repository.delete(veterinario);// excluir o veterinário do banco em memória (H2)

        // Assert
        var resultado = repository.findByNome(nome);// buscar o veterinário pelo nome no banco em memória (H2) após a exclusão
        assertTrue(resultado.isEmpty());// verificar se o resultado da busca é vazio, ou seja, o veterinário não foi encontrado pelo nome
    }

    // teste 2 - excluir um veterinário e verificar se ele não é mais encontrado
    // Erro no: sem erro, pois tem método delete() do JpaRepository, e tem id 1 no
    // banco.
    // Motivo: já tem método delete()/apagar no repositório e tem id 1 no banco.
    // Solução: ja tem método delete() e tem id 1 no banco.
    @Test
    @Order(15)
    public void deveRetornarvaterianario_quandoExcluirVeterinario() {
        // Arrange
        int idExistente = 1;// id existente no banco de dados

        var veterinario = repository.findById(idExistente).get();// buscar o veterinário com id 1 no banco em memória
                                                                 // (H2)

        // Act
        repository.delete(veterinario);// excluir o veterinário do banco em memória (H2)

        // Assert
        var resultado = repository.findById(idExistente);// buscar o veterinário com id 1 no banco em memória (H2) após
                                                         // a exclusão
        assertTrue(resultado.isEmpty());// verificar se o resultado da busca é vazio, ou seja, o veterinário não foi
                                        // encontrado
    }

    // teste 1 - busca por id inexistente deve retornar vazio
    // Erro no: sem erro, pois não tem registro no banco com o id 999.
    // Motivo: não tem registro no banco com o id 999.
    // Solução: certificar que não tem registro com id 999 no banco.
    @Test
    @Order(14)
    public void deveRetornarVeterinarios_quandoIdNaoExisteNoBanco() {
        // Arrange
        int idInexistente = 999;// id inxistente no banco de dados

        // Act
        var resultado = repository.findById(idInexistente);

        // Assert
        assertTrue(resultado.isEmpty());
    }

    // ===================== 4º CICLO ===================

    // teste 1 - atualizar um veterinário e verificar se os dados antigos não são
    // mais encontrados
    // Erro no: sem erro, pois tem método criado no repositório, e tem nome antigo
    // no banco.
    // Solução: ja tem metodo no repositrio, e tem nome antigo no banco
    @Test
    @Order(13)
    public void deveAtualizarVeterinario_eNaoEncontrarDadosAntigos() {
        // 1. ARRANGE (Preparação)
        // Busca um veterinário já existente no banco H2 (carregado via import.sql)
        int idExistente = 1;
        var veterinario = repository.findById(idExistente).get();

        // Guarda o nome antigo para validar depois
        String nomeAntigo = veterinario.getNome();

        // 2. ACT (Ação)
        // Altera os dados do objeto em memória
        veterinario.setNome("CARLOS");
        veterinario.setSalario(new BigDecimal("9999"));

        // Salva as alterações no banco em memória (H2)
        repository.save(veterinario);

        // 3. ASSERT (Verificação)
        // 3.1 Verifica se o registro NÃO é mais encontrado pelo nome antigo
        var antigo = repository.findByNome(nomeAntigo);
        assertTrue(antigo.isEmpty());

        // 3.2 Verifica se o registro é encontrado pelo novo nome
        var novo = repository.findByNome("CARLOS");
        assertFalse(novo.isEmpty());

        // 4. OBSERVAÇÃO IMPORTANTE
        // O arquivo import.sql é utilizado apenas para carga inicial dos dados.
        // As alterações feitas com save() não modificam o import.sql, apenas o banco
        // temporário.
    }

    // ===================== 3º CICLO ===================

    // teste 3 - busca por salário maior que um valor específico
    // Erro no: erro no findBySalarioBetween e isEmpty
    // Motivo: não tem método criado no repositório.
    // Solução: criar método findBySalarioBetween no repositório
    @Test
    @Order(12)
    public void deveRetornarVeterinarios_comSalarioEntreValores() {
        // 1. ARRANGE (Preparação)
        var min = new BigDecimal("3000");
        var max = new BigDecimal("4500");

        // 2. ACT (Ação)
        var resultado = repository.findBySalarioBetween(min, max);

        // 3. ASSERT (Verificação)
        assertFalse(resultado.isEmpty());
        // ou
        // assertEquals(5, resultado.size());//posso limitar quanto quero traga
    }

    // teste 2 - busca por salário menor que um valor específico
    // Erro no: erro no findBySalarioLessThan e isEmpty
    // Motivo: não tem método criado no repositório.
    // Solução: criar método findBySalarioLessThan no repositório
    @Test
    @Order(11)
    public void deveRetornarVeterinarios_comSalarioMenorQue() {
        // 1. ARRANGE (Preparação)
        var valor = new BigDecimal("3500");

        // 2. ACT (Ação)
        var resultado = repository.findBySalarioLessThan(valor);

        // 3. ASSERT (Verificação)
        assertFalse(resultado.isEmpty());
    }

    // teste 1 - busca por salário maior que um valor específico
    // Erro no: erro no findBySalarioGreaterThan e isEmpty
    // Motivo: não tem método criado no repositório.
    // Solução: criar método findBySalarioGreaterThan no repositório
    @Test
    @Order(10)
    public void deveRetornarVeterinarios_comSalarioMaiorQue() {
        // 1. ARRANGE (Preparação)
        var valor = new BigDecimal("4000");

        // 2. ACT (Ação)
        var resultado = repository.findBySalarioGreaterThan(valor);

        // 3. ASSERT (Verificação)
        assertFalse(resultado.isEmpty());
    }

    // ===================== 2º CICLO ===================

    // teste 3 - busca por string cm qualquer coisa.
    // Erro no: sem erro, pois tem método criado no repositório, e tem nomes que
    // contém qualquer coisa dentro de "" no banco.
    // Solução: ja tem metodo no repositrio.
    @Test
    @Order(9)
    public void eveRetornarVeterinarios_quandoBuscaVazia() {
        // 1. ARRANGE (Preparação)
        String termo = "";

        // 2. ACT (Ação)
        var resultado = repository.findByNomeContains(termo);

        // 3. ASSERT (Verificação)
        assertFalse(resultado.isEmpty());
    }

    // teste 2 - busca por nome Maria
    // Erro no: csem erro, pois não tem Maria no banco.
    // Motivo: tem método criado no repositório, mas não tem nome Maria no banco.
    // Solução: certificar que não tem Maria no banco.
    @Test
    @Order(8)
    public void deveRetornarVeterianario__quandoNaoEncontrarNome() {
        // 1. ARRANGE (Preparação)
        String termo = "Maria";

        // 2. ACT (Ação)
        var resultado = repository.findByNomeContains(termo);

        // 3. ASSERT (Verificação)
        assertTrue(resultado.isEmpty());
    }

    // teste 1 - busca por nome exato case sensitive
    // Erro no: com erro, so retorno um registro no banco: Erica Queiroz Pinto
    // Motivo: tem método criado no repositório, mas o termo de busca é "ro", e só
    // tem um nome que contém "ro" no banco, que é "QUEIROZ".
    // Solução: alterar o termo de busca para "CASE SENSITIVE", pois tem mais de um
    // nome que contém "ro" no banco, como "RODINEI" e "ROBERTO".
    @Test
    @Order(7)
    public void deveRetornarVeterinarios_quandoBuscarPorParteDoNome() {
        // 1. ARRANGE (Preparação)
        String termo = "ro";// SO CAIXA BAIXA

        // 2. ACT (Ação)
        var resultado = repository.findByNomeContainingIgnoreCase(termo);// adicionara para sanar o erro.

        // 3. ASSERT (Verificação)
        assertEquals(2, resultado.size());
    }

    // ===================== 1º CICLO ===================

    // teste 4 - busca por nome exato case insensitive
    // Erro no: csem erro
    // Motivo: tem método criado no repositório
    // Solução: ja criado, alterado o assserTrue para false, pois o resultado não
    // deve ser vazio.
    @Test
    @Order(6)
    public void deveRetornarVeterinario_quandoNomeMaiusculoOuMinusculo() {
        // 1. ARRANGE (Preparação)
        String nomeBusca = "pedro"; // minúsculo

        // 2. ACT (Ação)
        var resultado = repository.findByNomeIgnoreCase(nomeBusca);

        // 3. ASSERT (Verificação)
        assertFalse(resultado.isEmpty());
    }

    // teste 3 - busca por nome exato case insensitive
    // Erro no: com erro no método findByNomeIgnoreCase e isEmpty.
    // Motivo: tem método criado no repositório. e não tem nome no banco
    // Solução: criar método findByNomeIgnoreCase no repositório
    @Test
    @Order(5)
    public void deveRetornarVeterianario_quandoNomeNaoForExato() {
        // 1. ARRANGE (Preparação)
        String nomeBusca = "jose";

        // 2. ACT (Ação)
        var resultado = repository.findByNomeIgnoreCase(nomeBusca);

        // 3. ASSERT (Verificação)
        assertTrue(resultado.isEmpty());
    }

    // teste 2 - busca por nome exato case sensitive
    // Erro no: sem erro
    // Motivo: tem método criado no repositório. e tem nome exato no banco.
    @Test
    @Order(4)
    public void deveRetornarVeterinario_quandoNomeExatoCorreto() {
        // 1. ARRANGE (Preparação)
        String nomeBusca = "PEDRO";

        // 2. ACT (Ação)
        var resultado = repository.findByNome(nomeBusca);

        // 3. ASSERT (Verificação)
        assertFalse(resultado.isEmpty());
    }

    // teste 1 - busca por nome exato case sensitive
    // Erro no: findByNome e isEmpty.
    // Motivo: não tem método criado no repositor.
    // Solução: criar método findByNome no repositório e corrigir assertFalse para
    // assertTrue, pois o resultado não deve ser vazio.
    @Test
    @Order(3)
    public void deveRetornarVazio_quandoNomeMinusculoENoBancoMaiusculo() {
        // 1. ARRANGE (Preparação)
        String nomeBusca = "pedro";// no banco PEDRO

        // 2. ACT (Ação)
        var resultado = repository.findByNome(nomeBusca);

        // 3. ASSERT (Verificação)
        assertTrue(resultado.isEmpty());
    }

    @Test
    @Order(2)
    public void testarBuscaVeterinarioComIdExistenteRetornaCorreto1() {
        // 1. ARRANGE (Preparação)
        int idExistente = 1;
        String nomeEsperado = "Conceicao Evaristo";
        String emailEsperado = "conceicao@gmail.com";

        // 2. ACT (Ação)
        Veterinario veterinarioEncontrado = repository.getById(idExistente);

        // 3. ASSERT (Verificação)
        assertNotNull(veterinarioEncontrado);
        assertEquals(nomeEsperado, veterinarioEncontrado.getNome());
        assertEquals(emailEsperado, veterinarioEncontrado.getEmail());
    }

    @Test
    @Order(1)
    public void testarBuscaVeterinarioComIdExistenteRetornaCorreto2() {
        // 1. ARRANGE (Preparação)
        int idExistente = 1;
        String nomeEsperado = "Conceicao Evaristo";
        String emailEsperado = "conceicao@gmail.com";

        // 2. ACT (Ação)
        Optional<Veterinario> veterinarioEncontrado = repository.findById(idExistente);

        // 3. ASSERT (Verificação)
        assertTrue(veterinarioEncontrado.isPresent());
        assertEquals(nomeEsperado, veterinarioEncontrado.get().getNome());
        assertEquals(emailEsperado, veterinarioEncontrado.get().getEmail());
    }

}
