package me.Abhigya.core.util.noteblockapi.songplayer;

import me.Abhigya.core.util.noteblockapi.NoteBlockAPI;
import me.Abhigya.core.util.noteblockapi.model.*;
import me.Abhigya.core.util.noteblockapi.model.playmode.ChannelMode;
import me.Abhigya.core.util.noteblockapi.model.playmode.MonoMode;
import me.Abhigya.core.util.noteblockapi.model.playmode.MonoStereoMode;
import org.bukkit.entity.Player;

/**
 * SongPlayer playing to everyone added to it no matter where he is
 */
public class RadioSongPlayer extends SongPlayer {

    //protected boolean stereo = true;

    public RadioSongPlayer( NoteBlockAPI api, Song song ) {
        super( api, song );
    }

    public RadioSongPlayer( NoteBlockAPI api, Song song, SoundCategory soundCategory ) {
        super( api, song, soundCategory );
    }

    public RadioSongPlayer( NoteBlockAPI api, Playlist playlist, SoundCategory soundCategory ) {
        super( api, playlist, soundCategory );
    }

    public RadioSongPlayer( NoteBlockAPI api, Playlist playlist ) {
        super( api, playlist );
    }

    @Override
    public void playTick( Player player, int tick ) {
        byte playerVolume = this.api.getPlayerVolume( player );

        for ( Layer layer : song.getLayerHashMap( ).values( ) ) {
            Note note = layer.getNote( tick );
            if ( note == null ) {
                continue;
            }

            float volume = ( layer.getVolume( ) * (int) this.volume * (int) playerVolume * note.getVelocity( ) ) / 100_00_00_00F;

            channelMode.play( player, player.getEyeLocation( ), song, layer, note, soundCategory, volume, !enable10Octave );
        }
    }

    /**
     * Returns if the SongPlayer will play Notes from two sources as stereo
     *
     * @return if is played stereo
     * @deprecated
     */
    @Deprecated
    public boolean isStereo( ) {
        return !( channelMode instanceof MonoMode );
    }

    /**
     * Sets if the SongPlayer will play Notes from two sources as stereo
     *
     * @param stereo
     * @deprecated
     */
    @Deprecated
    public void setStereo( boolean stereo ) {
        channelMode = stereo ? new MonoMode( ) : new MonoStereoMode( );
    }

    /**
     * Sets how will be {@link Note} played to {@link Player} (eg. mono or stereo). Default is {@link MonoMode}.
     *
     * @param mode
     */
    public void setChannelMode( ChannelMode mode ) {
        channelMode = mode;
    }

}
