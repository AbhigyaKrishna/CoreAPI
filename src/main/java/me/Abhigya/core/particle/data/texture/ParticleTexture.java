package me.Abhigya.core.particle.data.texture;

import me.Abhigya.core.particle.data.ParticleData;
import org.bukkit.Material;

/**
 * A implementation of {@link ParticleData} to support particles that require a texture
 * to function properly.
 */
public class ParticleTexture extends ParticleData {

    /**
     * The {@link Material} that should be displayed by the particle.
     */
    private final Material material;
    /**
     * The damage data to be displayed by the given texture.
     */
    private final byte data;

    /**
     * Initializes a new {@link ParticleData} object.
     * <p>
     *
     * @param material {@link Material} the particle should display
     * @param data     Damage value that should influence the texture
     */
    ParticleTexture(Material material, byte data) {
        this.material = material;
        this.data = data;
    }

    /**
     * Gets the {@link Material} that will be displayed b the particle.
     * <p>
     *
     * @return {@link Material} the current data is assigned to
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Gets the damage value that will be displayed by the client.
     * <p>
     *
     * @return Damage value of the current texture
     */
    public byte getData() {
        return data;
    }

    /**
     * Converts the current {@link ParticleData} instance into nms data. If the current
     * minecraft version was released before 1.13 a int array should be returned. If the
     * version was released after 1.12 a nms "ParticleParam" has to be returned.
     * <p>
     *
     * @return Nms data
     */
    @Override
    public Object toNMSData() {
        return new int[]{getMaterial().ordinal(), getData()};
    }

}
