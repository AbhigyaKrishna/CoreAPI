package me.Abhigya.core.util.itemstack.safe;

import me.Abhigya.core.util.itemstack.custom.CustomItemStack;
import me.Abhigya.core.util.material.MaterialUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * A {@link ItemStack} that is safe because of uses
 * the method {@link MaterialUtils#getRightMaterial(Material)}.
 */
public class SafeItemStack extends CustomItemStack {

    /**
     * Construct new {@link SafeItemStack}.
     * <p>
     *
     * @param material Type.
     */
    public SafeItemStack(Material material) {
        this(material, 1);
    }

    /**
     * Construct new {@link SafeItemStack}.
     * <p>
     *
     * @param material Type.
     * @param amount   Stack size.
     */
    public SafeItemStack(Material material, int amount) {
        super.setType(MaterialUtils.getRightMaterial(material));
        this.setAmount(amount);
    }

}
