package me.Abhigya.core.util.configurable;

import me.Abhigya.core.util.loadable.Loadable;
import me.Abhigya.core.util.saveable.Saveable;

/**
 * Simple interface that represents Objects that can be loaded by {@link Loadable} interface, and
 * saved by {@link Saveable} interface.
 */
public interface Configurable extends Loadable, Saveable {}
