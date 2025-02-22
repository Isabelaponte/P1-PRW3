package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.models.Aluno;

public class AlunoDAO {
    private EntityManager em;

    public AlunoDAO(EntityManager em) {
        this.em = em;
    }

    public void create(Aluno aluno) {
        this.em.persist(aluno);
    }

    public void update(Aluno aluno) {}

    public void delete(Long id) {}

    public void searchAluno(String name) {}

    public void listAlunos() {}
}
