package physics;


import java.awt.Color;
import java.awt.Graphics2D;


/**
 *
 * @author admin
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
        Camera.da.set(position);
        Camera.da.sub(translate);
        Camera.da.rotateY(-angle);
        Camera.da.scale(scale);
        Camera.da.add(Camera.camera);
        
        Camera.db.set(position);
        Camera.db.sub(translate);
        Camera.db.rotateY(-angle);
        Camera.db.y -= radius;
        Camera.db.scale(scale);
        Camera.db.add(Camera.camera);

        
//        double ax = scale * (position.x - radius + translate.x);
//        double ay = scale * (position.y - radius + translate.y);
//        double ax2 = scale * (position.x + radius + translate.x);
//        double ay2 = scale * (position.y + radius + translate.y);
//        double az = scale * (position.z + translate.z);

        int sax = (int) (500 * (Camera.da.x / -Camera.da.z));
        int say = (int) (500 * (Camera.da.y / -Camera.da.z));
        int sax2 = (int) (500 * (Camera.db.x / -Camera.da.z));
        int say2 = (int) (500 * (Camera.db.y / -Camera.da.z));
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
