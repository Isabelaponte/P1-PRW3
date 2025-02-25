package org.example;

// TO RUN H2 DB: java -cp h2.jar org.h2.tools.Shell

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.dao.AlunoDAO;
import org.example.models.Aluno;
import org.example.utils.JPAUtil;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;

public class Main {
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        EntityManager em = JPAUtil.getEntityManager();
        AlunoDAO alunoDAO = new AlunoDAO(em);

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("""
                        ** CADASTRO DE ALUNOS **\
                        
                        1 - Cadastrar aluno\
                        
                        2 - Excluir aluno\
                        
                        3 - Alterar aluno\
                        
                        4 - Busca aluno pelo nome\
                        
                        5 - Listar alunos (com status de aprovação)\
                        
                        6 - FIM\
                        
                        Digite a opção desejada:
                        """
            );
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 1) {
                createScanner(scanner, alunoDAO, em);
            } else if (option == 2) {
                deleteScanner(scanner, alunoDAO, em);
            } else if (option == 3) {
                updateScanner(scanner, alunoDAO, em);
            } else if (option == 4) {
                System.out.println("option 4");
            } else if (option == 5) {
                System.out.println(alunoDAO.findAll());
            }
            else if (option == 6) {
                System.out.println("Saindo...");
                break;
            }

        }

    }

    public static Aluno findOneByNameScanner(Scanner scanner, AlunoDAO alunoDAO) {
        System.out.println("Digite o nome:");
        String nome = scanner.nextLine();

        try {
            Aluno aluno = alunoDAO.findOneByName(nome);
            System.out.println(aluno);
            return aluno;
        } catch (NoResultException e) {
            System.out.println("\nAluno não encontrado.");
        }
        return null;
    }

    public static void createScanner(Scanner scanner, AlunoDAO alunoDAO, EntityManager em){
        System.out.println("Digite o nome:");
        String nome = scanner.nextLine();

        System.out.println("Digite o RA:");
        String ra = scanner.nextLine();

        System.out.println("Digite o email: ");
        String email = scanner.nextLine();

        System.out.println("Digite a nota 1: ");
        BigDecimal nota1 = BigDecimal.valueOf(scanner.nextInt());

        System.out.println("Digite a nota 2: ");
        BigDecimal nota2 = BigDecimal.valueOf(scanner.nextInt());

        System.out.println("Digite a nota 3: ");
        BigDecimal nota3 = BigDecimal.valueOf(scanner.nextInt());

        Aluno aluno = new Aluno(nome, ra, email, nota1, nota2, nota3);

        try {
            em.getTransaction().begin();
            alunoDAO.create(aluno);
            em.getTransaction().commit();
            System.out.println("Aluno cadastrado com sucesso!\n");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erro ao cadastrar aluno: " + e.getMessage() + "\n");
        }
    }

    public static void deleteScanner(Scanner scanner, AlunoDAO alunoDAO, EntityManager em) {
        Aluno aluno = findOneByNameScanner(scanner, alunoDAO);

        try {
            em.getTransaction().begin();
            alunoDAO.deleteByName(Objects.requireNonNull(aluno).getNome());
            em.getTransaction().commit();
            System.out.println("Aluno removido com sucesso!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erro ao remover aluno.");
        }
    }

    public static void updateScanner(Scanner scanner, AlunoDAO alunoDAO, EntityManager em) {

        Aluno aluno = findOneByNameScanner(scanner, alunoDAO);

        try {
            em.getTransaction().begin();
            alunoDAO.update(Objects.requireNonNull(aluno).getNome());
            em.getTransaction().commit();
            System.out.println("Aluno alterado com sucesso!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erro ao remover aluno.");
        }
    }


}