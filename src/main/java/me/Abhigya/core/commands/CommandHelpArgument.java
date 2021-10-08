package me.Abhigya.core.commands;

/**
 * An implementation of {@link CommandArgument} intended for sending help messages.
 */
public abstract class CommandHelpArgument implements CommandArgument {

    @Override
    public final String getName( ) {
        return "help";
    }

}
