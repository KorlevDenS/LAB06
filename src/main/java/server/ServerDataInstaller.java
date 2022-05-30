package server;

import common.TransportedData;

public class ServerDataInstaller {

    private final TransportedData receivedData;

    public ServerDataInstaller(TransportedData receivedData) {
        this.receivedData = receivedData;
    }

    public void installFromTransported() {
        ServerStatusRegister.appleMusic = receivedData.getAppleMusic();
        ServerStatusRegister.currentXml = receivedData.getCurrentXml();
        ServerStatusRegister.passports = receivedData.getPassports();
        ServerStatusRegister.uniqueIdList = receivedData.getUniqueIdList();
        ServerStatusRegister.current = receivedData.getCurrent();
        ServerStatusRegister.xmlData = receivedData.getXmlData();
    }

    public static TransportedData installIntoTransported() {
        TransportedData data = new TransportedData();
        data.setAppleMusic(ServerStatusRegister.appleMusic);
        data.setCurrent(ServerStatusRegister.current);
        data.setCurrentXml(ServerStatusRegister.currentXml);
        data.setUniqueIdList(ServerStatusRegister.uniqueIdList);
        data.setPassports(ServerStatusRegister.passports);
        data.setXmlData(ServerStatusRegister.xmlData);
        return data;
    }
}