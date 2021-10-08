package me.Abhigya.core.util.hologram;

import me.Abhigya.core.util.reflection.general.ClassReflection;
import me.Abhigya.core.util.reflection.general.ConstructorReflection;
import me.Abhigya.core.util.reflection.general.MethodReflection;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ReflectionCache {

    public static final Class< ? > CRAFT_WORLD;
    public static final Class< ? > CRAFT_ITEM_STACK;
    public static final Class< ? > WORLD;
    public static final Class< ? > ITEM_STACK;
    public static final Class< ? > ENTITY_ARMOR_STAND;
    public static final Class< ? > PACKET_PLAY_OUT_ENTITY_EQUIPMENT;
    public static final Class< ? > PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING;
    public static final Class< ? > PACKET_PLAY_OUT_ENTITY_VELOCITY;
    public static final Class< ? > PACKET_PLAY_OUT_ENTITY_DESTROY;
    public static final Class< ? > PACKET_PLAY_OUT_ENTITY_METADATA;
    public static final Class< ? > PACKET_PLAY_OUT_ENTITY_TELEPORT;
    public static final Class< ? > PACKET_PLAY_OUT_ENTITY_HEAD_ROTATION;
    public static final Class< ? > ENTITY;
    public static final Class< ? > DATA_WATCHER;
    public static final Class< ? > ENTITY_LIVING;
    public static final Class< ? > I_CHAT_BASE_COMPONENT;
    public static final Class< ? > CHAT_MESSAGE;

    public static Constructor< ? > ENTITY_ARMOR_STAND_CONSTRUCTOR = null;
    public static Constructor< ? > CHAT_MESSAGE_CONSTRUCTOR = null;
    public static Constructor< ? > PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CONSTRUCTOR = null;
    public static Constructor< ? > PACKET_PLAY_OUT_ENTITY_DESTROY_CONSTRUCTOR = null;
    public static Constructor< ? > PACKET_PLAY_OUT_ENTITY_METADATA_CONSTRUCTOR = null;
    public static Constructor< ? > PACKET_PLAY_OUT_ENTITY_TELEPORT_CONSTRUCTOR = null;
    public static Constructor< ? > PACKET_PLAY_OUT_ENTITY_EQUIPMENT_CONSTRUCTOR = null;

    public static Method SET_INVISIBLE = null;
    public static Method SET_CUSTOM_NAME_VISIBLE = null;
    public static Method SET_MARKER = null;
    public static Method SET_BASE_PLATE = null;
    public static Method SET_NO_GRAVITY = null;
    public static Method SET_CUSTOM_NAME = null;
    public static Method GET_ID = null;
    public static Method GET_DATA_WATCHER = null;
    public static Method SET_LOCATION = null;
    public static Method SET_SMALL = null;
    public static Method AS_NMS_COPY = null;

    static {
        CRAFT_WORLD = ClassReflection.getCraftClass( "CraftWorld", "" );
        CRAFT_ITEM_STACK = ClassReflection.getCraftClass( "CraftItemStack", "inventory" );
        WORLD = ClassReflection.getNmsClass( "World", "world.level" );
        ITEM_STACK = ClassReflection.getNmsClass( "ItemStack", "world.item" );
        ENTITY_ARMOR_STAND = ClassReflection.getNmsClass( "EntityArmorStand", "world.entity.decoration" );
        PACKET_PLAY_OUT_ENTITY_EQUIPMENT = ClassReflection.getNmsClass( "PacketPlayOutEntityEquipment", "network.protocol.game" );
        PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING = ClassReflection.getNmsClass( "PacketPlayOutSpawnEntityLiving", "network.protocol.game" );
        PACKET_PLAY_OUT_ENTITY_VELOCITY = ClassReflection.getNmsClass( "PacketPlayOutEntityVelocity", "network.protocol.game" );
        PACKET_PLAY_OUT_ENTITY_DESTROY = ClassReflection.getNmsClass( "PacketPlayOutEntityDestroy", "network.protocol.game" );
        PACKET_PLAY_OUT_ENTITY_METADATA = ClassReflection.getNmsClass( "PacketPlayOutEntityMetadata", "network.protocol.game" );
        PACKET_PLAY_OUT_ENTITY_TELEPORT = ClassReflection.getNmsClass( "PacketPlayOutEntityTeleport", "network.protocol.game" );
        PACKET_PLAY_OUT_ENTITY_HEAD_ROTATION = ClassReflection.getNmsClass( "PacketPlayOutEntityHeadRotation", "network.protocol.game" );
        ENTITY = ClassReflection.getNmsClass( "Entity", "world.entity" );
        DATA_WATCHER = ClassReflection.getNmsClass( "DataWatcher", "network.syncher" );
        ENTITY_LIVING = ClassReflection.getNmsClass( "EntityLiving", "world.entity" );
        I_CHAT_BASE_COMPONENT = ClassReflection.getNmsClass( "IChatBaseComponent", "network.chat" );
        CHAT_MESSAGE = ClassReflection.getNmsClass( "ChatMessage", "network.chat" );

        try {
            ENTITY_ARMOR_STAND_CONSTRUCTOR = ConstructorReflection.get( ENTITY_ARMOR_STAND, WORLD, double.class, double.class, double.class );
            CHAT_MESSAGE_CONSTRUCTOR = ConstructorReflection.get( CHAT_MESSAGE, String.class, Object[].class );
            PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING_CONSTRUCTOR = ConstructorReflection.get( PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING, ENTITY_LIVING );
            PACKET_PLAY_OUT_ENTITY_DESTROY_CONSTRUCTOR = ConstructorReflection.get( PACKET_PLAY_OUT_ENTITY_DESTROY, int[].class );
            PACKET_PLAY_OUT_ENTITY_METADATA_CONSTRUCTOR = ConstructorReflection.get( PACKET_PLAY_OUT_ENTITY_METADATA, int.class, DATA_WATCHER, boolean.class );
            PACKET_PLAY_OUT_ENTITY_TELEPORT_CONSTRUCTOR = ConstructorReflection.get( PACKET_PLAY_OUT_ENTITY_TELEPORT, ENTITY );

            SET_INVISIBLE = MethodReflection.get( ENTITY, "setInvisible", boolean.class );
            SET_CUSTOM_NAME_VISIBLE = MethodReflection.get( ENTITY_ARMOR_STAND, "setCustomNameVisible", boolean.class );
            try {
                SET_MARKER = MethodReflection.get( ENTITY_ARMOR_STAND, "setMarker", boolean.class );
            } catch ( NoSuchMethodException e ) {
                SET_MARKER = null;
            }
            SET_BASE_PLATE = MethodReflection.get( ENTITY_ARMOR_STAND, "setBasePlate", boolean.class );
            try {
                SET_NO_GRAVITY = MethodReflection.get( ENTITY_ARMOR_STAND, "setGravity", boolean.class );
            } catch ( NoSuchMethodException e ) {
                SET_NO_GRAVITY = MethodReflection.get( ENTITY_ARMOR_STAND, "setNoGravity", boolean.class );
            }
            SET_LOCATION = MethodReflection.get( ENTITY, "setLocation", double.class, double.class, double.class, float.class, float.class );
            SET_SMALL = MethodReflection.get( ENTITY_ARMOR_STAND, "setSmall", boolean.class );
            GET_ID = MethodReflection.get( ENTITY_ARMOR_STAND, "getId" );
            GET_DATA_WATCHER = MethodReflection.get( ENTITY, "getDataWatcher" );
            AS_NMS_COPY = MethodReflection.get( CRAFT_ITEM_STACK, "asNMSCopy", ItemStack.class );
            try {
                SET_CUSTOM_NAME = MethodReflection.get( ENTITY_ARMOR_STAND, "setCustomName", String.class );
            } catch ( NoSuchMethodException e ) {
                SET_CUSTOM_NAME = MethodReflection.get( ENTITY_ARMOR_STAND, "setCustomName", I_CHAT_BASE_COMPONENT );
            }

            if ( ClassReflection.nmsClassExist( "EnumItemSlot", "world.entity" ) ) {
                try {
                    PACKET_PLAY_OUT_ENTITY_EQUIPMENT_CONSTRUCTOR = ConstructorReflection.get( PACKET_PLAY_OUT_ENTITY_EQUIPMENT, int.class, ClassReflection.getNmsClass( "EnumItemSlot", "world.entity" ), ITEM_STACK );
                } catch ( NoSuchMethodException e ) {
                    PACKET_PLAY_OUT_ENTITY_EQUIPMENT_CONSTRUCTOR = ConstructorReflection.get( PACKET_PLAY_OUT_ENTITY_EQUIPMENT, int.class, int.class, ITEM_STACK );
                }
            } else {
                PACKET_PLAY_OUT_ENTITY_EQUIPMENT_CONSTRUCTOR = ConstructorReflection.get( PACKET_PLAY_OUT_ENTITY_EQUIPMENT, int.class, int.class, ITEM_STACK );
            }
        } catch ( NoSuchMethodException e ) {
            e.printStackTrace( );
        }
    }

}
