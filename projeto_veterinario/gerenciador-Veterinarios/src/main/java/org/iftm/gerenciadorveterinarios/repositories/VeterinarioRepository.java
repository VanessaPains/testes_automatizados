package org.iftm.gerenciadorveterinarios.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VeterinarioRepository extends JpaRepository<Veterinario, Integer> {

   public List<Veterinario> findByNomeContains(String nome);

   //==================== 1º CICLO ===================
   //teste 1 - busca por nome exato case sensitive
   //solucao: criar método findByNome no repositório
   List<Veterinario> findByNome(String nome);

   //teste 3 - busca por nome exato case insensitive
   //solucao: criar método findByNomeIgnoreCase no repositório
   List<Veterinario> findByNomeIgnoreCase(String nome);


   //===================== 2º CICLO ===================
   //teste 1 - busca por nome contendo parte do nome, case insensitive
   //solucao: criar método findByNomeContainingIgnoreCase no repositório

   //teste 3 - busca vazia, ouse ja, tenha qual coisa dentro de "" no banco.

   List<Veterinario> findByNomeContainingIgnoreCase(String nome);
   

   //==================== 3º CICLO ===================
   //teste 1 - busca por salário maior que um valor específico
   //solucao: criar método findBySalarioGreaterThan no repositório
   List<Veterinario> findBySalarioGreaterThan(BigDecimal valor);


   //teste 2 - busca por salário menor que um valor específico
   //solucao: criar método findBySalarioLessThan no repositório
   List<Veterinario> findBySalarioLessThan(BigDecimal valor);

   //teste 3 - busca por salário entre dois valores específicos
   //solucao: criar método findBySalarioBetween no repositório
   List<Veterinario> findBySalarioBetween(BigDecimal min, BigDecimal max);
}
