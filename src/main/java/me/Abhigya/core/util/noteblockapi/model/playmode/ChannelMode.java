package me.Abhigya.core.util.noteblockapi.model.playmode;

import me.Abhigya.core.util.noteblockapi.model.Layer;
import me.Abhigya.core.util.noteblockapi.model.Note;
import me.Abhigya.core.util.noteblockapi.model.Song;
import me.Abhigya.core.util.noteblockapi.model.SoundCategory;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/** Decides how is {@link Note} played to {@link Player} */
public abstract class ChannelMode {

    @Deprecated
    public abstract void play(
            Player player,
            Location location,
            Song song,
            Layer layer,
            Note note,
            SoundCategory soundCategory,
            float volume,
            float pitch);

    public abstract void play(
            Player player,
            Location location,
            Song song,
            Layer layer,
            Note note,
            SoundCategory soundCategory,
            float volume,
            boolean doTranspose);
}
