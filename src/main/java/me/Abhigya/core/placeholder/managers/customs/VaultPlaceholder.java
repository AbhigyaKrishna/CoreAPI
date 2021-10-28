package me.Abhigya.core.placeholder.managers.customs;

import me.Abhigya.core.economy.Balance;
import me.Abhigya.core.economy.EconomyManager;
import me.Abhigya.core.placeholder.Placeholder;
import org.bukkit.entity.Player;

public class VaultPlaceholder implements Placeholder {

    @Override
    public String getId() {
        return "vault";
    }

    @Override
    public String resolve(Player player, String id) {
        Balance economy = EconomyManager.get(player);
        double balance = economy == null ? 0 : economy.get();

        switch (id) {
            case "eco_balance":
                return String.valueOf(balance);
            case "eco_balance_fixed":
                return String.valueOf((long) balance);
        }

        return null;
    }
}
