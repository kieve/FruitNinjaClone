package ca.kieve.fruitninja.fruit;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import ca.kieve.fruitninja.FruitNinja;
import ca.kieve.fruitninja.fruit.species.CircleFruit;
import ca.kieve.fruitninja.fruit.species.SquareFruit;
import ca.kieve.fruitninja.util.GameObject;

public class FruitBasket implements GameObject {
    private final int   m_fruitPerTick;
    private final long  m_tickPeriod;

    private long        m_timeSinceLastFruit;
    private Random      m_random;
    private List<Fruit> m_fruit;

    public FruitBasket(int fruitPerTick, int ticksPerSecond) {
        m_fruitPerTick = fruitPerTick;
        m_tickPeriod = 1000 / ticksPerSecond;

        m_random = new Random();
        m_fruit = new ArrayList<Fruit>(30);
    }

    public int attemptCuts(Point point) {
        int totalCut = 0;
        Iterator<Fruit> it = m_fruit.iterator();
        while (it.hasNext()) {
            Fruit fruit = it.next();
            if (point == null) {
                fruit.resetCuts();
                continue;
            }
            boolean sliced = fruit.applyCut(point);
            if (sliced) {
                totalCut++;
                it.remove();
                // TODO: Add the pieces and adjust the velocities
            }
        }
        return totalCut;
    }

    public void addFruit(int num) {
        for (int i = 0; i < num; i++) {
            addFruit();
        }
    }

    public void addFruit() {
        boolean leftSide = m_random.nextInt(2) == 1;

        double x = m_random.nextDouble() * (FruitNinja.WIDTH / 2);
        if (!leftSide) x += FruitNinja.WIDTH / 2;

        double y = FruitNinja.HEIGHT + 100;

        Fruit fruit;
        switch (m_random.nextInt(2)) {
        case 1:
            fruit = new CircleFruit(x, y);
            break;
        case 0:
        default:
            fruit = new SquareFruit(x, y);
        }

        fruit.setVel((leftSide ? 1 : -1) * m_random.nextDouble() * 0.1,
                     m_random.nextDouble() * -0.1 - 0.25);
        m_fruit.add(fruit);
    }

    @Override
    public void update(long dtMs) {
        Iterator<Fruit> it = m_fruit.iterator();
        while (it.hasNext()) {
            Fruit fruit = it.next();
            fruit.update(dtMs);
            if (fruit.getX() > FruitNinja.WIDTH) {
                it.remove();
            } else if (fruit.getHighestPoint() < 300
                    && fruit.getY() > FruitNinja.HEIGHT) {
                it.remove();
            }
        }
        m_timeSinceLastFruit += dtMs;
        if (m_timeSinceLastFruit >= m_tickPeriod) {
            addFruit(m_fruitPerTick);
            m_timeSinceLastFruit -= m_tickPeriod;
        }
    }

    @Override
    public void render(Graphics2D g) {
        for (Fruit fruit : m_fruit) {
            fruit.render(g);
        }
    }
}
