package com.project.service.enums;

public enum ServiceCommands {
    HELP("/help"),
    START("/cancel"),
    PROGRESS("/progress"),
    CANCEL("/cancel"),
    PROGRAM("/program");

    private final String value;

    ServiceCommands(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static  ServiceCommands fromValue(String v) {
        for (ServiceCommands c : ServiceCommands.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        return null;
    }
}
