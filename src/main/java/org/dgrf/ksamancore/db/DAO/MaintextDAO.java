/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.ksamancore.db.DAO;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.dgrf.ksamancore.db.JPA.MaintextJpaController;
import org.dgrf.ksamancore.db.entities.Maintext;
import org.dgrf.ksamancore.db.entities.MaintextPK;

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
    
    public Integer getMaxShlokaNum(int parvaId, int adhyayId) {
        EntityManager em = getEntityManager();
        TypedQuery<Integer> query = em.createNamedQuery("Maintext.findMaxShlokaNum", Integer.class);
        query.setParameter("parvaId", parvaId);
        query.setParameter("adhyayId", adhyayId);
        Integer maxShlokaNum = query.getSingleResult();
        return maxShlokaNum;
    }
    
    public Integer getMaxShlokaLine(int parvaId, int adhyayId, int shlokaNum) {
        EntityManager em = getEntityManager();
        TypedQuery<Integer> query = em.createNamedQuery("Maintext.findMaxShlokaLine", Integer.class);
        query.setParameter("parvaId", parvaId);
        query.setParameter("adhyayId", adhyayId);
        query.setParameter("shlokaNum", shlokaNum);
        Integer maxShlokaLine = query.getSingleResult();
        return maxShlokaLine;
    }
    
    public List<Maintext> getShlokaTranslation(int parvaId, int adhyayId, int shlokaNum) {
        EntityManager em = getEntityManager();
        TypedQuery<Maintext> query = em.createNamedQuery("Maintext.findShlokTranslation", Maintext.class);
        query.setParameter("parvaId", parvaId);
        query.setParameter("adhyayId", adhyayId);
        query.setParameter("shlokaNum", shlokaNum);
        List<Maintext> shlokaList = query.getResultList();
        return shlokaList;
    }
    
    public List<String> getUniqueShlokaFirstCharList() {
        EntityManager em = getEntityManager();
        TypedQuery<String> query = em.createNamedQuery("Maintext.findShlokaFirstChar", String.class);
        List<String> firstCharList = query.getResultList();
        return firstCharList;
    }
    
//    public List<Maintext> getShlokaByFirstChar(String firstChar) {
//        EntityManager em = getEntityManager();
//        TypedQuery<Maintext> query = em.createNamedQuery("Maintext.findShlokaByFirstChar", Maintext.class);
//        query.setParameter("firstChar", firstChar);
////        query.setParameter("first", first);
////        query.setParameter("pagesize", pagesize);
//        List<Maintext> shlokaList = query.getResultList();
//        return shlokaList;
//    }
    public List<Maintext> getShlokaByFirstChar(String firstChar,int first,int pagesize) {
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("select parva_id,adhyayid,shlokanum,shlokaline,ubacha_id,shlokatext,firstchar,endchar,translatedtext, lastupdatedts  from dgrfdb0601.maintext where firstchar = ?1 order by shlokatext limit ?2,?3");
        query.setParameter(1, firstChar);
        query.setParameter(2, first);
        query.setParameter(3, pagesize);
        List<Object[]> shlokaLines = query.getResultList();
        List<Maintext> maintextList = new ArrayList<>();
        for (Object[] shlokaLine:shlokaLines ) {
            Maintext maintext = new Maintext();
            MaintextPK maintextPK = new MaintextPK();
            int parvaId = (Integer)shlokaLine[0];
            int adhyayid = (Integer)shlokaLine[1];
            int shlokanum = (Integer)shlokaLine[2];
            int shlokaline = (Integer)shlokaLine[3];
            int ubachaId = (Integer)shlokaLine[4];
            int shlokatext = (Integer)shlokaLine[5];
            int firstchar = (Integer)shlokaLine[6];
            
        }
        
        return maintextList;
    }
    
    public Long getShlokaCountByFirstChar(String firstChar) {
        EntityManager em = getEntityManager();
        TypedQuery<Long> query = em.createNamedQuery("Maintext.findShlokaCountByFirstChar", Long.class);
        query.setParameter("firstChar", firstChar);
        Long shlokaCount = query.getSingleResult();
        return shlokaCount;
    }
}
