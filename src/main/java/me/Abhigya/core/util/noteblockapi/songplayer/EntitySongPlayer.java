package me.Abhigya.core.util.noteblockapi.songplayer;

import me.Abhigya.core.util.noteblockapi.NoteBlockAPI;
import me.Abhigya.core.util.noteblockapi.event.PlayerRangeStateChangeEvent;
import me.Abhigya.core.util.noteblockapi.model.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class EntitySongPlayer extends RangeSongPlayer {

    private Entity entity;

    public EntitySongPlayer(NoteBlockAPI api, Song song) {
        super(api, song);
    }

    public EntitySongPlayer(NoteBlockAPI api, Song song, SoundCategory soundCategory) {
        super(api, song, soundCategory);
    }

    public EntitySongPlayer(NoteBlockAPI api, Playlist playlist, SoundCategory soundCategory) {
        super(api, playlist, soundCategory);
    }

    public EntitySongPlayer(NoteBlockAPI api, Playlist playlist) {
        super(api, playlist);
    }

    /**
     * Returns true if the Player is able to hear the current {@link EntitySongPlayer}
     *
     * @param player in range
     * @return ability to hear the current {@link EntitySongPlayer}
     */
    @Override
    public boolean isInRange(Player player) {
        return player.getLocation().distance(entity.getLocation()) <= getDistance();
    }

    /**
     * Get {@link Entity} associated with this {@link EntitySongPlayer}
     *
     * @return
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Set entity associated with this {@link EntitySongPlayer}
     *
     * @param entity
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void playTick(Player player, int tick) {
        if (entity.isDead()) {
            if (autoDestroy) {
                destroy();
            } else {
                setPlaying(false);
            }
        }
        if (!player.getWorld().getName().equals(entity.getWorld().getName())) {
            return; // not in same world
        }

        byte playerVolume = this.api.getPlayerVolume(player);

        for (Layer layer : song.getLayerHashMap().values()) {
            Note note = layer.getNote(tick);
            if (note == null) continue;

            float volume =
                    ((layer.getVolume()
                                            * (int) this.volume
                                            * (int) playerVolume
                                            * note.getVelocity())
                                    / 100_00_00_00F)
                            * ((1F / 16F) * getDistance());

            channelMode.play(
                    player,
                    entity.getLocation(),
                    song,
                    layer,
                    note,
                    soundCategory,
                    volume,
                    !enable10Octave);

            if (isInRange(player)) {
                if (!playerList.get(player.getUniqueId())) {
                    playerList.put(player.getUniqueId(), true);
                    Bukkit.getPluginManager()
                            .callEvent(new PlayerRangeStateChangeEvent(this, player, true));
                }
            } else {
                if (playerList.get(player.getUniqueId())) {
                    playerList.put(player.getUniqueId(), false);
                    Bukkit.getPluginManager()
                            .callEvent(new PlayerRangeStateChangeEvent(this, player, false));
                }
            }
        }
    }
}
