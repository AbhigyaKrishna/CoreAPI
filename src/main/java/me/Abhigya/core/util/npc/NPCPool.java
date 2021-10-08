package me.Abhigya.core.util.npc;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.packetwrappers.play.out.animation.WrappedPacketOutAnimation;
import me.Abhigya.core.util.npc.event.PlayerNPCHideEvent;
import me.Abhigya.core.util.npc.event.PlayerNPCInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents the main management point for {@link NPC}s.
 */
public class NPCPool implements Listener {

    private static final Random RANDOM = new Random( );

    protected final Plugin plugin;
    protected final Map< Integer, NPC > npcMap = new ConcurrentHashMap<>( );
    private final double spawnDistance;
    private final double actionDistance;
    private final long tabListRemoveTicks;

    /**
     * Creates a new NPC pool which handles events, spawning and destruction of the NPCs for players.
     * Please use {@link #createDefault(Plugin)} instead, this constructor will be private in a
     * further release.
     *
     * @param plugin the instance of the plugin which creates this pool
     * @deprecated Use {@link #createDefault(Plugin)} instead
     */
    @Deprecated
    public NPCPool( @NotNull Plugin plugin ) {
        this( plugin, 50, 20, 30 );
    }

    /**
     * Creates a new NPC pool which handles events, spawning and destruction of the NPCs for players.
     * Please use {@link #builder(Plugin)} instead, this constructor will be private in a further
     * release.
     *
     * @param plugin             the instance of the plugin which creates this pool
     * @param spawnDistance      the distance in which NPCs are spawned for players
     * @param actionDistance     the distance in which NPC actions are displayed for players
     * @param tabListRemoveTicks the time in ticks after which the NPC will be removed from the
     *                           players tab
     */
    @Deprecated
    public NPCPool( @NotNull Plugin plugin, int spawnDistance, int actionDistance, long tabListRemoveTicks ) {
        Preconditions.checkArgument( spawnDistance > 0 && actionDistance > 0, "Distance has to be > 0!" );
        Preconditions.checkArgument( actionDistance <= spawnDistance, "Action distance cannot be higher than spawn distance!" );

        this.plugin = plugin;

        // limiting the spawn distance to the Bukkit view distance to avoid NPCs not being shown
        this.spawnDistance = Math.min( spawnDistance * spawnDistance, Math.pow( Bukkit.getViewDistance( ) << 4, 2 ) );
        this.actionDistance = actionDistance * actionDistance;
        this.tabListRemoveTicks = tabListRemoveTicks;

        Bukkit.getPluginManager( ).registerEvents( this, plugin );

        this.addInteractListener( );
        this.npcTick( );
    }

    /**
     * Creates a new npc pool with the default values of a npc pool. The default values of a builder
     * are {@code spawnDistance} to {@code 50}, {@code actionDistance} to {@code 20} and {@code
     * tabListRemoveTicks} to {@code 30}.
     *
     * @param plugin the instance of the plugin which creates this pool.
     * @return the created npc pool with the default values of a pool.
     * @since 2.5-SNAPSHOT
     */
    @NotNull
    public static NPCPool createDefault( @NotNull Plugin plugin ) {
        return NPCPool.builder( plugin ).build( );
    }

    /**
     * Creates a new builder for a npc pool.
     *
     * @param plugin the instance of the plugin which creates the builder for the pool.
     * @return a new builder for creating a npc pool instance.
     * @since 2.5-SNAPSHOT
     */
    @NotNull
    public static Builder builder( @NotNull Plugin plugin ) {
        return new Builder( plugin );
    }

    /**
     * Adds a packet listener for listening to all use entity packets sent by a client.
     */
    protected void addInteractListener( ) {
        PacketEvents.get( ).getEventManager( ).registerListener( new PacketListener( this ) );
    }

