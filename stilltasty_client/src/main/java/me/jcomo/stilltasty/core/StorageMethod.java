package me.jcomo.stilltasty.core;

import static java.lang.Character.isDigit;

public class StorageMethod {
    private String location;
    private String expiration;
    private Long expirationTime;

    public StorageMethod(String location, String expiration) {
        this.location = location;
        this.expiration = expiration;
        this.expirationTime = parseExpirationTime();
    }

    public String getLocation() {
        return location;
    }

    public String getExpiration() {
        return expiration;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    /**
     * Parses the number value of the expiration time from the expiration label.
     * The expiration looks like eg. '3-4 days' and in this case would return
     * the value of 3 days in seconds.
     *
     * @return The number value of the expiration label in seconds
     */
    private Long parseExpirationTime() {
        int index = endIndexOfFirstNumber();
        if (index < 0) {
            return null;
        }

        int amount = Integer.parseInt(expiration.substring(0, index));
        long amountDaysInS = amount * 60 * 60 * 24;

        // Start by assuming the expiration is in days. Use 'contains' to account for plurals
        if (expiration.contains("week")) {
            amountDaysInS *= 7;
        } else if (expiration.contains("month")) {
            amountDaysInS *= 30;
        } else if (expiration.contains("year")) {
            amountDaysInS *= 365;
        }

        return amountDaysInS;
    }

    private int endIndexOfFirstNumber() {
        if (null == expiration || "".equals(expiration) || !isDigit(expiration.charAt(0))) {
            return -1;
        }

        for (int i = 0; i < expiration.length(); i++) {
            char c = expiration.charAt(i);
            if (!isDigit(c)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StorageMethod that = (StorageMethod) o;

        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        return !(expiration != null ? !expiration.equals(that.expiration) : that.expiration != null);

    }

    @Override
    public int hashCode() {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + (expiration != null ? expiration.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StorageMethod{" +
                "location='" + location + '\'' +
                ", expiration='" + expiration + '\'' +
                ", expirationTime=" + expirationTime +
                '}';
    }
}
