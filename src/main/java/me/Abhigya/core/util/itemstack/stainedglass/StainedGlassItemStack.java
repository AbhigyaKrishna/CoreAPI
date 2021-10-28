package me.Abhigya.core.util.itemstack.stainedglass;

import com.cryptomorin.xseries.XMaterial;
import me.Abhigya.core.util.itemstack.ItemStackUtils;
import me.Abhigya.core.util.itemstack.custom.CustomItemStack;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.Utility;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Represents the item stacks whose type is stained glass, and allows to change its color easily.
 */
@SuppressWarnings("deprecation")
public final class StainedGlassItemStack extends CustomItemStack {

    public static final StainedGlassColor DEFAULT_COLOR = StainedGlassColor.WHITE;
    public static final Material ITEM_STACK_TYPE = Material.valueOf("STAINED_GLASS");
    public static final Material PANE_ITEM_STACK_TYPE = Material.valueOf("STAINED_GLASS");
    public static final boolean COLOR_SET_BY_SHORT =
            (Material.matchMaterial("PINK_STAINED_GLASS") == null);

    private boolean pane;

    @Utility
    public StainedGlassItemStack() {
        this(DEFAULT_COLOR);
    }

    /**
     * A glass item stack with the specific color.
     *
     * <p>
     *
     * @param color Color that this glass item stack will have
     */
    public StainedGlassItemStack(StainedGlassColor color) {
        this(color, 1);
    }

    /**
     * A glass item stack with the specific color.
     *
     * <p>
     *
     * @param color Color that this glass item stack will have
     * @param amount Stack size
     */
    public StainedGlassItemStack(StainedGlassColor color, int amount) {
        this(color, amount, false);
    }

    /**
     * A glass item stack with the specific color.
     *
     * <p>
     *
     * @param color Color that this glass item stack will have.
     * @param pane If will be a stained glass pane.
     */
    public StainedGlassItemStack(StainedGlassColor color, boolean pane) {
        this(color, 1, pane);
    }

    /**
     * A glass item stack with the specific color.
     *
     * <p>
     *
     * @param color Color that this glass item stack will have
     * @param amount Stack size
     * @param pane If will be a stained glass pane
     */
    public StainedGlassItemStack(StainedGlassColor color, int amount, boolean pane) {
        Validate.notNull(color, "Color cannot be null");

        /* initialize */
        this.pane = pane;
        super.setType(pane ? ITEM_STACK_TYPE : PANE_ITEM_STACK_TYPE);
        this.setAmount(amount);
        this.setColor(color);
    }

    /**
     * Creates a new glass item stack derived from the specified stack.
     *
     * <p>
     *
     * @param stack Stack to copy
     * @throws IllegalArgumentException if the specified stack is null or returns an item meta not
     *     created by the item factory
     */
    public StainedGlassItemStack(final StainedGlassItemStack stack)
            throws IllegalArgumentException {
        Validate.notNull(stack, "Cannot copy null stack");
        this.setAmount(stack.getAmount());
        if (ItemStackUtils.AVAILABLE_DURABILITY_FIELD) {
            super.setDurability(stack.getDurability());
        }

        super.setData(stack.getData());
        if (stack.hasItemMeta()) {
            setItemMeta0(stack.getItemMeta(), pane ? ITEM_STACK_TYPE : PANE_ITEM_STACK_TYPE);
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
     * Sets the color of this glass item stack.
     *
     * <p>
     *
     * @param color New color
     */
    public void setColor(StainedGlassColor color) {
        Validate.notNull(color, "Color cannot be null");
        if (COLOR_SET_BY_SHORT) {
            /* setting color by changing the durability */
            rawSetDurability(color.getShortValue());
        } else {
            /* setting color by changing the item stack type */
            super.setType(
                    XMaterial.valueOf((color.name() + "_STAINED_GLASS" + (pane ? "_PANE" : "")))
                            .parseMaterial());
        }
    }

    /** The durability of the glass item stack cannot be changed manually. */
    private void rawSetDurability(short durability) {
        if (!ItemStackUtils.AVAILABLE_DURABILITY_FIELD && durability == 0) {
            return;
        }
        super.setDurability(durability);
    }

    /** Cannot be overridden, so it's safe for constructor call */
    private void setItemMeta0(ItemMeta itemMeta, Material material) {
        try {
            Method method =
                    this.getClass()
                            .getDeclaredMethod("setItemMeta0", ItemMeta.class, Material.class);
            method.setAccessible(true);
            method.invoke(this, itemMeta, material);
        } catch (NoSuchMethodException
                | SecurityException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
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
    // }

}
