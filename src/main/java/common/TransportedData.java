package common;

import common.basic.MusicBand;

import java.io.Serializable;
import java.util.HashSet;

public class TransportedData implements Serializable {

    private HashSet<MusicBand> appleMusic;
    private HashSet<String> passports;

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

}
