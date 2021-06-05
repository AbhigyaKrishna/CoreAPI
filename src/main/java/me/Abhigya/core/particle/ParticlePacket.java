package me.Abhigya.core.particle;

import me.Abhigya.core.particle.data.ParticleData;
import me.Abhigya.core.particle.data.color.NoteColor;
import me.Abhigya.core.particle.data.color.ParticleColor;
import me.Abhigya.core.particle.data.color.RegularColor;
import me.Abhigya.core.particle.data.texture.BlockTexture;
import me.Abhigya.core.particle.data.texture.ItemTexture;
import me.Abhigya.core.util.server.Version;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

import static me.Abhigya.core.particle.ParticleConstants.PACKET_PLAY_OUT_WORLD_PARTICLES_CONSTRUCTOR;
import static me.Abhigya.core.particle.ParticleEffect.NOTE;
import static me.Abhigya.core.particle.ParticleEffect.REDSTONE;

/**
 * Represents the nms "PacketPlayOutWorldParticles" packet.
 */
public class ParticlePacket {

    /**
     * The {@link ParticleEffect} which should be displayed by the client.
     */
    private final ParticleEffect particle;
    /**
     * This field has three uses:
     * <p>
     * The offsetX defines in which x oriented range the particles can
     * spawn.
     * <p>
     * It represents the x velocity a particle with the
     * {@link PropertyType#DIRECTIONAL} property should have.
     * <p>
     * It sets the red value of a {@link PropertyType#COLORABLE}
     * particle. However, since 1.13 a ParticleParam has to be used to set
     * the colors of redstone.
     */
    private final float offsetX;
    /**
     * This field has three uses:
     * <p>
     * The offsetY defines in which y oriented range the particles can
     * spawn.
     * <p>
     * It represents the y velocity a particle with the
     * {@link PropertyType#DIRECTIONAL}  property should have.
     * <p>
     * It sets the green value of a {@link PropertyType#COLORABLE}
     * particle. However, since 1.13 a  ParticleParam has to be used to set
     * the colors of redstone.
     */
    private final float offsetY;
    /**
     * This field has three uses:
     * <p>
     * The offsetZ defines in which z oriented range the particles can
     * spawn.
     * <p>
     * It represents the z velocity a  particle with the
     * {@link PropertyType#DIRECTIONAL}  property should have.
     * <p>
     * It sets the blue value of a {@link PropertyType#COLORABLE}
     * particle. However, since 1.13 a ParticleParam has to be used to set
     * the colors of redstone.
     */
    private final float offsetZ;
    /**
     * Normally this field is used to multiply the velocity of a
     * particle by the given speed. There  are however some special cases
     * where this value is used for something different. (e.g. {@link ParticleEffect#NOTE}).
     */
    private final float speed;
    /**
     * The amount of particles that should be spawned. For the extra data defined
     * in offsetX, offsetY and offsetZ to work the amount has to be set to {@code 0}.
     */
    private final int amount;
    /**
     * The data of the particle which should be displayed. This data contains additional
     * information the client needs to display  the particle correctly.
     */
    private final ParticleData particleData;

    /**
     * Creates a new {@link ParticlePacket} that can be sent to one or multiple
     * {@link Player players}.
     * <p>
     *
     * @param particle     {@link ParticleEffect} that should be sent.
     * @param offsetX      OffsetX or extra data the particle should have.
     * @param offsetY      OffsetY or extra data the particle should have.
     * @param offsetZ      OffsetZ or extra data the particle should have.
     * @param speed        Multiplier of the velocity.
     * @param amount       Amount of particles that should be spawned.
     * @param particleData {@link ParticleData} of the particle
     * @see #particle
     * @see #offsetX
     * @see #offsetY
     * @see #offsetZ
     * @see #speed
     * @see #amount
     * @see #particleData
     * @see ParticleData
     */
    public ParticlePacket(ParticleEffect particle, float offsetX, float offsetY, float offsetZ, float speed, int amount, ParticleData particleData) {
        this.particle = particle;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.amount = amount;
        this.particleData = particleData;
    }

    /**
     * Creates a new {@link ParticlePacket} that can be sent to one or multiple
     * {@link Player players}.
     * <p>
     *
     * @param particle {@link ParticleEffect} that should be sent.
     * @param offsetX  OffsetX or extra data the particle should have.
     * @param offsetY  OffsetY or extra data the particle should have.
     * @param offsetZ  OffsetZ or extra data the particle should have.
     * @param speed    Multiplier of the velocity.
     * @param amount   Amount of particles that should be spawned.
     * @see #particle
     * @see #offsetX
     * @see #offsetY
     * @see #offsetZ
     * @see #speed
     * @see #amount
     */
    public ParticlePacket(ParticleEffect particle, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
        this.particle = particle;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.amount = amount;
        this.particleData = null;
    }

    /**
     * Gets the {@link ParticleEffect} that will be displayed by the client.
     * <p>
     *
     * @return The {@link ParticleEffect} which should be displayed by the client
     */
    public ParticleEffect getParticle() {
        return particle;
    }

    /**
     * Gets the offsetX value of the particle.
     * <p>
     *
     * @return OffsetX value.
     */
    public float getOffsetX() {
        return offsetX;
    }

    /**
     * Gets the offsetY value of the particle.
     * <p>
     *
     * @return OffsetY value
     */
    public float getOffsetY() {
        return offsetY;
    }

    /**
     * Gets the offsetZ value of the particle.
     * <p>
     *
     * @return OffsetZ value
     */
    public float getOffsetZ() {
        return offsetZ;
    }

