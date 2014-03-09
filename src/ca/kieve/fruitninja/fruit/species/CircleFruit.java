package ca.kieve.fruitninja.fruit.species;

import java.awt.Color;
import java.awt.Polygon;

import ca.kieve.fruitninja.fruit.Fruit;

public class CircleFruit extends Fruit {
    public CircleFruit(double x, double y) {
        super(new Polygon(
                new int[] { 20, 19, 15, 10,  3, -3, -10, -15, -19, -20, -19, -15, -10,  -3,   3,  10,  15, 19 },
                new int[] {  0,  7, 13, 17, 20, 20,  17,  13,   7,   0,  -7, -13, -17, -20, -20, -17, -13, -7 },
                18), Color.GREEN, x, y);
    }
}