package ca.kieve.fruitninja.fruit;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

import ca.kieve.fruitninja.util.GameEntity;
import ca.kieve.fruitninja.util.GameObject;

public abstract class Fruit extends GameEntity implements GameObject, FruitInterface {
    private double   m_highestPoint;

    private Polygon  m_originalBounds;
    private Polygon  m_currentBounds; // Most likely rotated
    private Color    m_color;

    private Point       m_entryCut;
    private List<Point> m_internalCuts;
    private Point       m_exitCut;

    protected Fruit(Polygon originalBounds, Color color, double x, double y) {
        super(x, y);
        m_originalBounds = originalBounds;
        m_currentBounds = m_originalBounds;
        m_color = color;
        m_highestPoint = y;
    }

    public double getHighestPoint() { return m_highestPoint; }

    @Override
    public void update(long dtMs) {
        m_x += m_vx * dtMs;
        m_y += m_vy * dtMs;

        // gravity
        m_vy += 0.1 * dtMs / 1000;

        m_highestPoint = Math.min(m_y, m_highestPoint);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(m_color);
        g.translate(m_x, m_y);
        g.fillPolygon(m_currentBounds);
        g.translate(-m_x, -m_y);
    }

    @Override
    public boolean applyCut(Point oldPoint) {
        // Adjust the point to be relative to the polygon bounds
        Point newPoint = (Point) oldPoint.clone();
        newPoint.x -= m_x;
        newPoint.y -= m_y;

        if (m_entryCut == null) {
            m_entryCut = newPoint;
            return false;
        }
        if (m_currentBounds.contains(newPoint)) {
            if (m_internalCuts == null) {
                m_internalCuts = new ArrayList<Point>();
            }
            m_internalCuts.add(newPoint);

            return false;
        }
        m_exitCut = newPoint;
        if (m_internalCuts == null) {
            Line2D cut = new Line2D.Double(m_entryCut, m_exitCut);
            List<Line2D> segments = getSegments(new Area(m_currentBounds));
            for (Line2D line : segments) {
                if (cut.intersectsLine(line)) {
                    resetCuts();
                    return true;
                }
            }
            resetCuts();
            return false;
        }
        // TODO: Calculate the path and create the new pieces
        return true;
    }

    @Override
    public List<Fruit> getPieces() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void resetCuts() {
        m_entryCut = null;
        m_internalCuts = null;
        m_exitCut = null;
    }

    private List<Line2D> getSegments(Area area) {
        ArrayList<double[]> areaPoints = new ArrayList<double[]>();
        ArrayList<Line2D> areaSegments = new ArrayList<Line2D>();
        double[] coords = new double[6];

        for (PathIterator pi = area.getPathIterator(null); !pi.isDone(); pi.next()) {
            // The type will be SEG_LINETO, SEG_MOVETO, or SEG_CLOSE
            // Because the Area is composed of straight lines
            int type = pi.currentSegment(coords);
            // We record a double array of {segment type, x coord, y coord}
            double[] pathIteratorCoords = {type, coords[0], coords[1]};
            areaPoints.add(pathIteratorCoords);
        }

        double[] start = new double[3]; // To record where each polygon starts

        for (int i = 0; i < areaPoints.size(); i++) {
            // If we're not on the last point, return a line from this point to the next
            double[] currentElement = areaPoints.get(i);

            // We need a default value in case we've reached the end of the ArrayList
            double[] nextElement = {-1, -1, -1};
            if (i < areaPoints.size() - 1) {
                nextElement = areaPoints.get(i + 1);
            }

            // Make the lines
            if (currentElement[0] == PathIterator.SEG_MOVETO) {
                start = currentElement; // Record where the polygon started to close it later
            }

            if (nextElement[0] == PathIterator.SEG_LINETO) {
                areaSegments.add(
                        new Line2D.Double(
                            currentElement[1], currentElement[2],
                            nextElement[1], nextElement[2]
                        )
                    );
            } else if (nextElement[0] == PathIterator.SEG_CLOSE) {
                areaSegments.add(
                        new Line2D.Double(
                            currentElement[1], currentElement[2],
                            start[1], start[2]
                        )
                    );
            }
        }
        return areaSegments;
    }
}
