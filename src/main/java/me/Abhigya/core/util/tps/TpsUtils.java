package me.Abhigya.core.util.tps;

import me.Abhigya.core.util.reflection.general.ClassReflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/** Class for getting the current tips per second of the running server. */
public class TpsUtils {

    /**
     * Gets current server ticks per second.
     *
     * <p>
     *
     * @return server current ticks per second.
     */
    public static double getTicksPerSecond() {
        try {
            final Class<?> server_class = ClassReflection.getNmsClass("MinecraftServer", "");
            final Method server_getter = server_class.getMethod("getServer");

            final Object server = server_getter.invoke(null);
            final double[] tps = (double[]) server.getClass().getField("recentTps").get(server);

            return tps[0];
        } catch (NoSuchMethodException
                | SecurityException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException
                | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
        return 0D;
    }

    /**
     * // * Gets the current ticks per seconds of the current running server. // *
     *
     * <p>// * @return current the ticks per seconds. //
     */
    //	public static long getTps() {
    //		final double[]       recent = getRecentTps();
    //		final StringBuilder builder = new StringBuilder();
    //
    //		for ( double tps : recent ) {
    //			builder.append ( format ( tps ) );
    //			builder.append ( ", " );
    //		}
    //
    //		return Long.valueOf ( builder.substring ( 0 , ( builder.length ( ) - 2 ) )
    //				.split ( "," ) [ 0 ].trim ( ) ).longValue ( );
    //	}
    //
    //	protected static double[] getRecentTps() {
    //		try {
    //			return (double[]) FieldReflection.getValue ( ClassReflection.getNmsClass (
    // "MinecraftServer" ) , "recentTps" );
    //		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException |
    // IllegalAccessException
    //				| ClassNotFoundException ex ) {
    //			throw new IllegalStateException ( "couldn't get current server ticks per second!" +
    // ex.getMessage ( ) );
    //		}
    //	}
    //
    //	protected static String format ( double tps ) {
    //		return ((tps > 18.0) ? "" : (tps > 16.0) ? "" : "").toString() + ((tps > 20.0) ? "" : "")
    //				+ Math.min(Math.round(tps * 100.0) / 100.0, 20.0);
    //	}

}
