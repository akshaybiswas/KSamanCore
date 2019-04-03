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
import org.dgrf.ksamancore.db.entities.Parva;

/**
 *
 * @author dgrfiv
 */
public class ParvaJpaController implements Serializable {

    public ParvaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Parva parva) throws PreexistingEntityException, Exception {
        if (parva.getMaintextCollection() == null) {
            parva.setMaintextCollection(new ArrayList<Maintext>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Maintext> attachedMaintextCollection = new ArrayList<Maintext>();
            for (Maintext maintextCollectionMaintextToAttach : parva.getMaintextCollection()) {
                maintextCollectionMaintextToAttach = em.getReference(maintextCollectionMaintextToAttach.getClass(), maintextCollectionMaintextToAttach.getMaintextPK());
                attachedMaintextCollection.add(maintextCollectionMaintextToAttach);
            }
            parva.setMaintextCollection(attachedMaintextCollection);
            em.persist(parva);
            for (Maintext maintextCollectionMaintext : parva.getMaintextCollection()) {
                Parva oldParvaOfMaintextCollectionMaintext = maintextCollectionMaintext.getParva();
                maintextCollectionMaintext.setParva(parva);
                maintextCollectionMaintext = em.merge(maintextCollectionMaintext);
                if (oldParvaOfMaintextCollectionMaintext != null) {
                    oldParvaOfMaintextCollectionMaintext.getMaintextCollection().remove(maintextCollectionMaintext);
                    oldParvaOfMaintextCollectionMaintext = em.merge(oldParvaOfMaintextCollectionMaintext);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findParva(parva.getId()) != null) {
                throw new PreexistingEntityException("Parva " + parva + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Parva parva) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Parva persistentParva = em.find(Parva.class, parva.getId());
            Collection<Maintext> maintextCollectionOld = persistentParva.getMaintextCollection();
            Collection<Maintext> maintextCollectionNew = parva.getMaintextCollection();
            List<String> illegalOrphanMessages = null;
            for (Maintext maintextCollectionOldMaintext : maintextCollectionOld) {
                if (!maintextCollectionNew.contains(maintextCollectionOldMaintext)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Maintext " + maintextCollectionOldMaintext + " since its parva field is not nullable.");
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
            parva.setMaintextCollection(maintextCollectionNew);
            parva = em.merge(parva);
            for (Maintext maintextCollectionNewMaintext : maintextCollectionNew) {
                if (!maintextCollectionOld.contains(maintextCollectionNewMaintext)) {
                    Parva oldParvaOfMaintextCollectionNewMaintext = maintextCollectionNewMaintext.getParva();
                    maintextCollectionNewMaintext.setParva(parva);
                    maintextCollectionNewMaintext = em.merge(maintextCollectionNewMaintext);
                    if (oldParvaOfMaintextCollectionNewMaintext != null && !oldParvaOfMaintextCollectionNewMaintext.equals(parva)) {
                        oldParvaOfMaintextCollectionNewMaintext.getMaintextCollection().remove(maintextCollectionNewMaintext);
                        oldParvaOfMaintextCollectionNewMaintext = em.merge(oldParvaOfMaintextCollectionNewMaintext);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = parva.getId();
                if (findParva(id) == null) {
                    throw new NonexistentEntityException("The parva with id " + id + " no longer exists.");
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
            Parva parva;
            try {
                parva = em.getReference(Parva.class, id);
                parva.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parva with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Maintext> maintextCollectionOrphanCheck = parva.getMaintextCollection();
            for (Maintext maintextCollectionOrphanCheckMaintext : maintextCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Parva (" + parva + ") cannot be destroyed since the Maintext " + maintextCollectionOrphanCheckMaintext + " in its maintextCollection field has a non-nullable parva field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(parva);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Parva> findParvaEntities() {
        return findParvaEntities(true, -1, -1);
    }

    public List<Parva> findParvaEntities(int maxResults, int firstResult) {
        return findParvaEntities(false, maxResults, firstResult);
    }

    private List<Parva> findParvaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Parva.class));
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

    public Parva findParva(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Parva.class, id);
        } finally {
            em.close();
        }
    }

    public int getParvaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Parva> rt = cq.from(Parva.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
