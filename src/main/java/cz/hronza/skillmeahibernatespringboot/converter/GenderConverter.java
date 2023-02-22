package cz.hronza.skillmeahibernatespringboot.converter;

import cz.hronza.skillmeahibernatespringboot.enums.Gender;

import javax.persistence.AttributeConverter;

public class GenderConverter implements AttributeConverter<Gender, Character> {
    @Override
    public Character convertToDatabaseColumn(Gender gender) {
        return gender != null ? gender.getCode() : null;

    }

    @Override
    public Gender convertToEntityAttribute(Character code) {
        if (code != null && Character.toUpperCase(code) == 'M') {
            return Gender.MAN;
        } else if (code != null && Character.toUpperCase(code) == 'W') {
            return Gender.WOMAN;
        } else throw new UnsupportedOperationException(String.format("unknown Gender: %s ", code));
    }
}
