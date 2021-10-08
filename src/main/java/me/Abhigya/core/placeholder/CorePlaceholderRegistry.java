package me.Abhigya.core.placeholder;

import java.util.HashMap;
import java.util.Map;

public class CorePlaceholderRegistry implements PlaceholderRegistry< CorePlaceholderRegistry > {

    private final Map< String, Placeholder > placeholders;
    private PlaceholderRegistry< ? > parent;

    public CorePlaceholderRegistry( Map< String, Placeholder > handle ) {
        this.placeholders = handle;
        this.parent = PlaceholderUtil.getRegistry( );
    }

    public CorePlaceholderRegistry( ) {
        this( new HashMap<>( ) );
    }

    public CorePlaceholderRegistry( PlaceholderRegistry< ? > parent ) {
        this( new HashMap<>( ) );
        this.parent = parent;
    }

    @Override
    public Placeholder getLocal( String key ) {
        return placeholders.get( key );
    }

    @Override
    public Placeholder get( String key ) {
        Placeholder p = placeholders.get( key );
        return p != null ? p : parent != null ? parent.get( key ) : null;
    }

    @Override
    public boolean hasLocal( String key ) {
        return placeholders.containsKey( key );
    }

    @Override
    public boolean has( String key ) {
        return placeholders.containsKey( key ) || ( parent != null && parent.has( key ) );
    }

    @Override
    public CorePlaceholderRegistry set( Placeholder placeholder ) {
        placeholders.put( placeholder.getId( ), placeholder );
        return this;
    }

    @Override
    public PlaceholderRegistry< ? > getParent( ) {
        return parent;
    }

    @Override
    public void setParent( PlaceholderRegistry< ? > parent ) {
        this.parent = parent;
    }

}
