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
import org.dgrf.ksamancore.db.entities.Maintext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.dgrf.ksamancore.db.JPA.exceptions.IllegalOrphanException;
import org.dgrf.ksamancore.db.JPA.exceptions.NonexistentEntityException;
import org.dgrf.ksamancore.db.JPA.exceptions.PreexistingEntityException;
import org.dgrf.ksamancore.db.entities.Ubacha;

/**
 *
 * @author dgrfiv
 */
public class UbachaJpaController implements Serializable {

    public UbachaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ubacha ubacha) throws PreexistingEntityException, Exception {
        if (ubacha.getMaintextCollection() == null) {
            ubacha.setMaintextCollection(new ArrayList<Maintext>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Maintext> attachedMaintextCollection = new ArrayList<Maintext>();
            for (Maintext maintextCollectionMaintextToAttach : ubacha.getMaintextCollection()) {
                maintextCollectionMaintextToAttach = em.getReference(maintextCollectionMaintextToAttach.getClass(), maintextCollectionMaintextToAttach.getMaintextPK());
                attachedMaintextCollection.add(maintextCollectionMaintextToAttach);
            }
            ubacha.setMaintextCollection(attachedMaintextCollection);
            em.persist(ubacha);
            for (Maintext maintextCollectionMaintext : ubacha.getMaintextCollection()) {
                Ubacha oldUbachaIdOfMaintextCollectionMaintext = maintextCollectionMaintext.getUbachaId();
                maintextCollectionMaintext.setUbachaId(ubacha);
                maintextCollectionMaintext = em.merge(maintextCollectionMaintext);
                if (oldUbachaIdOfMaintextCollectionMaintext != null) {
                    oldUbachaIdOfMaintextCollectionMaintext.getMaintextCollection().remove(maintextCollectionMaintext);
                    oldUbachaIdOfMaintextCollectionMaintext = em.merge(oldUbachaIdOfMaintextCollectionMaintext);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUbacha(ubacha.getId()) != null) {
                throw new PreexistingEntityException("Ubacha " + ubacha + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ubacha ubacha) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ubacha persistentUbacha = em.find(Ubacha.class, ubacha.getId());
            Collection<Maintext> maintextCollectionOld = persistentUbacha.getMaintextCollection();
            Collection<Maintext> maintextCollectionNew = ubacha.getMaintextCollection();
            List<String> illegalOrphanMessages = null;
            for (Maintext maintextCollectionOldMaintext : maintextCollectionOld) {
                if (!maintextCollectionNew.contains(maintextCollectionOldMaintext)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Maintext " + maintextCollectionOldMaintext + " since its ubachaId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Maintext> attachedMaintextCollectionNew = new ArrayList<Maintext>();
            for (Maintext maintextCollectionNewMaintextToAttach : maintextCollectionNew) {
                maintextCollectionNewMaintextToAttach = em.getReference(maintextCollectionNewMaintextToAttach.getClass(), maintextCollectionNewMaintextToAttach.getMaintextPK());
                attachedMaintextCollectionNew.add(maintextCollectionNewMaintextToAttach);
            }
            maintextCollectionNew = attachedMaintextCollectionNew;
            ubacha.setMaintextCollection(maintextCollectionNew);
            ubacha = em.merge(ubacha);
            for (Maintext maintextCollectionNewMaintext : maintextCollectionNew) {
                if (!maintextCollectionOld.contains(maintextCollectionNewMaintext)) {
                    Ubacha oldUbachaIdOfMaintextCollectionNewMaintext = maintextCollectionNewMaintext.getUbachaId();
                    maintextCollectionNewMaintext.setUbachaId(ubacha);
                    maintextCollectionNewMaintext = em.merge(maintextCollectionNewMaintext);
                    if (oldUbachaIdOfMaintextCollectionNewMaintext != null && !oldUbachaIdOfMaintextCollectionNewMaintext.equals(ubacha)) {
                        oldUbachaIdOfMaintextCollectionNewMaintext.getMaintextCollection().remove(maintextCollectionNewMaintext);
                        oldUbachaIdOfMaintextCollectionNewMaintext = em.merge(oldUbachaIdOfMaintextCollectionNewMaintext);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ubacha.getId();
                if (findUbacha(id) == null) {
                    throw new NonexistentEntityException("The ubacha with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ubacha ubacha;
            try {
                ubacha = em.getReference(Ubacha.class, id);
                ubacha.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ubacha with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Maintext> maintextCollectionOrphanCheck = ubacha.getMaintextCollection();
            for (Maintext maintextCollectionOrphanCheckMaintext : maintextCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ubacha (" + ubacha + ") cannot be destroyed since the Maintext " + maintextCollectionOrphanCheckMaintext + " in its maintextCollection field has a non-nullable ubachaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ubacha);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ubacha> findUbachaEntities() {
        return findUbachaEntities(true, -1, -1);
    }

    public List<Ubacha> findUbachaEntities(int maxResults, int firstResult) {
        return findUbachaEntities(false, maxResults, firstResult);
    }

    private List<Ubacha> findUbachaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ubacha.class));
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

    public Ubacha findUbacha(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ubacha.class, id);
        } finally {
            em.close();
        }
    }

    public int getUbachaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ubacha> rt = cq.from(Ubacha.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
