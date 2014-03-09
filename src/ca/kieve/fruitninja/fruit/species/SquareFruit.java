package ca.kieve.fruitninja.fruit.species;

import java.awt.Color;
import java.awt.Polygon;

import ca.kieve.fruitninja.fruit.Fruit;

public class SquareFruit extends Fruit {
    public SquareFruit(double x, double y) {
        super(new Polygon(
                new int[] { -20, 20,  20, -20},
                new int[] {  20, 20, -20, -20},
                4), Color.RED, x, y);
    }
}
