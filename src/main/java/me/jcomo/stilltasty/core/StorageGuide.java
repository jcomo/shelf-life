package me.jcomo.stilltasty.core;

import java.util.List;

import static me.jcomo.stilltasty.core.Humanize.titleize;

public class StorageGuide {
    private String food;
    private List<StorageMethod> storageMethods;
    private List<String> storageTips;

    public StorageGuide(String food, List<StorageMethod> storageMethods, List<String> storageTips) {
        this.food = titleize(food);
        this.storageMethods = storageMethods;
        this.storageTips = storageTips;
    }

    public String getFood() {
        return food;
    }

    public List<StorageMethod> getStorageMethods() {
        return storageMethods;
    }

    public List<String> getStorageTips() {
        return storageTips;
    }

    @Override
    public String toString() {
        return "StorageGuide{" +
                "food='" + food + '\'' +
                ", storageMethods=" + storageMethods +
                '}';
    }
}