    /**
     * Starts the npc tick.
     */
    protected void npcTick( ) {
        Bukkit.getScheduler( ).runTaskTimerAsynchronously( this.plugin, ( ) -> {
            for ( Player player : ImmutableList.copyOf( Bukkit.getOnlinePlayers( ) ) ) {
                for ( NPC npc : this.npcMap.values( ) ) {
                    Location npcLoc = npc.getLocation( );
                    Location playerLoc = player.getLocation( );
                    if ( !npcLoc.getWorld( ).equals( playerLoc.getWorld( ) ) ) {
                        if ( npc.isShownFor( player ) ) {
                            npc.hide( player, this.plugin, PlayerNPCHideEvent.Reason.SPAWN_DISTANCE );
                        }
                        continue;
                    } else if ( !npcLoc.getWorld( ).isChunkLoaded( npcLoc.getBlockX( ) >> 4, npcLoc.getBlockZ( ) >> 4 ) ) {
                        if ( npc.isShownFor( player ) ) {
                            npc.hide( player, this.plugin, PlayerNPCHideEvent.Reason.UNLOADED_CHUNK );
                        }
                        continue;
                    }

                    double distance = npcLoc.distanceSquared( playerLoc );
                    boolean inRange = distance <= this.spawnDistance;

                    if ( ( npc.isExcluded( player ) || !inRange ) && npc.isShownFor( player ) ) {
                        npc.hide( player, this.plugin, PlayerNPCHideEvent.Reason.SPAWN_DISTANCE );
                    } else if ( ( !npc.isExcluded( player ) && inRange ) && !npc.isShownFor( player ) ) {
                        npc.show( player, this.plugin, this.tabListRemoveTicks );
                    }

                    if ( npc.isShownFor( player ) && npc.isLookAtPlayer( ) && distance <= this.actionDistance ) {
                        npc.rotation( ).queueLookAt( playerLoc ).send( player );
                    }
                }
            }
        }, 20, 2 );
    }

    /**
     * @return A free entity id which can be used for NPCs
     */
    protected int getFreeEntityId( ) {
        int id;

        do {
            id = RANDOM.nextInt( Integer.MAX_VALUE );
        } while ( this.npcMap.containsKey( id ) );

        return id;
    }

    /**
     * Adds the given {@code npc} to the list of handled NPCs of this pool.
     *
     * @param npc The npc to add.
     * @see NPC#builder()
     */
    protected void takeCareOf( @NotNull NPC npc ) {
        this.npcMap.put( npc.getEntityId( ), npc );
    }

    /**
     * Gets a specific npc by the given {@code entityId}.
     *
     * @param entityId the entity id of the npc to get.
     * @return The npc or {@code null} if there is no npc with the given entity id.
     * @deprecated Use {@link #getNpc(int)} instead.
     */
    @Nullable
    @Deprecated
    public NPC getNPC( int entityId ) {
        return this.npcMap.get( entityId );
    }

    /**
     * Gets a specific npc by the given {@code entityId}.
     *
     * @param entityId the entity id of the npc to get.
     * @return The npc by the given {@code entityId}.
     * @since 2.5-SNAPSHOT
     */
    @NotNull
    public Optional< NPC > getNpc( int entityId ) {
        return Optional.ofNullable( this.npcMap.get( entityId ) );
    }

    /**
     * Gets a specific npc by the given {@code uniqueId}.
     *
     * @param uniqueId the entity unique id of the npc to get.
     * @return The npc by the given {@code uniqueId}.
     * @since 2.5-SNAPSHOT
     */
    @NotNull
    public Optional< NPC > getNpc( @NotNull UUID uniqueId ) {
        return this.npcMap.values( ).stream( ).filter( npc -> npc.getProfile( ).getUniqueId( ).equals( uniqueId ) ).findFirst( );
    }

    /**
     * Removes the given npc by it's entity id from the handled NPCs of this pool.
     *
     * @param entityId the entity id of the npc to get.
     */
    public void removeNPC( int entityId ) {
        this.getNpc( entityId ).ifPresent( npc -> {
            this.npcMap.remove( entityId );
            npc.getSeeingPlayers( ).forEach( player -> npc.hide( player, this.plugin, PlayerNPCHideEvent.Reason.REMOVED ) );
        } );
    }

    /**
     * Get an unmodifiable copy of all NPCs handled by this pool.
     *
     * @return a copy of the NPCs this pool manages.
     */
    @NotNull
    public Collection< NPC > getNPCs( ) {
        return Collections.unmodifiableCollection( this.npcMap.values( ) );
    }

    @EventHandler
    private void handleRespawn( PlayerRespawnEvent event ) {
        Player player = event.getPlayer( );

        this.npcMap.values( ).stream( )
                .filter( npc -> npc.isShownFor( player ) )
                .forEach( npc -> npc.hide( player, this.plugin, PlayerNPCHideEvent.Reason.RESPAWNED ) );
    }

    @EventHandler
    private void handleQuit( PlayerQuitEvent event ) {
        Player player = event.getPlayer( );

        this.npcMap.values( ).stream( )
                .filter( npc -> npc.isShownFor( player ) || npc.isExcluded( player ) )
                .forEach( npc -> {
                    npc.removeSeeingPlayer( player );
                    npc.removeExcludedPlayer( player );
                } );
    }

