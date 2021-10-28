package me.Abhigya.core.util.hologram;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Hologram {

    /** Spawn the hologram */
    void spawn(Collection<? extends Player> players);

    /**
     * Spawn the hologram
     *
     * @param location the spawn location
     */
    void spawn(Location location, Collection<? extends Player> players);

    void spawn();

    /**
     * Teleport the hologram to the specified location, instead of having to spawn it again
     *
     * @param newLocation the new hologram location
     */
    void teleport(Location newLocation);

    /**
     * Hide the armor stand for the specified players
     *
     * @param players the player to hide the armor stand to
     */
    void hide(Player... players);

    /**
     * Hide the armor stand for the specified players
     *
     * @param players the player to hide the armor stand to
     */
    void show(Player... players);

    /**
     * Set the hologram visibility
     *
     * @param status the hologram visibility
     */
    void setVisible(boolean status);

    /** Clear all the hologram lines */
    void clearLines();

    /** Update the lines text */
    void updateLines();

    /**
     * Add a new line to the hologram
     *
     * @param line the new line text
     */
    void add(String line);

    /**
     * Insert an item stack between two lines
     *
     * @param item the item to add
     */
    void add(ItemStack item, boolean small);

    /**
     * Insert a line or image read from the specified file
     *
     * @param file the file to read from
     */
    void add(File file);

    /**
     * Remove a hologram line
     *
     * @param index the line number to remove
     */
    void remove(int index);

    void rotate(int index, float yaw);

    /** Destroy the hologram, completely */
    void destroy();

    void destroy(Collection<? extends Player> players);

    void safeDestroy(Player... players);

    /**
     * Get this persistent hologram with the specified configuration
     *
     * @param config the hologram configuration
     * @return this instance with the new configuration
     */
    Hologram withConfiguration(HologramConfiguration config);

    /**
     * Get the line from an index
     *
     * @param index the index
     * @return the line
     */
    String getLine(int index);

    /**
     * Get an item from an index
     *
     * @param index the index
     * @return the item
     */
    ItemStack getItem(int index);

    /**
     * Get the index from a line
     *
     * @param line the line
     * @return the line index
     */
    int getIndex(String line);

    /**
     * Get the hologram schema
     *
     * @return the hologram schema
     */
    Map<Integer, String> getHologramSchema();

    /**
     * Get the location
     *
     * @return the serializable location
     */
    Location getLocation();

    /**
     * Get if the player can see the hologram
     *
     * @param player the player
     * @return if the player can see the hologram
     */
    boolean canSee(final Player player);

    boolean isSpawned();

    /**
     * Get a set of players who the hologram is hidden
     *
     * @return a set of the players with hologram hidden
     */
    Set<Player> getHidden();

    Set<Player> getViewers();
}
