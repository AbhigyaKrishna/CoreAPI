package me.Abhigya.core.menu.inventory.custom.book;

import me.Abhigya.core.menu.inventory.Item;
import me.Abhigya.core.menu.inventory.ItemMenu;
import me.Abhigya.core.menu.inventory.action.ItemMenuClickAction;
import me.Abhigya.core.menu.inventory.size.ItemMenuSize;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

/** Implementation of {@link ItemMenu} interacting with paginated menu. */
public class BookItemMenu extends ItemMenu {

    /** Default page button bar size. */
    public static final ItemMenuSize DEFAULT_BUTTONS_BAR_SIZE = ItemMenuSize.ONE_LINE;

    private final ItemMenuSize buttons_bar_size;
    private final Item[] bar_buttons;
    private BookPageItemMenu[] pages;

    /**
     * Constructs the Book menu.
     *
     * <p>
     *
     * @param title Title of the menu
     * @param pages_size Page size of the menu
     * @param buttons_bar_size Page button bar size of menu
     * @param parent Parent menu if any, else null
     * @param contents Contents of the menu
     */
    public BookItemMenu(
            String title,
            ItemMenuSize pages_size,
            ItemMenuSize buttons_bar_size,
            ItemMenu parent,
            Item... contents) {
        super(title, pages_size, parent, contents);
        Validate.notNull(pages_size, "The pages size cannot be null!");
        Validate.notNull(buttons_bar_size, "The buttons bar size cannot be null!");
        Validate.isTrue(
                pages_size.isHigherThan(ItemMenuSize.ONE_LINE),
                "The book pages size must be higher than ItemMenuSize.ONE_LINE!");
        Validate.isTrue(
                pages_size.isHigherThan(buttons_bar_size),
                "The pages size must be higher the buttons bar size");

        this.buttons_bar_size = buttons_bar_size;
        this.bar_buttons = new Item[buttons_bar_size.getSize()];
        int pages_amount = 1;
        if (contents != null) {
            int contents_length = contents.length;
            pages_amount = getPagesAmount(contents_length, pages_size, buttons_bar_size);
        }

        this.pages = new BookPageItemMenu[pages_amount];
        for (int i = 0; i < this.pages.length; i++) { // default pages.
            this.pages[i] = new BookPageItemMenu(this, new Item[pages_size.getSize()]);
            this.pages[i].setPageNumber(i);
        }

        this.addItems(contents);
    }

    /**
     * Constructs the Book menu.
     *
     * <p>
     *
     * @param title Title of the menu
     * @param pages_size Page size of the menu
     * @param parent Parent menu if any, else null
     * @param contents Contents of the menu
     */
    public BookItemMenu(String title, ItemMenuSize pages_size, ItemMenu parent, Item... contents) {
        this(title, pages_size, DEFAULT_BUTTONS_BAR_SIZE, parent, contents);
    }

    /**
     * Returns the per page menu size.
     *
     * <p>
     *
     * @return Size of the menu
     */
    public ItemMenuSize getPagesSize() {
        return super.getSize();
    }

    /**
     * Returns the button bar size size.
     *
     * <p>
     *
     * @return Size of button bar
     */
    public ItemMenuSize getButtonsBarSize() {
        return this.buttons_bar_size;
    }

    /**
     * Returns the per page menu size.
     *
     * <p>
     *
     * @return Size of the menu
     */
    @Override
    @Deprecated
    public final ItemMenuSize getSize() {
        return super.getSize();
    }

    /**
     * Returns the all the pages of the book menu.
     *
     * <p>
     *
     * @return Array of pages
     */
    public BookPageItemMenu[] getPages() {
        return Arrays.copyOf(pages, pages.length);
    }

    /**
     * Returns the page of the menu from index.
     *
     * <p>
     *
     * @param page_index Index of page
     * @return Page of menu
     */
    public BookPageItemMenu getPage(int page_index) {
        pagesRangeCheck(page_index, page_index);
        return pages[page_index];
    }

    /**
     * Returns the button-bar buttons of the menu.
     *
     * <p>
     *
     * @return Array of button-bar buttons.
     */
    public Item[] getBarButtons() {
        return Arrays.copyOf(bar_buttons, bar_buttons.length);
    }

    /**
     * Returns the button-bar button at the index.
     *
     * <p>
     *
     * @param button_index Index of button-bar
     * @return Button-bar button
     */
    public Item getBarButton(int button_index) {
        buttonsRangeCheck(button_index, button_index);
        return bar_buttons[button_index];
    }

    @Override
    @Deprecated
    public final Item[] getContents() {
        return null;
    }

    @Override
    @Deprecated
    public final Stream<Item> getContentsStream() {
        return null;
    }

