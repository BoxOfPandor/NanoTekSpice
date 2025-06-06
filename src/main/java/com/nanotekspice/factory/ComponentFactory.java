package com.nanotekspice.factory;

import com.nanotekspice.component.IComponent;
import com.nanotekspice.component.Input;
import com.nanotekspice.component.Output;
import com.nanotekspice.component.True;
import com.nanotekspice.component.False;
import com.nanotekspice.component.gates.*;

public class ComponentFactory {
    public IComponent createComponent(String type) {
        return switch (type) {
            case "input" -> new Input();
            case "output" -> new Output();
            case "true" -> new True();
            case "false" -> new False();
            case "and" -> new AndGate();
            case "or" -> new OrGate();
            case "xor" -> new XorGate();
            case "not" -> new NotGate();
            case "nand" -> new NandGate();
            case "nor" -> new NorGate();
            default -> throw new RuntimeException("Unknown component type: " + type);
        };
    }
}
