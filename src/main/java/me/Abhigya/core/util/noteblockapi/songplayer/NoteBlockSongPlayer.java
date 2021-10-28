package me.Abhigya.core.util.noteblockapi.songplayer;

import me.Abhigya.core.util.noteblockapi.NoteBlockAPI;
import me.Abhigya.core.util.noteblockapi.event.PlayerRangeStateChangeEvent;
import me.Abhigya.core.util.noteblockapi.model.*;
import me.Abhigya.core.util.noteblockapi.utils.CompatibilityUtils;
import me.Abhigya.core.util.noteblockapi.utils.InstrumentUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/** SongPlayer created at a specified NoteBlock */
public class NoteBlockSongPlayer extends RangeSongPlayer {

    private Block noteBlock;

    public NoteBlockSongPlayer(NoteBlockAPI api, Song song) {
        super(api, song);
    }

    public NoteBlockSongPlayer(NoteBlockAPI api, Song song, SoundCategory soundCategory) {
        super(api, song, soundCategory);
    }

    public NoteBlockSongPlayer(NoteBlockAPI api, Playlist playlist, SoundCategory soundCategory) {
        super(api, playlist, soundCategory);
    }

    public NoteBlockSongPlayer(NoteBlockAPI api, Playlist playlist) {
        super(api, playlist);
    }

    @Override
    void update(String key, Object value) {
        super.update(key, value);

        switch (key) {
            case "noteBlock":
                noteBlock = (Block) value;
                break;
        }
    }

    /**
     * Get the Block this SongPlayer is played at
     *
     * @return Block representing a NoteBlock
     */
    public Block getNoteBlock() {
        return noteBlock;
    }

    /** Set the Block this SongPlayer is played at */
    public void setNoteBlock(Block noteBlock) {
        this.noteBlock = noteBlock;
    }

    @Override
    public void playTick(Player player, int tick) {
        if (noteBlock.getType() != CompatibilityUtils.getNoteBlockMaterial()) {
            return;
        }
        if (!player.getWorld().getName().equals(noteBlock.getWorld().getName())) {
            // not in same world
            return;
        }
        byte playerVolume = this.api.getPlayerVolume(player);
        Location loc = noteBlock.getLocation();
        loc = new Location(loc.getWorld(), loc.getX() + 0.5f, loc.getY() - 0.5f, loc.getZ() + 0.5f);

        for (Layer layer : song.getLayerHashMap().values()) {
            Note note = layer.getNote(tick);
            if (note == null) {
                continue;
            }
            int key = note.getKey() - 33;

            while (key < 0) key += 12;
            while (key > 24) key -= 12;

            player.playNote(
                    loc,
                    InstrumentUtils.getBukkitInstrument(note.getInstrument()),
                    new org.bukkit.Note(key));

            float volume =
                    ((layer.getVolume()
                                            * (int) this.volume
                                            * (int) playerVolume
                                            * note.getVelocity())
                                    / 100_00_00_00F)
                            * ((1F / 16F) * getDistance());

            channelMode.play(
                    player, loc, song, layer, note, soundCategory, volume, !enable10Octave);

            if (isInRange(player)) {
                if (!this.playerList.get(player.getUniqueId())) {
                    playerList.put(player.getUniqueId(), true);
                    Bukkit.getPluginManager()
                            .callEvent(new PlayerRangeStateChangeEvent(this, player, true));
                }
            } else {
                if (this.playerList.get(player.getUniqueId())) {
                    playerList.put(player.getUniqueId(), false);
                    Bukkit.getPluginManager()
                            .callEvent(new PlayerRangeStateChangeEvent(this, player, false));
                }
            }
        }
    }

    /**
     * Returns true if the Player is able to hear the current NoteBlockSongPlayer
     *
     * @param player in range
     * @return ability to hear the current NoteBlockSongPlayer
     */
    @Override
    public boolean isInRange(Player player) {
        Location loc = noteBlock.getLocation();
        loc = new Location(loc.getWorld(), loc.getX() + 0.5f, loc.getY() - 0.5f, loc.getZ() + 0.5f);
        if (player.getLocation().distance(loc) > getDistance()) {
            return false;
        } else {
            return true;
        }
    }
}
