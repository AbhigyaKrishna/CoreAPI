package me.Abhigya.core.util.noteblockapi.songplayer;

import me.Abhigya.core.util.noteblockapi.NoteBlockAPI;
import me.Abhigya.core.util.noteblockapi.event.PlayerRangeStateChangeEvent;
import me.Abhigya.core.util.noteblockapi.model.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/** SongPlayer created at a specified Location */
public class PositionSongPlayer extends RangeSongPlayer {

    private Location targetLocation;

    public PositionSongPlayer(NoteBlockAPI api, Song song) {
        super(api, song);
    }

    public PositionSongPlayer(NoteBlockAPI api, Song song, SoundCategory soundCategory) {
        super(api, song, soundCategory);
    }

    public PositionSongPlayer(NoteBlockAPI api, Playlist playlist, SoundCategory soundCategory) {
        super(api, playlist, soundCategory);
    }

    public PositionSongPlayer(NoteBlockAPI api, Playlist playlist) {
        super(api, playlist);
    }

    @Override
    void update(String key, Object value) {
        super.update(key, value);

        switch (key) {
            case "targetLocation":
                targetLocation = (Location) value;
                break;
        }
    }

    /**
     * Gets location on which is the PositionSongPlayer playing
     *
     * @return {@link Location}
     */
    public Location getTargetLocation() {
        return targetLocation;
    }

    /** Sets location on which is the PositionSongPlayer playing */
    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    @Override
    public void playTick(Player player, int tick) {
        if (!player.getWorld().getName().equals(targetLocation.getWorld().getName())) {
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
                    targetLocation,
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

    /**
     * Returns true if the Player is able to hear the current PositionSongPlayer
     *
     * @param player in range
     * @return ability to hear the current PositionSongPlayer
     */
    @Override
    public boolean isInRange(Player player) {
        return player.getLocation().distance(targetLocation) <= getDistance();
    }
}
