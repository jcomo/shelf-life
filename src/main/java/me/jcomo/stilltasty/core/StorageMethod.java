package me.jcomo.stilltasty.core;

public class StorageMethod {
    private String location;
    private String expiration;

    public StorageMethod(String location, String expiration) {
        this.location = location;
        this.expiration = expiration;
    }

    public String getLocation() {
        return location;
    }

    public String getExpiration() {
        return expiration;
    }

    @Override
    public String toString() {
        return "StorageMethod{" +
                "location='" + location + '\'' +
                ", expiration='" + expiration + '\'' +
                '}';
    }
}
