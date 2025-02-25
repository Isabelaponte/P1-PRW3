package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.models.Aluno;

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

    public void update(String name) {
        Aluno aluno = findOneByName(name);

        if (aluno == null) return;

    }

    public void deleteByName(String name) {
        Aluno aluno = findOneByName(name);

        if (aluno == null) return;

        em.remove(aluno);
    }


    public void listAlunos() {}
}
