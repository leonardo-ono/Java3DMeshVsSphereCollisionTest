package physics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class Face {
    
    private final Vec3 normal = new Vec3();
    private final List<Vec3> points = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();
    private final Response response = new Response();
    
    public Face() {
    }

    public Vec3 getNormal() {
        return normal;
    }

    public List<Vec3> getPoints() {
        return points;
    }
    
    public void addPoint(Vec3 point) {
        points.add(point);
    }
    
    public void addPoint(double x, double y, double z) {
        points.add(new Vec3(x, y, z));
    }
    
    public void close() {
        Vec3 v1 = new Vec3(points.get(0));
        v1.sub(points.get(1));
        Vec3 v2 = new Vec3(points.get(2));
        v2.sub(points.get(1));
        normal.set(v1);
        normal.cross(v2);
        normal.normalize();
        
        for (int i = 0; i < points.size(); i++) {
            Vec3 a = points.get(i);
            Vec3 b = points.get((i + 1) % points.size());
            Edge edge = new Edge(this, a, b);
            edges.add(edge);
            //System.out.println("edge: " + edge);
        }
    }
    private final Vec3 vTmp = new Vec3();
    
    public Response checkCollision(Sphere sphere) {
        vTmp.set(sphere.getPosition()); // contact point in the triangle plane
        vTmp.sub(points.get(0));
        double dot = vTmp.dot(normal);
        
        vTmp.set(normal);
        vTmp.scale(-dot);
        vTmp.add(sphere.getPosition());
        
        //System.out.println("contact point: " + vTmp);
        
        for (Edge edge : edges) {
            if (!edge.isInside(vTmp)) {
                return edge.checkCollision(vTmp, sphere, response);
            }
        }
        
        response.setCollides(Math.abs(dot) <= sphere.getRadius());
        response.getContactPoint().set(vTmp);
        response.getContactNormal().set(normal);
        response.getContactNormal().scale(sphere.getRadius() - dot);
        
        return response;
    }

    public void draw(Graphics2D g, double scale) {
        for (Edge edge : edges) {
            edge.draw(g, scale);
        }
    }
    
    
    public void draw3D(Graphics2D g, double scale, double angle, Vec3 translate) {
        for (Edge edge : edges) {
            edge.draw3D(g, scale, angle, translate);
        }

        if (response.isCollides()) {
            
            Camera.da.set(response.getContactPoint());
            Camera.da.sub(translate);
            Camera.da.rotateY(-angle);
            Camera.da.scale(scale);
            Camera.da.add(Camera.camera);
        
            int cpx = (int) (500 * (Camera.da.x / -Camera.da.z));
            int cpy = (int) (500 * (Camera.da.y / -Camera.da.z));
            
            g.setColor(Color.ORANGE);
            g.fillOval(cpx - 5, cpy - 5, 10, 10);
            
//            double x1 = (scale * response.getContactPoint().x);
//            double y1 = (scale * response.getContactPoint().y);
//            double z1 = (scale * response.getContactPoint().z);
//            double x2 = (scale * (response.getContactPoint().x + response.getContactNormal().x));
//            double y2 = (scale * (response.getContactPoint().y + response.getContactNormal().y));
//            double z2 = (scale * (response.getContactPoint().z + response.getContactNormal().z));
//            
//            int csx1 = (int) (500 * (x1 / -z1));
//            int csy1 = (int) (500 * (y1 / -z1));
//            int csx2 = (int) (500 * (x2 / -z2));
//            int csy2 = (int) (500 * (y2 / -z2));
//            g.drawLine(csx1, csy1, csx2, csy2);
        }
        
    } 
 
    //private final Color color = Color.BLUE;
    private final Polygon polygonTmp = new Polygon();
    
    public void drawTop(Graphics2D g, Color color) {
        g.setColor(color);
        polygonTmp.reset();
        polygonTmp.addPoint((int) points.get(0).x, (int) points.get(0).z);
        polygonTmp.addPoint((int) points.get(1).x, (int) points.get(1).z);
        polygonTmp.addPoint((int) points.get(2).x, (int) points.get(2).z);
        g.draw(polygonTmp);
    }

    public void drawFront(Graphics2D g, Color color) {
        g.setColor(color);
        polygonTmp.reset();
        polygonTmp.addPoint((int) points.get(0).x, (int) points.get(0).y);
        polygonTmp.addPoint((int) points.get(1).x, (int) points.get(1).y);
        polygonTmp.addPoint((int) points.get(2).x, (int) points.get(2).y);
        g.draw(polygonTmp);
    }
    
}
