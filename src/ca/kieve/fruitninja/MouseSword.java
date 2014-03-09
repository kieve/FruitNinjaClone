package ca.kieve.fruitninja;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ca.kieve.fruitninja.util.GameObject;
import ca.kieve.fruitninja.util.TimedParticle;

public class MouseSword extends MouseAdapter implements GameObject {
    private boolean              m_mouseDown;
    private List<TimedParticle>  m_mouseTrail;
    private Point                m_currentPoint;

    public MouseSword() {
        m_mouseTrail = Collections.synchronizedList(new LinkedList<TimedParticle>());
    }

    public Point getCurrentPoint() {
        return m_currentPoint;
    }

    @Override
    public void update(long dtMs) {
        synchronized (m_mouseTrail) {
            Iterator<TimedParticle> it = m_mouseTrail.iterator();
            while (it.hasNext()) {
                TimedParticle particle = it.next();
                particle.update(dtMs);
                if (particle.isDead()) {
                    it.remove();
                }
            }
        }
    }

    private void updateMouse(MouseEvent e) {
        Color color = Color.BLUE;
        if (m_mouseDown) {
            m_currentPoint = e.getPoint();
            color = Color.RED;
        }
        Point p = e.getPoint();
        m_mouseTrail.add(new TimedParticle(150, color, p.x, p.y));
    }

    @Override
    public void render(Graphics2D g) {
        if (m_mouseTrail.size() == 0) return;

        synchronized(m_mouseTrail) {
            TimedParticle prev = null;
            for (int i = 0; i < m_mouseTrail.size(); i++) {
                TimedParticle cur = m_mouseTrail.get(i);
                if (prev != null) {
                    g.setColor(cur.getColor());
                    g.drawLine(prev.getXi(), prev.getYi(), cur.getXi(), cur.getYi());
                }
                prev = cur;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        updateMouse(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        updateMouse(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        m_mouseDown = true;
        m_currentPoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        m_mouseDown = false;
        m_currentPoint = null;
    }
}
