package me.Abhigya.core.util.reflection.bukkit;

import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.util.math.collision.BoundingBox;
import me.Abhigya.core.util.reflection.general.ClassReflection;
import me.Abhigya.core.util.reflection.general.ConstructorReflection;
import me.Abhigya.core.util.reflection.general.FieldReflection;
import me.Abhigya.core.util.reflection.general.MethodReflection;
import me.Abhigya.core.util.server.Version;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Class for reflecting Bukkit entities
 */
public class EntityReflection {

    /**
     * Gets the {@link BoundingBox} for the provided {@link Entity}.
     * <p>
     *
     * @param entity Entity to get its BoundingBox
     * @param height Entity's height
     * @return BoundingBox for the entity, or null if couldn't get
     */
    public static BoundingBox getBoundingBox(Entity entity, float height) {
        try {
            final Object handle = BukkitReflection.getHandle(entity);
            final Object nms_bb = handle.getClass().getMethod("getBoundingBox").invoke(handle); // NMS bounding box

            final Field[] fields = nms_bb.getClass().getDeclaredFields();

            int i = 0;

            if (CoreAPI.getInstance().getServerVersion().isNewerEquals(Version.v1_17_R1))
                i = 1;

            final double min_x = (double) FieldReflection.getValue(nms_bb, fields[i++].getName());
            final double min_y = (double) FieldReflection.getValue(nms_bb, fields[i++].getName()) - height;
            final double min_z = (double) FieldReflection.getValue(nms_bb, fields[i++].getName());

            final double max_x = (double) FieldReflection.getValue(nms_bb, fields[i++].getName());
            final double max_y = (double) FieldReflection.getValue(nms_bb, fields[i++].getName()) - height;
            final double max_z = (double) FieldReflection.getValue(nms_bb, fields[i++].getName());

            return new BoundingBox(new Vector(min_x, min_y, min_z), new Vector(max_x, max_y, max_z));
        } catch (Throwable ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the {@link BoundingBox} for the provided {@link Entity}. The accuracy is
     * not guaranteed when calculating the entity's height.
     * <p>
     *
     * @param entity Entity to get its BoundingBox
     * @return BoundingBox for the entity, or null if couldn't get
     */
    public static BoundingBox getBoundingBox(Entity entity) {
        try {
            final Object handle = BukkitReflection.getHandle(entity);
            final float head_height = (float) handle.getClass().getMethod("getHeadHeight").invoke(handle);

            return getBoundingBox(entity, head_height);
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | SecurityException | NoSuchMethodException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * This method makes the provided {@code entity} invisible to the desired target
     * players.
     * <p>
     * Note that after calling this, the entity cannot be made visible. Instead the
     * entity will be invisible if the player left the server and joins it again.
     * <p>
     *
     * @param entity  Entity to make invisible to the {@code targets} players
     * @param targets Players that will not can see the entity
     */
    public static void setInvisibleTo(Entity entity, Player... targets) {
        try {
            Class<?> packetPlayOutEntityDestroy;
            if (CoreAPI.getInstance().getServerVersion().isNewerEquals(Version.v1_17_R1))
                packetPlayOutEntityDestroy = Class.forName("net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy");
            else
                packetPlayOutEntityDestroy = ClassReflection.getNmsClass("PacketPlayOutEntityDestroy");

            Object packet = ConstructorReflection.get(packetPlayOutEntityDestroy, int[].class).newInstance(new int[]{entity.getEntityId()});
            for (Player target : targets) {
                if (target.isOnline()) {
                    BukkitReflection.sendPacket(target, packet);
                }
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets whether the provided {@code entity} will have AI.
     * <p>
     *
     * @param entity Target entity
     * @param ai     Whether the entity will have AI or not
     */
    public static void setAI(LivingEntity entity, boolean ai) {
        try {
            if (CoreAPI.getInstance().getServerVersion().isOlder(Version.v1_9_R2)) {
                Class<?> nbt_class = ClassReflection.getNmsClass("NBTTagCompound");

                Object handle = BukkitReflection.getHandle(entity);
                Object ntb = ConstructorReflection.newInstance(nbt_class, new Class<?>[0]);
//				Object    ntb = ConstructorReflection.newInstance(nbt_class);

                Method m0 = MethodReflection.get(handle.getClass(), "c", nbt_class);
                Method m1 = MethodReflection.get(nbt_class, "setInt", String.class, int.class);
                Method m2 = MethodReflection.get(handle.getClass(), "f", nbt_class);

                MethodReflection.invoke(m0, handle, ntb);
                MethodReflection.invoke(m1, ntb, "NoAI", (ai ? 0 : 1));
                MethodReflection.invoke(m2, handle, ntb);
            } else {
                MethodReflection.invoke(MethodReflection.get(entity.getClass(), "setAI", boolean.class), entity, ai);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException | ClassNotFoundException | InstantiationException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sets whether the provided {@code entity} is will have collisions.
     * <p>
     *
     * @param entity     Target entity
     * @param collidable Whether to enable collisions for the entity
     */
    public static void setCollidable(LivingEntity entity, boolean collidable) {
        try {
            if (CoreAPI.getInstance().getServerVersion().isNewerEquals(Version.v1_9_R2)) {
                MethodReflection.invoke(MethodReflection.get(entity.getClass(), "setCollidable", boolean.class), entity, collidable);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the entity's current position.
     * <p>
     *
     * @param entity Entity to get
     * @return Copy of Location containing the position of the desired entity, or null if couldn't get
     */
    public static Location getLocation(Entity entity) {
        try {
            Object handle = BukkitReflection.getHandle(entity);

            String locYaw, locPitch;
            if (CoreAPI.getInstance().getServerVersion().isNewerEquals(Version.v1_17_R1)) {
                locYaw = "ay";
                locPitch = "az";
            } else {
                locYaw = "yaw";
                locPitch = "pitch";
            }

            final double x = (double) MethodReflection.get(handle.getClass(), "locX").invoke(handle);
            final double y = (double) MethodReflection.get(handle.getClass(), "locY").invoke(handle);
            final double z = (double) MethodReflection.get(handle.getClass(), "locZ").invoke(handle);

            final float yaw = FieldReflection.get(handle.getClass(), locYaw).getFloat(handle);
            final float pitch = FieldReflection.get(handle.getClass(), locPitch).getFloat(handle);

            return new Location(entity.getWorld(), x, y, z, yaw, pitch);
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException |
                InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sets the location of the provided nms entity. Note that this method doesn't teleport the entity.
     * <p>
     *
     * @param entity Nms entity to set
     * @param x      Location's x
     * @param y      Location's y
     * @param z      Location's z
     * @param yaw    Rotation around axis y
     * @param pitch  Rotation around axis x
     */
    public static void setLocation(Object entity, double x, double y, double z, float yaw, float pitch) {
        try {
            MethodReflection.invoke(MethodReflection.get(entity.getClass(), "setLocation", double.class,
                    double.class, double.class, float.class, float.class), entity, x, y, z, yaw, pitch);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the location of the provided entity. Note that this method doesn't teleport the entity.
     * <p>
     *
     * @param entity Entity to set
     * @param x      Location's x
     * @param y      Location's y
     * @param z      Location's z
     * @param yaw    Rotation around axis y
     * @param pitch  Rotation around axis x
     */
    public static void setLocation(Entity entity, double x, double y, double z, float yaw, float pitch) {
        try {
            Object handle = BukkitReflection.getHandle(entity);

            MethodReflection.invoke(MethodReflection.get(handle.getClass(), "setLocation", double.class,
                    double.class, double.class, float.class, float.class), handle, x, y, z, yaw, pitch);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the location of the provided entity. Note that this method doesn't teleport the entity.
     * <p>
     *
     * @param entity   Entity to set
     * @param location Location for the entity
     */
    public static void setLocation(Entity entity, Location location) {
        setLocation(entity, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    /**
     * Sets the location of the provided nms entity. Note that this method doesn't teleport the entity.
     * <p>
     *
     * @param entity   Nms entity to set
     * @param location Location for the entity
     */
    public static void setLocation(Object entity, Location location) {
        setLocation(entity, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    /**
     * Sets the yaw and pitch of the provided entity. Note that this method doesn't
     * teleport the entity.
     * <p>
     * Also {@link #setLocation(Entity, double, double, double, float, float)} is
     * recommended to be used instead.
     * <p>
     *
     * @param entity Entity the entity to set
     * @param yaw    New yaw
     * @param pitch  New pitch
     */
    public static void setYawPitch(Entity entity, float yaw, float pitch) {
        try {
            Object handle = BukkitReflection.getHandle(entity);

            MethodReflection.invoke(MethodReflection.get(handle.getClass(), "setYawPitch", float.class, float.class),
                    handle, yaw, pitch);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets whether the provided entity is visible or not.
     * <p>
     *
     * @param entity Entity to check
     * @return true if visible
     */
    public static boolean isVisible(Entity entity) {
        try {
            Object handle = BukkitReflection.getHandle(entity);

            return !(boolean) MethodReflection.invoke(MethodReflection.get(handle.getClass(), "isInvisible"), handle);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sets whether the provided entity is visible or not.
     * <p>
     *
     * @param entity  Entity to set
     * @param visible {@code true} = visible, {@code false} = invisible
     */
    public static void setVisible(Entity entity, boolean visible) {
        try {
            Object handle = BukkitReflection.getHandle(entity);

            MethodReflection.invoke(MethodReflection.get(handle.getClass(), "setInvisible", boolean.class), handle, !visible);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets whether the provided entity is silent or not.
     * <p>
     *
     * @param entity Entity to check
     * @return true if silent
     */
    public static boolean isSilent(Entity entity) {
        try {
            Object handle = BukkitReflection.getHandle(entity);
            Method getter = null;

            switch (Version.getServerVersion()) {
                case v1_8_R1:
                case v1_8_R2:
                case v1_8_R3:
                    getter = MethodReflection.get(handle.getClass(), "R");
                    break;

                case v1_9_R1:
                case v1_9_R2:
                    getter = MethodReflection.get(handle.getClass(), "ad");
                    break;

                default:
                    return (boolean) MethodReflection.invoke(MethodReflection.get(entity.getClass(), "isSilent"), entity);
            }
            return (boolean) MethodReflection.invoke(getter, handle);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sets whether the provided entity is silent or not.
     * <p>
     *
     * @param entity Entity to set
     * @param silent true = silent, false = not silent
     */
    public static void setSilent(Entity entity, boolean silent) {
        try {
            Object handle = BukkitReflection.getHandle(entity);

            if (Version.getServerVersion().isOlderEquals(Version.v1_9_R2)) {
                MethodReflection.invoke(MethodReflection.get(handle.getClass(), "b", boolean.class), handle, silent);
            } else {
                MethodReflection.invoke(MethodReflection.get(entity.getClass(), "setSilent", boolean.class), entity, silent);
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets whether or not the provided entity is invulnerable.
     * <p>
     *
     * @param entity Entity to check
     * @return true if invulnerable
     */
    public static boolean isInvulnerable(Entity entity) {
        try {
            Object handle = BukkitReflection.getHandle(entity);

            if (Version.getServerVersion().isOlderEquals(Version.v1_9_R2)) {
                Class<?> damage_class = ClassReflection.getNmsClass("DamageSource");
                Object generic_damage = FieldReflection.getValue(damage_class, "GENERIC");
                Method getter = MethodReflection.get(handle.getClass(), "isInvulnerable", damage_class);

                return (boolean) MethodReflection.invoke(getter, handle, generic_damage);
            } else {
                return (boolean) MethodReflection.invoke(MethodReflection.get(entity.getClass(), "isInvulnerable"), entity);
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sets whether the provided entity is invulnerable or not.
     * <p>
     * When an entity is invulnerable it can only be damaged by players in
     * creative mode.
     * <p>
     *
     * @param entity       Entity to set
     * @param invulnerable true = invulnerable, false = vulnerable
     */
    public static void setInvulnerable(Entity entity, boolean invulnerable) {
        try {
            Object handle = BukkitReflection.getHandle(entity);

            if (Version.getServerVersion().isOlderEquals(Version.v1_9_R2)) {
                Class<?> nms_entity_class = ClassReflection.getNmsClass("Entity");
                Field field = FieldReflection.getAccessible(nms_entity_class, "invulnerable");

                field.set(handle, invulnerable);
            } else {
                MethodReflection.invoke(MethodReflection.get(entity.getClass(), "setInvulnerable", boolean.class),
                        entity, invulnerable);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException | NoSuchFieldException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the provided {@link ArmorStand} as invulnerable. This method is to be
     * used instead of {@link #setInvulnerable(Entity, boolean)}.
     * <p>
     *
     * @param stand Armor stand to set
     */
    public static void setInvulnerable(ArmorStand stand, boolean invulnerable) {
        String field_name;
        switch (Version.getServerVersion()) {
            case v1_9_R1:
                field_name = "by";
                break;
            case v1_9_R2:
            case v1_11_R1:
                field_name = "bz";
                break;
            case v1_10_R1:
            case v1_12_R1:
                field_name = "bA";
                break;
            case v1_13_R1:
            case v1_13_R2:
                field_name = "bG";
                break;
            case v1_14_R1:
                field_name = "bD";
                break;
            case v1_15_R1:
            case v1_16_R1:
            case v1_16_R2:
            case v1_16_R3:
                field_name = "armorStandInvisible";
                break;
            case v1_17_R1:
                field_name = "ce";
                break;
            default:
                field_name = "h";
                break;
        }

        try {
            FieldReflection.setValue(BukkitReflection.getHandle(stand), field_name, !invulnerable);
        } catch (SecurityException | NoSuchFieldException
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Plays a sound for the provided player.
     * <p>
     * This method will fail silently if Location or Sound are null. No
     * sound will be heard by the player if their client does not have the
     * respective sound for the value passed.
     * <p>
     *
     * @param player Location to play the sound
     * @param sound  Internal sound name to play
     * @param volume Volume of the sound
     * @param pitch  Pitch of the sound
     */
    public static void playNamedSound(Player player, String sound, float volume, float pitch) {
        try {
            Class<?> category_enum, packet_play_out_custom_sound_effect;
            if (CoreAPI.getInstance().getServerVersion().isNewerEquals(Version.v1_17_R1)) {
                category_enum = Class.forName("net.minecraft.sounds.SoundCategory");
                packet_play_out_custom_sound_effect = Class.forName("net.minecraft.network.protocol.game.PacketPlayOutCustomSoundEffect");
            }
            else {
                category_enum = ClassReflection.getNmsClass("SoundCategory");
                packet_play_out_custom_sound_effect = ClassReflection.getNmsClass("PacketPlayOutCustomSoundEffect");
            }

            Object master = MethodReflection.invoke(MethodReflection.get(category_enum, "valueOf", String.class), category_enum, "MASTER");

            Location location = player.getEyeLocation();
            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();

            Object packet = ConstructorReflection.newInstance(
                    packet_play_out_custom_sound_effect, new Class<?>[]{String.class,
                            category_enum, double.class, double.class, double.class, float.class, float.class},
                    sound, master, x, y, z, volume, pitch);

            BukkitReflection.sendPacket(player, packet);
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays a Sound at the provided Location in the World.
     * <p>
     * This method will fail silently if Location or Sound are null. No
     * sound will be heard by the players if their clients do not have the
     * respective sound for the value passed.
     * <p>
     *
     * @param location Location to play the sound
     * @param sound    Internal sound name to play
     * @param volume   Volume of the sound
     * @param pitch    Pitch of the sound
     */
    public static void playNameSoundAt(Location location, String sound, float volume, float pitch) {
        World w = location.getWorld();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        try {
            boolean newest = CoreAPI.getInstance().getServerVersion().isNewerEquals(Version.v1_17_R1);
            Class<?> category_enum, packet_play_out_custom_sound_effect, human_class, packet_class;
            if (newest) {
                category_enum = Class.forName("net.minecraft.sounds.SoundCategory");
                packet_play_out_custom_sound_effect = Class.forName("net.minecraft.network.protocol.game.PacketPlayOutCustomSoundEffect");
                human_class = Class.forName("net.minecraft.world.entity.player.EntityHuman");
                packet_class = Class.forName("net.minecraft.network.protocol.Packet");
            }
            else {
                category_enum = ClassReflection.getNmsClass("SoundCategory");
                packet_play_out_custom_sound_effect = ClassReflection.getNmsClass("PacketPlayOutCustomSoundEffect");
                human_class = ClassReflection.getNmsClass("EntityHuman");
                packet_class = ClassReflection.getNmsClass("Packet");
            }

            Object master = MethodReflection.invoke(MethodReflection.get(category_enum, "valueOf", String.class),
                    category_enum, newest ? "a" : "MASTER");

            Object packet = ConstructorReflection.newInstance(
                    packet_play_out_custom_sound_effect, new Class<?>[]{String.class,
                            category_enum, double.class, double.class, double.class, float.class, float.class},
                    sound, master, x, y, z, volume, pitch);

            Object world_server = FieldReflection.getValue(w.getClass(), "world");
            Object minecraft_server = MethodReflection.invoke(MethodReflection.get(world_server.getClass(), "getMinecraftServer"),
                    world_server);
            Object player_list = MethodReflection.invoke(MethodReflection.get(minecraft_server.getClass(), "getPlayerList"),
                    minecraft_server);

            Object dimension;
            if (CoreAPI.getInstance().getServerVersion().isNewerEquals(Version.v1_16_R1)) {
                dimension = MethodReflection.get(world_server.getClass().getSuperclass(), "getDimensionKey")
                        .invoke(world_server);
            } else if (CoreAPI.getInstance().getServerVersion().isNewerEquals(Version.v1_13_R2)) {
                Object world_provider = MethodReflection.get(world_server.getClass().getSuperclass(), "getWorldProvider")
                        .invoke(world_server);
                dimension = MethodReflection.get(world_provider.getClass(), "getDimensionManager").invoke(world_provider);
            } else {
                dimension = world_server.getClass().getField("dimension").getInt(world_server);
            }

            MethodReflection.invoke(
                    MethodReflection.get(player_list.getClass(), "sendPacketNearby", human_class, double.class, double.class,
                            double.class, double.class, dimension.getClass(), packet_class),
                    player_list, null, x, y, z, (volume > 1.0F ? 16.0F * volume : 16.0D), dimension, packet);
        } catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException
                | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
