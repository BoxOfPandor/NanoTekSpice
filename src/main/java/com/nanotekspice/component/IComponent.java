package com.nanotekspice.component;

import com.nanotekspice.utils.Tristate;

interface IComponent {
    class Link {
        public final IComponent component;
        public final int pin;

        public Link(IComponent component, int pin) {
            this.component = component;
            this.pin = pin;
        }
    }

    void simulate(int tick);
    Tristate compute(int pin);
    void setLink(int pin, IComponent component, int otherPin);
}

