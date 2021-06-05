package me.Abhigya.core.menu;

import me.Abhigya.core.menu.action.ItemClickAction;
import me.Abhigya.core.menu.action.ItemMenuClickAction;
import me.Abhigya.core.menu.handler.ItemMenuHandler;
import me.Abhigya.core.menu.holder.ItemMenuHolder;
import me.Abhigya.core.menu.size.ItemMenuSize;
import me.Abhigya.core.util.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemMenu {

    public static final String DEFAULT_TITLE = "Empty Title";

    protected String title;
    protected ItemMenuSize size;
    protected final Item[] contents;
    protected ItemMenu parent;
    protected ItemMenuHandler handler;

    public ItemMenu(String title, ItemMenuSize size, ItemMenu parent, Item... contents) {
        Validate.notNull(size, "The size cannot be null!");
        this.title = StringUtils.defaultIfBlank(title, DEFAULT_TITLE);
        this.size = size;
        this.parent = parent;
        this.contents = new Item[size.getSize()];
        fill(contents);
    }

    public String getTitle() {
        return title;
    }

    public ItemMenuSize getSize() {
        return size;
    }

    public Item[] getContents() {
        return contents;
    }

    public Stream<Item> getContentsStream() {
        return Arrays.stream(getContents());
    }

    public Item[] getContents(Predicate<? super Item> predicate_filter) {
        List<Item> filtered = getContentsStream().filter(predicate_filter).collect(Collectors.toList());
        return filtered.toArray(new Item[filtered.size()]);
    }

    public ItemMenu getParent() {
        return parent;
    }

    public boolean hasParent() {
        return getParent() != null;
    }

    public ItemMenuHandler getHandler() {
        return this.handler;
    }

    public Item getItem(int index) {
        rangeCheck(index, index);
        return this.contents[index];
    }

    public Integer[] getIndexes(Predicate<? super Item> predicate_filter) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < getContents().length; i++) {
            if (predicate_filter == null || predicate_filter.test(getItem(i))) {
                set.add(i);
            }
        }
        return set.toArray(new Integer[set.size()]);
    }

    /**
     * Returns the first empty slot.
     * <p>
     *
     * @return the first empty slot found, or -1 if no empty slots.
     */
    public int getFirstEmpty() {
        return getEmptyIndexes().length > 0 ? getEmptyIndexes()[0] : -1;
    }

    /**
     * Returns the empty slot indexes.
     * <p>
     *
     * @return the empty slot indexes found.
     */
    public Integer[] getEmptyIndexes() {
        return getIndexes(content -> content == null || content.getDisplayIcon() == null
                || content.getDisplayIcon().getType() == Material.AIR);
    }

    public boolean isFull() {
        return getEmptyIndexes().length == 0;
    }

    public boolean isEmpty() {
        return getEmptyIndexes().length == this.getContents().length;
    }

    /**
     * Returns true if the opened inventory that the given {@link Player} has is
     * equals this.
     * <p>
     *
     * @param player the player with the opened inventory.
     * @return true if the opened inventory that the given {@link Player} has is
     * this.
     */
    public boolean isMenuOpen(Player player) {
        if (player.getOpenInventory() == null || player.getOpenInventory().getTopInventory() == null
                || player.getOpenInventory().getTopInventory().getType() != InventoryType.CHEST
                || !(player.getOpenInventory().getTopInventory().getHolder() instanceof ItemMenuHolder)) {
            return false;
        }
        return this.equals(((ItemMenuHolder) player.getOpenInventory().getTopInventory().getHolder()).getItemMenu());
    }

    /**
     * Gets whether the provided {@link Inventory} is an instance of this {@link ItemMenu}.
     * <br>
     *
     * @param inventory the inventory to check.
     * @return true if the provided {@link Inventory} is an instance of this {@link ItemMenu}
     */
    public boolean isThisMenu(Inventory inventory) {
        if (inventory.getType() == InventoryType.CHEST && inventory.getHolder() instanceof ItemMenuHolder) {
            return equals(((ItemMenuHolder) inventory.getHolder()).getItemMenu());
        } else {
            return false;
        }
    }

    /**
     * Sets the title of this menu.
     * <p>
     * Note that all the Bukkit inventories created from this, must be re-opened to
     * see the changes.
     * <p>
     *
     * @param title new title.
     * @return this Object, for chaining.
     */
    public ItemMenu setTitle(String title) {
        this.title = StringUtils.defaultIfBlank(title, DEFAULT_TITLE);
        return this;
    }

