package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.models.Aluno;

import java.math.BigDecimal;
import java.util.List;

public class AlunoDAO {
    private final EntityManager em;

    public AlunoDAO(EntityManager em) {
        this.em = em;
    }

    public Aluno findById(Long id) {
        return em.find(Aluno.class, id);
    }

    public Aluno findOneByName(String name) throws NoResultException {
        String jpql = "SELECT a FROM Aluno a WHERE a.nome = ?1";
        return em.createQuery(jpql, Aluno.class)
                .setParameter(1, name)
                .getSingleResult();
    }

    public List<Aluno> findAll() {
        String jpql = "SELECT a FROM Aluno a";
        return em.createQuery(jpql, Aluno.class).getResultList();
    }

    public void create(Aluno aluno) {
        this.em.persist(aluno);
    }

    public void update(Aluno aluno, String newNome, String newRa, String newEmail,
                       BigDecimal nota1, BigDecimal nota2, BigDecimal nota3) {

        aluno.setNome(newNome);
        aluno.setRa(newRa);
        aluno.setEmail(newEmail);
        aluno.setNota1(nota1);
        aluno.setNota2(nota2);
        aluno.setNota3(nota3);

        em.merge(aluno);
    }

    public void deleteByName(Aluno aluno) {
        em.remove(aluno);
    }
}
