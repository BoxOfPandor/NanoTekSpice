package com.nanotekspice;

import com.nanotekspice.component.IComponent;
import com.nanotekspice.factory.ComponentFactory;
import com.nanotekspice.parser.NtsParser;

import java.io.File;
import java.util.Map;

public class NanoTekSpice {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: ./nanotekspice <file.nts>");
            System.exit(84);
        }

        File ntsFile = new File(args[0]);
        if (!ntsFile.exists() || !ntsFile.isFile()) {
            System.err.println("File not found: " + args[0]);
            System.exit(84);
        }

        try {
            ComponentFactory factory = new ComponentFactory();
            NtsParser parser = new NtsParser(factory);
            Map<String, IComponent> components = parser.parse(ntsFile);

            System.out.println("Composants chargés :");
            for (String name : components.keySet()) {
                System.out.println("- " + name + " (" + components.get(name).getClass().getSimpleName() + ")");
            }

        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            System.exit(84);
        }
    }
}
