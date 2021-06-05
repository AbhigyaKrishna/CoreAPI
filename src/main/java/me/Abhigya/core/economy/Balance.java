package me.Abhigya.core.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;

/**
 * Class for managing player balance
 */
public class Balance {

    private final Economy economy;
    private final OfflinePlayer player;

    /**
     * Constructs the Balance class.
     * <p>
     *
     * @param economy {@code Vault} {@link Economy}
     * @param player  Player
     */
    public Balance(Economy economy, OfflinePlayer player) {
        this.economy = economy;
        this.player = player;
    }

    /**
     * Constructs the Balance class.
     * <p>
     *
     * @param player Player
     */
    public Balance(OfflinePlayer player) {
        this(EconomyManager.getEconomy(), player);
        if (economy == null)
            throw new IllegalStateException("Cannot find economy, vault not found (?)");
    }

    /**
     * Gets the balance of player.
     * <p>
     *
     * @return Balance of player
     */
    public double get() {
        return economy.getBalance(player);
    }

    /**
     * Get the formatted value of balance.
     * <p>
     *
     * @return Formatted value of player balance
     */
    public String getFormatted() {
        return economy.format(get());
    }

    /**
     * Add balance to player
     * <p>
     *
     * @param money Amount to add
     */
    public void give(double money) {
        economy.depositPlayer(player, money);
    }

    /**
     * Subtract balance from player
     * <p>
     *
     * @param money Amount to subtract
     * @return Whether the transaction was successful
     */
    public boolean take(double money) {
        return economy.withdrawPlayer(player, money).transactionSuccess();
    }

    /**
     * Checks if the player account has the amount.
     * <p>
     * <h2><strong>Note:</strong></h2>
     * <strong>DO NOT USE NEGATIVE AMOUNTS</strong>
     * <p>
     *
     * @param money Amount to check
     * @return {@code true} if the player has the given amount, false otherwise
     */
    public boolean has(double money) {
        return economy.has(player, money);
    }

}
