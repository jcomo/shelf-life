package me.jcomo.foodie.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import me.jcomo.stilltasty.core.StorageGuide;
import me.jcomo.stilltasty.core.StorageMethod;

import java.io.IOException;

public class StorageGuideSerializer extends JsonSerializer<StorageGuide> {
    @Override
    public Class<StorageGuide> handledType() {
        return StorageGuide.class;
    }

    @Override
    public void serialize(StorageGuide guide, JsonGenerator json, SerializerProvider serializerProvider) throws IOException {
        json.writeStartObject();
        writeFood(guide, json);
        writeStorageMethods(guide, json);
        writeStorageTips(guide, json);
        json.writeEndObject();
    }

    private void writeFood(StorageGuide guide, JsonGenerator json) throws IOException {
        json.writeStringField("name", guide.getFood());
    }

    private void writeStorageMethods(StorageGuide guide, JsonGenerator json) throws IOException {
        json.writeArrayFieldStart("methods");
        for (StorageMethod method : guide.getStorageMethods()) {
            json.writeObject(method);
        }
        json.writeEndArray();
    }

    private void writeStorageTips(StorageGuide guide, JsonGenerator json) throws IOException {
        json.writeArrayFieldStart("tips");
        for (String tip : guide.getStorageTips()) {
            json.writeString(tip);
        }
        json.writeEndArray();
    }
}
