package server;

import common.CompleteMessage;
import common.ResultPattern;
import common.TransportedData;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ResponseHandler extends Thread {

    private final ObjectOutputStream sendToClient;
    private final TransportedData transportedData;
    private final ResultPattern resultPattern;

    public ResponseHandler(ObjectOutputStream sendToClient, TransportedData transportedData, ResultPattern resultPattern) {
        this.sendToClient = sendToClient;
        this.transportedData = transportedData;
        this.resultPattern = resultPattern;
    }

    public void run() {
        CompleteMessage sendingMessage = new CompleteMessage(transportedData, resultPattern);
        try {
            sendToClient.writeObject(sendingMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
