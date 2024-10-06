package com.project.service.enums;

public enum ServiceCommands {
    HELP("/help"),
    START("/cancel"),
    PROGRESS("/progress"),
    CANCEL("/cancel"),
    PROGRAMM("/programm");

    private final String cmd;

    ServiceCommands(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return cmd;
    }

    public boolean equals(String cmd) {
        return this.toString().equals(cmd);
    }
}
