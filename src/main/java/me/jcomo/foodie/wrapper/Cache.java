package me.jcomo.foodie.wrapper;

public interface Cache<K, V> {
    V get(K key);
    void set(K key, V value, int ttl);
    void clear();
}
