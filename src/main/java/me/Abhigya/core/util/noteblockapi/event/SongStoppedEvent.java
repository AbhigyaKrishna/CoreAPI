package me.Abhigya.core.util.noteblockapi.event;

import me.Abhigya.core.util.noteblockapi.songplayer.SongPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


/**
 * Called whenever a SongPlayer is stopped
 *
 * @see SongPlayer
 */
public class SongStoppedEvent extends Event {

    private static final HandlerList handlers = new HandlerList( );
    private SongPlayer songPlayer;

    public SongStoppedEvent( SongPlayer songPlayer ) {
        this.songPlayer = songPlayer;
    }

    public static HandlerList getHandlerList( ) {
        return handlers;
    }

    /**
     * Returns SongPlayer which is now stopped
     *
     * @return SongPlayer
     */
    public SongPlayer getSongPlayer( ) {
        return songPlayer;
    }

    public HandlerList getHandlers( ) {
        return handlers;
    }

}

