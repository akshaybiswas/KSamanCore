/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.ksamancore.bl.service;

import java.util.List;
import org.dgrf.ksamancore.db.DAO.ParvaDAO;
import org.dgrf.ksamancore.db.entities.Parva;

/**
 *
 * @author bhaduri
 */
public class KSCoreService {
    
    public void getAllParva() {
        ParvaDAO parvaDAO = new ParvaDAO(DatabaseConnection.EMF);
        List<Parva> parvaList = parvaDAO.findParvaEntities();
        parvaList.stream().forEach(x->System.out.println(x.getName()));
    }
    
}
