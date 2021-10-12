package me.Abhigya.core.util.noteblockapi;

import me.Abhigya.core.util.noteblockapi.songplayer.SongPlayer;
import me.Abhigya.core.util.scheduler.SchedulerUtils;
import me.Abhigya.core.util.tasks.Workload;
import me.Abhigya.core.util.tasks.WorkloadThread;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NoteBlockAPI {

    private final Plugin plugin;
    private final WorkloadThread syncThread;
    private final WorkloadThread asyncThread;

    private final Map< UUID, ArrayList< SongPlayer > > playingSongs;
    private final Map< UUID, Byte > playerVolume;

    public NoteBlockAPI( Plugin plugin, WorkloadThread syncThread, WorkloadThread asyncThread ) {
        this.plugin = plugin;
        this.syncThread = syncThread;
        this.asyncThread = asyncThread;
        this.playingSongs = new ConcurrentHashMap<>( );
        this.playerVolume = new ConcurrentHashMap<>( );
    }

    public NoteBlockAPI( Plugin plugin ) {
        this(plugin, null, null);
    }

    /**
     * Returns true if a Player is currently receiving a song
     *
     * @param player
     * @return is receiving a song
     */
    public boolean isReceivingSong( Player player ) {
        return isReceivingSong( player.getUniqueId( ) );
    }

    /**
     * Returns true if a Player with specified UUID is currently receiving a song
     *
     * @param uuid
     * @return is receiving a song
     */
    public boolean isReceivingSong( UUID uuid ) {
        ArrayList< SongPlayer > songs = this.playingSongs.get( uuid );
        return ( songs != null && !songs.isEmpty( ) );
    }

    /**
     * Stops the song for a Player
     *
     * @param player
     */
    public void stopPlaying( Player player ) {
        stopPlaying( player.getUniqueId( ) );
    }

    /**
     * Stops the song for a Player
     *
     * @param uuid
     */
    public void stopPlaying( UUID uuid ) {
        ArrayList< SongPlayer > songs = this.playingSongs.get( uuid );
        if ( songs == null ) {
            return;
        }
        for ( SongPlayer songPlayer : songs ) {
            songPlayer.removePlayer( uuid );
        }
    }

    /**
     * Sets the volume for a given Player
     *
     * @param player
     * @param volume
     */
    public void setPlayerVolume( Player player, byte volume ) {
        this.setPlayerVolume( player.getUniqueId( ), volume );
    }

    /**
     * Sets the volume for a given Player
     *
     * @param uuid
     * @param volume
     */
    public void setPlayerVolume( UUID uuid, byte volume ) {
        this.playerVolume.put( uuid, volume );
    }

    /**
     * Gets the volume for a given Player
     *
     * @param player
     * @return volume (byte)
     */
    public byte getPlayerVolume( Player player ) {
        return this.getPlayerVolume( player.getUniqueId( ) );
    }

    /**
     * Gets the volume for a given Player
     *
     * @param uuid
     * @return volume (byte)
     */
    public byte getPlayerVolume( UUID uuid ) {
        Byte byteObj = this.playerVolume.get( uuid );
        if ( byteObj == null ) {
            byteObj = 100;
            this.playerVolume.put( uuid, byteObj );
        }
        return byteObj;
    }

    public ArrayList< SongPlayer > getSongPlayersByPlayer( Player player ) {
        return getSongPlayersByPlayer( player.getUniqueId( ) );
    }

    public ArrayList< SongPlayer > getSongPlayersByPlayer( UUID player ) {
        return this.playingSongs.get( player );
    }

    public void setSongPlayersByPlayer( Player player, ArrayList< SongPlayer > songs ) {
        setSongPlayersByPlayer( player.getUniqueId( ), songs );
    }

    public void setSongPlayersByPlayer( UUID player, ArrayList< SongPlayer > songs ) {
        this.playingSongs.put( player, songs );
    }

    public void doSync( Runnable runnable ) {
        if ( this.syncThread != null ) {
            this.syncThread.add( new Workload( ) {
                @Override
                public void compute( ) {
                    runnable.run();
                }
            } );
        } else {
            SchedulerUtils.runTask( runnable, this.plugin );
        }
    }

    public void doAsync( Runnable runnable ) {
        if ( this.asyncThread != null ) {
            this.asyncThread.add( new Workload( ) {
                @Override
                public void compute( ) {
                    runnable.run();
                }
            } );
        } else {
            SchedulerUtils.runTaskAsynchronously( runnable, this.plugin );
        }
    }

    public Plugin getPlugin( ) {
        return this.plugin;
    }

}
