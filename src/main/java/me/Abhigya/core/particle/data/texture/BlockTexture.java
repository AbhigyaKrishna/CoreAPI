package me.Abhigya.core.particle.data.texture;

import me.Abhigya.core.particle.ParticleConstants;
import me.Abhigya.core.particle.PropertyType;
import me.Abhigya.core.particle.data.ParticleData;
import me.Abhigya.core.util.reflection.general.FieldReflection;
import me.Abhigya.core.util.server.Version;
import org.bukkit.Material;

import java.lang.reflect.Field;

/**
 * A implementation of the {@link ParticleTexture} object to support block texture particles.
 */
public class BlockTexture extends ParticleTexture {

    /**
     * Initializes a new {@link ParticleData} object.
     * <p>
     *
     * @param material {@link Material} the particle should display
     */
    public BlockTexture(Material material) {
        super(material, (byte) 0);
    }

    /**
     * Initializes a new {@link ParticleData} Object.
     * <p>
     *
     * @param material {@link Material} the particle should display
     * @param data     Damage value that should influence the texture
     */
    public BlockTexture(Material material, byte data) {
        super(material, data);
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
        if (getMaterial() == null || !getMaterial().isBlock() || getEffect() == null || !getEffect().hasProperty(PropertyType.REQUIRES_BLOCK))
            return null;
        if (Version.getServerVersion().isOlder(Version.v1_13_R1))
            return super.toNMSData();
        Object block = getBlockData(getMaterial());
        if (block == null)
            return null;
        try {
            return ParticleConstants.PARTICLE_PARAM_BLOCK_CONSTRUCTOR.newInstance(getEffect().getNMSObject(), block);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Gets the nms block data of the given bukkit {@link Material}.
     * <p>
     *
     * @param material {@link Material} whose data should be getted
     * @return Block data of the specified {@link Material} or {@code null} when an error occurs.
     */
    public Object getBlockData(Material material) {
        try {
            Field blockField = FieldReflection.get(ParticleConstants.BLOCKS_CLASS, material.name(), false);
            if (blockField == null)
                return null;
            Object block = FieldReflection.getValue(blockField, blockField.getName());
            return ParticleConstants.BLOCK_GET_BLOCK_DATA_METHOD.invoke(block);
        } catch (Exception ex) {
            return null;
        }
    }

}
