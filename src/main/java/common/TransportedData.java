package common;

import common.basic.MusicBand;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

public class TransportedData implements Serializable {

    private File currentXml;
    private HashSet<MusicBand> appleMusic;
    private Date current;
    private HashSet<String> passports;
    private HashSet<Long> uniqueIdList;
    private byte[] xmlData;


    public byte[] getXmlData() {
        return this.xmlData;
    }

    public void setXmlData(byte[] arr) {
        this.xmlData = arr;
    }

    public HashSet<String> getPassports() {
        return this.passports;
    }

    public void setPassports(HashSet<String> passports) {
        this.passports = passports;
    }

    public void setAppleMusic(HashSet<MusicBand> appleMusic) {
        this.appleMusic = appleMusic;
    }

    public HashSet<MusicBand> getAppleMusic() {
        return this.appleMusic;
    }

    public void setUniqueIdList(HashSet<Long> uniqueIdList) {
        this.uniqueIdList = uniqueIdList;
    }

    public HashSet<Long> getUniqueIdList() {
        return this.uniqueIdList;
    }

    public Date getCurrent() {
        return this.current;
    }

    public void setCurrent(Date current) {
        this.current = current;
    }

    public void setCurrentXml(File currentXml) {
        this.currentXml = currentXml;
    }

    public File getCurrentXml() {
        return this.currentXml;
    }

}
