package client;

import common.TransportedData;

public class ClientDataInstaller {

    private final TransportedData receivedData;

    public ClientDataInstaller(TransportedData receivedData) {
        this.receivedData = receivedData;
    }

    public void installFromTransported() {
        ClientStatusRegister.appleMusic = receivedData.getAppleMusic();
        ClientStatusRegister.passports = receivedData.getPassports();
    }

    public static TransportedData installIntoTransported() {
        TransportedData data = new TransportedData();
        data.setAppleMusic(ClientStatusRegister.appleMusic);
        data.setPassports(ClientStatusRegister.passports);
        return data;
    }
}
