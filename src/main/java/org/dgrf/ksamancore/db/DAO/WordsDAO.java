/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.ksamancore.db.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.dgrf.ksamancore.db.JPA.WordsJpaController;
import org.dgrf.ksamancore.db.entities.Words;

/**
 *
 * @author dgrfiv
 */
public class WordsDAO extends WordsJpaController {

    public WordsDAO(EntityManagerFactory emf) {
        super(emf);
    }

    public void deleteAllWordsAndChars(int parvaId, int adhyayId, int shlokaNum, int shlokaLine) {
        EntityManager em = getEntityManager();
        TypedQuery<Words> query = em.createNamedQuery("Words.deleteAllWordsAndChars", Words.class);
        query.setParameter("parvaId", parvaId);
        query.setParameter("adhyayId", adhyayId);
        query.setParameter("shlokaNum", shlokaNum);
        query.setParameter("shlokaLine", shlokaLine);

        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
