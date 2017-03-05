package me.jcomo.foodie.tasks;

import com.google.common.collect.ImmutableMultimap;
import io.dropwizard.servlets.tasks.Task;
import me.jcomo.foodie.wrapper.Cache;

import java.io.PrintWriter;

public class ClearCacheTask<K, V> extends Task {
    private Cache<K, V> cache;

    public ClearCacheTask(Cache<K, V> cache) {
        super("clear-cache");
        this.cache = cache;
    }

    @Override
    public void execute(ImmutableMultimap<String, String> immutableMultimap,
                        PrintWriter printWriter) throws Exception {
        cache.clear();
    }
}
