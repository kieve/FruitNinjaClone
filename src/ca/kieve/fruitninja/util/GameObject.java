package ca.kieve.fruitninja.util;

import java.awt.Graphics2D;

public interface GameObject {
    public void update(long dtMs);
    public void render(Graphics2D g);
}
