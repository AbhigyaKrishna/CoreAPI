package me.Abhigya.core.menu.anvil;

import me.Abhigya.core.menu.anvil.action.AnvilItemClickAction;

public interface ClickAction {

    /**
     * Action triggered when a gui slot is clicked.
     * <p>
     *
     * @param action {@link AnvilItemClickAction}
     */
    void onClick( AnvilItemClickAction action );

}