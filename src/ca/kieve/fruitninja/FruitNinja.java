package ca.kieve.fruitninja;

import javax.swing.JFrame;

public class FruitNinja {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static void main(String ...args) {
        JFrame frame = new JFrame("Fruit Ninja!");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Engine engine = new Engine(60);
        frame.add(engine.getDrawArea());

        engine.start();
    }
}
