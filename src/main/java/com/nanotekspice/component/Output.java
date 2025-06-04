package com.nanotekspice.component;

import com.nanotekspice.utils.Tristate;

public class Output implements IComponent {
    private Link inputLink = null;
    private static final int INPUT_PIN = 1;

    @Override
    public void simulate(int tick) {
        if (inputLink != null) {
            inputLink.component.simulate(tick);
        }
    }

    @Override
    public Tristate compute(int pin) {
        if (pin != INPUT_PIN) {
            throw new IllegalArgumentException("Invalid pin number: " + pin);
        }
        
        if (inputLink == null) {
            return Tristate.UNDEFINED;
        }

        return inputLink.component.compute(inputLink.pin);
    }

    @Override
    public void setLink(int pin, IComponent component, int otherPin) {
        if (pin != INPUT_PIN) {
            throw new IllegalArgumentException("Invalid pin number for Output: " + pin);
        }

        if (component == null) {
            throw new IllegalArgumentException("Cannot link to null component");
        }

        this.inputLink = new Link(component, otherPin);
    }
}
