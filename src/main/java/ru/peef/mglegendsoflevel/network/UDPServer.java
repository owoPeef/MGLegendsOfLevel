package ru.peef.mglegendsoflevel.network;

import org.bukkit.entity.Player;
import ru.peef.mglegendsoflevel.LegendsOfLevelMain;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UDPServer extends Thread {
    private DatagramSocket socket;
    private int port;
    private boolean running;
    private byte[] buf = new byte[256];

    public UDPServer() {
        try {
            port = LegendsOfLevelMain.getInstance().getServer().getPort()+1;
            socket = new DatagramSocket(port);
        } catch (SocketException exception) {
            LegendsOfLevelMain.getLog().info("Socket error: " + exception.getMessage());
        }
    }

    public void run() {
        running = true;
        LegendsOfLevelMain.getLog().info("Start UDP server on port " + port);

        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                String received = new String(packet.getData(), 0, packet.getLength());

                LegendsOfLevelMain.getLog().info("received => " + received);
                if (received.equals("end")) {
                    running = false;
                    continue;
                }
                socket.send(packet);
            } catch (Exception exception) {
                LegendsOfLevelMain.getLog().info("Socket error: " + exception.getMessage());
            }
        }
        socket.close();
    }

    public void sendMessage(Player player, String message) {
        try {
            if (player.getAddress() != null) {
                InetAddress address = player.getAddress().getAddress();
                buf = message.getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            }
        } catch (Exception exception) {
            LegendsOfLevelMain.getLog().info("Socket error: " + exception.getMessage());
        }
    }

    public boolean isRunning() { return running; }
    public void disableSocket() { running = false; }
}
