/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.ksamancore.db.DAO;

import javax.persistence.EntityManagerFactory;
import org.dgrf.ksamancore.db.JPA.UbachaJpaController;

/**
 *
 * @author dgrfiv
 */
public class UbachaDAO extends UbachaJpaController{
    
    public UbachaDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
}
