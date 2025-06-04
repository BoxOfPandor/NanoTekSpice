package com.nanotekspice.component;

import com.nanotekspice.utils.Tristate;
import java.util.HashMap;
import java.util.Map;

public class Input implements IComponent
{
    private Tristate value = Tristate.UNDEFINED;
    private final Map<Integer, Link> links = new HashMap<>();

    public void setValue(Tristate value)
    {
        this.value = value;
    }

    @Override
    public void simulate(int tick) {}

    @Override
    public Tristate compute(int pin)
    {
        return value;
    }

    @Override
    public void setLink(int pin, IComponent component, int otherPin)
    {
        links.put(pin, new Link(component, otherPin));
    }
}
