/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.ksamancore.DTO;

import java.util.Date;

/**
 *
 * @author dgrfiv
 */
public class MaintextDTO {
    
    private int parvaId;
    private int adhyayId;
    private int ubachaId;
    private int shlokaNum;
    private int shlokaLine;
    private String shlokaText;
    private String firstChar;
    private String endChar;
    private String anubadText;
    private String ubachaName;
    private String ubachaBachan;
    private Date lastEditTS;
    
    private int maxShlokaNum;
    private int maxShlokaLine;

    public int getParvaId() {
        return parvaId;
    }

    public void setParvaId(int parvaId) {
        this.parvaId = parvaId;
    }

    public int getAdhyayId() {
        return adhyayId;
    }

    public void setAdhyayId(int adhyayId) {
        this.adhyayId = adhyayId;
    }

    public int getUbachaId() {
        return ubachaId;
    }

    public void setUbachaId(int ubachaId) {
        this.ubachaId = ubachaId;
    }

    public int getShlokaNum() {
        return shlokaNum;
    }

    public void setShlokaNum(int shlokaNum) {
        this.shlokaNum = shlokaNum;
    }

    public int getShlokaLine() {
        return shlokaLine;
    }

    public void setShlokaLine(int shlokaLine) {
        this.shlokaLine = shlokaLine;
    }

    public String getShlokaText() {
        return shlokaText;
    }

    public void setShlokaText(String shlokaText) {
        this.shlokaText = shlokaText;
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }

    public String getEndChar() {
        return endChar;
    }

    public void setEndChar(String endChar) {
        this.endChar = endChar;
    }

    public String getAnubadText() {
        return anubadText;
    }

    public void setAnubadText(String anubadText) {
        this.anubadText = anubadText;
    }

    public Date getLastEditTS() {
        return lastEditTS;
    }

    public void setLastEditTS(Date lastEditTS) {
        this.lastEditTS = lastEditTS;
    }

    public String getUbachaName() {
        return ubachaName;
    }

    public void setUbachaName(String ubachaName) {
        this.ubachaName = ubachaName;
    }

    public String getUbachaBachan() {
        return ubachaBachan;
    }

    public void setUbachaBachan(String ubachaBachan) {
        this.ubachaBachan = ubachaBachan;
    }

    public int getMaxShlokaNum() {
        return maxShlokaNum;
    }

    public void setMaxShlokaNum(int maxShlokaNum) {
        this.maxShlokaNum = maxShlokaNum;
    }

    public int getMaxShlokaLine() {
        return maxShlokaLine;
    }

    public void setMaxShlokaLine(int maxShlokaLine) {
        this.maxShlokaLine = maxShlokaLine;
    }
    
}
