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
        g.drawOval((int) (scale * (position.x - radius)), (int) (scale * (position.y - radius)), diameter, diameter);
        
//        if (response.isCollides()) {
//            g.setColor(Color.RED);
//            int x1 = (int) (scale * response.getContactPoint().x);
//            int y1 = (int) (scale * response.getContactPoint().y);
//            int x2 = (int) (scale * (response.getContactPoint().x + response.getContactNormal().x));
//            int y2 = (int) (scale * (response.getContactPoint().y + response.getContactNormal().y));
//            g.drawLine(x1, y1, x2, y2);
//        }
    }

    public void draw3D(Graphics2D g, double scale, double angle, Vec3 translate) {
        Camera.DA.set(position);
        Camera.DA.sub(translate);
        Camera.DA.rotateY(-angle);
        Camera.DA.scale(scale);
        Camera.DA.add(Camera.DISTANCE);
        
        Camera.DB.set(position);
        Camera.DB.sub(translate);
        Camera.DB.rotateY(-angle);
        Camera.DB.y -= radius;
        Camera.DB.scale(scale);
        Camera.DB.add(Camera.DISTANCE);

        
//        double ax = scale * (position.x - radius + translate.x);
//        double ay = scale * (position.y - radius + translate.y);
//        double ax2 = scale * (position.x + radius + translate.x);
//        double ay2 = scale * (position.y + radius + translate.y);
//        double az = scale * (position.z + translate.z);

        int sax = (int) (500 * (Camera.DA.x / -Camera.DA.z));
        int say = (int) (500 * (Camera.DA.y / -Camera.DA.z));
        int sax2 = (int) (500 * (Camera.DB.x / -Camera.DA.z));
        int say2 = (int) (500 * (Camera.DB.y / -Camera.DA.z));
        int sr = Math.abs(sax - sax2); // screen radius
        
        g.setColor(Color.BLACK);
        int radius = Math.abs(say - say2);
        int diameter = radius * 2;
        g.drawOval(sax - radius, say - radius, diameter, diameter);
        
        // draw contact point
        
    }
    
    @Override
    public String toString() {
        return "Sphere{" + "radius=" + radius + ", position=" + position + '}';
    }
    
}
