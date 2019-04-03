/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.ksamancore.db.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author dgrfiv
 */
@Embeddable
public class ReferencetextPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "maintext_parva_id")
    private int maintextParvaId;
    @Basic(optional = false)
    @Column(name = "maintext_adhyayid")
    private int maintextAdhyayid;
    @Basic(optional = false)
    @Column(name = "maintext_shlokanum")
    private int maintextShlokanum;
    @Basic(optional = false)
    @Column(name = "maintext_shlokaline")
    private int maintextShlokaline;
    @Basic(optional = false)
    @Column(name = "positionintranstext")
    private int positionintranstext;

    public ReferencetextPK() {
    }

    public ReferencetextPK(int maintextParvaId, int maintextAdhyayid, int maintextShlokanum, int maintextShlokaline, int positionintranstext) {
        this.maintextParvaId = maintextParvaId;
        this.maintextAdhyayid = maintextAdhyayid;
        this.maintextShlokanum = maintextShlokanum;
        this.maintextShlokaline = maintextShlokaline;
        this.positionintranstext = positionintranstext;
    }

    public int getMaintextParvaId() {
        return maintextParvaId;
    }

    public void setMaintextParvaId(int maintextParvaId) {
        this.maintextParvaId = maintextParvaId;
    }

    public int getMaintextAdhyayid() {
        return maintextAdhyayid;
    }

    public void setMaintextAdhyayid(int maintextAdhyayid) {
        this.maintextAdhyayid = maintextAdhyayid;
    }

    public int getMaintextShlokanum() {
        return maintextShlokanum;
    }

    public void setMaintextShlokanum(int maintextShlokanum) {
        this.maintextShlokanum = maintextShlokanum;
    }

    public int getMaintextShlokaline() {
        return maintextShlokaline;
    }

    public void setMaintextShlokaline(int maintextShlokaline) {
        this.maintextShlokaline = maintextShlokaline;
    }

    public int getPositionintranstext() {
        return positionintranstext;
    }

    public void setPositionintranstext(int positionintranstext) {
        this.positionintranstext = positionintranstext;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) maintextParvaId;
        hash += (int) maintextAdhyayid;
        hash += (int) maintextShlokanum;
        hash += (int) maintextShlokaline;
        hash += (int) positionintranstext;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReferencetextPK)) {
            return false;
        }
        ReferencetextPK other = (ReferencetextPK) object;
        if (this.maintextParvaId != other.maintextParvaId) {
            return false;
        }
        if (this.maintextAdhyayid != other.maintextAdhyayid) {
            return false;
        }
        if (this.maintextShlokanum != other.maintextShlokanum) {
            return false;
        }
        if (this.maintextShlokaline != other.maintextShlokaline) {
            return false;
        }
        if (this.positionintranstext != other.positionintranstext) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dgrf.ksamancore.db.entities.ReferencetextPK[ maintextParvaId=" + maintextParvaId + ", maintextAdhyayid=" + maintextAdhyayid + ", maintextShlokanum=" + maintextShlokanum + ", maintextShlokaline=" + maintextShlokaline + ", positionintranstext=" + positionintranstext + " ]";
    }
    
}
