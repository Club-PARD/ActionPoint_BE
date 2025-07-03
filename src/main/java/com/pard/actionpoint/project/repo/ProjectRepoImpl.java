package com.pard.actionpoint.project.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepoImpl implements ProjectRepoCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean existsByProjectCode(String code) {
        String jpql = "SELECT COUNT(p) > 0 FROM Project p WHERE p.projectCode = :code";
        Boolean exists = em.createQuery(jpql, Boolean.class)
                .setParameter("code", code)
                .getSingleResult();
        return exists;
    }
}
