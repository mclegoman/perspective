/*
    Perspective
    Contributor(s): dannytaylor, Nettakrim
    Github: https://github.com/mclegoman/perspective
    Licence: LGPL-3.0-or-later
*/

package dev.dannytaylor.perspective.base.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic Registry for storing specified types.
 */
public class GenericRegistry<K, V> {
    public final Map<K, V> registry = new HashMap<>();
    public void register(K key, V value) {
        if (!registry.containsKey(key)) registry.put(key, value);
    }
    public V get(K key) {
        return registry.get(key);
    }
    public void modify(K key, V value) {
        registry.replace(key, value);
    }
    public void remove(K key) {
        registry.remove(key);
    }
}