    @Override
    @Deprecated
    public final Item[] getContents(Predicate<? super Item> predicate_filter) {
        return null;
    }

    @Override
    @Deprecated
    public final Item getItem(int index) {
        return null;
    }

    @Override
    @Deprecated
    public final Integer[] getIndexes(Predicate<? super Item> predicate_filter) {
        return null;
    }

    @Override
    @Deprecated
    public final int getFirstEmpty() {
        return -1;
    }

    @Override
    @Deprecated
    public final Integer[] getEmptyIndexes() {
        return null;
    }

    @Override
    public boolean isFull() {
        for (int i = 0; i < getPages().length; i++) {
            if (!getPage(i).isFull()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < getPages().length; i++) {
            if (!getPage(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isMenuOpen(Player player) {
        return getOpenPageNumber(player) != -1;
    }

    /**
     * Returns the number of the page of this book that the given player is viewing, or -1 if the
     * player is not viewing any page of this book.
     *
     * <p>
     *
     * @param player Player viewer
     * @return Number of the page of this book that the given player is viewing, or -1 if the player
     *     is not viewing any page of this book
     */
    public int getOpenPageNumber(Player player) {
        for (int i = 0; i < getPages().length; i++) {
            if (getPage(i).isMenuOpen(player)) {
                return getPage(i).getPageNumber();
            }
        }
        return -1;
    }

    //	@Override @Deprecated
    //	public final ItemMenu setSize(ItemMenuSize size) {
    //		return null;
    //	}

    @Override
    @Deprecated
    public final ItemMenu setContents(Item[] contents) {
        return null;
    }

    @Override
    @Deprecated
    public final ItemMenu setItem(int index, Item content) {
        return null;
    }

    @Override
    @Deprecated
    public final boolean setItemIf(int index, Item content, Predicate<? super Item> predicate) {
        return false;
    }

    @Override
    public boolean addItem(Item item) {
        addItems(item);
        return true;
    }

    /**
     * Stores the given items.
     *
     * <p>New pages will be created if required.
     *
     * <p>
     *
     * @param items Items to add
     * @return This Object, for chaining
     */
    public BookItemMenu addItems(Item... items) {
        final int count = addItemsNotFull(items);
        if ((items.length - count) > 0) { // if there is no added items
            int pages_amount =
                    getPagesAmount((items.length - count), getPagesSize(), getButtonsBarSize());
            this.pages = Arrays.copyOfRange(this.pages, 0, this.pages.length + pages_amount);
            for (int i = 0; i < pages.length; i++) {
                if (pages[i] == null) {
                    pages[i] = new BookPageItemMenu(this);
                    pages[i].setPageNumber(i);

                    // here we're registering listener of the new page.
                    if (getHandler() != null) {
                        pages[i].registerListener(getHandler().getPlugin());
                    }
                }
            }
            addItemsNotFull(Arrays.copyOfRange(items, count, items.length));
        }
        return this;
    }

    /**
     * Stores the given items only if the menu is not full at the moment of storing the current
     * element of the iteration.
     *
     * <p>
     *
     * @param items Items to store
     * @return Amount of items that was stored successfully
     */
    public int addItemsNotFull(Item... items) {
        int added_count = 0;
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < getPages().length; j++) {
                if (!getPage(j).isFull()) {
                    getPage(j).addItem(items[i]);
                    added_count++;
                    break;
                }
            }
        }
        return added_count;
    }

    /**
     * Stores the given bar button on the given slot.
     *
     * <p>
     *
     * @param index Index of Slot
     * @param button Button to store
     * @return This Object, for chaining
     */
    public BookItemMenu setBarButton(int index, Item button) {
        this.buttonsRangeCheck(index, index);
        this.bar_buttons[index] = button;
        return this;
    }

    /**
     * Stores the given bar button item on the next empty slot of the bar.
     *
     * <p>
     *
     * @param buttons Button items to store
     * @return true if the button could be stored successfully
     */
    public boolean addBarButton(Item... buttons) {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < this.getButtonsBarSize().getSize(); j++) {
                if (this.bar_buttons[j] == null) {
                    this.bar_buttons[j] = buttons[i];
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    @Deprecated
    public final boolean clearItem(int slot) {
        return false;
    }

    @Override
    @Deprecated
    public final boolean clearItemIf(int slot, Predicate<? super Item> predicate) {
        return false;
    }

    @Override
    @Deprecated
    public final ItemMenu fill(Item... contents) {
        return null;
    }

    @Override
    @Deprecated
    public final ItemMenu fill(int from_index, Item... contents) {
        return null;
    }

    /**
     * Clears all the items (including button-bar buttons and content) in the book menu.
     *
     * <p>
     *
     * @return ItemMenu Object
     * @see #clearContents()
     * @see #clearBarButtons()
     */
    public ItemMenu clear() {
        clearContents();
        clearBarButtons();
        return this;
    }

    /**
     * Clears all the content in the book menu.
     *
     * <p>
     *
     * @return ItemMenu Object
     */
    public ItemMenu clearContents() {
        for (int i = 0; i < this.getPages().length; i++) { // clear
            BookPageItemMenu page = getPage(i);
            for (int j = 0; j < page.getContents().length; j++) {
                page.fillToAll(null);
            }
        }
        return this;
    }

    /**
     * Clears all the button-bar buttons in the book menu.
     *
     * <p>
     *
     * @return ItemMenu Object
     */
    public ItemMenu clearBarButtons() {
        for (int i = 0; i < this.getButtonsBarSize().getSize(); i++) { // clear
            this.bar_buttons[i] = null;
        }
        return this;
    }

    @Override
    @Deprecated
    public final Inventory apply(Inventory inventory) {
        return null;
    }

    /**
     * Applies button-bar button to the given inventory.
     *
     * <p>
     *
     * @param inventory Inventory to apply button-bar
     * @return Inventory instance
     */
    public Inventory applyBarButtons(Inventory inventory) {
        int from_index = this.getPagesSize().getSize() - this.getButtonsBarSize().getSize();
        for (int i = 0; i < getButtonsBarSize().getSize(); i++) {
            Item button = this.bar_buttons[i];
            if (button != null) {
                inventory.setItem(from_index + i, button.getDisplayIcon());
            }
        }
        return inventory;
    }

    @Override
    public boolean registerListener(Plugin plugin) {
        for (int i = 0; i < this.getPages().length; i++) {
            if (!this.getPage(i).registerListener(plugin)) {
                return false;
            }
        }
        return super.registerListener(plugin);
    }

    @Override
    public boolean unregisterListener() {
        for (int i = 0; i < this.getPages().length; i++) {
            if (!this.getPage(i).unregisterListener()) {
                return false;
            }
        }
        return this.unregisterListener();
    }

    @Override
    public Inventory open(Player player) {
        return open(0, player);
    }

    /**
     * Opens a new {@link Inventory} with the contents of the given page.
     *
     * <p>
     *
     * @param page_number the page number.
     * @param player the player viewer.
     * @return the opened inventory.
     */
    public Inventory open(int page_number, Player player) {
        this.pagesRangeCheck(page_number, page_number);
        return getPage(page_number).open(player); // apply page contents and bar buttons
    }

    @Override
    public boolean update(Player player) {
        for (int i = 0; i < this.getPages().length; i++) {
            BookPageItemMenu page = this.getPage(i);
            if (page.update(player)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Deprecated
    public ItemMenu onClick(final ItemMenuClickAction action) {
        return this;
    }

    protected void bookRangeCheck(int from, int to) {
        rangeCheck(getPagesSize().getSize() * pages.length, from, to);
    }

    protected void pagesRangeCheck(int from, int to) {
        rangeCheck(this.getPages().length, from, to);
    }

    protected void buttonsRangeCheck(int from, int to) {
        rangeCheck(this.getButtonsBarSize().getSize(), from, to);
    }

    protected void rangeCheck(int array_length, int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("from(" + from + ") > to(" + to + ")!");
        }

        if (from < 0) {
            throw new ArrayIndexOutOfBoundsException(from);
        }

        if (to >= array_length) {
            throw new ArrayIndexOutOfBoundsException(to - 1);
        }
    }

    @Override
    @Deprecated
    protected final void rangeCheck(int from, int to) {
        /* do nothing because is deprecated! */
    }

    protected int getPagesAmount(
            int contents_length, ItemMenuSize pages_size, ItemMenuSize buttons_bar_size) {
        Validate.isTrue(
                pages_size.isHigherThan(ItemMenuSize.ONE_LINE),
                "The book pages size must be higher than ItemMenuSize.ONE_LINE!");
        Validate.isTrue(
                pages_size.isHigherThan(buttons_bar_size),
                "The pages size must be higher the buttons bar size");
        int pages_amount = 1;
        if (contents != null) {
            int pages_fit_size =
                    pages_size.getSize()
                            - buttons_bar_size
                                    .getSize(); // pages_fit_size = the pages size - (buttons bar
                                                // size).
            int length = contents_length;
            if (length > pages_fit_size) {
                while (length - pages_fit_size > 0) {
                    length -= pages_fit_size;
                    pages_amount++;
                }
            }
        }
        return pages_amount;
    }
}
