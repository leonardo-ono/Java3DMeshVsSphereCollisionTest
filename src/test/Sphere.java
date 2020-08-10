package test;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Sphere class.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com)
 */
public class Sphere {

    private double radius;
    private Vec3 position = new Vec3();
    
    public Sphere() {
    }

    public Sphere(double radius) {
        this.radius = radius;
    }

    public Sphere(double radius, double x, double y, double z) {
        this.radius = radius;
        this.position.set(x, y, z);
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Vec3 getPosition() {
        return position;
    }

    public void setPosition(Vec3 position) {
        this.position = position;
    }

    public void draw(Graphics2D g, double scale) {
        g.setColor(Color.BLACK);
        int diameter = (int) (2 * radius * scale);
        int ox = (int) (scale * (position.x - radius));
        int oy = (int) (scale * (position.y - radius));
        g.drawOval(ox, oy, diameter, diameter);
    }

    public void draw3D(Graphics2D g, double scale, double angle, Vec3 translate) {
        Camera.DA.set(position);
        Camera.DA.sub(translate);
        Camera.DA.rotateY(-angle);
        Camera.DA.scale(scale);
        Camera.DA.add(Camera.POSITION);
        
        Camera.DB.set(position);
        Camera.DB.sub(translate);
        Camera.DB.rotateY(-angle);
        Camera.DB.y -= radius;
        Camera.DB.scale(scale);
        Camera.DB.add(Camera.POSITION);
        
        int sax = (int) (Camera.DISTANCE * (Camera.DA.x / -Camera.DA.z));
        int say = (int) (Camera.DISTANCE * (Camera.DA.y / -Camera.DA.z));
        int say2 = (int) (Camera.DISTANCE * (Camera.DB.y / -Camera.DA.z));
        
        int sRadius = Math.abs(say - say2);
        int diameter = sRadius * 2;
        g.setColor(Color.BLACK);
        g.drawOval(sax - sRadius, say - sRadius, diameter, diameter);
    }
    
    @Override
    public String toString() {
        return "Sphere{" + "radius=" + radius + ", position=" + position + '}';
    }
    
}