    /**
     * Gets the speed at which the particle will fly off.
     * <p>
     *
     * @return Speed of the particle
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Gets how many particles will be shown by the client.
     * <p>
     *
     * @return Amount of particles to be spawned
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Gets the {@link ParticleData} that should be used when displaying the
     * particle.
     * <p>
     *
     * @return {@link ParticleData} that will be used
     */
    public ParticleData getParticleData() {
        return particleData;
    }

    /**
     * Creates a NMS PacketPlayOutWorldParticles packet with the data in the current
     * {@link ParticlePacket} data.
     * <p>
     *
     * @param location {@link Location} the particle should be displayed at
     * @return PacketPlayOutWorldParticles or {@code null} when something goes wrong
     */
    public Object createPacket(Location location) {
        try {
            ParticleEffect effect = getParticle();
            ParticleData data = getParticleData();
            if (effect == null || effect.getFieldName().equals("NONE"))
                return null;
            if (data != null) {
                if (data.getEffect() != effect)
                    return null;
                if ((data instanceof BlockTexture && effect.hasProperty(PropertyType.REQUIRES_BLOCK))
                        || (data instanceof ItemTexture && effect.hasProperty(PropertyType.REQUIRES_ITEM)))
                    return createTexturedParticlePacket(location);
                if (data instanceof ParticleColor && effect.hasProperty(PropertyType.COLORABLE))
                    return createColoredParticlePacket(location);
                else
                    return null;
            } else if (!effect.hasProperty(PropertyType.REQUIRES_BLOCK) && !effect.hasProperty(PropertyType.REQUIRES_ITEM)) {
                return createPacket(effect.getNMSObject(), (float) location.getX(), (float) location.getY(), (float) location.getZ(), getOffsetX(), getOffsetY(), getOffsetZ(), getSpeed(), getAmount(), new int[0]);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Creates a new packet for particles that support custom textures.
     * <p>
     * <b>Note: This method does not check if the given particle and
     * data match!</b>
     * <p>
     *
     * @param location {@link Location} the particle should be displayed at
     * @return PacketPlayOutWorldParticles or {@code null} when something goes wrong
     * @see PropertyType#REQUIRES_BLOCK
     * @see PropertyType#REQUIRES_ITEM
     */
    private Object createTexturedParticlePacket(Location location) {
        ParticleEffect effect = getParticle();
        ParticleData data = getParticleData();
        return createPacket(Version.getServerVersion().isOlder(Version.v1_13_R1) ? effect.getNMSObject() : data.toNMSData(),
                (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                getOffsetX(), getOffsetY(), getOffsetZ(),
                getSpeed(), getAmount(), Version.getServerVersion().isOlder(Version.v1_13_R1) ? (int[]) data.toNMSData() : new int[0]
        );
    }

    /**
     * Creates a new packet for particles that support custom colors.
     * <p>
     * <b>Note: This method does not check if the given particle and
     * data match!</b>
     * <p>
     *
     * @param location {@link Location} the particle should be displayed at
     * @return PacketPlayOutWorldParticles or {@code null} when something goes wrong
     * @see PropertyType#COLORABLE
     */
    private Object createColoredParticlePacket(Location location) {
        ParticleEffect effect = getParticle();
        ParticleData data = getParticleData();
        if (data instanceof NoteColor && effect.equals(NOTE)) {
            return createPacket(effect.getNMSObject(),
                    (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                    ((NoteColor) data).getRed(), 0f, 0f,
                    getSpeed(), getAmount(), new int[0]
            );
        } else if (data instanceof RegularColor) {
            RegularColor color = ((RegularColor) data);
            if (Version.getServerVersion().isOlder(Version.v1_13_R1) || !effect.equals(REDSTONE)) {
                return createPacket(effect.getNMSObject(),
                        (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                        (effect.equals(REDSTONE) && color.getRed() == 0 ? Float.MIN_NORMAL : color.getRed()), color.getGreen(), color.getBlue(),
                        1f, 0, new int[0]
                );
            } else {
                return createPacket(data.toNMSData(),
                        (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                        getOffsetX(), getOffsetY(), getOffsetZ(),
                        getSpeed(), getAmount(), new int[0]
                );
            }
        } else return null;
    }

    /**
     * Creates a new PacketPlayOutWorldParticles
     * object with the given data.
     * <p>
     *
     * @param param     ParticleParam of the  packet
     * @param locationX X coordinate of the location the particle
     *                  should be displayed at
     * @param locationY Y coordinate of the location the particle
     *                  should be displayed at
     * @param locationZ Z coordinate of the location the particle
     *                  should be displayed at
     * @param offsetX   Offset x value of the packet
     * @param offsetY   Offset y value of the packet
     * @param offsetZ   Offset z value of the packet
     * @param speed     Speed of the particle
     * @param amount    Amount of particles
     * @param data      Extra data for the particle
     * @return A PacketPlayOutWorldParticles instance with the given data or {@code null} if an error occurs.
     */
    private Object createPacket(Object param, float locationX, float locationY, float locationZ, float offsetX, float offsetY, float offsetZ, float speed, int amount, int[] data) {
        Constructor packetConstructor = PACKET_PLAY_OUT_WORLD_PARTICLES_CONSTRUCTOR;
        try {
            if (Version.getServerVersion().isOlder(Version.v1_13_R1))
                return packetConstructor.newInstance(param, true, locationX, locationY, locationZ, offsetX, offsetY, offsetZ, speed, amount, data);
            if (Version.getServerVersion().isOlder(Version.v1_15_R1))
                return packetConstructor.newInstance(param, true, locationX, locationY, locationZ, offsetX, offsetY, offsetZ, speed, amount);
            return packetConstructor.newInstance(param, true, (double) locationX, (double) locationY, (double) locationZ, offsetX, offsetY, offsetZ, speed, amount);
        } catch (Exception ex) {
            return null;
        }
    }

}
