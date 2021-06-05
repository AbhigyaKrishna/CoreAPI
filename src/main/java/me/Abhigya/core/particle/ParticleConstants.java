package me.Abhigya.core.particle;

import me.Abhigya.core.util.reflection.general.ClassReflection;
import me.Abhigya.core.util.reflection.general.ConstructorReflection;
import me.Abhigya.core.util.reflection.general.FieldReflection;
import me.Abhigya.core.util.reflection.general.MethodReflection;
import me.Abhigya.core.util.server.Version;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Constants for particles.
 */
public class ParticleConstants {

    /* ---------------- Classes ---------------- */

    /**
     * Represents the ItemStack class.
     */
    public static Class ITEM_STACK_CLASS = null;
    /**
     * Represents the Packet class.
     */
    public static Class PACKET_CLASS = null;
    /**
     * Represents the PacketPlayOutWorldParticles class.
     */
    public static Class PACKET_PLAY_OUT_WORLD_PARTICLES_CLASS = null;
    /**
     * Represents the EnumParticle enum.
     */
    public static Class PARTICLE_ENUM = null;
    /**
     * Represents the Particle class.
     */
    public static Class PARTICLE_CLASS = null;
    /**
     * Represents the MiencraftKey class.
     */
    public static Class MINECRAFT_KEY_CLASS = null;
    /**
     * Represents the abstract IRegistry class.
     */
    public static Class REGISTRY_CLASS = null;
    /**
     * Represents the Block class.
     */
    public static Class BLOCK_CLASS = null;
    /**
     * Represents the IBLockData interface.
     */
    public static Class BLOCK_DATA_INTERFACE = null;
    /**
     * Represents the Blocks class.
     */
    public static Class BLOCKS_CLASS = null;
    /**
     * Represents the EntityPlayer class.
     */
    public static Class ENTITY_PLAYER_CLASS = null;
    /**
     * Represents the PlayerConnection class.
     */
    public static Class PLAYER_CONNECTION_CLASS = null;
    /**
     * Represents the CraftPlayer class.
     */
    public static Class CRAFT_PLAYER_CLASS = null;
    /**
     * Represents the CraftItemStack class.
     */
    public static Class CRAFT_ITEM_STACK_CLASS = null;
    /**
     * Represents the ParticleParam class.
     */
    public static Class PARTICLE_PARAM_CLASS = null;
    /**
     * Represents the ParticleParamRedstone class.
     */
    public static Class PARTICLE_PARAM_REDSTONE_CLASS = null;
    /**
     * Represents the ParticleParamBlock class.
     */
    public static Class PARTICLE_PARAM_BLOCK_CLASS = null;
    /**
     * Represents the ParticleParamItem class.
     */
    public static Class PARTICLE_PARAM_ITEM_CLASS = null;
    /**
     * Represents the PluginClassLoader class.
     */
    public static Class PLUGIN_CLASS_LOADER_CLASS = null;

    /* ---------------- Methods ---------------- */

    /**
     * Represents the IRegistry#get(MinecraftKey) method.
     */
    public static Method REGISTRY_GET_METHOD = null;
    /**
     * Represents the PlayerConnection#sendPacket(); method.
     */
    public static Method PLAYER_CONNECTION_SEND_PACKET_METHOD = null;
    /**
     * Represents the CraftPlayer#getHandle(); method.
     */
    public static Method CRAFT_PLAYER_GET_HANDLE_METHOD = null;
    /**
     * Represents the Block#getBlockData(); method.
     */
    public static Method BLOCK_GET_BLOCK_DATA_METHOD = null;
    /**
     * Represents the CraftItemStack#asNMSCopy(); method.
     */
    public static Method CRAFT_ITEM_STACK_AS_NMS_COPY_METHOD = null;

    /* ---------------- Fields ---------------- */

    /**
     * Represents the EntityPlayer#playerConnection field.
     */
    public static Field ENTITY_PLAYER_PLAYER_CONNECTION_FIELD = null;
    /**
     * Represents the PluginClassLoader#plugin field.
     */
    public static Field PLUGIN_CLASS_LOADER_PLUGIN_FIELD = null;

    /* ---------------- Constructor ---------------- */

