package pl.dev4lazy.waste.utils;

import pl.dev4lazy.waste.interfaces.Value;

public class DoubleValue implements Value<Double> {

    private Double value;

    @Override
    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DoubleValue{" +
                "value=" + value +
                '}';
    }
}
