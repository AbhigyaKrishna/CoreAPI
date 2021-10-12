package me.Abhigya.core.util.noteblockapi.songplayer;

import me.Abhigya.core.util.noteblockapi.NoteBlockAPI;
import me.Abhigya.core.util.noteblockapi.model.Playlist;
import me.Abhigya.core.util.noteblockapi.model.Song;
import me.Abhigya.core.util.noteblockapi.model.SoundCategory;
import org.bukkit.entity.Player;

/**
 * SongPlayer playing only in specified distance
 */
public abstract class RangeSongPlayer extends SongPlayer {

    private int distance = 16;

    public RangeSongPlayer( NoteBlockAPI api, Song song, SoundCategory soundCategory ) {
        super( api, song, soundCategory );
    }

    public RangeSongPlayer( NoteBlockAPI api, Song song ) {
        super( api, song );
    }

    public RangeSongPlayer( NoteBlockAPI api, Playlist playlist, SoundCategory soundCategory ) {
        super( api, playlist, soundCategory );
    }

    public RangeSongPlayer( NoteBlockAPI api, Playlist playlist ) {
        super( api, playlist );
    }

    @Override
    void update( String key, Object value ) {
        super.update( key, value );

        switch ( key ) {
            case "distance":
                distance = (int) value;
                break;
        }
    }

    public int getDistance( ) {
        return distance;
    }

    /**
     * Sets distance in blocks where would be player able to hear sound.
     *
     * @param distance (Default 16 blocks)
     */
    public void setDistance( int distance ) {
        this.distance = distance;
    }

    /**
     * Returns true if the Player is able to hear the current RangeSongPlayer
     *
     * @param player in range
     * @return ability to hear the current RangeSongPlayer
     */
    public abstract boolean isInRange( Player player );

}
