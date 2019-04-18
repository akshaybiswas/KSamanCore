/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.ksamancore.bl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dgrf.cloud.response.DGRFResponseCode;
import org.dgrf.ksamancore.DTO.MaintextDTO;
import org.dgrf.ksamancore.DTO.ParvaDTO;
import org.dgrf.ksamancore.DTO.UbachaDTO;
import org.dgrf.ksamancore.DTO.WordsDTO;
import org.dgrf.ksamancore.db.DAO.MaintextDAO;
import org.dgrf.ksamancore.db.DAO.ParvaDAO;
import org.dgrf.ksamancore.db.DAO.UbachaDAO;
import org.dgrf.ksamancore.db.DAO.WordsDAO;
import org.dgrf.ksamancore.db.JPA.exceptions.IllegalOrphanException;
import org.dgrf.ksamancore.db.JPA.exceptions.NonexistentEntityException;
import org.dgrf.ksamancore.db.JPA.exceptions.PreexistingEntityException;
import org.dgrf.ksamancore.db.entities.Maintext;
import org.dgrf.ksamancore.db.entities.MaintextPK;
import org.dgrf.ksamancore.db.entities.Parva;
import org.dgrf.ksamancore.db.entities.Ubacha;
import org.dgrf.ksamancore.db.entities.Words;
import org.dgrf.ksamancore.db.entities.WordsPK;

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

    public WordsPK getWordsPK(WordsDTO wordsDTO) {
        WordsPK wordsPK = new WordsPK();

        wordsPK.setMaintextParvaId(wordsDTO.getParvaId());
        wordsPK.setMaintextAdhyayid(wordsDTO.getAdhyayId());
        wordsPK.setMaintextShlokanum(wordsDTO.getShlokaNum());
        wordsPK.setMaintextShlokaline(wordsDTO.getShlokaLine());
        wordsPK.setWordnum(wordsDTO.getWordNum());

        return wordsPK;
    }

    public WordsDTO getWordsDTO(WordsDTO wordsDTO) {
        WordsDAO wordsDAO = new WordsDAO(DatabaseConnection.EMF);
        WordsPK wordsPK = getWordsPK(wordsDTO);

        Words words = wordsDAO.findWords(wordsPK);

        wordsDTO.setWordText(words.getWordtext());
        wordsDTO.setWordFirstChar(words.getFirstchar());

        return wordsDTO;
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
        maintextDTO.setUbachaId(maintext.getUbachaId().getId());
        maintextDTO.setUbachaName(maintext.getUbachaId().getName());
        maintextDTO.setUbachaBachan(maintext.getUbachaId().getBachan());

        return maintextDTO;
    }

    public List<ParvaDTO> getParvaDTOList() {
        ParvaDAO parvaDAO = new ParvaDAO(DatabaseConnection.EMF);
        List<Parva> parvaList = parvaDAO.findParvaEntities();

        List<ParvaDTO> parvaDTOList = new ArrayList<>();

        for (int i = 0; i < parvaList.size(); i++) {
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

        for (int i = 0; i < ubachaList.size(); i++) {
            UbachaDTO ubachaDTO = new UbachaDTO();

            ubachaDTO.setUbachaId(ubachaList.get(i).getId());
            ubachaDTO.setUbachaName(ubachaList.get(i).getName());
            ubachaDTO.setUbachaBachan(ubachaList.get(i).getBachan());

            ubachaDTOList.add(ubachaDTO);
        }
        return ubachaDTOList;
    }

    public List<WordsDTO> getWordsDTOList() {
        WordsDAO wordsDAO = new WordsDAO(DatabaseConnection.EMF);
        List<Words> wordsList = wordsDAO.findWordsEntities();

        List<WordsDTO> wordsDTOList = new ArrayList<>();

        for (int i = 0; i < wordsList.size(); i++) {
            WordsDTO wordsDTO = new WordsDTO();

            wordsDTO.setAdhyayId(wordsList.get(i).getWordsPK().getMaintextAdhyayid());
            wordsDTO.setParvaId(wordsList.get(i).getWordsPK().getMaintextParvaId());
            wordsDTO.setShlokaNum(wordsList.get(i).getWordsPK().getMaintextShlokanum());
            wordsDTO.setShlokaLine(wordsList.get(i).getWordsPK().getMaintextShlokaline());
            wordsDTO.setWordNum(wordsList.get(i).getWordsPK().getWordnum());
            wordsDTO.setWordText(wordsList.get(i).getWordtext());
            wordsDTO.setWordFirstChar(wordsList.get(i).getFirstchar());

            wordsDTOList.add(wordsDTO);
        }
        return wordsDTOList;
    }

    public List<MaintextDTO> getMaintextDTOList() {
        MaintextDAO maintextDAO = new MaintextDAO(DatabaseConnection.EMF);
        List<Maintext> maintextList = maintextDAO.findMaintextEntities();

        List<MaintextDTO> maintextDTOList = new ArrayList<>();

        for (int i = 0; i < maintextList.size(); i++) {
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

    //////////////////// WORDS OPERATIONS ////////////////////
    public void insertWordsToWordsTable(Maintext maintext) {
        WordsDAO wordsDAO = new WordsDAO(DatabaseConnection.EMF);

        String shloka = maintext.getShlokatext().trim();
        String[] shlokaWords = shloka.split(" ");

        for (int i = 0; i < shlokaWords.length; i++) {

            Words words = new Words();
            WordsPK wordsPK = new WordsPK();

            wordsPK.setMaintextParvaId(maintext.getMaintextPK().getParvaId());
            wordsPK.setMaintextAdhyayid(maintext.getMaintextPK().getAdhyayid());
            wordsPK.setMaintextShlokanum(maintext.getMaintextPK().getShlokanum());
            wordsPK.setMaintextShlokaline(maintext.getMaintextPK().getShlokaline());
            wordsPK.setWordnum(i);
            words.setWordsPK(wordsPK);
            words.setMaintext(maintext);
            words.setFirstchar(Character.toString(shlokaWords[i].charAt(0)));
            words.setWordtext(shlokaWords[i]);

            try {
                wordsDAO.create(words);
            } catch (Exception ex) {
                Logger.getLogger(KSCoreService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //////////////////// MAINTEXT OPERATIONS ////////////////////
    
    public List<MaintextDTO> getAdhyayIdList(int parvaId) {
        MaintextDAO maintextDAO = new MaintextDAO(DatabaseConnection.EMF);

        List<Integer> adhyayList = maintextDAO.getAdhyayByParvaId(parvaId);
        List<MaintextDTO> adhyayDTOList = new ArrayList<>();

        for (int i = 0; i < adhyayList.size(); i++) {

            MaintextDTO maintextDTO = new MaintextDTO();

            maintextDTO.setAdhyayId(adhyayList.get(i));

            adhyayDTOList.add(maintextDTO);
        }
        return adhyayDTOList;
    }

    public List<MaintextDTO> getShlokaList(int parvaId, int adhyayId) {
        MaintextDAO maintextDAO = new MaintextDAO(DatabaseConnection.EMF);

        List<Maintext> shlokaList = maintextDAO.getShlokaByParvaAndAdhyayId(parvaId, adhyayId);
        List<MaintextDTO> shlokaDTOList = new ArrayList<>();
        UbachaDTO ubachaDTO = new UbachaDTO();

        for (int i = 0; i < shlokaList.size(); i++) {
            MaintextDTO shlokaDTO = new MaintextDTO();

            ubachaDTO.setUbachaId(shlokaList.get(i).getUbachaId().getId());
            UbachaDTO ubachaData = getUbachaDTO(ubachaDTO);

            shlokaDTO.setUbachaName(ubachaData.getUbachaName());
            shlokaDTO.setUbachaBachan(ubachaData.getUbachaBachan());
            shlokaDTO.setShlokaText(shlokaList.get(i).getShlokatext());
            shlokaDTO.setShlokaLine(shlokaList.get(i).getMaintextPK().getShlokaline());
            shlokaDTO.setShlokaNum(shlokaList.get(i).getMaintextPK().getShlokanum());
            shlokaDTO.setUbachaId(shlokaList.get(i).getUbachaId().getId());

            shlokaDTOList.add(shlokaDTO);
        }
        return shlokaDTOList;
    }

    public int getMaxShlokaNumber(MaintextDTO maintextDTO) {
        MaintextDAO maintextDAO = new MaintextDAO(DatabaseConnection.EMF);

        int maintextMaxShlokaNum;

        maintextMaxShlokaNum = maintextDAO.getMaxShlokaNum(maintextDTO.getParvaId(), maintextDTO.getAdhyayId());

        return maintextMaxShlokaNum;
    }

    public int getMaxShlokaLine(MaintextDTO maintextDTO) {
        MaintextDAO maintextDAO = new MaintextDAO(DatabaseConnection.EMF);

        int maintextMaxShlokaLine;

        maintextMaxShlokaLine = maintextDAO.getMaxShlokaLine(maintextDTO.getParvaId(), maintextDTO.getAdhyayId(), maintextDTO.getMaxShlokaNum());

        return maintextMaxShlokaLine;
    }
    
    public List<MaintextDTO> getMaintextTranslation(int parvaId, int adhyayId, int shlokaNum) {
        MaintextDAO maintextDAO = new MaintextDAO(DatabaseConnection.EMF);
        
        List<Maintext> shlokaList = maintextDAO.getShlokaTranslation(parvaId, adhyayId, shlokaNum);
        List<MaintextDTO> translationDTOList = new ArrayList<>();
        UbachaDTO ubachaDTO = new UbachaDTO();

        for (int i = 0; i < shlokaList.size(); i++) {
            MaintextDTO translationDTO = new MaintextDTO();

            ubachaDTO.setUbachaId(shlokaList.get(i).getUbachaId().getId());
            UbachaDTO ubachaData = getUbachaDTO(ubachaDTO);

            translationDTO.setUbachaName(ubachaData.getUbachaName());
            translationDTO.setUbachaBachan(ubachaData.getUbachaBachan());
            translationDTO.setShlokaText(shlokaList.get(i).getShlokatext());
            translationDTO.setAdhyayId(shlokaList.get(i).getMaintextPK().getAdhyayid());
            translationDTO.setShlokaLine(shlokaList.get(i).getMaintextPK().getShlokaline());
            translationDTO.setShlokaNum(shlokaList.get(i).getMaintextPK().getShlokanum());
            translationDTO.setUbachaId(shlokaList.get(i).getUbachaId().getId());
            translationDTO.setAnubadText(shlokaList.get(i).getTranslatedtext());
            translationDTO.setParvaId(shlokaList.get(i).getMaintextPK().getParvaId());
            translationDTO.setParvaName(shlokaList.get(i).getParva().getName());

            translationDTOList.add(translationDTO);
        }
        return translationDTOList;
    }
    
    public int updateMaintextTranslation(MaintextDTO maintextDTO) {
        int responseCode;

        MaintextDAO maintextDAO = new MaintextDAO(DatabaseConnection.EMF);
        MaintextPK maintextPK = new MaintextPK();
        Date updatedDate = new Date();

        maintextPK.setParvaId(maintextDTO.getParvaId());
        maintextPK.setAdhyayid(maintextDTO.getAdhyayId());
        maintextPK.setShlokanum(maintextDTO.getShlokaNum());
        maintextPK.setShlokaline(1);

        Maintext maintext = maintextDAO.findMaintext(maintextPK);
        
        maintext.setTranslatedtext(maintextDTO.getAnubadText());
        maintext.setLastupdatedts(updatedDate);

        try {
            maintextDAO.edit(maintext);
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

    public int addNewShloka(MaintextDTO maintextDTO) {
        int responseCode;

        MaintextDAO maintextDAO = new MaintextDAO(DatabaseConnection.EMF);
        MaintextPK maintextPK = new MaintextPK();
        Maintext maintext = new Maintext();
        Date createdDate = new Date();
        UbachaDAO ubachaDAO = new UbachaDAO(DatabaseConnection.EMF);
        Ubacha ubacha = ubachaDAO.findUbacha(maintextDTO.getUbachaId());
        ParvaDAO parvaDAO = new ParvaDAO(DatabaseConnection.EMF);
        Parva parva = parvaDAO.findParva(maintextDTO.getParvaId());

        maintextPK.setParvaId(maintextDTO.getParvaId());
        maintextPK.setAdhyayid(maintextDTO.getAdhyayId());
        maintextPK.setShlokaline(maintextDTO.getShlokaLine());
        maintextPK.setShlokanum(maintextDTO.getShlokaNum());

        maintext.setMaintextPK(maintextPK);
        maintext.setUbachaId(ubacha);
        maintext.setShlokatext(maintextDTO.getShlokaText());
        maintext.setTranslatedtext(maintextDTO.getAnubadText());
        maintext.setLastupdatedts(createdDate);
        maintext.setParva(parva);

        String startChar = maintextDTO.getShlokaText();
        String endChar = maintextDTO.getEndChar();

        maintext.setFirstchar(Character.toString(startChar.charAt(0)));
        maintext.setEndchar(endChar);

        try {
            maintextDAO.create(maintext);
            insertWordsToWordsTable(maintext);
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

    public int updateShloka(MaintextDTO maintextDTO) {
        int responseCode;

        MaintextDAO maintextDAO = new MaintextDAO(DatabaseConnection.EMF);
        MaintextPK maintextPK = new MaintextPK();
        Date updatedDate = new Date();
        UbachaDAO ubachaDAO = new UbachaDAO(DatabaseConnection.EMF);
        Ubacha ubacha = ubachaDAO.findUbacha(maintextDTO.getUbachaId());
        ParvaDAO parvaDAO = new ParvaDAO(DatabaseConnection.EMF);
        Parva parva = parvaDAO.findParva(maintextDTO.getParvaId());
        WordsDAO wordsDAO = new WordsDAO(DatabaseConnection.EMF);

        maintextPK.setParvaId(maintextDTO.getParvaId());
        maintextPK.setAdhyayid(maintextDTO.getAdhyayId());
        maintextPK.setShlokaline(maintextDTO.getShlokaLine());
        maintextPK.setShlokanum(maintextDTO.getShlokaNum());
        
        wordsDAO.deleteAllWordsAndChars(maintextDTO.getParvaId(), maintextDTO.getAdhyayId(), maintextDTO.getShlokaNum(), maintextDTO.getShlokaLine());
        Maintext maintext = maintextDAO.findMaintext(maintextPK);

        maintext.setMaintextPK(maintextPK);
        maintext.setUbachaId(ubacha);
        maintext.setShlokatext(maintextDTO.getShlokaText());
        maintext.setTranslatedtext(maintextDTO.getAnubadText());
        maintext.setLastupdatedts(updatedDate);
        maintext.setParva(parva);

        String startChar = maintextDTO.getShlokaText();
        String endChar = maintextDTO.getEndChar();

        maintext.setFirstchar(Character.toString(startChar.charAt(0)));
        maintext.setEndchar(endChar);

        try {
            maintextDAO.edit(maintext);
            
            insertWordsToWordsTable(maintext);
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

    public int removeShloka(MaintextDTO maintextDTO) {
        int responseCode;

        MaintextDAO maintextDAO = new MaintextDAO(DatabaseConnection.EMF);
        WordsDAO wordsDAO = new WordsDAO(DatabaseConnection.EMF);
        MaintextPK maintextPK = new MaintextPK();

        maintextPK.setParvaId(maintextDTO.getParvaId());
        maintextPK.setAdhyayid(maintextDTO.getAdhyayId());
        maintextPK.setShlokaline(maintextDTO.getShlokaLine());
        maintextPK.setShlokanum(maintextDTO.getShlokaNum());

        try {
            wordsDAO.deleteAllWordsAndChars(maintextDTO.getParvaId(), maintextDTO.getAdhyayId(), maintextDTO.getShlokaNum(), maintextDTO.getShlokaLine());
            maintextDAO.destroy(maintextPK);
            responseCode = DGRFResponseCode.SUCCESS;

        } catch (NonexistentEntityException ex) {
            Logger.getLogger(KSCoreService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = DGRFResponseCode.DB_NON_EXISTING;

        } catch (IllegalOrphanException ex) {
            Logger.getLogger(KSCoreService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = DGRFResponseCode.DB_ILLEGAL_ORPHAN;

        }
        return responseCode;
    }
    
    public List<MaintextDTO> getShlokaUniqueFirstCharList() {
        MaintextDAO maintextDAO = new MaintextDAO(DatabaseConnection.EMF);
        
        List<String> firstCharList;
        firstCharList = maintextDAO.getUniqueShlokaFirstCharList();
        List<MaintextDTO> maintextDTOList = new ArrayList<>();
        
        for(int i = 0; i<firstCharList.size(); i++) {
            MaintextDTO maintextDTO = new MaintextDTO();
            
            maintextDTO.setFirstChar(firstCharList.get(i));
            
            maintextDTOList.add(maintextDTO);
        }
        return maintextDTOList;
    }
    
    public List<MaintextDTO> getShlokaListByFirstChar(String selectedFirstChar,int first,int pagesize){
        
        MaintextDAO maintextDAO = new MaintextDAO(DatabaseConnection.EMF);
        
        List<Maintext> maintext = maintextDAO.getShlokaByFirstChar(selectedFirstChar, first, pagesize);
        
        List<MaintextDTO> maintextDTOList = new ArrayList<>();
        for(int i = 0; i<maintext.size(); i++) {
            MaintextDTO maintextDTO = new MaintextDTO();
            
            maintextDTO.setParvaId(maintext.get(i).getMaintextPK().getParvaId());
            maintextDTO.setAdhyayId(maintext.get(i).getMaintextPK().getAdhyayid());
            maintextDTO.setShlokaNum(maintext.get(i).getMaintextPK().getShlokanum());
            maintextDTO.setShlokaLine(maintext.get(i).getMaintextPK().getShlokaline());
            maintextDTO.setShlokaText(maintext.get(i).getShlokatext());
            maintextDTO.setParvaName(maintext.get(i).getParva().getName());
            maintextDTO.setUbachaId(maintext.get(i).getUbachaId().getId());
            maintextDTO.setUbachaName(maintext.get(i).getUbachaId().getName());
            maintextDTO.setUbachaBachan(maintext.get(i).getUbachaId().getBachan());
            
            maintextDTOList.add(maintextDTO);
        }
        return maintextDTOList;
    }
    
    public int getShlokaCountByFirstChar(String firstChar){
        MaintextDAO maintextDAO = new MaintextDAO(DatabaseConnection.EMF);
        int shlokaCount = maintextDAO.getShlokaCountByFirstChar(firstChar);
        return shlokaCount;
    }
}
