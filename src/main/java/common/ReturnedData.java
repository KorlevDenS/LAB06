package common;

import common.basic.MusicBand;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

public class ReturnedData implements Serializable {

    private HashSet<MusicBand> appleMusic;
    private Date current;
    private HashSet<String> passports;
    HashSet<Long> uniqueIsList;


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

    public void setUniqueIsList(HashSet<Long> uniqueIsList) {
        this.uniqueIsList = uniqueIsList;
    }

    public HashSet<Long> getUniqueIsList() {
        return this.uniqueIsList;
    }

    public Date getCurrent() {
        return this.current;
    }

    public void setCurrent(Date current) {
        this.current = current;
    }

}