//	public ItemMenu setSize(ItemMenuSize size) {
//		Validate.notNull(size, "The size cannot be null!");
//		this.size = size;
//		return this;
//	}

    public ItemMenu setContents(Item[] contents) {
        fill(contents);
        return this;
    }

    public ItemMenu setParent(ItemMenu parent) {
        this.parent = parent;
        return this;
    }

    public ItemMenu setItem(int slot, Item content) {
        setItemIf(slot, content, null);
        return this;
    }

    /**
     * Sets the item on the given slot.
     * <p>
     *
     * @param slot      the slot where the given item will be stored.
     * @param item      the item to store.
     * @param predicate your predicate expression.
     * @return true if was set.
     */
    public boolean setItemIf(int slot, Item item, Predicate<? super Item> predicate) {
        rangeCheck(slot, slot);
        if (predicate == null || predicate.test(this.getItem(slot))) {
            this.contents[slot] = item;

            if (item != null) {
                item.menu = this;
            }
            return true;
        }
        return false;
    }

    /**
     * Stores the given {@link Item} only if
     * not full.
     * <p>
     *
     * @param item the item to add.
     * @return true if could be added.
     */
    public boolean addItem(Item item) {
        if (!isFull()) {
            this.setItem(getFirstEmpty(), item);
            return true;
        }
        return false;
    }

    /**
     * Clear the item stored on the given slot.
     * <p>
     *
     * @param slot the slot where the item is stored.
     * @return true if was clear.
     */
    public boolean clearItem(int slot) {
        return clearItemIf(slot, null);
    }

    /**
     * Clear the item stored on the given slot.
     * <p>
     *
     * @param slot      the slot where the item is stored.
     * @param predicate your predicate expression.
     * @return true if was clear.
     */
    public boolean clearItemIf(int slot, Predicate<? super Item> predicate) {
        return setItemIf(slot, null, predicate);
    }

    public ItemMenu fill(Item... contents) {
        fill(0, contents);
        return this;
    }

    public ItemMenu fillToAll(Item content) {
        return fillToAllIf(content, null);
    }

    public ItemMenu fillToAllIf(Item content, Predicate<? super Item> predicate) {
        for (int i = 0; i < this.getContents().length; i++) {
            if (predicate == null || predicate.test(this.contents[i])) {
                this.setItem(i, content);
            }
        }
        return this;
    }

    public ItemMenu fill(int from_index, Item... contents) {
        rangeCheck(from_index, from_index);
        for (int i = from_index; i < this.getContents().length; i++) {
            if (contents == null || i >= contents.length) {
                break;
            }
            this.setItem(i, contents[i]);
        }
        return this;
    }

    public ItemMenu clear() {
        return fillToAll(null);
    }

    public Inventory apply(Inventory inventory) {
        for (int i = 0; i < getContents().length; i++) {
            if (i >= inventory.getSize()) {
                break;
            }

            if (getItem(i) != null) {
                inventory.setItem(i, getItem(i).getDisplayIcon());
            }
        }
        return inventory;
    }

    /**
     * Initializes the {@link ItemMenuHandler} of this.
     * <p>
     *
     * @param plugin the plugin owner of the listener.
     * @return true if not already registered.
     */
    public boolean registerListener(Plugin plugin) {
        if (this.handler == null) {
            Bukkit.getPluginManager().registerEvents((this.handler = new ItemMenuHandler(this, plugin)), plugin);
            return true;
        }
        return false;
    }

    /**
     * Stop handling this.
     * <p>
     *
     * @return false if not already registered.
     */
    public boolean unregisterListener() {
        if (this.handler != null) {
            this.handler.unregisterListener();
            this.handler = null;
            return true;
        }
        return false;
    }

    /**
     * Opens a new {@link Inventory} with the contents
     * of this {@link ItemMenu}.
     * <p>
     *
     * @param player the player viewer.
     * @return the opened inventory.
     */
    public Inventory open(Player player) {
        Inventory inventory = apply(
                Bukkit.createInventory(new ItemMenuHolder(this, Bukkit.createInventory(player, size.getSize())),
                        size.getSize(), StringUtils.limit(StringUtils.translateAlternateColorCodes(getTitle()), 32)));
        player.closeInventory();
        player.openInventory(inventory);
        return inventory;
    }

    /**
     * Updates the viewing inventory of the given {@link Player} only if it is equals
     * this.
     * <p>
     *
     * @return true if was updated.
     */
    public boolean update(Player player) {
        if (isMenuOpen(player)) {
            apply(player.getOpenInventory().getTopInventory());
            player.updateInventory();
            return true;
        }
        return false;
    }

    /**
     * Updates the viewing inventory of each online player only if it is equals this.
     */
    public ItemMenu updateOnlinePlayers() {
        Bukkit.getOnlinePlayers().forEach(player -> update(player));
        return this;
    }

    /**
     * Closes the viewing inventory of the given {@link Player} only if it is equals
     * this.
     * <p>
     *
     * @return true if was closed.
     */
    public boolean close(Player player) {
        if (isMenuOpen(player)) {
            player.closeInventory();
            return true;
        }
        return false;
    }

    /**
     * Closes the viewing inventory of each online player only if it is equals this.
     */
    public ItemMenu closeOnlinePlayers() {
        Bukkit.getOnlinePlayers().forEach(player -> close(player));
        return this;
    }

    public ItemMenu onClick(final ItemMenuClickAction action) {
        if (this.getHandler() == null) {
            throw new UnsupportedOperationException("This menu has never been registered!");
        }

        if (!action.isRightClick() && !action.isLeftClick()) {
            return this;
        }

        if (action.getSlot() < 0 || action.getSlot() >= getContents().length
                || this.getItem(action.getSlot()) == null) {
            return this;
        }

        ItemClickAction sub_action = new ItemClickAction(this, action.getInventoryView(),
                action.getClickType(), action.getInventoryAction(), action.getSlotType(), action.getSlot(),
                action.getRawSlot(), action.getCurrentItem(), action.getHotbarKey(), false, false, false);

        /* calling click method on the content class */
        getItem(action.getSlot()).onClick(sub_action);

        /* just update the view */
        if (sub_action.isWillUpdate()) {
            getHandler().delayedUpdate(action.getPlayer(), 1);
//			update(action.getPlayer());
        } else if (sub_action.isWillClose() || sub_action.isWillGoBack()) { // closing or getting back
            getHandler().delayedClose(action.getPlayer(), 1);

            /* opening parent */
            if (sub_action.isWillGoBack() && hasParent()) {
                getParent().getHandler().delayedOpen(action.getPlayer(), 3);
            }
        }
        return this;
    }

    protected void rangeCheck(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("from(" + from + ") > to(" + to + ")!");
        }

        if (from < 0) {
            throw new ArrayIndexOutOfBoundsException(from);
        }

        if (to >= getContents().length) {
            throw new ArrayIndexOutOfBoundsException(to);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(contents);
        result = prime * result + ((handler == null) ? 0 : handler.hashCode());
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        result = prime * result + ((size == null) ? 0 : size.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ItemMenu other = (ItemMenu) obj;
        if (!Arrays.equals(contents, other.contents))
            return false;
        if (handler == null) {
            if (other.handler != null)
                return false;
        } else if (!handler.equals(other.handler))
            return false;
        if (parent == null) {
            if (other.parent != null)
                return false;
        } else if (!parent.equals(other.parent))
            return false;
        if (size != other.size)
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

}
