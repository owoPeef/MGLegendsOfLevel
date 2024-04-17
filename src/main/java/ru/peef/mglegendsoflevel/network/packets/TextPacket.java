package ru.peef.mglegendsoflevel.network.packets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.peef.chilove.ConsoleColor;

import ru.peef.mglegendsoflevel.LegendsOfLevelMain;

import java.util.Arrays;

public class TextPacket {
    public static boolean sendPacket(Player player, String message) {
        try {
            /*String full_msg = "0|TextPacket|" + message;
            byte[] data = full_msg.getBytes(StandardCharsets.UTF_8);

            player.sendPluginMessage(LegendsOfLevelMain.getInstance(), "chilove:main", data);*/


            byte[] byteMessage = message.getBytes();
            /*byte[] messageForSend = new byte[byteMessage.length + 1];
            messageForSend[0] = (byte) 0;
            for (int i = 1; i < byteMessage.length + 1; i++) {
                messageForSend[i] = byteMessage[i-1];
            }
            LegendsOfLevelMain.getInstance().getLogger().info("Sending message => " + message + ", byte array length => " + messageForSend.length);
            player.sendPluginMessage(LegendsOfLevelMain.getInstance(), LegendsOfLevelMain.PACKETS_CHANNEL, messageForSend);*/


            int maxLength = 100; // Максимальная длина части сообщения
            int numParts = (byteMessage.length + maxLength - 1) / maxLength; // Вычисляем количество частей

            for (int i = 0; i < numParts; i++) {
                int offset = i * maxLength;
                int length = Math.min(byteMessage.length - offset, maxLength);

                byte[] partMessage = new byte[offset + length + 1];
                partMessage[0] = (byte) 0;

                for (int j = 1; j < length + 1; j++) {
                    partMessage[j] = byteMessage[offset + length-1];
                }

                byte[] part = Arrays.copyOfRange(byteMessage, offset, offset + length);

                // Отправляем каждую часть как пакет
                player.sendPluginMessage(LegendsOfLevelMain.getInstance(), LegendsOfLevelMain.PACKETS_CHANNEL, partMessage);
            }

            return true;
        } catch (Exception exception) {
            Bukkit.getLogger().info(ConsoleColor.ANSI_RED + "Error on TextPacket:" + exception.getStackTrace()[2].getLineNumber() + ConsoleColor.ANSI_CYAN + " (" + exception.getMessage() + ")" + ConsoleColor.ANSI_RESET);
            exception.printStackTrace();
        }
        return false;
    }
}
