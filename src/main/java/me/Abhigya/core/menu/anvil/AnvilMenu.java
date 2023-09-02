package me.Abhigya.core.menu.anvil;

import me.Abhigya.core.menu.anvil.action.AnvilItemClickAction;
import me.Abhigya.core.menu.anvil.action.AnvilMenuClickAction;
import me.Abhigya.core.menu.anvil.handler.AnvilMenuHandler;
import me.Abhigya.core.util.reflection.bukkit.BukkitReflection;
import me.Abhigya.core.util.reflection.bukkit.PlayerReflection;
import me.Abhigya.core.util.reflection.general.ClassReflection;
import me.Abhigya.core.util.reflection.general.ConstructorReflection;
import me.Abhigya.core.util.reflection.general.FieldReflection;
import me.Abhigya.core.util.reflection.general.MethodReflection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/** Class for creating custom Anvil Inventory menus. */
public class AnvilMenu {

    private final Set<OpenAnvilGui> openMenus;

    protected AnvilItem leftItem;
    protected AnvilItem rightItem;
    protected ClickAction outputAction;
    private AnvilMenuHandler handler;

    /**
     * Constructs the AnvilMenu.
     *
     * <p>
     *
     * @param leftItem Item for the left slot
     * @param rightItem Item for the right slot
     */
    public AnvilMenu(AnvilItem leftItem, AnvilItem rightItem) {
        this.leftItem = leftItem;
        this.rightItem = rightItem;
        if (this.leftItem != null) this.leftItem.menu = this;
        if (this.rightItem != null) this.rightItem.menu = this;
        this.openMenus = new HashSet<>();
    }

    /** Constructs the AnvilMenu. */
    public AnvilMenu() {
        this(null, null);
    }

    /**
     * Returns the Left slot AnvilItem.
     *
     * <p>
     *
     * @return Left slot AnvilItem
     */
    public AnvilItem getLeftItem() {
        return leftItem;
    }

    /**
     * Sets the Left slot AnvilItem.
     *
     * <p>
     *
     * @param leftItem AnvilItem to set
     * @return This Object, for chaining
     */
    public AnvilMenu setLeftItem(AnvilItem leftItem) {
        this.leftItem = leftItem;
        if (this.leftItem != null) this.leftItem.menu = this;
        return this;
    }

    /**
     * Returns the Right slot AnvilItem.
     *
     * <p>
     *
     * @return Right slot AnvilItem
     */
    public AnvilItem getRightItem() {
        return rightItem;
    }

    /**
     * Sets the Right slot AnvilItem.
     *
     * <p>
     *
     * @param rightItem AnvilItem to set
     * @return This Object, for chaining
     */
    public AnvilMenu setRightItem(AnvilItem rightItem) {
        this.rightItem = rightItem;
        if (this.rightItem != null) this.rightItem.menu = this;
        return this;
    }

    /**
     * Returns the AnvilMenuHandler for this AnvilMenu.
     *
     * <p>
     *
     * @return {@link AnvilMenuHandler}
     */
    public AnvilMenuHandler getHandler() {
        return handler;
    }

    /**
     * Sets the output slot click action.
     *
     * <p>
     *
     * @param outputAction Click action for output slot of the Anvil
     * @return This Object, for chaining
     */
    public AnvilMenu setOutputAction(ClickAction outputAction) {
        this.outputAction = outputAction;
        return this;
    }

    /**
     * Return all open menus.
     *
     * <p>
     *
     * @return All {@link OpenAnvilGui}
     */
    public Set<OpenAnvilGui> getOpenMenus() {
        return openMenus;
    }

    /**
     * Checks if this menu is open for the given player.
     *
     * <p>
     *
     * @param player Player to check for open gui
     * @return <code>true</code> if the gui is open, false otherwise
     */
    public boolean isMenuOpen(Player player) {
        return player.getOpenInventory() != null
                && player.getOpenInventory().getTopInventory() != null
                && player.getOpenInventory().getTopInventory().getType() == InventoryType.ANVIL
                && this.getOpenGuiByPlayer(player) != null;
    }

