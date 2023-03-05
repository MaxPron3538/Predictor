package main.model;

public enum StatusCode {
    Ok("Ok"),
    BAD("Incorrect format email or password!"),
    ALREADY_EXIST("Account with such email already exist!"),
    NOT_EXIST("Incorrect data or you haven't yet account!");

    private final String text;

    StatusCode(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
