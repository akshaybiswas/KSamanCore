/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.ksamancore.db.JPA;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.dgrf.ksamancore.db.entities.Parva;
import org.dgrf.ksamancore.db.entities.Ubacha;
import org.dgrf.ksamancore.db.entities.Referencetext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.dgrf.ksamancore.db.JPA.exceptions.IllegalOrphanException;
import org.dgrf.ksamancore.db.JPA.exceptions.NonexistentEntityException;
import org.dgrf.ksamancore.db.JPA.exceptions.PreexistingEntityException;
import org.dgrf.ksamancore.db.entities.Maintext;
import org.dgrf.ksamancore.db.entities.MaintextPK;
import org.dgrf.ksamancore.db.entities.Words;

/**
 *
 * @author dgrfiv
 */
public class MaintextJpaController implements Serializable {

    public MaintextJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Maintext maintext) throws PreexistingEntityException, Exception {
        if (maintext.getMaintextPK() == null) {
            maintext.setMaintextPK(new MaintextPK());
        }
        if (maintext.getReferencetextCollection() == null) {
            maintext.setReferencetextCollection(new ArrayList<Referencetext>());
        }
        if (maintext.getWordsCollection() == null) {
            maintext.setWordsCollection(new ArrayList<Words>());
        }
        maintext.getMaintextPK().setParvaId(maintext.getParva().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Parva parva = maintext.getParva();
            if (parva != null) {
                parva = em.getReference(parva.getClass(), parva.getId());
                maintext.setParva(parva);
            }
            Ubacha ubachaId = maintext.getUbachaId();
            if (ubachaId != null) {
                ubachaId = em.getReference(ubachaId.getClass(), ubachaId.getId());
                maintext.setUbachaId(ubachaId);
            }
            Collection<Referencetext> attachedReferencetextCollection = new ArrayList<Referencetext>();
            for (Referencetext referencetextCollectionReferencetextToAttach : maintext.getReferencetextCollection()) {
                referencetextCollectionReferencetextToAttach = em.getReference(referencetextCollectionReferencetextToAttach.getClass(), referencetextCollectionReferencetextToAttach.getReferencetextPK());
                attachedReferencetextCollection.add(referencetextCollectionReferencetextToAttach);
            }
            maintext.setReferencetextCollection(attachedReferencetextCollection);
            Collection<Words> attachedWordsCollection = new ArrayList<Words>();
            for (Words wordsCollectionWordsToAttach : maintext.getWordsCollection()) {
                wordsCollectionWordsToAttach = em.getReference(wordsCollectionWordsToAttach.getClass(), wordsCollectionWordsToAttach.getWordsPK());
                attachedWordsCollection.add(wordsCollectionWordsToAttach);
            }
            maintext.setWordsCollection(attachedWordsCollection);
            em.persist(maintext);
            if (parva != null) {
                parva.getMaintextCollection().add(maintext);
                parva = em.merge(parva);
            }
            if (ubachaId != null) {
                ubachaId.getMaintextCollection().add(maintext);
                ubachaId = em.merge(ubachaId);
            }
            for (Referencetext referencetextCollectionReferencetext : maintext.getReferencetextCollection()) {
                Maintext oldMaintextOfReferencetextCollectionReferencetext = referencetextCollectionReferencetext.getMaintext();
                referencetextCollectionReferencetext.setMaintext(maintext);
                referencetextCollectionReferencetext = em.merge(referencetextCollectionReferencetext);
                if (oldMaintextOfReferencetextCollectionReferencetext != null) {
                    oldMaintextOfReferencetextCollectionReferencetext.getReferencetextCollection().remove(referencetextCollectionReferencetext);
                    oldMaintextOfReferencetextCollectionReferencetext = em.merge(oldMaintextOfReferencetextCollectionReferencetext);
                }
            }
            for (Words wordsCollectionWords : maintext.getWordsCollection()) {
                Maintext oldMaintextOfWordsCollectionWords = wordsCollectionWords.getMaintext();
                wordsCollectionWords.setMaintext(maintext);
                wordsCollectionWords = em.merge(wordsCollectionWords);
                if (oldMaintextOfWordsCollectionWords != null) {
                    oldMaintextOfWordsCollectionWords.getWordsCollection().remove(wordsCollectionWords);
                    oldMaintextOfWordsCollectionWords = em.merge(oldMaintextOfWordsCollectionWords);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMaintext(maintext.getMaintextPK()) != null) {
                throw new PreexistingEntityException("Maintext " + maintext + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Maintext maintext) throws IllegalOrphanException, NonexistentEntityException, Exception {
        maintext.getMaintextPK().setParvaId(maintext.getParva().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maintext persistentMaintext = em.find(Maintext.class, maintext.getMaintextPK());
            Parva parvaOld = persistentMaintext.getParva();
            Parva parvaNew = maintext.getParva();
            Ubacha ubachaIdOld = persistentMaintext.getUbachaId();
            Ubacha ubachaIdNew = maintext.getUbachaId();
            Collection<Referencetext> referencetextCollectionOld = persistentMaintext.getReferencetextCollection();
            Collection<Referencetext> referencetextCollectionNew = maintext.getReferencetextCollection();
            Collection<Words> wordsCollectionOld = persistentMaintext.getWordsCollection();
            Collection<Words> wordsCollectionNew = maintext.getWordsCollection();
            List<String> illegalOrphanMessages = null;
            for (Referencetext referencetextCollectionOldReferencetext : referencetextCollectionOld) {
                if (!referencetextCollectionNew.contains(referencetextCollectionOldReferencetext)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Referencetext " + referencetextCollectionOldReferencetext + " since its maintext field is not nullable.");
                }
            }
            for (Words wordsCollectionOldWords : wordsCollectionOld) {
                if (!wordsCollectionNew.contains(wordsCollectionOldWords)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Words " + wordsCollectionOldWords + " since its maintext field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (parvaNew != null) {
                parvaNew = em.getReference(parvaNew.getClass(), parvaNew.getId());
                maintext.setParva(parvaNew);
            }
            if (ubachaIdNew != null) {
                ubachaIdNew = em.getReference(ubachaIdNew.getClass(), ubachaIdNew.getId());
                maintext.setUbachaId(ubachaIdNew);
            }
            Collection<Referencetext> attachedReferencetextCollectionNew = new ArrayList<Referencetext>();
            for (Referencetext referencetextCollectionNewReferencetextToAttach : referencetextCollectionNew) {
                referencetextCollectionNewReferencetextToAttach = em.getReference(referencetextCollectionNewReferencetextToAttach.getClass(), referencetextCollectionNewReferencetextToAttach.getReferencetextPK());
                attachedReferencetextCollectionNew.add(referencetextCollectionNewReferencetextToAttach);
            }
            referencetextCollectionNew = attachedReferencetextCollectionNew;
            maintext.setReferencetextCollection(referencetextCollectionNew);
            Collection<Words> attachedWordsCollectionNew = new ArrayList<Words>();
            for (Words wordsCollectionNewWordsToAttach : wordsCollectionNew) {
                wordsCollectionNewWordsToAttach = em.getReference(wordsCollectionNewWordsToAttach.getClass(), wordsCollectionNewWordsToAttach.getWordsPK());
                attachedWordsCollectionNew.add(wordsCollectionNewWordsToAttach);
            }
            wordsCollectionNew = attachedWordsCollectionNew;
            maintext.setWordsCollection(wordsCollectionNew);
            maintext = em.merge(maintext);
            if (parvaOld != null && !parvaOld.equals(parvaNew)) {
                parvaOld.getMaintextCollection().remove(maintext);
                parvaOld = em.merge(parvaOld);
            }
            if (parvaNew != null && !parvaNew.equals(parvaOld)) {
                parvaNew.getMaintextCollection().add(maintext);
                parvaNew = em.merge(parvaNew);
            }
            if (ubachaIdOld != null && !ubachaIdOld.equals(ubachaIdNew)) {
                ubachaIdOld.getMaintextCollection().remove(maintext);
                ubachaIdOld = em.merge(ubachaIdOld);
            }
            if (ubachaIdNew != null && !ubachaIdNew.equals(ubachaIdOld)) {
                ubachaIdNew.getMaintextCollection().add(maintext);
                ubachaIdNew = em.merge(ubachaIdNew);
            }
            for (Referencetext referencetextCollectionNewReferencetext : referencetextCollectionNew) {
                if (!referencetextCollectionOld.contains(referencetextCollectionNewReferencetext)) {
                    Maintext oldMaintextOfReferencetextCollectionNewReferencetext = referencetextCollectionNewReferencetext.getMaintext();
                    referencetextCollectionNewReferencetext.setMaintext(maintext);
                    referencetextCollectionNewReferencetext = em.merge(referencetextCollectionNewReferencetext);
                    if (oldMaintextOfReferencetextCollectionNewReferencetext != null && !oldMaintextOfReferencetextCollectionNewReferencetext.equals(maintext)) {
                        oldMaintextOfReferencetextCollectionNewReferencetext.getReferencetextCollection().remove(referencetextCollectionNewReferencetext);
                        oldMaintextOfReferencetextCollectionNewReferencetext = em.merge(oldMaintextOfReferencetextCollectionNewReferencetext);
                    }
                }
            }
            for (Words wordsCollectionNewWords : wordsCollectionNew) {
                if (!wordsCollectionOld.contains(wordsCollectionNewWords)) {
                    Maintext oldMaintextOfWordsCollectionNewWords = wordsCollectionNewWords.getMaintext();
                    wordsCollectionNewWords.setMaintext(maintext);
                    wordsCollectionNewWords = em.merge(wordsCollectionNewWords);
                    if (oldMaintextOfWordsCollectionNewWords != null && !oldMaintextOfWordsCollectionNewWords.equals(maintext)) {
                        oldMaintextOfWordsCollectionNewWords.getWordsCollection().remove(wordsCollectionNewWords);
                        oldMaintextOfWordsCollectionNewWords = em.merge(oldMaintextOfWordsCollectionNewWords);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MaintextPK id = maintext.getMaintextPK();
                if (findMaintext(id) == null) {
                    throw new NonexistentEntityException("The maintext with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MaintextPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maintext maintext;
            try {
                maintext = em.getReference(Maintext.class, id);
                maintext.getMaintextPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The maintext with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Referencetext> referencetextCollectionOrphanCheck = maintext.getReferencetextCollection();
            for (Referencetext referencetextCollectionOrphanCheckReferencetext : referencetextCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Maintext (" + maintext + ") cannot be destroyed since the Referencetext " + referencetextCollectionOrphanCheckReferencetext + " in its referencetextCollection field has a non-nullable maintext field.");
            }
            Collection<Words> wordsCollectionOrphanCheck = maintext.getWordsCollection();
            for (Words wordsCollectionOrphanCheckWords : wordsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Maintext (" + maintext + ") cannot be destroyed since the Words " + wordsCollectionOrphanCheckWords + " in its wordsCollection field has a non-nullable maintext field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Parva parva = maintext.getParva();
            if (parva != null) {
                parva.getMaintextCollection().remove(maintext);
                parva = em.merge(parva);
            }
            Ubacha ubachaId = maintext.getUbachaId();
            if (ubachaId != null) {
                ubachaId.getMaintextCollection().remove(maintext);
                ubachaId = em.merge(ubachaId);
            }
            em.remove(maintext);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Maintext> findMaintextEntities() {
        return findMaintextEntities(true, -1, -1);
    }

    public List<Maintext> findMaintextEntities(int maxResults, int firstResult) {
        return findMaintextEntities(false, maxResults, firstResult);
    }

    private List<Maintext> findMaintextEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Maintext.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Maintext findMaintext(MaintextPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Maintext.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaintextCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Maintext> rt = cq.from(Maintext.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
