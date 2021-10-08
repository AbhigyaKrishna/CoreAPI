package me.Abhigya.core.util.entity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * An implementation of {@link UUIDEntity} intended for {@link Player} entities only.
 */
public class UUIDPlayer extends UUIDEntity< Player > {

    /**
     * Construct the {@code UUIDPlayer} from its {@link UUID unique id}.
     * <p>
     *
     * @param uuid Player's {@link UUID unique id}.
     */
    public UUIDPlayer( final UUID uuid ) {
        super( uuid, Player.class );
    }

    /**
     * Construct the {@code UUIDPlayer} from its respective {@link Player player}.
     * <p>
     *
     * @param player Respective player.
     */
    public UUIDPlayer( final Player player ) {
        this( player.getUniqueId( ) );
    }

    /**
     * Gets the {@link Player} associated with the {@link UUIDEntity#uuid}.
     */
    @Override
    public final Player get( ) {
        return Bukkit.getPlayer( getUniqueId( ) );
    }

}