package ca.kieve.fruitninja.util;

import java.awt.Color;
import java.awt.Graphics2D;

public class TimedParticle extends GameEntity implements GameObject {
    private long    m_lifeSpan;
    private boolean m_dead;
    private Color   m_color;

    public TimedParticle(long lifeSpan, Color color, double x, double y) {
        super(x, y);
        m_lifeSpan = lifeSpan;
        m_color = color;
    }

    public boolean isDead() { return m_dead; }
    public Color getColor() { return m_color; }

    @Override
    public void update(long dtMs) {
        if (m_dead) return;

        m_lifeSpan -= dtMs;
        if (m_lifeSpan < 0) {
            m_dead = true;
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (m_dead) return;

        g.setColor(m_color);
        g.fillOval((int)m_x, (int)m_y, 5, 5);
    }
}
