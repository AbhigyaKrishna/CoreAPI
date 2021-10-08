package me.Abhigya.core.util.acionbar;

import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.util.StringUtils;
import me.Abhigya.core.util.reflection.bukkit.BukkitReflection;
import me.Abhigya.core.util.reflection.general.ClassReflection;
import me.Abhigya.core.util.reflection.general.ConstructorReflection;
import me.Abhigya.core.util.reflection.general.MethodReflection;
import me.Abhigya.core.util.server.Version;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * Class for sending actionbar to players
 */
public class ActionBarUtils {

    /**
     * Sends an action bar message to the provided {@link Player}.
     * <p>
     * Note that the length {@code message} message must be
     * <strong>{@code < 63}</strong>, if the length of the provided message is
     * higher than <strong>{@code 63}</strong>, that it will limited.
     * <p>
     *
     * @param player  Player that will receive the message.
     * @param message Message to send.
     */
    public static void send( Player player, String message ) {
        try {
            Class< ? > component_class = ClassReflection.getNmsClass( "IChatBaseComponent", "network.chat" );
            Class< ? >[] inner_classes = component_class.getClasses( );
            Class< ? > chat_serializer = inner_classes.length > 0 ? component_class.getClasses( )[0]
                    : ClassReflection.getNmsClass( "ChatSerializer", "network.chat.IChatBaseComponent" );
            Class< ? > packet_class = ClassReflection.getNmsClass( "PacketPlayOutChat", "network.protocol.game" );


            Object component = MethodReflection.get( chat_serializer, "a", String.class ).invoke( chat_serializer,
                    "{\"text\":\"" + StringUtils.limit( message, 63 ) + "\"}" );

            Object packet;
            if ( CoreAPI.getInstance( ).getServerVersion( ).isOlder( Version.v1_12_R1 ) ) {
                packet = ConstructorReflection.newInstance( packet_class, new Class< ? >[]{ component_class, byte.class },
                        component, (byte) 2 );
            } else {
                Class< ? > chat_type_class = ClassReflection.getNmsClass( "ChatMessageType", "network.chat" );

                packet = ConstructorReflection.newInstance( packet_class,
                        new Class< ? >[]{ component_class, chat_type_class },
                        component, MethodReflection
                                .get( chat_type_class, "valueOf", String.class ).invoke( chat_type_class, "GAME_INFO" ) );
            }

            BukkitReflection.sendPacket( player, packet );
        } catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
                InvocationTargetException | InstantiationException e ) {
            e.printStackTrace( );
        }
    }

    /**
     * Sends an action bar with the provide {@code message} to all the players
     * online.
     * <p>
     *
     * @param message Message to send.
     * @see #send(Player, String)
     */
    public static void broadcast( final String message ) {
        Bukkit.getOnlinePlayers( ).forEach( player -> ActionBarUtils.send( player, message ) );
    }

}
