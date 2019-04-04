/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.ksamancore.bl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dgrf.cloud.response.DGRFResponseCode;
import org.dgrf.ksamancore.DTO.MaintextDTO;
import org.dgrf.ksamancore.DTO.ParvaDTO;
import org.dgrf.ksamancore.DTO.UbachaDTO;
import org.dgrf.ksamancore.db.DAO.MaintextDAO;
import org.dgrf.ksamancore.db.DAO.ParvaDAO;
import org.dgrf.ksamancore.db.DAO.UbachaDAO;
import org.dgrf.ksamancore.db.JPA.exceptions.IllegalOrphanException;
import org.dgrf.ksamancore.db.JPA.exceptions.NonexistentEntityException;
import org.dgrf.ksamancore.db.JPA.exceptions.PreexistingEntityException;
import org.dgrf.ksamancore.db.entities.Maintext;
import org.dgrf.ksamancore.db.entities.MaintextPK;
import org.dgrf.ksamancore.db.entities.Parva;
import org.dgrf.ksamancore.db.entities.Ubacha;

/**
 *
 * @author bhaduri
 */
public class KSCoreService {
    
//    public void getAllParva() {
//        ParvaDAO parvaDAO = new ParvaDAO(DatabaseConnection.EMF);
//        List<Parva> parvaList = parvaDAO.findParvaEntities();
//        parvaList.stream().forEach(x->System.out.println(x.getName()));
//    }
    
    public ParvaDTO getParvaDTO(ParvaDTO parvaDTO) {
        ParvaDAO parvaDAO = new ParvaDAO(DatabaseConnection.EMF);
        Parva parva = parvaDAO.findParva(parvaDTO.getParvaId());
        
        parvaDTO.setParvaId(parva.getId());
        parvaDTO.setParvaName(parva.getName());
        
        return parvaDTO;
    }
    
    public UbachaDTO getUbachaDTO(UbachaDTO ubachaDTO) {
        UbachaDAO ubachaDAO = new UbachaDAO(DatabaseConnection.EMF);
        Ubacha ubacha = ubachaDAO.findUbacha(ubachaDTO.getUbachaId());
        
        ubachaDTO.setUbachaId(ubacha.getId());
        ubachaDTO.setUbachaName(ubacha.getName());
        ubachaDTO.setUbachaBachan(ubacha.getBachan());
        
        return ubachaDTO;
    }
    
    public MaintextPK getMaintextPK(MaintextDTO maintextDTO) {
        MaintextPK maintextPK = new MaintextPK();
        
        maintextPK.setParvaId(maintextDTO.getParvaId());
        maintextPK.setAdhyayid(maintextDTO.getAdhyayId());
        maintextPK.setShlokanum(maintextDTO.getShlokaNum());
        maintextPK.setShlokaline(maintextDTO.getShlokaLine());
        
        return maintextPK;
    }
    
    public MaintextDTO getMaintextDTO(MaintextDTO maintextDTO) {
        
        MaintextDAO maintextDAO = new MaintextDAO(DatabaseConnection.EMF);
        
        MaintextPK maintextPK = getMaintextPK(maintextDTO);
        Maintext maintext = maintextDAO.findMaintext(maintextPK);
        
        maintextDTO.setShlokaText(maintext.getShlokatext());
        maintextDTO.setFirstChar(maintext.getFirstchar());
        maintextDTO.setEndChar(maintext.getEndchar());
        maintextDTO.setAnubadText(maintext.getTranslatedtext());
        
        return maintextDTO;
    } 
    
    public List<ParvaDTO> getParvaDTOList() {
        ParvaDAO parvaDAO = new ParvaDAO(DatabaseConnection.EMF);
        List<Parva> parvaList = parvaDAO.findParvaEntities();
        
        List<ParvaDTO> parvaDTOList = new ArrayList<>();
        
        for(int i = 0; i<parvaList.size(); i++) {
            ParvaDTO parvaDTO = new ParvaDTO();
            
            parvaDTO.setParvaId(parvaList.get(i).getId());
            parvaDTO.setParvaName(parvaList.get(i).getName());
            
            parvaDTOList.add(parvaDTO);
        }
        return parvaDTOList;
    }
    
    public List<UbachaDTO> getUbachaDTOList() {
        UbachaDAO ubachaDAO = new UbachaDAO(DatabaseConnection.EMF);
        List<Ubacha> ubachaList = ubachaDAO.findUbachaEntities();
        
        List<UbachaDTO> ubachaDTOList = new ArrayList<>();
        
        for(int i=0; i<ubachaList.size(); i++) {
            UbachaDTO ubachaDTO = new UbachaDTO();
            
            ubachaDTO.setUbachaId(ubachaList.get(i).getId());
            ubachaDTO.setUbachaName(ubachaList.get(i).getName());
            ubachaDTO.setUbachaBachan(ubachaList.get(i).getBachan());
            
            ubachaDTOList.add(ubachaDTO);
        }
        return ubachaDTOList;
    }
    
