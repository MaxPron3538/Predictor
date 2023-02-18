package main.model;

public enum StatusCode {
    BAD("Incorrect input data!"),
    NOTEXIST("You have not yet account!");

    private final String text;

    StatusCode(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
