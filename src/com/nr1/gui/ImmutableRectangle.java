package com.nr1.gui;

public record ImmutableRectangle(
        float x,
        float y,
        float width,
        float height
) {
    ImmutablePoint getPointTopLeft() {
        return new ImmutablePoint(x, y);
    }

    ImmutablePoint getPointTopRight() {
        return new ImmutablePoint(x + width, y);
    }

    ImmutablePoint getPointBottomLeft() {
        return new ImmutablePoint(x, y + height);
    }

    ImmutablePoint getPointBottomRight() {
        return new ImmutablePoint(x + width, y + height);
    }
}
