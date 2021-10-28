package me.Abhigya.core.util.npc.modifier;

import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import io.github.retrooper.packetevents.packetwrappers.play.out.entityequipment.WrappedPacketOutEntityEquipment;
import me.Abhigya.core.util.npc.NPC;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/** A modifier for modifying the equipment of a player. */
public class EquipmentModifier extends NPCModifier {

    /**
     * The id of the main hand item slot.
     *
     * @since 2.5-SNAPSHOT
     */
    public static final int MAINHAND = 0;
    /**
     * The id of the off hand item slot.
     *
     * @since 2.5-SNAPSHOT
     */
    public static final int OFFHAND = 1;
    /**
     * The id of the feet armor item slot.
     *
     * @since 2.5-SNAPSHOT
     */
    public static final int FEET = 2;
    /**
     * The id of the legs armor item slot.
     *
     * @since 2.5-SNAPSHOT
     */
    public static final int LEGS = 3;
    /**
     * The id of the chest armor item slot.
     *
     * @since 2.5-SNAPSHOT
     */
    public static final int CHEST = 4;
    /**
     * The id of the head armor item slot.
     *
     * @since 2.5-SNAPSHOT
     */
    public static final int HEAD = 5;

    private static final WrappedPacketOutEntityEquipment.EquipmentSlot[] ITEM_SLOTS =
            WrappedPacketOutEntityEquipment.EquipmentSlot.values();

    /**
     * Creates a new modifier.
     *
     * @param npc The npc this modifier is for.
     * @see NPC#equipment()
     */
    public EquipmentModifier(@NotNull NPC npc) {
        super(npc);
    }

    /**
     * Queues the change of an item slot using the protocol lib item slot enum wrapper directly. If
     * you don't want to use protocol lib as a dependency, use {@link #queue(int, ItemStack)} with
     * the item slot numbers defined at the top of this class.
     *
     * @param itemSlot The item slot the modification should take place.
     * @param equipment The item which should be placed at the specific slot.
     * @return The same instance of this class, for chaining.
     */
    @NotNull
    public EquipmentModifier queue(
            @NotNull WrappedPacketOutEntityEquipment.EquipmentSlot itemSlot,
            @NotNull ItemStack equipment) {
        WrappedPacket packetContainer =
                new WrappedPacketOutEntityEquipment(this.npc.getEntityId(), itemSlot, equipment);
        this.packetContainers.add(packetContainer);

        //        if (MINECRAFT_VERSION < 16) {
        //            if (MINECRAFT_VERSION < 9) {
        //                packetContainer.getIntegers().write(1, itemSlot.ordinal());
        //            } else {
        //                packetContainer.getItemSlots().write(0, itemSlot);
        //            }
        //            packetContainer.getItemModifier().write(0, equipment);
        //        } else {
        //            packetContainer.getSlotStackPairLists()
        //                    .write(0, Collections.singletonList(new Pair<>(itemSlot, equipment)));
        //        }

        return this;
    }

    /**
     * Queues the change of an item slot using the specified slot number.
     *
     * @param itemSlot The item slot the modification should take place.
     * @param equipment The item which should be placed at the specific slot.
     * @return The same instance of this class, for chaining.
     */
    @NotNull
    public EquipmentModifier queue(int itemSlot, @NotNull ItemStack equipment) {
        for (WrappedPacketOutEntityEquipment.EquipmentSlot slot : ITEM_SLOTS) {
            if (slot.ordinal() == itemSlot) {
                return queue(slot, equipment);
            }
        }

        throw new IllegalArgumentException("Provided itemSlot is invalid");
    }
}
