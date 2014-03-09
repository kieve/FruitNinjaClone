package ca.kieve.fruitninja.util;

public abstract class GameEntity {
    protected double m_x;
    protected double m_y;
    protected double m_vx;
    protected double m_vy;

    protected GameEntity(double x, double y) {
        m_x = x;
        m_y = y;
    }

    public double getX() { return m_x; }
    public double getY() { return m_y; }
    public int getXi() { return (int)m_x; }
    public int getYi() { return (int)m_y; }
    public void setPos(double x, double y) { setX(x); setY(y); }
    public void setX(double x) { m_x = x; }
    public void setY(double y) { m_y = y; }

    public void setVel(double vx, double vy) { setVX(vx); setVY(vy); }
    public void setVX(double vx) { m_vx = vx; }
    public void setVY(double vy) { m_vy = vy; }
}
