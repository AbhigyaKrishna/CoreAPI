package me.Abhigya.core.util.function;

import java.util.function.Predicate;

/**
 * Class for dealing with functions.
 */
public class FunctionUtils {

    /**
     * Returns a predicate that represents the logical negation of the provided
     * predicate.
     * <p>
     *
     * @return Predicate that represents the logical negation of the provided
     * predicate
     */
    public static < T > Predicate< T > negate( Predicate< T > predicate ) {
        return predicate.negate( );
    }

}
