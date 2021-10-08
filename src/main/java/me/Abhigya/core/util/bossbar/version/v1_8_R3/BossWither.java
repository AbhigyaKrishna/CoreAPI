package me.Abhigya.core.util.bossbar.version.v1_8_R3;

import me.Abhigya.core.util.server.Version;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftWither;

import java.util.Random;

/**
 * Class for handling {@link Version#v1_8_R3} wither entity
 */
public class BossWither extends EntityWither {

    /* type registering */
    static {
        BossTypeRegistering.register( BossWither.class, "BossWither", 64 );
    }

    protected final org.bukkit.World bukkit_world;

    protected BossWither( org.bukkit.World world ) {
        // we reduced the visibility of this construtor
        // so to create an instance it is required to
        // use a static method which triggers the base
        // static block and register this entity type
        super( ( (CraftWorld) world ).getHandle( ) );

        this.bukkit_world = world;
        this.bukkitEntity = new CraftWither( this.world.getServer( ), this );

        // move controller
        this.moveController = new BossWitherControllerMove( this );
        // navigation
        this.navigation = new BossWitherNavigation( this, this.world );

        // goal/target selector
        // we're re-initializing in order to clear it
        this.goalSelector = new PathfinderGoalSelector( this.world.methodProfiler );
        this.targetSelector = new PathfinderGoalSelector( this.world.methodProfiler );

        // injecting random
        this.random = new Random( ) {
            private static final long serialVersionUID = 6970368193757980528L;

            @Override
            public synchronized double nextGaussian( ) {
                return Double.MIN_VALUE;
            }

            @Override
            public float nextFloat( ) {
                return Float.MIN_VALUE;
            }
        };
    }

    public static BossWither getNewInstance( org.bukkit.World world ) {
        return new BossWither( world );
    }

    @Override
    public void collide( Entity entity ) {
        // no collisions
    }

    @Override
    public boolean cm( ) {
        return false;
    }

    @Override
    public int cl( ) {
        return 0;
    }

}
