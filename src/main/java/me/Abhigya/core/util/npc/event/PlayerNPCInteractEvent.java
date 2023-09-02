package me.Abhigya.core.util.npc.event;

import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;
import me.Abhigya.core.util.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/** An event called when a player interacts with a npc. */
public class PlayerNPCInteractEvent extends PlayerNPCEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    /** The action type of the interact. */
    private final EntityUseAction action;

    /** The player hand used for the interact. */
    private final Hand hand;

    /**
     * Constructs a new event instance.
     *
     * @param who The player who interacted with the npc.
     * @param npc The npc with whom the player has interacted.
     * @param action The action type of the interact.
     */
    public PlayerNPCInteractEvent(
            @NotNull Player who,
            @NotNull NPC npc,
            @NotNull WrappedPacketInUseEntity.EntityUseAction action) {
        this(who, npc, EntityUseAction.fromHandle(action), Hand.MAIN_HAND);
    }

    /**
     * Constructs a new event instance.
     *
     * @param who The player who interacted with the npc.
     * @param npc The npc with whom the player has interacted.
     * @param action The action type of the interact.
     * @param hand The player hand used for the interact.
     */
    public PlayerNPCInteractEvent(
            @NotNull Player who,
            @NotNull NPC npc,
            @NotNull WrappedPacketInUseEntity.EntityUseAction action,
            @NotNull io.github.retrooper.packetevents.utils.player.Hand hand) {
        this(who, npc, EntityUseAction.fromHandle(action), Hand.fromHandle(hand));
    }

    /**
     * Constructs a new event instance.
     *
     * @param who The player who interacted with the npc.
     * @param npc The npc with whom the player has interacted.
     * @param action The action type of the interact.
     */
    public PlayerNPCInteractEvent(
            @NotNull Player who, @NotNull NPC npc, @NotNull EntityUseAction action) {
        this(who, npc, action, Hand.MAIN_HAND);
    }

    /**
     * Constructs a new event instance.
     *
     * @param who The player who interacted with the npc.
     * @param npc The npc with whom the player has interacted.
     * @param action The action type of the interact.
     * @param hand The player hand used for the interact.
     */
    public PlayerNPCInteractEvent(
            @NotNull Player who,
            @NotNull NPC npc,
            @NotNull EntityUseAction action,
            @NotNull Hand hand) {
        super(who, npc);
        this.action = action;
        this.hand = hand;
    }

    /**
     * Get the handlers for this event.
     *
     * @return the handlers for this event.
     */
    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    /**
     * Gets the interact action as a protocol lib wrapper. This is not recommended to use, the
     * alternative is {@link #getUseAction()}.
     *
     * @return the interact action as a protocol lib wrapper.
     */
    @NotNull
    @Deprecated
    public WrappedPacketInUseEntity.EntityUseAction getAction() {
        return this.action.handle;
    }

    /**
     * Gets the interact action with the associated npc.
     *
     * @return the interact action with the associated npc.
     * @since 2.5-SNAPSHOT
     */
    @NotNull
    public EntityUseAction getUseAction() {
        return this.action;
    }

    /**
     * Gets the hand which the player used to interact with the associated npc.
     *
     * @return The hand the player used to interact
     */
    @NotNull
    public Hand getHand() {
        return this.hand;
    }

    /** {@inheritDoc} */
    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    /**
     * A wrapper for the interact action with a npc.
     *
     * @since 2.5-SNAPSHOT
     */
    public enum EntityUseAction {
        /** A normal interact. (right click) */
        INTERACT(WrappedPacketInUseEntity.EntityUseAction.INTERACT),
        /** An attack. (left click) */
        ATTACK(WrappedPacketInUseEntity.EntityUseAction.ATTACK),
        /** A normal interact to a specific entity. (right click) */
        INTERACT_AT(WrappedPacketInUseEntity.EntityUseAction.INTERACT_AT);

        /** All values of the action, to prevent a copy. */
        private static final EntityUseAction[] VALUES = values();

        /** The entity use action as the protocol lib wrapper. */
        private final WrappedPacketInUseEntity.EntityUseAction handle;

        /**
         * Constructs an instance of the interact action.
         *
         * @param handle The protocol lib association with the action.
         */
        EntityUseAction(WrappedPacketInUseEntity.EntityUseAction handle) {
            this.handle = handle;
        }

        /**
         * Converts the protocol lib wrapper to the associated action.
         *
         * @param action The protocol lib wrapper of the association.
         * @return The association with the protocol lib wrapper action.
         * @throws IllegalArgumentException When no association was found.
         */
        @NotNull
        private static EntityUseAction fromHandle(
                @NotNull WrappedPacketInUseEntity.EntityUseAction action) {
            for (EntityUseAction value : VALUES) {
                if (value.handle == action) {
                    return value;
                }
            }
            throw new IllegalArgumentException("No use action for handle: " + action);
        }
    }

    /** A wrapper for the hand used for interacts. */
    public enum Hand {
        /** Main hand of the player. */
        MAIN_HAND(io.github.retrooper.packetevents.utils.player.Hand.MAIN_HAND),
        /** Off hand of the player. */
        OFF_HAND(io.github.retrooper.packetevents.utils.player.Hand.OFF_HAND);

        /** All hand enum values, to prevent a copy. */
        private static final Hand[] VALUES = values();

        /** The hand as the protocol lib wrapper. */
        private final io.github.retrooper.packetevents.utils.player.Hand handle;

        /**
         * @param handle The hand as the protocol lib wrapper.
         */
        Hand(io.github.retrooper.packetevents.utils.player.Hand handle) {
            this.handle = handle;
        }

        /**
         * Converts the protocol lib wrapper to the associated hand.
         *
         * @param hand The protocol lib wrapper of the association.
         * @return The association with the protocol lib wrapper hand.
         * @throws IllegalArgumentException When no association was found.
         */
        @NotNull
        private static Hand fromHandle(
                @NotNull io.github.retrooper.packetevents.utils.player.Hand hand) {
            for (Hand value : VALUES) {
                if (value.handle == hand) {
                    return value;
                }
            }
            throw new IllegalArgumentException("No hand for handle: " + hand);
        }
    }
}
