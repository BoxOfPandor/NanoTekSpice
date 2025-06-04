package com.nanotekspice.factory;

import com.nanotekspice.component.IComponent;
import com.nanotekspice.component.Input;
import com.nanotekspice.component.Output;
import com.nanotekspice.component.True;
import com.nanotekspice.component.False;

public class ComponentFactory {
    public IComponent createComponent(String type) {
        return switch (type) {
            case "input" -> new Input();
            case "output" -> new Output();
            case "true" -> new True();
            case "false" -> new False();
            default -> throw new RuntimeException("Unknown component type: " + type);
        };
    }
}
