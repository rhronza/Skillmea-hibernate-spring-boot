package cz.hronza.skillmeahibernatespringboot.enums;

public enum Gender {
    MAN('M'),
    WOMAN('W');

    private final Character code;

    Gender(char code) {
        this.code = code;
    }

    public Character getCode() {
        return code;
    }
}
