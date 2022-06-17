package testing;

import java.io.*;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ThreadedEchoHandler implements Runnable{

    DatagramChannel dc;
    ByteBuffer buf;
    SocketAddress adrToSendBack;

    public ThreadedEchoHandler(ByteBuffer buf, SocketAddress adr) throws IOException {
        this.dc =  DatagramChannel.open();
        this.buf = buf;
        this.adrToSendBack = adr;
    }

    public void run() {

        ByteArrayInputStream i = new ByteArrayInputStream(buf.array());
        try {
            ObjectInputStream in = new ObjectInputStream(i);
             Cat receivedMessage = (Cat) in.readObject();
             receivedMessage.name += " имя получено";

            Thread.sleep(10000);
             for (int j = 0; j < 1000000; j++) {
                 receivedMessage.weight += 2;
             }

            ByteArrayOutputStream o = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(o);
            out.writeObject(receivedMessage);
            byte[] buff = o.toByteArray();

            buf = ByteBuffer.wrap(buff);
            dc.send(buf, adrToSendBack);
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            dc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
