package me.Abhigya.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Represents an argument of a command handled by a {@link CommandHandler}.
 */
public interface CommandArgument {

    /**
     * Gets the name which identifies this argument.
     * <p>
     *
     * @return Argument's name
     */
    public String getName();

    /**
     * Gets the usage of this argument.
     * <p>
     *
     * @return Argument's usage
     */
    public String getUsage();

    /**
     * Executes this argument. Note that the {@code subargs} array might exclude the
     * name of this argument.
     * <p>
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param subargs Passed command arguments, excluding this
     * @return true if the argument was successful, otherwise false
     */
    public boolean execute(CommandSender sender, Command command, String label, String[] subargs);

    /**
     * Executes this argument. Note that the {@code subargs} array might exclude the
     * name of this argument.
     * <p>
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param alias   Alias of the command which was used
     * @param subargs Passed command arguments, excluding this, including final
     *                partial argument to be completed and command label.
     * @return List of possible completions for the final argument, or null to
     * default to the command executor.
     */
    public List<String> tab(CommandSender sender, Command command, String alias, String[] subargs);

}
