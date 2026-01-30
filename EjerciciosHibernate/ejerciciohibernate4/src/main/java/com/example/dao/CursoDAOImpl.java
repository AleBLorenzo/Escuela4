package com.example.dao;

import java.time.LocalDate;
import java.util.List;

import com.example.model.Curso;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class CursoDAOImpl implements CursoDAO {

    private EntityManager em;

    public CursoDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void guardar(Curso curso) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(curso);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            throw e;
        }
    }

    @Override
    public void actualizar(Curso curso) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(curso);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            throw e;
        }
    }

    @Override
    public void eliminar(Curso curso) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(curso) ? curso : em.merge(curso));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            throw e;
        }
    }

    @Override
    public Curso buscarPorId(Long id) {
        return em.find(Curso.class, id);
    }

    @Override
    public Curso buscarPorCodigo(String codigo) {
        TypedQuery<Curso> query = em.createQuery(
                "SELECT c FROM Curso c WHERE c.codigo = :codigo", Curso.class);
        query.setParameter("codigo", codigo);
        return query.getResultStream().findFirst().orElse(null);
    }

    @Override
    public List<Curso> listarTodos() {
        TypedQuery<Curso> query = em.createQuery(
                "SELECT c FROM Curso c", Curso.class);
        return query.getResultList();
    }

    @Override
    public List<Curso> cursosConMasMatriculados(int top) {
        TypedQuery<Curso> query = em.createQuery(
                "SELECT c FROM Curso c LEFT JOIN c.matriculas m " +
                        "GROUP BY c ORDER BY COUNT(m) DESC",
                Curso.class);
        query.setMaxResults(top);
        return query.getResultList();
    }

    @Override
    public List<Curso> cursosPorCompletarEsteMes() {
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        LocalDate finMes = inicioMes.plusMonths(1).minusDays(1);

        TypedQuery<Curso> query = em.createQuery(
                "SELECT c FROM Curso c WHERE c.fechaFin BETWEEN :inicio AND :fin",
                Curso.class);
        query.setParameter("inicio", inicioMes);
        query.setParameter("fin", finMes);
        return query.getResultList();
    }

}
