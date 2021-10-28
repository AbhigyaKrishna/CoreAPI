package me.Abhigya.core.util.npc;

import com.google.common.base.Preconditions;
import io.github.retrooper.packetevents.utils.gameprofile.GameProfileUtil;
import io.github.retrooper.packetevents.utils.gameprofile.WrappedGameProfile;
import io.github.retrooper.packetevents.utils.player.Skin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/** A wrapper for a game profile which can be completed. */
public class Profile implements Cloneable {

    private String name;
    private UUID uniqueId;
    private Skin skin;

    /**
     * Creates a new profile.
     *
     * @param uniqueId The unique id of the profile.
     */
    public Profile(@NotNull UUID uniqueId) {
        this(uniqueId, null);
    }

    /**
     * Creates a new profile.
     *
     * @param uniqueId The unique id of the profile.
     * @param skin The properties of the profile.
     */
    public Profile(@NotNull UUID uniqueId, Skin skin) {
        this(uniqueId, null, skin);
    }

    /**
     * Creates a new profile.
     *
     * @param name The name of the profile.
     */
    public Profile(@NotNull String name) {
        this(name, null);
    }

    /**
     * Creates a new profile.
     *
     * @param name The name of the profile.
     * @param skin The properties of the profile.
     */
    public Profile(@NotNull String name, Skin skin) {
        this(null, name, skin);
    }

    /**
     * Creates a new profile. Either {@code uniqueId} or {@code name} must be non-null.
     *
     * @param uniqueId The unique id of the profile.
     * @param name The name of the profile.
     * @param skin The properties of the profile.
     */
    public Profile(UUID uniqueId, String name, Skin skin) {
        Preconditions.checkArgument(
                name != null || uniqueId != null, "Either name or uniqueId must be given!");

        this.uniqueId = uniqueId;
        this.name = name;
        this.skin = skin;
    }

    /**
     * Checks if this profile is complete. Complete does not mean, that the profile has textures.
     *
     * @return if this profile is complete (has unique id and name)
     */
    public boolean isComplete() {
        return this.uniqueId != null && this.name != null;
    }

    /**
     * Checks if this profile has properties.
     *
     * @return if this profile has properties
     */
    public boolean hasSkin() {
        return this.skin != null;
    }

    /**
     * Checks if this profile has a unique id.
     *
     * @return if this profile has a unique id.
     */
    public boolean hasUniqueId() {
        return this.uniqueId != null;
    }

    /**
     * Get the unique id of this profile. May be null when this profile was created using a name and
     * is not complete. Is never null when {@link #hasUniqueId()} is {@code true}.
     *
     * @return the unique id of this profile.
     */
    public UUID getUniqueId() {
        return this.uniqueId;
    }

    /**
     * Sets the unique of this profile. To re-request the profile textures/uuid of this profile,
     * make sure the properties are clear.
     *
     * @param uniqueId the new unique of this profile.
     * @return the same profile instance, for chaining.
     */
    public Profile setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }

    /**
     * Check if this profile has a name.
     *
     * @return if this profile has a name.
     * @since 2.5-SNAPSHOT
     */
    public boolean hasName() {
        return this.name != null;
    }

    /**
     * Get the name of this profile. May be null when this profile was created using a unique id and
     * is not complete. Is never null when {@link #hasName()} ()} is {@code true}.
     *
     * @return the name of this profile.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of this profile. To re-request the profile textures/uuid of this profile, make
     * sure the properties are clear.
     *
     * @param name the new name of this profile.
     * @return the same profile instance, for chaining.
     */
    @NotNull
    public Profile setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets the properties of this profile.
     *
     * @return the properties of this profile.
     */
    @NotNull
    public Skin getSkin() {
        return this.skin;
    }

    /**
     * Sets the properties of this profile.
     *
     * @param skin The new properties of this profile.
     */
    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    /**
     * Converts this profile to a protocol lib wrapper. This method requires protocol lib a
     * dependency of your project and is not the point of this class. It will be removed in a
     * further release.
     *
     * @return this profile as a protocol lib wrapper.
     * @deprecated No longer supported for public use, convert it yourself when needed.
     */
    @NotNull
    @Deprecated
    public WrappedGameProfile asWrapped() {
        return this.asWrapped(true);
    }

    /**
     * Converts this profile to a protocol lib wrapper. This method requires protocol lib a
     * dependency of your project and is not the point of this class. It will be removed in a
     * further release.
     *
     * @param withProperties If the properties of this wrapper should get copied.
     * @return this profile as a protocol lib wrapper.
     */
    @NotNull
    public WrappedGameProfile asWrapped(boolean withProperties) {
        Object profile = GameProfileUtil.getGameProfile(this.getUniqueId(), this.getName());

        if (withProperties) {
            GameProfileUtil.setGameProfileSkin(profile, this.skin);
        }

        return GameProfileUtil.getWrappedGameProfile(profile);
    }

    /**
     * Creates a clone of this profile.
     *
     * @return the cloned profile.
     */
    @Override
    public Profile clone() {
        try {
            return (Profile) super.clone();
        } catch (CloneNotSupportedException exception) {
            return new Profile(this.uniqueId, this.name, this.skin);
        }
    }
}
