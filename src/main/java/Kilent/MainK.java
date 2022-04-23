package Kilent;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class MainK {
    public static void main(String[] args) throws IOException {
        byte[] arr = {1,2,3,4,5,6,7,8,9};
        int len = arr.length;
        DatagramChannel dc;
        ByteBuffer buf;
        InetAddress host = InetAddress.getByName("LAPTOP-5P3L6GLR");
        int port = 6789;
        SocketAddress addr;

        addr = new InetSocketAddress(host, port);
        dc = DatagramChannel.open();

        buf = ByteBuffer.wrap(arr);
        dc.send(buf, addr);

        buf.clear();
        addr = dc.receive(buf);

        for (byte j: arr){
            System.out.println(j);
        }

    }
}
