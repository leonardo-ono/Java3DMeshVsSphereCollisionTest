package test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Face class.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com)
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
    
    public void draw3D(Graphics2D g, double scale, double angle, Vec3 translate) {
        edges.forEach((edge) -> {
            edge.draw3D(g, scale, angle, translate);
        });

        if (response.isCollides()) {
            
            Camera.DA.set(response.getContactPoint());
            Camera.DA.sub(translate);
            Camera.DA.rotateY(-angle);
            Camera.DA.scale(scale);
            Camera.DA.add(Camera.POSITION);
        
            int cpx = (int) (Camera.DISTANCE * (Camera.DA.x / -Camera.DA.z));
            int cpy = (int) (Camera.DISTANCE * (Camera.DA.y / -Camera.DA.z));
            
            g.setColor(Color.ORANGE);
            g.fillOval(cpx - 5, cpy - 5, 10, 10);
        }
    } 
    
}
