package me.Abhigya.core.util.bungeecord;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/**
 * Class for reading responses from BungeeCord channel.
 */
public class ReadUtils {

    /**
     * Read messages from 'BungeeCord' message channel.
     * <p>
     *
     * @param message Message from bungeecord channel
     * @return Parsed message
     */
    public static Object[] read(byte[] message) {
        if (message == null || message.length == 0) { // don't read empty messages
            return new Object[0];
        }

        try {
            final DataInputStream in_stream = new DataInputStream(new ByteArrayInputStream(message));
            final String argument = in_stream.readUTF();
            final Object[] reponse = readResponseFully(argument, in_stream);

            if (reponse == null || reponse.length == 0) {
                return new String[0];
            }

            final Object[] all = new Object[reponse.length + 1];
            for (int x = 0; x < all.length; x++) {
                if (x == 0) {
                    all[x] = argument;
                } else {
                    all[x] = reponse[(x - 1)];
                }
            }
            return all;
        } catch (Throwable t) {
            t.printStackTrace();
            return new String[0];
        }
    }

    /**
     * Read arguments from 'BungeeCord' message channel.
     * <p>
     *
     * @param message Argument from bungeecord channel
     * @return Parsed message
     */
    public static String readArgument(byte[] message) {
        final Object[] arg_repo = read(message);
        if (arg_repo.length > 0) {
            return (String) arg_repo[0];
        }
        return "";
    }

    /**
     * Read response from 'BungeeCord' message channel.
     * <p>
     *
     * @param message Response from bungeecord channel
     * @return Parsed message
     */
    public static Object[] readResponse(byte[] message) {
        final Object[] arg_repo = read(message);
        if (arg_repo.length > 1) {
            Object[] reponse = new Object[arg_repo.length - 1];
            for (int x = 0; x < reponse.length; x++) {
                reponse[x] = arg_repo[x + 1];
            }
            return reponse;
        }
        return new Object[0];
    }

    /**
     * Read response fully from 'BungeeCord' message channel.
     * <p>
     *
     * @param argument Arguments for the message
     * @param in       {@link DataInputStream}
     * @return Parsed message
     */
    private static Object[] readResponseFully(final String argument, final DataInputStream in) {
        try {
//			String fully = ""; /* readed from data input stream */
            Object[] all = null;
            switch (argument) {
                case MessagingUtils.PLAYER_IP_ARGUMENT:
                case MessagingUtils.SERVER_PLAYER_COUNT_ARGUMENT:
                    all = new Object[]
                            {
                                    in.readUTF(),
                                    in.readInt()
                            };
                    break;
                case MessagingUtils.SERVER_PLAYER_LIST_ARGUMENT:
                    String server = in.readUTF();
                    String[] player_list = in.readUTF().split(", ");
                    all = new Object[player_list.length + 1];
                    all[0] = server;
                    for (int x = 0; x < player_list.length; x++) {
                        all[x + 1] = player_list[x];
                    }
                    break;
                case MessagingUtils.SERVER_NAME_ARGUMENT:
                    all = new Object[]
                            {
                                    in.readUTF()
                            };
                    break;
                case MessagingUtils.SERVERS_NAMES_ARGUMENT:
                    String[] server_list = in.readUTF().split(", ");
                    all = new Object[server_list.length];
                    for (int x = 0; x < server_list.length; x++) {
                        all[x] = server_list[x];
                    }
                    break;
                case MessagingUtils.SERVER_IP_ARGUMENT:
                    all = new Object[]
                            {
                                    in.readUTF(),
                                    in.readUTF(),
                                    in.readUnsignedShort()
                            };
                    break;
            }
            return all;
        } catch (Throwable t) {
            return new Object[0];
        }
    }

}
