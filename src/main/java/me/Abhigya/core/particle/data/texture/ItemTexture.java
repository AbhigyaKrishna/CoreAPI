package me.Abhigya.core.particle.data.texture;

import me.Abhigya.core.particle.ParticleConstants;
import me.Abhigya.core.particle.PropertyType;
import me.Abhigya.core.particle.data.ParticleData;
import me.Abhigya.core.util.server.Version;
import org.bukkit.inventory.ItemStack;

/**
 * A implementation of the {@link ParticleTexture} object to support item texture particles.
 */
public class ItemTexture extends ParticleTexture {

    /**
     * The {@link ItemStack} that will be displayed by the particle.
     */
    private final ItemStack itemStack;

    /**
     * Initializes a new {@link ParticleData} object.
     * <p>
     *
     * @param itemStack {@link ItemStack} which should be displayed by the
     *                  particle
     */
    public ItemTexture(ItemStack itemStack) {
        super(itemStack == null ? null : itemStack.getType(), (byte) 0);
        this.itemStack = itemStack;
    }

    /**
     * Converts the current {@link ParticleData} instance into nms data. If the current
     * minecraft version was released before 1.13 a int array should be returned. If the
     * version was released after 1.12 a nms ParticleParam has to be returned.
     *
     * @return Nms data
     */
    @Override
    public Object toNMSData() {
        if (getMaterial() == null || getData() < 0 || getEffect() == null || !getEffect().hasProperty(PropertyType.REQUIRES_ITEM))
            return null;
        if (Version.getServerVersion().isOlder(Version.v1_13_R1))
            return super.toNMSData();
        else {
            try {
                return ParticleConstants.PARTICLE_PARAM_ITEM_CONSTRUCTOR.newInstance(getEffect().getNMSObject(), toNMSItemStack(getItemStack()));
            } catch (Exception ex) {
                return null;
            }
        }
    }

    /**
     * Gets the {@link ItemStack} that will be displayed by the particle.
     * <p>
     *
     * @return The assigned {@link ItemStack}
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Gets the NMS ItemStack instance of a CraftItemSTack.
     * <p>
     *
     * @param itemStack CraftItemStack
     * @return ItemStack instance of the specified CraftItemStack or {@code null}
     * if either the given parameter is invalid or an error occurs.
     */
    public static Object toNMSItemStack(ItemStack itemStack) {
        if (itemStack == null)
            return null;
        try {
            return ParticleConstants.CRAFT_ITEM_STACK_AS_NMS_COPY_METHOD.invoke(null, itemStack);
        } catch (Exception ex) {
            return null;
        }
    }

}
