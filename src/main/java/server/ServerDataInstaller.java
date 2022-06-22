package server;

import common.TransportedData;

public class ServerDataInstaller {

    private final TransportedData receivedData;

    public ServerDataInstaller(TransportedData receivedData) {
        this.receivedData = receivedData;
    }

    public void installFromTransported() {
        ServerStatusRegister.appleMusic = receivedData.getAppleMusic();
        ServerStatusRegister.passports = receivedData.getPassports();
    }

    public static TransportedData installIntoTransported() {
        TransportedData data = new TransportedData();
        data.setAppleMusic(ServerStatusRegister.appleMusic);
        data.setPassports(ServerStatusRegister.passports);
        return data;
    }
}