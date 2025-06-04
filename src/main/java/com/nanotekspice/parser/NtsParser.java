package com.nanotekspice.parser;

import com.nanotekspice.component.IComponent;
import com.nanotekspice.factory.ComponentFactory;

import java.io.*;
import java.util.*;

public class NtsParser {
    private final ComponentFactory factory;

    public NtsParser(ComponentFactory factory) {
        this.factory = factory;
    }

    public Map<String, IComponent> parse(File file) throws IOException {
        Map<String, IComponent> components = new HashMap<>();
        List<String> lines = readLines(file);

        boolean inChipsetSection = false;
        boolean inLinksSection = false;
        List<String> linkLines = new ArrayList<>();

        for (String rawLine : lines) {
            String line = rawLine.replaceAll("#.*", "").trim(); // Remove comments and trim
            if (line.isEmpty()) continue;

            switch (line) {
                case ".chipsets:" -> {
                    inChipsetSection = true;
                    inLinksSection = false;
                }
                case ".links:" -> {
                    inChipsetSection = false;
                    inLinksSection = true;
                }
                default -> {
                    if (inChipsetSection) {
                        String[] parts = line.split("\\s+");
                        if (parts.length != 2) throw new RuntimeException("Invalid component line: " + line);
                        String type = parts[0];
                        String name = parts[1];
                        if (components.containsKey(name)) throw new RuntimeException("Duplicate component: " + name);
                        IComponent component = factory.createComponent(type);
                        components.put(name, component);
                    } else if (inLinksSection) {
                        linkLines.add(line);
                    } else {
                        throw new RuntimeException("Line outside of any section: " + line);
                    }
                }
            }
        }

        // Now resolve links
        for (String linkLine : linkLines) {
            String[] parts = linkLine.split("\\s+");
            if (parts.length != 2) throw new RuntimeException("Invalid link line: " + linkLine);
            connect(parts[0], parts[1], components);
        }

        return components;
    }

    private void connect(String left, String right, Map<String, IComponent> components) {
        String[] leftParts = left.split(":");
        String[] rightParts = right.split(":");
        if (leftParts.length != 2 || rightParts.length != 2)
            throw new RuntimeException("Invalid link format: " + left + " - " + right);

        String leftName = leftParts[0];
        int leftPin = Integer.parseInt(leftParts[1]);
        String rightName = rightParts[0];
        int rightPin = Integer.parseInt(rightParts[1]);

        IComponent leftComp = components.get(leftName);
        IComponent rightComp = components.get(rightName);

        if (leftComp == null || rightComp == null)
            throw new RuntimeException("Unknown component in link: " + leftName + " or " + rightName);

        leftComp.setLink(leftPin, rightComp, rightPin);
        rightComp.setLink(rightPin, leftComp, leftPin); // Bidirectional
    }

    private List<String> readLines(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) lines.add(line);
        }
        return lines;
    }
}
