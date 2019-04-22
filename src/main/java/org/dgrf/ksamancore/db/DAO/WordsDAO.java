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
import org.dgrf.ksamancore.db.JPA.WordsJpaController;
import org.dgrf.ksamancore.db.entities.Maintext;
import org.dgrf.ksamancore.db.entities.MaintextPK;
import org.dgrf.ksamancore.db.entities.Words;
import org.dgrf.ksamancore.db.entities.WordsPK;

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
    
    public List<String> getUniqueShlokaWordsFirstCharList() {
        EntityManager em = getEntityManager();
        TypedQuery<String> query = em.createNamedQuery("Words.findAllDistinctFirstchar", String.class);
        List<String> firstCharList = query.getResultList();
        return firstCharList;
    }
    
    public Long getWordsCountByFirstChar(String firstChar) {
        EntityManager em = getEntityManager();
        TypedQuery<Long> query = em.createNamedQuery("Words.findWordsCountByFirstChar", Long.class);
        query.setParameter("firstChar", firstChar);
        Long wordsCount = query.getSingleResult();
        return wordsCount;
    }
    
    public List<Words> getWordsByFirstChar(String firstChar,int first,int pagesize) {
        
        EntityManager em = getEntityManager();
        String myQuery = "select w.maintext_parva_id,w.maintext_adhyayid,w.maintext_shlokanum,w.maintext_shlokaline,w.wordtext,w.firstchar from dgrfdb0601.words w where firstchar = ?1 order by wordtext limit ?2,?3";
        Query query = em.createNativeQuery(myQuery);
        System.out.println(myQuery);
        query.setParameter(1, firstChar);
        query.setParameter(2, first);
        query.setParameter(3, pagesize);
        List<Object[]> wordTexts = query.getResultList();
        List<Words> wordsList = new ArrayList<>();
        
        for (Object[] wordText:wordTexts ) {
            Words words = new Words();
            WordsPK wordsPK = new WordsPK();
            MaintextPK maintextPK = new MaintextPK();
            
            int parvaId = (Integer)wordText[0];
            int adhyayid = (Integer)wordText[1];
            int shlokanum = (Integer)wordText[2];
            int shlokaline = (Integer)wordText[3];
            String wordtext = (String)wordText[4];
            String firstchar = (String)wordText[5];
            
            maintextPK.setParvaId(parvaId);
            maintextPK.setAdhyayid(adhyayid);
            maintextPK.setShlokanum(shlokanum);
            maintextPK.setShlokaline(shlokaline);
            
            Maintext maintext = new Maintext(maintextPK);
            
            wordsPK.setMaintextParvaId(parvaId);
            wordsPK.setMaintextAdhyayid(adhyayid);
            wordsPK.setMaintextShlokanum(shlokanum);
            wordsPK.setMaintextShlokaline(shlokaline);
            words.setWordsPK(wordsPK);
            words.setWordtext(wordtext);
            words.setFirstchar(firstchar);
            words.setMaintext(maintext);
            
            wordsList.add(words);
        }
        
        return wordsList;
    }
}
