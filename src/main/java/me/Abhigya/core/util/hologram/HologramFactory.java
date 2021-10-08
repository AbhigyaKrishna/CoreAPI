package me.Abhigya.core.util.hologram;

import me.Abhigya.core.handler.PluginHandler;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HologramFactory extends PluginHandler {

    private final Map< String, Hologram > holograms;

    public HologramFactory( Plugin plugin ) {
        super( plugin );
        this.holograms = new ConcurrentHashMap<>( );
        this.register( );
    }

    public Hologram createHologram( String id, Location location, String... lines ) {
        if ( this.hasHologram( id ) )
            return null;

        Hologram hologram = new DefaultHologram( location, lines );
        this.holograms.put( id, hologram );
        return hologram;
    }

    public Hologram getHologram( String id ) {
        if ( this.holograms.containsKey( id ) )
            return this.holograms.get( id );

        return null;
    }

    public boolean hasHologram( String id ) {
        return this.holograms.containsKey( id );
    }

    public void removeHologram( String id ) {
        if ( this.hasHologram( id ) ) {
            this.holograms.get( id ).destroy( );
            this.holograms.remove( id );
        }
    }

//    @EventHandler
//    private void handlePlayerTeleport(PlayerTeleportEvent event) {
//        for (Hologram hologram : this.holograms.values()) {
//            if (hologram.isSpawned()) {
//                if (hologram.getLocation().getWorld().getName().equals(event.getTo().getWorld().getName())) {
//                    if (hologram.canSee(event.getPlayer()))
//                        hologram.spawn(Collections.singleton(event.getPlayer()));
//                }
//            }
//        }
//    }

    @EventHandler
    private void handlePlayerWorldChange( PlayerChangedWorldEvent event ) {
        for ( Hologram hologram : this.holograms.values( ) ) {
            if ( hologram.isSpawned( ) ) {
                if ( hologram.getLocation( ).getWorld( ).getName( ).equals( event.getPlayer( ).getWorld( ).getName( ) ) ) {
                    if ( hologram.canSee( event.getPlayer( ) ) )
                        hologram.spawn( Collections.singleton( event.getPlayer( ) ) );
                } else if ( event.getFrom( ).getName( ).equals( hologram.getLocation( ).getWorld( ).getName( ) ) ) {
                    if ( hologram.canSee( event.getPlayer( ) ) )
                        hologram.safeDestroy( event.getPlayer( ) );
                }
            }
        }
    }

    @EventHandler
    private void handleChunkUnload( ChunkUnloadEvent event ) {
        for ( Hologram hologram : this.holograms.values( ) ) {
            if ( hologram.isSpawned( ) ) {
                if ( hologram.getLocation( ).getBlockX( ) >> 4 == event.getChunk( ).getX( ) && hologram.getLocation( ).getBlockZ( ) >> 4 ==
                        event.getChunk( ).getZ( ) ) {
                    for ( Player player : hologram.getViewers( ) )
                        hologram.safeDestroy( player );
                }
            }
        }
    }

    @EventHandler
    private void handleChunkLoad( ChunkLoadEvent event ) {
        for ( Hologram hologram : this.holograms.values( ) ) {
            if ( hologram.isSpawned( ) ) {
                if ( hologram.getLocation( ).getBlockX( ) >> 4 == event.getChunk( ).getX( ) && hologram.getLocation( ).getBlockZ( ) >> 4 ==
                        event.getChunk( ).getZ( ) ) {
                    for ( Player player : event.getWorld( ).getPlayers( ) ) {
                        if ( hologram.canSee( player ) )
                            hologram.spawn( Collections.singleton( player ) );
                    }
                }
            }
        }
    }

    @EventHandler
    private void handlePlayerLeave( PlayerQuitEvent event ) {
        for ( Hologram hologram : this.holograms.values( ) ) {
            if ( hologram.isSpawned( ) ) {
                if ( hologram.getLocation( ).getWorld( ).getName( ).equals( event.getPlayer( ).getWorld( ).getName( ) ) ) {
                    if ( hologram.canSee( event.getPlayer( ) ) )
                        hologram.safeDestroy( event.getPlayer( ) );
                }
            }
        }
    }

    @EventHandler
    private void handlePlayerJoin( PlayerJoinEvent event ) {
        for ( Hologram hologram : this.holograms.values( ) ) {
            if ( hologram.isSpawned( ) ) {
                if ( hologram.getLocation( ).getWorld( ).getName( ).equals( event.getPlayer( ).getWorld( ).getName( ) ) ) {
                    if ( hologram.canSee( event.getPlayer( ) ) )
                        hologram.spawn( Collections.singleton( event.getPlayer( ) ) );
                }
            }
        }
    }

    @EventHandler
    private void handlePlayerDeath( PlayerRespawnEvent event ) {
        for ( Hologram hologram : this.holograms.values( ) ) {
            if ( hologram.isSpawned( ) ) {
                if ( hologram.getLocation( ).getWorld( ).getName( ).equals( event.getPlayer( ).getWorld( ).getName( ) ) ) {
                    if ( hologram.canSee( event.getPlayer( ) ) )
                        hologram.spawn( Collections.singleton( event.getPlayer( ) ) );
                }
            }
        }
    }

    @Override
    public void register( ) {
        super.register( );
    }

    @Override
    public void unregister( ) {
        super.unregister( );
    }

    @Override
    protected boolean isAllowMultipleInstances( ) {
        return false;
    }

}
