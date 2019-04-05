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
import org.dgrf.ksamancore.db.JPA.MaintextJpaController;
import org.dgrf.ksamancore.db.entities.Maintext;

/**
 *
 * @author dgrfiv
 */
public class MaintextDAO extends MaintextJpaController {
    
    public MaintextDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
    public List<Integer> getAdhyayByParvaId(int parvaId) {
        EntityManager em = getEntityManager();
        TypedQuery<Integer> query = em.createNamedQuery("Maintext.getAdhyayByParvaId", Integer.class);
        query.setParameter("parvaId", parvaId);
        List<Integer> adhyayByParva = query.getResultList();
        return adhyayByParva;
    }
    
    public List<Maintext> getShlokaByParvaAndAdhyayId(int parvaId, int adhyayId) {
        EntityManager em = getEntityManager();
        TypedQuery<Maintext> query = em.createNamedQuery("Maintext.findShlokByParvaAndAdhyayId", Maintext.class);
        query.setParameter("parvaId", parvaId);
        query.setParameter("adhyayId", adhyayId);
        List<Maintext> shlokaList = query.getResultList();
        return shlokaList;
    }
}
