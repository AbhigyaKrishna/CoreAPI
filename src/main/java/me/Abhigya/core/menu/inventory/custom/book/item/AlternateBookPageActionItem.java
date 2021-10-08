package me.Abhigya.core.menu.inventory.custom.book.item;

import me.Abhigya.core.menu.inventory.action.ItemClickAction;
import me.Abhigya.core.menu.inventory.custom.book.BookItemMenu;
import me.Abhigya.core.menu.inventory.custom.book.BookPageItemMenu;
import me.Abhigya.core.menu.inventory.item.action.ActionItem;
import me.Abhigya.core.menu.inventory.item.action.ItemAction;
import me.Abhigya.core.menu.inventory.item.action.ItemActionPriority;
import org.bukkit.inventory.ItemStack;

/**
 * Implementation of {@link ActionItem} handling page change button item in book menu.
 */
public class AlternateBookPageActionItem extends ActionItem {

    protected BookItemMenu book;
    protected boolean go_next;

    /**
     * Constructs the book page change action item.
     * <p>
     *
     * @param name Name of the item
     * @param icon ItemStack icon of the item
     * @param lore Lore of the item
     */
    public AlternateBookPageActionItem( String name, ItemStack icon, String... lore ) {
        super( name, icon, lore );
        this.go_next = true;
        super.addAction( new ItemAction( ) {

            @Override
            public ItemActionPriority getPriority( ) {
                return ItemActionPriority.LOW;
            }

            @Override
            public void onClick( ItemClickAction action ) {
                if ( !( action.getMenu( ) instanceof BookPageItemMenu ) ) {
                    return;
                }

                if ( !( (BookPageItemMenu) action.getMenu( ) ).getBookMenu( ).equals( book ) ) {
                    return;
                }

                BookPageItemMenu current = book.getPage( book.getOpenPageNumber( action.getPlayer( ) ) );
                BookPageItemMenu to = go_next ? current.getNextBookPage( ) : current.getBeforeBookPage( );
                if ( to != null ) {
                    book.getHandler( ).delayedClose( action.getPlayer( ), 1 );
                    to.getHandler( ).delayedOpen( action.getPlayer( ), 2 );
                }
            }
        } );
    }

    /**
     * Sets the book menu owner of the pages that the player can
     * alternate.
     * <p>
     *
     * @param book {@link BookItemMenu} owner of the pages that the player can alternate
     * @return This Object, for chaining
     */
    public AlternateBookPageActionItem setBookMenu( BookItemMenu book ) {
        this.book = book;
        return this;
    }

    /**
     * If true: The next page will be opened.
     * If false: The page before the current will be opened.
     * <p>
     *
     * @param flag true == next page. false == page before
     * @return This Object, for chaining
     */
    public AlternateBookPageActionItem setGoNext( boolean flag ) {
        this.go_next = flag;
        return this;
    }

    @Deprecated
    @Override
    public final ActionItem addAction( ItemAction action ) {
        return this;
    }

    @Deprecated
    @Override
    public final ActionItem removeAction( ItemAction action ) {
        return this;
    }

}
