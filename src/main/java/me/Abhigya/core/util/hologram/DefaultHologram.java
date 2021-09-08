package me.Abhigya.core.util.hologram;

import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.util.StringUtils;
import me.Abhigya.core.util.file.FileUtils;
import me.Abhigya.core.util.image.ImageUtils;
import me.Abhigya.core.util.reflection.bukkit.BukkitReflection;
import me.Abhigya.core.util.reflection.general.ClassReflection;
import me.Abhigya.core.util.reflection.general.FieldReflection;
import me.Abhigya.core.util.server.Version;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultHologram implements Hologram {

    private Location location;
    private boolean spawned;
    private final List<String> lines = Collections.synchronizedList(new ArrayList<>());
    private final Map<Integer, ItemStack> items = new ConcurrentHashMap<>();

    private final Map<Integer, List<Integer>> entities = new ConcurrentHashMap<>();
    private final Set<Player> hidden = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final Set<Player> shown = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private HologramConfiguration configuration = new HologramConfiguration();

    /**
     * Initialize the temp hologram
     *
     * @param loc  the hologram location
     * @param text the hologram lines
     */
    protected DefaultHologram(final Location loc, final String... text) {
        this.location = loc;
        this.lines.addAll(Arrays.asList(text));
    }

    /**
     * Spawn the hologram
     */
    public void spawn(Collection<? extends Player> players) {
        Location location = this.applyLocationChanges(this.location);

        double original_y = location.getY();
        int index = 0;
        for (String line : lines) {
            if (line.equals("ITEM_ON_LINE:" + index)) {
                ItemStack item = this.getItem(index);
                if (item != null) {
                    this.summonItem(index, item, location, false, players.toArray(new Player[0]));
                }
            } else if (line.equals("ITEM_ON_LINE_SMALL:" + index)) {
                ItemStack item = this.getItem(index);
                if (item != null) {
                    this.summonItem(index, item, location, true, players.toArray(new Player[0]));
                }
            } else {
                this.summonTextStand(index, index + 1, line, location, players.toArray(new Player[0]));
            }

            index++;
        }

        location.setY(original_y);
        this.spawned = true;
    }

    /**
     * Spawn the hologram
     *
     * @param loc the spawn location
     */
    @Override
    public void spawn(Location loc, Collection<? extends Player> players) {
        this.location = loc;
        destroy();
        spawn(players);
    }

    @Override
    public void spawn() {
        this.spawn(this.getLocation().getWorld().getPlayers());
    }

    /**
     * Teleport the hologram to the specified location,
     * instead of having to spawn it again
     *
     * @param newLocation the new hologram location
     */
    @Override
    public void teleport(Location newLocation) {
        this.location = newLocation;
        for (List<Integer> l : entities.values()) {
            for (int id : l) {
                try {
                    Object packetPlayOutEntityTeleport = ReflectionCache.PACKET_PLAY_OUT_ENTITY_TELEPORT.newInstance();
                    FieldReflection.setValue(packetPlayOutEntityTeleport, "a", id);
                    if (CoreAPI.getInstance().getServerVersion().isOlderEquals(Version.v1_8_R3)) {
                        FieldReflection.setValue(packetPlayOutEntityTeleport, "b", (int) (this.location.getX() * 32D));
                        FieldReflection.setValue(packetPlayOutEntityTeleport, "c", (int) (this.location.getY() * 32D));
                        FieldReflection.setValue(packetPlayOutEntityTeleport, "d", (int) (this.location.getZ() * 32D));
                    } else {
                        FieldReflection.setValue(packetPlayOutEntityTeleport, "b", this.location.getX());
                        FieldReflection.setValue(packetPlayOutEntityTeleport, "c", this.location.getY());
                        FieldReflection.setValue(packetPlayOutEntityTeleport, "d", this.location.getZ());
                        FieldReflection.setValue(packetPlayOutEntityTeleport, "g", true);
                    }
                    FieldReflection.setValue(packetPlayOutEntityTeleport, "e", (byte) (int) (this.location.getYaw() * 256F / 360F));
                    FieldReflection.setValue(packetPlayOutEntityTeleport, "f", (byte) (int) (this.location.getPitch() * 256F / 360F));
                    for (Player player : this.shown) {
                        BukkitReflection.sendPacket(player, packetPlayOutEntityTeleport);
                    }
                } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Hide the armor stand for the specified players
     *
     * @param players the player to hide the armor
     *                stand to
     */
    public void hide(Player... players) {
        for (List<Integer> l : entities.values()) {
            for (int id : l) {
                try {
                    Object packetPlayOutEntityDestroy = ReflectionCache.PACKET_PLAY_OUT_ENTITY_DESTROY_CONSTRUCTOR.newInstance((Object) new int[]{id});
                    for (Player player : players) {
                        BukkitReflection.sendPacket(player, packetPlayOutEntityDestroy);
                        hidden.add(player);
                        shown.remove(player);
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Hide the armor stand for the specified players
     *
     * @param players the player to hide the armor
     *                stand to
     */
    public void show(Player... players) {
        if (this.location != null) {
            Location location = this.applyLocationChanges(this.location);
            int index = 0;
            for (String line : lines) {
                if (line.equals("ITEM_ON_LINE:" + index)) {
                    ItemStack item = this.getItem(index);
                    if (item != null) {
                        this.summonItem(index, item, location, false, players);
                    }
                } else if (line.equals("ITEM_ON_LINE_SMALL:" + index)) {
                    ItemStack item = this.getItem(index);
                    if (item != null) {
                        this.summonItem(index, item, location, true, players);
                    }
                } else {
                    this.summonTextStand(index, index + 1, line, location, players);
                }

                index++;
            }
        }
    }

    /**
     * Set the hologram visibility
     *
     * @param status the hologram visibility
     */
    public void setVisible(final boolean status) {
        if (status) {
            this.show(Bukkit.getOnlinePlayers().toArray(new Player[0]));
        } else {
            this.hide(Bukkit.getOnlinePlayers().toArray(new Player[0]));
            this.entities.clear();
        }
    }

    /**
     * Clear all the hologram lines
     */
    @Override
    public void clearLines() {
        this.lines.clear();
        this.setVisible(false);
        this.setVisible(true);
    }

    /**
     * Update the lines text
     */
    @Override
    public final void updateLines() {
        this.destroy();
        this.spawn(this.shown);
    }

    /**
     * Add a new line to the hologram
     *
     * @param text the new line text
     */
    public void add(String text) {
        this.lines.add(text);
    }

    /**
     * Insert an item stack between two lines
     *
     * @param item the item to add
     */
    @Override
    public void add(ItemStack item, boolean small) {
        items.put(lines.size(), item);
        lines.add("ITEM_ON_LINE" + (small ? "_SMALL" : "") + ":" + lines.size());
    }

    /**
     * Insert a line or image read from the specified
     * file
     *
     * @param file the file to read from
     */
    @Override
    public void add(File file) {
        if (FileUtils.getFileType(file).toLowerCase().startsWith("image")) {
            String result = ImageUtils.readImage(file);
            this.lines.add(result);
        } else {
            List<String> readLines;
            try {
                readLines = Files.readAllLines(file.toPath());
            } catch (IOException e) {
                readLines = Collections.emptyList();
            }
            this.lines.addAll(StringUtils.translateAlternateColorCodes(readLines));
        }
    }

    /**
     * Remove a hologram line
     *
     * @param line the line number to remove
     */
    public void remove(int line) {
        try {
            if (this.getLine(line).equals("ITEM_ON_LINE:" + line) || this.getLine(line).equals("ITEM_ON_LINE_SMALL:" + line)) {
                this.items.remove(line);
            }

            this.lines.remove(line);
            this.moveItemsDown();
        } catch (Throwable ignored) {
        }
    }

    @Deprecated
    @Override
    public void rotate(int index, float yaw) {
        if (!this.getLine(index).startsWith("ITEM_ON_LINE"))
            return;

        try {
            for (int id : this.entities.get(index)) {
                Object packetPlayOutEntityHeadRotation = ReflectionCache.PACKET_PLAY_OUT_ENTITY_HEAD_ROTATION.newInstance();
                FieldReflection.setValue(packetPlayOutEntityHeadRotation, "a", id);
                FieldReflection.setValue(packetPlayOutEntityHeadRotation, "b", (byte) ((yaw * 256.0F) / 360.0F));
                for (Player player : this.shown) {
                    BukkitReflection.sendPacket(player, packetPlayOutEntityHeadRotation);
                }
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * Destroy the hologram, completely
     */
    public void destroy() {
        this.destroy(this.shown);

        this.shown.clear();
        this.hidden.clear();
        this.entities.clear();
        this.spawned = false;
    }

    @Override
    public void destroy(Collection<? extends Player> players) {
        for (List<Integer> l : entities.values()) {
            for (int id : l) {
                try {
                    Object packetPlayOutEntityDestroy = ReflectionCache.PACKET_PLAY_OUT_ENTITY_DESTROY_CONSTRUCTOR.newInstance((Object) new int[]{id});
                    for (Player player : players) {
                        BukkitReflection.sendPacket(player, packetPlayOutEntityDestroy);
                        this.shown.remove(player);
                        this.hidden.remove(player);
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get this persistent hologram with the specified configuration
     *
     * @param config the hologram configuration
     * @return this instance with the new configuration
     */
    @Override
    public Hologram withConfiguration(final HologramConfiguration config) {
        this.configuration = config;
        return this;
    }

    /**
     * Get the line from an index
     *
     * @param index the index
     * @return the line
     */
    public final String getLine(final int index) {
        if (lines.size() > index)
            return lines.get(index);

        return "";
    }

    /**
     * Get an item from an index
     *
     * @param index the index
     * @return the item
     */
    @Override
    public ItemStack getItem(int index) {
        return this.items.getOrDefault(index, null);
    }

    /**
     * Get the index from a line
     *
     * @param line the line
     * @return the line index
     */
    public int getIndex(String line) {
        int index = 0;
        for (String str : this.lines) {
            if (str.equals(line)) {
                return index;
            }
            index++;
        }

        return -1;
    }

    /**
     * Get the hologram schema
     *
     * @return the hologram schema
     */
    public Map<Integer, String> getHologramSchema() {
        Map<Integer, String> schema = new HashMap<>();

        int index = 0;
        for (String line : this.lines)
            schema.put(index++, line);

        return schema;
    }

    /**
     * Get the location
     *
     * @return location
     */
    @Override
    public Location getLocation() {
        return this.location;
    }

    /**
     * Get if the player can see the hologram
     *
     * @param player the player
     * @return if the player can see the hologram
     */
    @Override
    public boolean canSee(Player player) {
        return !this.hidden.contains(player);
    }

    @Override
    public boolean isSpawned() {
        return this.spawned;
    }

    /**
     * Get a set of players who the hologram is
     * hidden
     *
     * @return a set of the players with hologram
     * hidden
     */
    @Override
    public Set<Player> getHidden() {
        return Collections.unmodifiableSet(this.hidden);
    }

    @Override
    public Set<Player> getViewers() {
        return Collections.unmodifiableSet(this.shown);
    }

    /**
     * Move all items down
     */
    private void moveItemsDown() {
        int index = 0;
        int max = lines.size();
        ItemStack result;
        do {
            result = items.getOrDefault(index, null);
            if (result != null) {
                if (index != 0) {
                    items.put((index - 1), result);
                }
            }

            index++;
        } while (index < max);
    }

    /**
     * Summon an item
     *
     * @param item     the item to add
     * @param location the item location
     * @param players  the player to show to
     */
    private void summonItem(int index, ItemStack item, Location location, boolean small, Player... players) {
        try {
            Object stand = ReflectionCache.ENTITY_ARMOR_STAND_CONSTRUCTOR.newInstance(BukkitReflection.getHandle(ReflectionCache.CRAFT_WORLD.cast(location.getWorld())),
                    location.getX(), location.getY(), location.getZ());
            Object itemStack = ReflectionCache.AS_NMS_COPY.invoke(null, item);
            ReflectionCache.SET_INVISIBLE.invoke(stand, true);
            if (small)
                ReflectionCache.SET_SMALL.invoke(stand, true);
            if (ReflectionCache.SET_MARKER != null)
                ReflectionCache.SET_MARKER.invoke(stand, true);
            ReflectionCache.SET_BASE_PLATE.invoke(stand, false);
            if (ReflectionCache.SET_NO_GRAVITY.getName().equals("setGravity")) {
                ReflectionCache.SET_NO_GRAVITY.invoke(stand, false);
            } else {
                ReflectionCache.SET_NO_GRAVITY.invoke(stand, true);
            }

            Object temp_id = ReflectionCache.GET_ID.invoke(stand);
            if (temp_id != null) {
                int id = (int) temp_id;
                List<Integer> l = this.entities.getOrDefault(index, null);
                if (l == null)
                    l = new ArrayList<>();

                l.add(id);
                this.entities.put(index, l);

                Object packetPlayOutSpawnEntity = ReflectionCache.PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CONSTRUCTOR.newInstance(stand);
                Object packetPlayOutEntityEquipment;
                if (ReflectionCache.PACKET_PLAY_OUT_ENTITY_EQUIPMENT_CONSTRUCTOR.getParameterTypes()[1].equals(Integer.TYPE)) {
                    packetPlayOutEntityEquipment = ReflectionCache.PACKET_PLAY_OUT_ENTITY_EQUIPMENT_CONSTRUCTOR.newInstance(id, 4, itemStack);
                } else {
                    packetPlayOutEntityEquipment = ReflectionCache.PACKET_PLAY_OUT_ENTITY_EQUIPMENT_CONSTRUCTOR.newInstance(id, ClassReflection.getNmsClass("EnumItemSlot", "world.entity").getEnumConstants()[5], itemStack);
                }
                Object packetPlayOutEntityMetadata = ReflectionCache.PACKET_PLAY_OUT_ENTITY_METADATA_CONSTRUCTOR.newInstance(id, ReflectionCache.GET_DATA_WATCHER.invoke(stand), true);
                for (Player player : players) {
                    hidden.remove(player);
                    shown.add(player);
                    BukkitReflection.sendPacket(player, packetPlayOutSpawnEntity);
                    BukkitReflection.sendPacket(player, packetPlayOutEntityEquipment);
                    BukkitReflection.sendPacket(player, packetPlayOutEntityMetadata);
                }

                if (small)
                    location.setY(location.getY() - configuration.getSmallItemBelowSeparator());
                else
                    location.setY(location.getY() - configuration.getBigItemBelowSeparator());
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Summon a text stand
     *
     * @param line     the armor stand text
     * @param location the armor stand location
     * @param players  the player to show to
     */
    private void summonTextStand(int index, int nextIndex, String line, Location location, Player... players) {
        try {
            Object stand = ReflectionCache.ENTITY_ARMOR_STAND_CONSTRUCTOR.newInstance(BukkitReflection.getHandle(ReflectionCache.CRAFT_WORLD.cast(location.getWorld())),
                    location.getX(), location.getY(), location.getZ());
            ReflectionCache.SET_INVISIBLE.invoke(stand, true);
            ReflectionCache.SET_SMALL.invoke(stand, true);
            if (ReflectionCache.SET_MARKER != null)
                ReflectionCache.SET_MARKER.invoke(stand, true);
            ReflectionCache.SET_BASE_PLATE.invoke(stand, false);
            if (ReflectionCache.SET_NO_GRAVITY.getName().equals("setGravity")) {
                ReflectionCache.SET_NO_GRAVITY.invoke(stand, false);
            } else {
                ReflectionCache.SET_NO_GRAVITY.invoke(stand, true);
            }
            ReflectionCache.SET_CUSTOM_NAME_VISIBLE.invoke(stand, true);
            if (ReflectionCache.SET_CUSTOM_NAME.getParameterTypes()[0].equals(String.class)) {
                ReflectionCache.SET_CUSTOM_NAME.invoke(stand, line);
            } else {
                ReflectionCache.SET_CUSTOM_NAME.invoke(stand, ReflectionCache.CHAT_MESSAGE_CONSTRUCTOR.newInstance(line, null));
            }
            Object temp_id = ReflectionCache.GET_ID.invoke(stand);
            if (temp_id != null) {
                int id = (int) temp_id;
                List<Integer> l = this.entities.getOrDefault(index, null);
                if (l == null)
                    l = new ArrayList<>();

                l.add(id);
                this.entities.put(index, l);

                Object packetPlayOutSpawnEntityLiving = ReflectionCache.PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CONSTRUCTOR.newInstance(stand);
                Object packetPlayOutEntityMetadata = ReflectionCache.PACKET_PLAY_OUT_ENTITY_METADATA_CONSTRUCTOR.newInstance(id, ReflectionCache.GET_DATA_WATCHER.invoke(stand), true);
                for (Player player : players) {
                    hidden.remove(player);
                    shown.add(player);
                    BukkitReflection.sendPacket(player, packetPlayOutSpawnEntityLiving);
                    BukkitReflection.sendPacket(player, packetPlayOutEntityMetadata);
                }

                if (this.getItem(nextIndex) == null)
                    location.setY(location.getY() - configuration.getLineSeparation());
                else
                    if (this.lines.get(nextIndex).startsWith("ITEM_ON_LINE_SMALL"))
                        location.setY(location.getY() - configuration.getSmallItemAboveSeparator());
                    else
                        location.setY(location.getY() - configuration.getBigItemAboveSeparator());
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Apply location changes
     *
     * @param location the location
     */
    private Location applyLocationChanges(Location location) {
        Location loc = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());

        loc.setX(location.getX() + configuration.getOffsetConfiguration().getX());
        loc.setY(location.getY() + configuration.getOffsetConfiguration().getY());
        loc.setZ(location.getZ() + configuration.getOffsetConfiguration().getZ());
        if (configuration.isAutoCenter()) {
            loc.add(0.5, 0, 0.5);
        }

        return loc;
    }

}
