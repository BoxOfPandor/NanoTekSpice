package com.nanotekspice.component.gates;

import com.nanotekspice.component.IComponent;
import com.nanotekspice.utils.Tristate;

import java.util.HashMap;
import java.util.Map;

public class AndGate implements IComponent {
    private final Map<Integer, Pin> pins = new HashMap<>();
    private long lastTick = -1;

    public AndGate() {
        pins.put(1, new Pin());
        pins.put(2, new Pin());
        pins.put(3, new Pin());
    }

    public void simulate(long tick) {
        if (lastTick == tick) return;
        lastTick = tick;

        Tristate a = getInputValue(1, tick);
        Tristate b = getInputValue(2, tick);
        Tristate result = and(a, b);

        pins.get(3).value = result;
    }

    @Override
    public void simulate(int tick) {
        simulate((long) tick);
    }

    @Override
    public Tristate compute(int pin) {
        if (pin == 3) return pins.get(3).value;
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

    private Tristate and(Tristate a, Tristate b) {
        if (a == Tristate.FALSE || b == Tristate.FALSE) return Tristate.FALSE;
        if (a == Tristate.UNDEFINED || b == Tristate.UNDEFINED) return Tristate.UNDEFINED;
        return Tristate.TRUE;
    }

    private static class Pin {
        Tristate value = Tristate.UNDEFINED;
        Link link = null;
    }

    private record Link(IComponent component, int pin) {}
}