    /**
     * Represents the PacketPlayOutWorldParticles constructor.
     */
    public static Constructor PACKET_PLAY_OUT_WORLD_PARTICLES_CONSTRUCTOR = null;
    /**
     * Represents the MinecraftKey constructor.
     */
    public static Constructor MINECRAFT_KEY_CONSTRUCTOR = null;
    /**
     * Represents the ParticleParamRedstone constructor.
     */
    public static Constructor PARTICLE_PARAM_REDSTONE_CONSTRUCTOR = null;
    /**
     * Represents the ParticleParamBlock constructor.
     */
    public static Constructor PARTICLE_PARAM_BLOCK_CONSTRUCTOR = null;
    /**
     * Represents the ParticleParamItem constructor.
     */
    public static Constructor PARTICLE_PARAM_ITEM_CONSTRUCTOR = null;

    /* ---------------- Object constants ---------------- */

    /**
     * Represents the ParticleType Registry.
     */
    public static Object PARTICLE_TYPE_REGISTRY = null;

    /* ---------------- INIT ---------------- */

    static {
        Version version = Version.getServerVersion();
        // Classes
        try {
            ITEM_STACK_CLASS = ClassReflection.getNmsClass("ItemStack");
        } catch (ClassNotFoundException ignored) {}
        try {
            PACKET_CLASS = ClassReflection.getNmsClass("Packet");
        } catch (ClassNotFoundException ignored) {}
        try {
            PACKET_PLAY_OUT_WORLD_PARTICLES_CLASS = ClassReflection.getNmsClass("PacketPlayOutWorldParticles");
        } catch (ClassNotFoundException ignored) {}
        try {
            PARTICLE_ENUM = version.isOlder(Version.v1_13_R1) ? ClassReflection.getNmsClass("EnumParticle") : null;
        } catch (ClassNotFoundException ignored) {}
        try {
            PARTICLE_CLASS = version.isOlder(Version.v1_13_R1) ? null : ClassReflection.getNmsClass("Particle");
        } catch (ClassNotFoundException ignored) {}
        try {
            MINECRAFT_KEY_CLASS = ClassReflection.getNmsClass("MinecraftKey");
        } catch (ClassNotFoundException ignored) {}
        try {
            REGISTRY_CLASS = version.isOlder(Version.v1_13_R1) ? null : ClassReflection.getNmsClass("IRegistry");
        } catch (ClassNotFoundException ignored) {}
        try {
            BLOCK_CLASS = ClassReflection.getNmsClass("Block");
        } catch (ClassNotFoundException ignored) {}
        try {
            BLOCK_DATA_INTERFACE = ClassReflection.getNmsClass("IBlockData");
        } catch (ClassNotFoundException ignored) {}
        try {
            BLOCKS_CLASS = version.isOlder(Version.v1_13_R1) ? null : ClassReflection.getNmsClass("Blocks");
        } catch (ClassNotFoundException ignored) {}
        try {
            ENTITY_PLAYER_CLASS = ClassReflection.getNmsClass("EntityPlayer");
        } catch (ClassNotFoundException ignored) {}
        try {
            PLAYER_CONNECTION_CLASS = ClassReflection.getNmsClass("PlayerConnection");
        } catch (ClassNotFoundException ignored) {}
        try {
            CRAFT_PLAYER_CLASS = ClassReflection.getCraftClass("CraftPlayer", "entity");
        } catch (ClassNotFoundException ignored) {}
        try {
            CRAFT_ITEM_STACK_CLASS = ClassReflection.getCraftClass("CraftItemStack", "inventory");
        } catch (ClassNotFoundException ignored) {}
        try {
            PARTICLE_PARAM_CLASS = version.isOlder(Version.v1_13_R1) ? null : ClassReflection.getNmsClass("ParticleParam");
        } catch (ClassNotFoundException ignored) {}
        try {
            PARTICLE_PARAM_REDSTONE_CLASS = version.isOlder(Version.v1_13_R1) ? null : ClassReflection.getNmsClass("ParticleParamRedstone");
        } catch (ClassNotFoundException ignored) {}
        try {
            PARTICLE_PARAM_BLOCK_CLASS = version.isOlder(Version.v1_13_R1) ? null : ClassReflection.getNmsClass("ParticleParamBlock");
        } catch (ClassNotFoundException ignored) {}
        try {
            PARTICLE_PARAM_ITEM_CLASS = version.isOlder(Version.v1_13_R1) ? null : ClassReflection.getNmsClass("ParticleParamItem");
        } catch (ClassNotFoundException ignored) {}
        try {
            PLUGIN_CLASS_LOADER_CLASS = Class.forName("org.bukkit.plugin.java.PluginClassLoader");
        } catch (ClassNotFoundException ignored) {}
        // Methods
        try {
            REGISTRY_GET_METHOD = version.isOlder(Version.v1_13_R1) ? null : MethodReflection.get(REGISTRY_CLASS, "get", MINECRAFT_KEY_CLASS);
        } catch (NoSuchMethodException ignored) {}
        try {
            PLAYER_CONNECTION_SEND_PACKET_METHOD = MethodReflection.get(PLAYER_CONNECTION_CLASS, "sendPacket", PACKET_CLASS);
        } catch (NoSuchMethodException ignored) {}
        try {
            CRAFT_PLAYER_GET_HANDLE_METHOD = MethodReflection.get(CRAFT_PLAYER_CLASS, "getHandle");
        } catch (NoSuchMethodException ignored) {}
        try {
            BLOCK_GET_BLOCK_DATA_METHOD = MethodReflection.get(BLOCK_CLASS, "getBlockData");
        } catch (NoSuchMethodException ignored) {}
        try {
            CRAFT_ITEM_STACK_AS_NMS_COPY_METHOD = MethodReflection.get(CRAFT_ITEM_STACK_CLASS, "asNMSCopy", ItemStack.class);
        } catch (NoSuchMethodException ignored) {}
        //Fields
        try {
            ENTITY_PLAYER_PLAYER_CONNECTION_FIELD = FieldReflection.get(ENTITY_PLAYER_CLASS, "playerConnection", false);
        } catch (NoSuchFieldException ignored) {}
        try {
            PLUGIN_CLASS_LOADER_PLUGIN_FIELD = FieldReflection.get(PLUGIN_CLASS_LOADER_CLASS, "plugin", true);
        } catch (NoSuchFieldException ignored) {}
        //Constructors
        if (version.isOlder(Version.v1_13_R1)) {
            try {
                PACKET_PLAY_OUT_WORLD_PARTICLES_CONSTRUCTOR = ConstructorReflection.get(PACKET_PLAY_OUT_WORLD_PARTICLES_CLASS, PARTICLE_ENUM, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class);
            } catch (NoSuchMethodException ignored) {}
        }
        else if (version.isOlder(Version.v1_15_R1)) {
            try {
                PACKET_PLAY_OUT_WORLD_PARTICLES_CONSTRUCTOR = ConstructorReflection.get(PACKET_PLAY_OUT_WORLD_PARTICLES_CLASS, PARTICLE_PARAM_CLASS, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class);
            } catch (NoSuchMethodException ignored) {}
        }
        else {
            try {
                PACKET_PLAY_OUT_WORLD_PARTICLES_CONSTRUCTOR = ConstructorReflection.get(PACKET_PLAY_OUT_WORLD_PARTICLES_CLASS, PARTICLE_PARAM_CLASS, boolean.class, double.class, double.class, double.class, float.class, float.class, float.class, float.class, int.class);
            } catch (NoSuchMethodException ignored) {}
        }
        try {
            MINECRAFT_KEY_CONSTRUCTOR = ConstructorReflection.get(MINECRAFT_KEY_CLASS, String.class);
        } catch (NoSuchMethodException ignored) {}
        try {
            PARTICLE_PARAM_REDSTONE_CONSTRUCTOR = version.isOlder(Version.v1_13_R1) ? null : ConstructorReflection.get(PARTICLE_PARAM_REDSTONE_CLASS, float.class, float.class, float.class, float.class);
        } catch (NoSuchMethodException ignored) {}
        try {
            PARTICLE_PARAM_BLOCK_CONSTRUCTOR = version.isOlder(Version.v1_13_R1) ? null : ConstructorReflection.get(PARTICLE_PARAM_BLOCK_CLASS, PARTICLE_CLASS, BLOCK_DATA_INTERFACE);
        } catch (NoSuchMethodException ignored) {}
        try {
            PARTICLE_PARAM_ITEM_CONSTRUCTOR = version.isOlder(Version.v1_13_R1) ? null : ConstructorReflection.get(PARTICLE_PARAM_ITEM_CLASS, PARTICLE_CLASS, ITEM_STACK_CLASS);
        } catch (NoSuchMethodException ignored) {}
        // Constants
        try {
            PARTICLE_TYPE_REGISTRY = version.isOlder(Version.v1_13_R1) ? null : FieldReflection.getValue(FieldReflection.get(REGISTRY_CLASS, "PARTICLE_TYPE", false),
                    FieldReflection.get(REGISTRY_CLASS, "PARTICLE_TYPE", false).getName());
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}
    }

}
