package ca.kieve.fruitninja;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;

import ca.kieve.fruitninja.fruit.FruitBasket;

public class Engine {
    private final DrawArea m_drawArea;
    private final long     m_framePeriod;

    private MouseSword     m_mouseSword;
    private FruitBasket    m_fruitBasket;

    public Engine(long fps) {
        m_drawArea = new DrawArea();
        m_framePeriod = 1000 / fps;
    }

    public Canvas getDrawArea() {
        return m_drawArea;
    }

    public void start() {
        m_drawArea.createBuffer();

        new Thread(new Runnable(){
            @Override
            public void run() {
                gameLoop();
            }
        }).run();
    }

    private void initGame() {
        m_fruitBasket = new FruitBasket(2, 2);
        m_mouseSword = new MouseSword();
        m_drawArea.addMouseListener(m_mouseSword);
        m_drawArea.addMouseMotionListener(m_mouseSword);
    }

    public void gameLoop() {
        initGame();

        long startMs = System.currentTimeMillis();
        long endMs = startMs;
        long dtMs = 0;
        while(true) {
            startMs = System.currentTimeMillis();
            dtMs = startMs - endMs;

            update(dtMs);
            Graphics2D g = m_drawArea.getGraphics();
            render(g);
            g.dispose();
            m_drawArea.update();

            endMs = System.currentTimeMillis();
            long sleepTime = m_framePeriod - (endMs - startMs);
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    System.err.println("Thread interrupted while attempting to sleep.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Can't keep up: " + sleepTime);
            }
        }
    }

    public void update(long dtMs) {
        m_fruitBasket.attemptCuts(m_mouseSword.getCurrentPoint());
        m_fruitBasket.update(dtMs);
        m_mouseSword.update(dtMs);
    }

    public void render(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, m_drawArea.getWidth(), m_drawArea.getHeight());
        m_fruitBasket.render(g);
        m_mouseSword.render(g);
    }
}
