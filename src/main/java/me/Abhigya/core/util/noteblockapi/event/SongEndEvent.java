package me.Abhigya.core.util.noteblockapi.event;

import me.Abhigya.core.util.noteblockapi.model.Song;
import me.Abhigya.core.util.noteblockapi.songplayer.SongPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a Song ends
 * or when no players are listening and auto destroy is enabled
 *
 * @see SongPlayer
 */
public class SongEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList( );
    private SongPlayer song;

    public SongEndEvent( SongPlayer song ) {
        this.song = song;
    }

    public static HandlerList getHandlerList( ) {
        return handlers;
    }

    /**
     * Returns SongPlayer which {@link Song} ends
     *
     * @return SongPlayer
     */
    public SongPlayer getSongPlayer( ) {
        return song;
    }

    public HandlerList getHandlers( ) {
        return handlers;
    }

}