package me.jcomo.foodie.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.jcomo.stilltasty.core.StorageMethod;

import java.util.List;

@SuppressWarnings("unused")
public interface StorageGuideMixin {
    @JsonProperty("name")
    String getFood();

    @JsonProperty("methods")
    List<StorageMethod> getStorageMethods();

    @JsonProperty("tips")
    List<String> getStorageTips();
}
