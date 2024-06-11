package org.example.service.enums;

public enum ServiceCommands {
    HELP("/help"),
    CANCEL("/cancel"),
    START("/start"),
    WORKOUT_PROGRAM("/program"),
    SURVEY("/survey");

    private final String cmd;

    ServiceCommands(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return cmd;
    }

    public boolean equals(String cmd) {
        return this.cmd.equals(cmd);
    }
}