    @EventHandler
    private void handleSneak( PlayerToggleSneakEvent event ) {
//        Player player = event.getPlayer();
//
//        this.npcMap.values().stream()
//                .filter(npc -> npc.isImitatePlayer() && npc.isShownFor(player))
//                .filter(npc -> npc.getLocation().getWorld().equals(player.getWorld())
//                        && npc.getLocation().distanceSquared(player.getLocation()) <= this.actionDistance)
//                .forEach(npc -> npc.metadata()
//                        .queue(MetadataModifier.EntityMetadata.SNEAKING, event.isSneaking()).send(player));
    }

    @EventHandler
    private void handleClick( PlayerInteractEvent event ) {
        Player player = event.getPlayer( );

        if ( event.getAction( ) == Action.LEFT_CLICK_AIR || event.getAction( ) == Action.LEFT_CLICK_BLOCK ) {
            this.npcMap.values( ).stream( )
                    .filter( npc -> npc.isImitatePlayer( ) && npc.isShownFor( player ) )
                    .filter( npc -> npc.getLocation( ).getWorld( ).equals( player.getWorld( ) )
                            && npc.getLocation( ).distanceSquared( player.getLocation( ) ) <= this.actionDistance )
                    .forEach( npc -> npc.animation( ).queue( WrappedPacketOutAnimation.EntityAnimationType.SWING_MAIN_ARM )
                            .send( player ) );
        }
    }

    @EventHandler
    private void handleInteract( PlayerNPCInteractEvent event ) {
        NPCInteractAction.ClickType clickType;
        if ( event.getUseAction( ) == PlayerNPCInteractEvent.EntityUseAction.INTERACT || event.getUseAction( ) == PlayerNPCInteractEvent.EntityUseAction.INTERACT_AT )
            clickType = NPCInteractAction.ClickType.RIGHT_CLICK;
        else
            clickType = NPCInteractAction.ClickType.LEFT_CLICK;
        event.getNPC( ).handleInteract( event.getPlayer( ), clickType );
    }

    /**
     * A builder for a npc pool.
     */
    public static class Builder {

        /**
         * The instance of the plugin which creates this pool
         */
        private final Plugin plugin;

        /**
         * The distance in which NPCs are spawned for players
         */
        private int spawnDistance = 50;
        /**
         * The distance in which NPC actions are displayed for players
         */
        private int actionDistance = 20;
        /**
         * The time in ticks after which the NPC will be removed from the players tab
         */
        private long tabListRemoveTicks = 30;

        /**
         * Creates a new builder for a npc pool.
         *
         * @param plugin The instance of the plugin which creates the builder.
         */
        private Builder( @NotNull Plugin plugin ) {
            this.plugin = Preconditions.checkNotNull( plugin, "plugin" );
        }

        /**
         * Sets the spawn distance in which NPCs are spawned for players. Must be higher than {@code
         * 0}.
         *
         * @param spawnDistance the spawn distance in which NPCs are spawned for players.
         * @return The same instance of this class, for chaining.
         */
        @NotNull
        public Builder spawnDistance( int spawnDistance ) {
            Preconditions.checkArgument( spawnDistance > 0, "Spawn distance must be more than 0" );
            this.spawnDistance = spawnDistance;
            return this;
        }

        /**
         * Sets the distance in which NPC actions are displayed for players. Must be higher than {@code 0}.
         *
         * @param actionDistance the distance in which NPC actions are displayed for players.
         * @return The same instance of this class, for chaining.
         */
        @NotNull
        public Builder actionDistance( int actionDistance ) {
            Preconditions.checkArgument( actionDistance > 0, "Action distance must be more than 0" );
            this.actionDistance = actionDistance;
            return this;
        }

        /**
         * Sets the distance in which NPC actions are displayed for players. A negative value indicates
         * that the npc is never removed from the player list by default.
         *
         * @param tabListRemoveTicks the distance in which NPC actions are displayed for players.
         * @return The same instance of this class, for chaining.
         */
        @NotNull
        public Builder tabListRemoveTicks( long tabListRemoveTicks ) {
            this.tabListRemoveTicks = tabListRemoveTicks;
            return this;
        }

        /**
         * Creates a new npc tool by the values passed to the builder.
         *
         * @return The created npc pool.
         */
        @NotNull
        public NPCPool build( ) {
            return new NPCPool( this.plugin, this.spawnDistance, this.actionDistance,
                    this.tabListRemoveTicks );
        }

    }

}