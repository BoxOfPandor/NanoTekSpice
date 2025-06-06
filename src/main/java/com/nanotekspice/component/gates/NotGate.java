package com.nanotekspice.component.gates;

import com.nanotekspice.component.IComponent;
import com.nanotekspice.utils.Tristate;

import java.util.HashMap;
import java.util.Map;

public class NotGate implements IComponent {
    private final Map<Integer, Pin> pins = new HashMap<>();
    private long lastTick = -1;

    public NotGate() {
        pins.put(1, new Pin()); // Input
        pins.put(2, new Pin()); // Output
    }

    public void simulate(long tick) {
        if (lastTick == tick) return;
        lastTick = tick;

        Tristate input = getInputValue(1, tick);
        Tristate result = not(input);

        pins.get(2).value = result;
    }

    @Override
    public void simulate(int tick) {
        simulate((long) tick);
    }

    @Override
    public Tristate compute(int pin) {
        if (pin == 2) return pins.get(2).value;
        throw new IllegalArgumentException("Cannot compute input pin " + pin);
    }

    @Override
    public void setLink(int pin, IComponent other, int otherPin) {
        if (!pins.containsKey(pin)) throw new IllegalArgumentException("Invalid pin: " + pin);
        pins.get(pin).link = new Link(other, otherPin);
    }

    private Tristate getInputValue(int pin, long tick) {
        Link link = pins.get(pin).link;
        if (link == null) return Tristate.UNDEFINED;
        link.component.simulate((int)tick);
        return link.component.compute(link.pin);
    }

    private Tristate not(Tristate input) {
        return switch (input) {
            case TRUE -> Tristate.FALSE;
            case FALSE -> Tristate.TRUE;
            case UNDEFINED -> Tristate.UNDEFINED;
        };
    }

    private static class Pin {
        Tristate value = Tristate.UNDEFINED;
        Link link = null;
    }
}