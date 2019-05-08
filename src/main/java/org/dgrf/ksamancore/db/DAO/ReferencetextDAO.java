/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.ksamancore.db.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.dgrf.ksamancore.db.JPA.ReferencetextJpaController;
import org.dgrf.ksamancore.db.entities.Referencetext;

/**
 *
 * @author dgrfiv
 */
public class ReferencetextDAO extends ReferencetextJpaController {
    
    public ReferencetextDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
    public List<Referencetext> getAnubadTika(int parvaId, int adhyayId, int shlokaNum, int shlokaLine) {
        EntityManager em = getEntityManager();
        TypedQuery<Referencetext> query = em.createNamedQuery("Referencetext.findAllTikaOfSelectedAnubad", Referencetext.class);
        query.setParameter("parvaId", parvaId);
        query.setParameter("adhyayId", adhyayId);
        query.setParameter("shlokaNum", shlokaNum);
        query.setParameter("shlokaLine", shlokaLine);
        List<Referencetext> tikaList = query.getResultList();
        return tikaList;
    }
}