    /**
     * Checks if the given inventory is of this menu.
     *
     * <p>
     *
     * @param inventory Inventory to check
     * @return <code>true</code> if the inventory is of this gui, false otherwise
     */
    public boolean isThisMenu(Inventory inventory) {
        return inventory.getType() == InventoryType.ANVIL
                && this.getOpenGuiByInventory(inventory) != null;
    }

    /**
     * Initializes the {@link AnvilMenuHandler} of this.
     *
     * <p>
     *
     * @param plugin The plugin owner of the listener.
     * @return true if not already registered.
     */
    public boolean registerListener(Plugin plugin) {
        if (this.handler == null) {
            Bukkit.getPluginManager()
                    .registerEvents(this.handler = new AnvilMenuHandler(this, plugin), plugin);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Stop handling this.
     *
     * <p>
     *
     * @return false if not already registered
     */
    public boolean unregisterListener() {
        if (this.handler != null) {
            this.handler.unregisterListener();
            this.handler = null;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Opens a new {@link Inventory} with the contents of this {@link AnvilMenu}.
     *
     * <p>
     *
     * @param player The player viewer
     * @return The opened inventory
     */
    public Inventory open(Player player) {
        Object container = this.newContainerAnvil(player);
        Inventory inventory = this.toBukkitInventory(container);
        if (leftItem != null) {
            inventory.setItem(Slot.INPUT_LEFT, leftItem.getDisplayIcon());
        }
        if (rightItem != null) {
            inventory.setItem(Slot.INPUT_RIGHT, rightItem.getDisplayIcon());
        }

        int containerId = this.getNextContainer(player);
        this.sendOpenWindowPacket(player, containerId);
        this.setActiveContainer(player, container);
        this.setActiveContainerId(container, containerId);
        this.addActiveContainerSlotListener(container, player);
        this.openMenus.add(new OpenAnvilGui(player, inventory, containerId));
        return inventory;
    }

    /**
     * Closes the viewing inventory of the given {@link Player} only if it is equals this.
     *
     * <p>
     *
     * @param player Player to close inventory
     * @return true if was closed.
     */
    public boolean close(Player player) {
        if (this.isMenuOpen(player)) {
            OpenAnvilGui gui = this.getOpenGuiByPlayer(player);
            this.handleInventoryClose(player);
            this.setActiveContainerDefault(player);
            this.sendCloseWindowPacket(player, gui.getContainerId());
            gui.getInventory().clear();
            this.openMenus.remove(gui);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Closes the viewing inventory of each online player only if it is equals this.
     *
     * <p>
     *
     * @return This Object, for chaining
     */
    public AnvilMenu closeOnlinePlayers() {
        Bukkit.getOnlinePlayers().forEach(this::close);
        return this;
    }

    /**
     * {@link AnvilMenuClickAction} for handling click in the menu.
     *
     * <p>
     *
     * @param action {@link AnvilMenuClickAction} to handle for clicks in the menu
     * @return This Object, for chaining
     */
    public AnvilMenu onClick(AnvilMenuClickAction action) {
        if (this.getHandler() == null) {
            throw new UnsupportedOperationException("This menu has never been registered!");
        } else if (!action.isRightClick() && !action.isLeftClick()) {
            return this;
        } else if (action.getRaw_slot() == Slot.OUTPUT) {
            AnvilItemClickAction clickAction =
                    new AnvilItemClickAction(
                            action.getMenu(),
                            action.getInventoryView(),
                            action.getClickType(),
                            action.getAction(),
                            action.getSlot_type(),
                            action.getRaw_slot(),
                            action.getCurrent(),
                            action.getHotbarKey(),
                            false);
            this.outputAction.onClick(clickAction);
        } else if (action.getRaw_slot() == Slot.INPUT_LEFT) {
            AnvilItemClickAction clickAction =
                    new AnvilItemClickAction(
                            action.getMenu(),
                            action.getInventoryView(),
                            action.getClickType(),
                            action.getAction(),
                            action.getSlot_type(),
                            action.getRaw_slot(),
                            action.getCurrent(),
                            action.getHotbarKey(),
                            false);
            this.leftItem.onClick(clickAction);
        } else if (action.getRaw_slot() == Slot.INPUT_RIGHT) {
            AnvilItemClickAction clickAction =
                    new AnvilItemClickAction(
                            action.getMenu(),
                            action.getInventoryView(),
                            action.getClickType(),
                            action.getAction(),
                            action.getSlot_type(),
                            action.getRaw_slot(),
                            action.getCurrent(),
                            action.getHotbarKey(),
                            false);
            this.rightItem.onClick(clickAction);
        }
        return this;
    }

    private OpenAnvilGui getOpenGuiByPlayer(Player player) {
        for (OpenAnvilGui gui : this.openMenus) {
            if (gui.getPlayer().equals(player)) return gui;
        }

        return null;
    }

    private OpenAnvilGui getOpenGuiByInventory(Inventory inventory) {
        for (OpenAnvilGui gui : this.openMenus) {
            if (gui.getInventory().equals(inventory)) return gui;
        }

        return null;
    }

    private int getNextContainer(Player player) {
        try {
            return (int)
                    MethodReflection.invoke(
                            PlayerReflection.getHandle(player), "nextContainerCounter");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void handleInventoryClose(Player player) {
        try {
            Object ePlayer = PlayerReflection.getHandle(player);
            Class<?> craftEventFactory =
                    ClassReflection.getCraftClass("CraftEventFactory", "event");
            Class<?> entityHuman =
                    ClassReflection.getNmsClass("EntityHuman", "world.entity.player");
            MethodReflection.get(craftEventFactory, "handleInventoryCloseEvent", entityHuman)
                    .invoke(craftEventFactory, ePlayer);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void sendOpenWindowPacket(Player player, int containerId) {
        try {
            Class<?> iChatComponent =
                    ClassReflection.getNmsClass("IChatBaseComponent", "network.chat");
            Class<?> packetPlayOutOpenWindow =
                    ClassReflection.getNmsClass("PacketPlayOutOpenWindow", "network.protocol.game");
            Class<?> chatMessage = ClassReflection.getNmsClass("ChatMessage", "network.chat");
            Class<?> block = ClassReflection.getNmsClass("Blocks", "world.level.block");
            Object blockAnvil = FieldReflection.get(block, "ANVIL").get(null);
            String blockAnvilA = (String) MethodReflection.invoke(blockAnvil, "a");

            Object packet =
                    ConstructorReflection.newInstance(
                            packetPlayOutOpenWindow,
                            new Class[] {int.class, String.class, iChatComponent},
                            containerId,
                            "minecraft:anvil",
                            ConstructorReflection.newInstance(
                                    chatMessage,
                                    new Class[] {String.class, Object[].class},
                                    blockAnvilA + ".name",
                                    new Object[0]));

            BukkitReflection.sendPacket(player, packet);
        } catch (IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException
                | InstantiationException
                | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void sendCloseWindowPacket(Player player, int containerId) {
        try {
            Class<?> packetPlayOutCloseWindow =
                    ClassReflection.getNmsClass(
                            "PacketPlayOutCloseWindow", "network.protocol.game");
            Object packet =
                    ConstructorReflection.newInstance(
                            packetPlayOutCloseWindow, new Class[] {int.class}, containerId);
            BukkitReflection.sendPacket(player, packet);
        } catch (InvocationTargetException
                | NoSuchMethodException
                | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setActiveContainerDefault(Player player) {
        try {
            Object ePlayer = PlayerReflection.getHandle(player);
            FieldReflection.setValue(
                    ePlayer,
                    "activeContainer",
                    FieldReflection.getValue(ePlayer, "defaultContainer"));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void setActiveContainer(Player player, Object container) {
        try {
            FieldReflection.setValue(
                    PlayerReflection.getHandle(player), "activeContainer", container);
        } catch (NoSuchFieldException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void setActiveContainerId(Object container, int containerId) {
        try {
            FieldReflection.setValue(container, "windowId", containerId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void addActiveContainerSlotListener(Object container, Player player) {
        try {
            Class<?> iCrafting = ClassReflection.getNmsClass("ICrafting", "world.inventory");
            MethodReflection.invoke(
                    container,
                    "addSlotListener",
                    new Class[] {iCrafting},
                    PlayerReflection.getHandle(player));
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private Inventory toBukkitInventory(Object container) {
        try {
            return ((InventoryView) MethodReflection.invoke(container, "getBukkitView"))
                    .getTopInventory();
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object newContainerAnvil(Player player) {
        try {
            Object ePlayer = PlayerReflection.getHandle(player);
            Class<?> containerAnvil =
                    ClassReflection.getNmsClass("ContainerAnvil", "world.inventory");
            Class<?> playerInventory =
                    ClassReflection.getNmsClass("PlayerInventory", "world.entity.player");
            Class<?> world = ClassReflection.getNmsClass("World", "world.level");
            Class<?> blockPosition = ClassReflection.getNmsClass("BlockPosition", "core");
            Class<?> entityHuman =
                    ClassReflection.getNmsClass("EntityHuman", "world.entity.player");
            Object anvil =
                    ConstructorReflection.newInstance(
                            containerAnvil,
                            new Class[] {playerInventory, world, blockPosition, entityHuman},
                            FieldReflection.getValue(ePlayer, "inventory"),
                            FieldReflection.getValue(ePlayer, "world"),
                            ConstructorReflection.newInstance(
                                    blockPosition,
                                    new Class[] {int.class, int.class, int.class},
                                    0,
                                    0,
                                    0),
                            ePlayer);
            FieldReflection.setValue(anvil, "checkReachable", false);
            return anvil;
        } catch (InvocationTargetException
                | NoSuchMethodException
                | InstantiationException
                | IllegalAccessException
                | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            AnvilMenu other = (AnvilMenu) obj;
            if (!Objects.equals(this.leftItem, other.leftItem)
                    || !Objects.equals(this.rightItem, other.rightItem)) {
                return false;
            } else {
                if (this.handler == null) {
                    if (other.handler != null) {
                        return false;
                    }
                } else if (!this.handler.equals(other.handler)) {
                    return false;
                }

                return true;
            }
        }
    }

    public static class OpenAnvilGui {

        private Player player;
        private Inventory inventory;
        private int containerId;

        protected OpenAnvilGui(Player player, Inventory inventory, int containerId) {
            this.player = player;
            this.inventory = inventory;
            this.containerId = containerId;
        }

        public Player getPlayer() {
            return player;
        }

        public Inventory getInventory() {
            return inventory;
        }

        public int getContainerId() {
            return containerId;
        }
    }

    public static class Slot {

        /**
         * The slot on the far left, where the first input is inserted. An {@link ItemStack} is
         * always inserted here to be renamed
         */
        public static final int INPUT_LEFT = 0;

        /**
         * Not used, but in a real anvil you are able to put the second item you want to combine
         * here
         */
        public static final int INPUT_RIGHT = 1;

        /**
         * The output slot, where an item is put when two items are combined from {@link
         * #INPUT_LEFT} and {@link #INPUT_RIGHT} or {@link #INPUT_LEFT} is renamed
         */
        public static final int OUTPUT = 2;

        private static final int[] values =
                new int[] {Slot.INPUT_LEFT, Slot.INPUT_RIGHT, Slot.OUTPUT};

        /**
         * Get all anvil slot values
         *
         * @return The array containing all possible anvil slots
         */
        public static int[] values() {
            return values;
        }
    }
}
