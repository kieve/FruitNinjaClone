package ca.kieve.fruitninja;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class DrawArea extends Canvas{
    private static final long serialVersionUID = -1897627374269409328L;

    public void createBuffer() {
        createBufferStrategy(2);
    }

    public Graphics2D getGraphics(){
        return (Graphics2D) getBufferStrategy().getDrawGraphics();
    }

    public void update(){
        BufferStrategy bs = getBufferStrategy();
        if(!bs.contentsLost()){
            bs.show();
        }
    }
}