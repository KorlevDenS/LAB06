package client;

import common.basic.MusicBand;

import java.io.File;
import java.util.Date;
import java.util.HashSet;

public class ClientStatusRegister {

    public static File currentXml;
    public static HashSet<MusicBand> appleMusic = new HashSet<>();
    public static Date current;
    public static HashSet<String> passports = new HashSet<>();
    public static HashSet<Long> uniqueIdList = new HashSet<>();
    public static byte[] xmlData;

}
