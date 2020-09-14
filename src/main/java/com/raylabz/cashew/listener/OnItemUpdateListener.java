package com.raylabz.cashew.listener;

import com.raylabz.cashew.CacheItem;

/**
 * Models listeners that execute code when an item is updated.
 */
public interface OnItemUpdateListener {

    /**
     * Defines code to be executed immediately after an item is successfully updated.
     * @param item The item.
     */
    void onUpdate(CacheItem<?> item);

}
