package me.Abhigya.core.util.itemstack.wool;

import me.Abhigya.core.util.itemstack.ItemStackUtils;
import me.Abhigya.core.util.itemstack.custom.CustomItemStack;
import me.Abhigya.core.util.reflection.general.EnumReflection;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.Utility;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Represents the item stacks whose type
 * is wool, and allows the developers to change
 * its color easily.
 */
@SuppressWarnings("deprecation")
public class WoolItemStack extends CustomItemStack {

    public static final WoolColor DEFAULT_COLOR = WoolColor.WHITE;
    public static final Material WOOL_ITEM_STACK_TYPE = Material.valueOf("WOOL");

    @Utility
    public WoolItemStack() {
        this(DEFAULT_COLOR);
    }

    /**
     * A wool item stack with the specific color.
     * <p>
     *
     * @param color Color that this wool item stack will have
     */
    public WoolItemStack(WoolColor color) {
        this(color, 1);
    }

    /**
     * A wool item stack with the specific color.
     * <p>
     *
     * @param color  Color that this wool item stack will have
     * @param amount Stack size
     */
    public WoolItemStack(WoolColor color, int amount) {
        Validate.notNull(color, "Color cannot be null");

        /* initialize */
        super.setType(WOOL_ITEM_STACK_TYPE);
        this.setAmount(amount);
        this.setColor(color);
    }

    /**
     * Creates a new wool item stack derived from the specified stack.
     * <p>
     *
     * @param stack Stack to copy
     * @throws IllegalArgumentException if the specified stack is null or
     *                                  returns an item meta not created by the item factory
     */
    public WoolItemStack(final WoolItemStack stack) throws IllegalArgumentException {
        Validate.notNull(stack, "Cannot copy null stack");
        this.setAmount(stack.getAmount());
        if (ItemStackUtils.AVAILABLE_DURABILITY_FIELD) {
            super.setDurability(stack.getDurability());
        }

        super.setData(stack.getData());
        if (stack.hasItemMeta()) {
            setItemMeta0(stack.getItemMeta(), WOOL_ITEM_STACK_TYPE);
        }
    }

    @Override
    public void setType(Material material) {
        /* ignore */
    }

    @Override
    public void setData(MaterialData data) {
        /* ignore */
    }

    @Override
    public void setDurability(short durability) {
        /* ignore */
    }

    /**
     * Sets the color of this wool item stack.
     * <p>
     *
     * @param color New color
     */
    public void setColor(WoolColor color) {
        Validate.notNull(color, "Color cannot be null");

        Material wool_material = EnumReflection.getEnumConstant(Material.class, color.name() + "_WOOL");
        if (wool_material == null) {
            // we are setting color by changing the durability-
            rawSetDurability(color.getShortValue());
        } else {
            // we are setting the color by changing the type.
            super.setType(wool_material);
        }
    }

    /**
     * The durability of the wool item stack
     * cannot be changed manually.
     */
    private void rawSetDurability(short durability) {
        if (ItemStackUtils.AVAILABLE_DURABILITY_FIELD) {
            super.setDurability(durability);
        }
    }

    /**
     * Cannot be overridden, so it's safe for constructor call
     */
    private void setItemMeta0(ItemMeta itemMeta, Material material) {
        try {
            Method method = this.getClass().getDeclaredMethod("setItemMeta0", ItemMeta.class, Material.class);
            method.setAccessible(true);
            method.invoke(this, itemMeta, material);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

//	private void createData(final byte data) {
//	try {
//		Method method = this.getClass().getDeclaredMethod("createData", byte.class);
//		method.setAccessible(true); method.invoke(this, data);
//	} catch (NoSuchMethodException | SecurityException | IllegalAccessException
//			| IllegalArgumentException | InvocationTargetException e) {
//		e.printStackTrace();
//	}
//}

}
