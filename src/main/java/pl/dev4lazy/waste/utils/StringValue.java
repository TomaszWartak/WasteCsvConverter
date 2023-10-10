package pl.dev4lazy.waste.utils;

import pl.dev4lazy.waste.interfaces.Value;

public class StringValue implements Value<String> {

    private String value;

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "StringValue{" +
                "value='" + value + '\'' +
                '}';
    }
}
