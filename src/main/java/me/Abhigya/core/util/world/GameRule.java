package me.Abhigya.core.util.world;

import me.Abhigya.core.util.Validable;
import org.apache.commons.lang.Validate;
import org.bukkit.World;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GameRule implements Validable {

    protected final GameRuleType type;
    protected final Object value;
    protected final Set< GameRule > parents;

    /**
     * Construct the {@link GameRule}.
     * <p>
     *
     * @param type    Game rule type
     * @param value   Game rule value
     * @param parents Required {@link GameRule GameRules} to work
     */
    public GameRule( GameRuleType type, Object value, GameRule... parents ) {
        Validate.notNull( type, "yype cannot be null!" );
        Validate.notNull( value, "value cannot be null!" );
        Validate.isTrue( type.isSameDataType( value ), "the specified type and value are incompatible!" );

        this.type = type;
        this.value = value;
        this.parents = new HashSet<>( );
        this.parents.addAll( Arrays.asList( parents )
                .stream( )
                .filter( GameRule::isValid )
                .collect( Collectors.toSet( ) ) );
    }

    /**
     * Gets the game rule type.
     * <p>
     *
     * @return Game rule type
     */
    public GameRuleType getType( ) {
        return type;
    }

    /**
     * Gets the game rule value.
     * <p>
     *
     * @return Game rule value
     */
    public Object getValue( ) {
        return value;
    }

    /**
     * Gets the {@link GameRule GameRules} this requires to work.
     * <p>
     *
     * @return Required game rules
     */
    public Set< GameRule > getParents( ) {
        return parents;
    }

    /**
     * Applies this rule the given world.
     * <p>
     *
     * @param world World to apply
     * @return Same world, useful for chaining
     */
    public World apply( World world ) {
        this.getType( ).apply( world, value );
        this.parents.forEach( parent -> parent.getType( ).apply( world, parent.getValue( ) ) );
        return world;
    }

    @Override
    public boolean isValid( ) {
        return getType( ) != null && getValue( ) != null && getType( ).isSameDataType( getValue( ) );
    }

    @Override
    public boolean isInvalid( ) {
        return !isValid( );
    }

}
