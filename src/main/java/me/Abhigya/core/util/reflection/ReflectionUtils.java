package me.Abhigya.core.util.reflection;

import me.Abhigya.core.util.reflection.bukkit.BukkitReflection;
import me.Abhigya.core.util.reflection.bukkit.EntityReflection;
import me.Abhigya.core.util.reflection.bukkit.PlayerReflection;
import me.Abhigya.core.util.reflection.general.*;

/**
 * Class for dealing with reflecting
 * <p>
 * This only contains short-cuts to the different classes for dealing with the
 * different reflections.
 */
public class ReflectionUtils {

    /**
     * Classes reflection
     */
    public static final class Classes extends ClassReflection {
    }

    /**
     * Constructors reflection
     */
    public static final class Constructors extends ConstructorReflection {
    }

    /**
     * Fields reflection
     */
    public static final class Fields extends FieldReflection {
    }

    /**
     * Methods reflection
     */
    public static final class Methods extends MethodReflection {
    }

    /**
     * Enumerations reflection
     */
    public static final class Enumerations extends EnumReflection {
    }

    /**
     * Bukkit reflection
     */
    public static final class Bukkit extends BukkitReflection {
    }

    /**
     * Entities reflection
     */
    public static final class Entities extends EntityReflection {
    }

    /**
     * Players reflection
     */
    public static final class Player extends PlayerReflection {
    }

}
