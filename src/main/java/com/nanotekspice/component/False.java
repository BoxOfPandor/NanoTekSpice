package com.nanotekspice.component;

import com.nanotekspice.utils.Tristate;

public class False implements IComponent {
    private static final int OUTPUT_PIN = 1;

    @Override
    public void simulate(int tick) {}

    @Override
    public Tristate compute(int pin) {
        if (pin != OUTPUT_PIN) {
            throw new IllegalArgumentException("Invalid pin number: " + pin);
        }
        return Tristate.FALSE;
    }

    @Override
    public void setLink(int pin, IComponent component, int otherPin) {
        if (pin != OUTPUT_PIN) {
            throw new IllegalArgumentException("Invalid pin number for False: " + pin);
        }
    }
}
