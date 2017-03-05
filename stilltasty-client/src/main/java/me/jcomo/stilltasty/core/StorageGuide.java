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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StorageGuide that = (StorageGuide) o;

        if (!food.equals(that.food)) return false;
        if (storageMethods != null ? !storageMethods.equals(that.storageMethods) : that.storageMethods != null)
            return false;
        return !(storageTips != null ? !storageTips.equals(that.storageTips) : that.storageTips != null);

    }

    @Override
    public int hashCode() {
        int result = food.hashCode();
        result = 31 * result + (storageMethods != null ? storageMethods.hashCode() : 0);
        result = 31 * result + (storageTips != null ? storageTips.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StorageGuide{" +
                "food='" + food + '\'' +
                ", storageMethods=" + storageMethods +
                '}';
    }
}
