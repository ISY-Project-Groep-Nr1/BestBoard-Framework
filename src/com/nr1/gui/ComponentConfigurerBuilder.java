package com.nr1.gui;

import com.nr1.interfaces.ComponentConfigurer;

import javax.swing.*;
import javax.swing.Box.Filler;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class ComponentConfigurerBuilder{
    private ArrayList<BiFunction<Container, JComponent, Container>> functions = new ArrayList<>();
    private ArrayList<BiConsumer<Container, JComponent>> postFunctions = new ArrayList<>();
    public ComponentConfigurerBuilder() {

    }

    public ComponentConfigurerBuilder horizontalCentered() {
        functions.add((parent, component) -> {
            component.setAlignmentX(Component.CENTER_ALIGNMENT);
            return parent;
        });
        return this;
    }

    public ComponentConfigurerBuilder verticalCentered() {
        functions.add((parent, component) -> {
            component.setAlignmentY(Component.CENTER_ALIGNMENT);
            return parent;
        });
        return this;
    }

    public ComponentConfigurerBuilder verticalTop() {
        functions.add((parent, component) -> {
            component.setAlignmentY(Component.TOP_ALIGNMENT);
            return parent;
        });
        return this;
    }

    public ComponentConfigurerBuilder swapParent(Container newParent) {
        functions.add((_, _) -> newParent);
        return this;
    }

    public ComponentConfigurerBuilder minSize(final int width, final int height) {
        functions.add((parent, component) -> {
            component.setMinimumSize(new Dimension(width, height));
            return parent;
        });
        return this;
    }

    public ComponentConfigurerBuilder maxSize(final int width, final int height) {
        functions.add((parent, component) -> {
            component.setMaximumSize(new Dimension(width, height));
            return parent;
        });
        return this;
    }

    public ComponentConfigurerBuilder preferredSize(final int width, final int height) {
        functions.add((parent, component) -> {
            component.setPreferredSize(new Dimension(width, height));
            return parent;
        });
        return this;
    }

    public ComponentConfigurerBuilder appendFiller(Dimension minSize, Dimension maxSize, Dimension preferredSize) {
        postFunctions.add((parent, component) -> {
            System.out.println(12);
            parent.add(new Filler(minSize, maxSize, preferredSize));
        });
        return this;
    }

    public ComponentConfigurer add() {
        functions.add((parent, component) -> {
            System.out.println(6);
            parent.add(component);
            return parent;
        });
        return build();
    }

    private ComponentConfigurer build() {
        return (parent, component) -> {
            for (BiFunction<Container, JComponent, Container> function : functions) {
                parent = function.apply(parent, component);
            }
            for (BiConsumer<Container, JComponent> function : postFunctions) {
                function.accept(parent, component);
            }
            System.out.println(parent.getComponent(0));
        };
    }


}
