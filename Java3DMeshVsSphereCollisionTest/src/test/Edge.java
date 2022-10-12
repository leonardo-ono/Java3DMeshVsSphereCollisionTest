package test;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Edge class.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com)
 */
public class Edge {

    private final Face face;
    private final Vec3 a;
    private final Vec3 b;
    
    // points to inside
    private final Vec3 normal = new Vec3();

    private final Vec3 vTmp1 = new Vec3();
    private final Vec3 vTmp2 = new Vec3();
    private final Vec3 vTmp3 = new Vec3();
        
    public Edge(Face face, Vec3 a, Vec3 b) {
        this.face = face;
        this.a = a;
        this.b = b;
        updateLengthAndNormal();
    }

    private void updateLengthAndNormal() {
        normal.set(b);
        normal.sub(a);
        normal.cross(face.getNormal());
        normal.normalize();
    }
    
    public Vec3 getA() {
        return a;
    }

    public Vec3 getB() {
        return b;
    }

    public boolean isInside(Vec3 contactPoint) {
        vTmp1.set(contactPoint);
        vTmp1.sub(a);
        return vTmp1.dot(normal) >= 0;
    }

    public Response checkCollision(Vec3 contactPoint, Sphere sphere, Response response) {
        vTmp1.set(contactPoint);
        vTmp1.sub(a);
        double dot = vTmp1.dot(normal);
        vTmp1.set(normal);
        vTmp1.scale(-dot);
        vTmp1.add(contactPoint);
        
        // keep contact point inside edge
        vTmp3.set(b);
        vTmp3.sub(a);
        vTmp2.set(vTmp1);
        vTmp2.sub(a);
        double dot1 = vTmp3.dot(vTmp3);
        double dot2 = vTmp3.dot(vTmp2);
        if (dot2 < 0) {
            vTmp1.set(a);
        }
        else if (dot2 > dot1) {
            vTmp1.set(b);
        }
        
        vTmp2.set(sphere.getPosition());
        vTmp2.sub(vTmp1);
        
        response.setCollides(vTmp2.getLength() <= sphere.getRadius());
        response.getContactPoint().set(vTmp1);
        response.getContactNormal().set(vTmp2);
        response.getContactNormal().normalize();
        response.getContactNormal().scale(sphere.getRadius() - vTmp2.getLength() + 0.000000001);
        response.setIsEdge(true);
        
        return response;
    }

    public void draw3D(Graphics2D g, double scale, double angle, Vec3 translate) {
        Camera.DA.set(a);
        Camera.DA.sub(translate);
        Camera.DA.rotateY(-angle);
        Camera.DA.scale(scale);
        Camera.DA.add(Camera.POSITION);
        
        Camera.DB.set(b);
        Camera.DB.sub(translate);
        Camera.DB.rotateY(-angle);
        Camera.DB.scale(scale);
        Camera.DB.add(Camera.POSITION);
        
        if (Camera.DA.z >= -0.1) return;
        if (Camera.DB.z >= -0.1) return;
        
        int sax = (int) (Camera.DISTANCE * (Camera.DA.x / -Camera.DA.z));
        int say = (int) (Camera.DISTANCE * (Camera.DA.y / -Camera.DA.z));
        int sbx = (int) (Camera.DISTANCE * (Camera.DB.x / -Camera.DB.z));
        int sby = (int) (Camera.DISTANCE * (Camera.DB.y / -Camera.DB.z));
        
        g.setColor(Color.RED);
        g.fillOval(sax - 3, say - 3, 6, 6);
        g.fillOval(sbx - 3, sby - 3, 6, 6);
        g.setColor(Color.BLACK);
        g.drawLine(sax, say, sbx, sby);
    }

    @Override
    public String toString() {
        return "Edge{" + "face=" + face + ", a=" + a + ", b=" + b 
            + ", normal=" + normal + '}';
    }
    
}