    public List<MaintextDTO> getMaintextDTOList() {
        MaintextDAO maintextDAO = new MaintextDAO(DatabaseConnection.EMF);
        List<Maintext> maintextList = maintextDAO.findMaintextEntities();
        
        List<MaintextDTO> maintextDTOList = new ArrayList<>();
        
        for(int i=0; i<maintextList.size(); i++) {
            MaintextDTO maintextDTO = new MaintextDTO();
            
            maintextDTO.setAdhyayId(maintextList.get(i).getMaintextPK().getAdhyayid());
            maintextDTO.setParvaId(maintextList.get(i).getMaintextPK().getParvaId());
            maintextDTO.setShlokaNum(maintextList.get(i).getMaintextPK().getShlokanum());
            maintextDTO.setShlokaLine(maintextList.get(i).getMaintextPK().getShlokaline());
            maintextDTO.setUbachaId(maintextList.get(i).getUbachaId().getId());
            maintextDTO.setShlokaText(maintextList.get(i).getShlokatext());
            maintextDTO.setAnubadText(maintextList.get(i).getTranslatedtext());
            maintextDTO.setFirstChar(maintextList.get(i).getFirstchar());
            maintextDTO.setEndChar(maintextList.get(i).getEndchar());
            maintextDTO.setLastEditTS(maintextList.get(i).getLastupdatedts());
            
            maintextDTOList.add(maintextDTO);
        }
        return maintextDTOList;
    }
    
    
    //////////////////// PARVA CRUD ////////////////////
    
    
    public int addParva(ParvaDTO parvaDTO) {
        int responseCode;
        
        ParvaDAO parvaDAO = new ParvaDAO(DatabaseConnection.EMF);
        Parva parva = new Parva();
        
        parva.setId(parvaDTO.getParvaId());
        parva.setName(parvaDTO.getParvaName());
        
        try {
            parvaDAO.create(parva);
            responseCode = DGRFResponseCode.SUCCESS;
            
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(KSCoreService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = DGRFResponseCode.DB_DUPLICATE;
        
        } catch (Exception ex) {
            Logger.getLogger(KSCoreService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = DGRFResponseCode.DB_SEVERE;
        }
        return responseCode;
    }
    
    public int updateParva(ParvaDTO parvaDTO) {
        int responseCode;
        
        ParvaDAO parvaDAO = new ParvaDAO(DatabaseConnection.EMF);
        Parva parva = parvaDAO.findParva(parvaDTO.getParvaId());
        
        parva.setName(parvaDTO.getParvaName());
        
        try {
            parvaDAO.edit(parva);
            responseCode = DGRFResponseCode.SUCCESS;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(KSCoreService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = DGRFResponseCode.DB_NON_EXISTING;
        } catch (Exception ex) {
            Logger.getLogger(KSCoreService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = DGRFResponseCode.DB_SEVERE;
        }
        return responseCode;
    }
    
    public int removeParva(ParvaDTO parvaDTO) {
        int responseCode;
        
        ParvaDAO parvaDAO = new ParvaDAO(DatabaseConnection.EMF);
        Parva parva = parvaDAO.findParva(parvaDTO.getParvaId());
        
        try {
            parvaDAO.destroy(parva.getId());
            responseCode = DGRFResponseCode.SUCCESS;
            
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(KSCoreService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = DGRFResponseCode.DB_ILLEGAL_ORPHAN;
            
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(KSCoreService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = DGRFResponseCode.DB_NON_EXISTING;
        }
        return responseCode;
    }
    
    //////////////////// UBACHA CRUD ////////////////////
    
    public int addUbacha(UbachaDTO ubachaDTO) {
        int responseCode;
        
        UbachaDAO ubachaDAO = new UbachaDAO(DatabaseConnection.EMF);
        Ubacha ubacha = new Ubacha();
        
        ubacha.setId(ubachaDTO.getUbachaId());
        ubacha.setName(ubachaDTO.getUbachaName());
        ubacha.setBachan(ubachaDTO.getUbachaBachan());
        
        try {
            ubachaDAO.create(ubacha);
            responseCode = DGRFResponseCode.SUCCESS;
            
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(KSCoreService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = DGRFResponseCode.DB_DUPLICATE;
        
        } catch (Exception ex) {
            Logger.getLogger(KSCoreService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = DGRFResponseCode.DB_SEVERE;
        }
        return responseCode;
    }
    
    public int updateUbacha(UbachaDTO ubachaDTO) {
        int responseCode;
        
        UbachaDAO ubachaDAO = new UbachaDAO(DatabaseConnection.EMF);
        Ubacha ubacha = ubachaDAO.findUbacha(ubachaDTO.getUbachaId());
        
        ubacha.setName(ubachaDTO.getUbachaName());
        ubacha.setBachan(ubachaDTO.getUbachaBachan());
        
        try {
            ubachaDAO.edit(ubacha);
            responseCode = DGRFResponseCode.SUCCESS;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(KSCoreService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = DGRFResponseCode.DB_NON_EXISTING;
        } catch (Exception ex) {
            Logger.getLogger(KSCoreService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = DGRFResponseCode.DB_SEVERE;
        }
        return responseCode;
    }
    
    
    public int removeUbacha(UbachaDTO ubachaDTO) {
        int responseCode;
        
        UbachaDAO ubachaDAO = new UbachaDAO(DatabaseConnection.EMF);
        Ubacha ubacha = ubachaDAO.findUbacha(ubachaDTO.getUbachaId());
        
        try {
            ubachaDAO.destroy(ubacha.getId());
            responseCode = DGRFResponseCode.SUCCESS;
            
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(KSCoreService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = DGRFResponseCode.DB_ILLEGAL_ORPHAN;
            
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(KSCoreService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = DGRFResponseCode.DB_NON_EXISTING;
        }
        return responseCode;
    }
}
