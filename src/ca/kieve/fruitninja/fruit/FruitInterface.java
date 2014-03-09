package ca.kieve.fruitninja.fruit;

import java.awt.Point;
import java.util.List;

public interface FruitInterface {
    public boolean applyCut(Point point);
    public List<Fruit> getPieces();
    public void resetCuts();
}
