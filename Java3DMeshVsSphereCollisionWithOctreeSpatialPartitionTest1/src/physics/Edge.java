package physics;


import java.awt.Color;
import java.awt.Graphics2D;


/**
 *
 * @author admin
 */
public class Edge {

    private final Face face;
    private final Vec3 a;
    private final Vec3 b;
    private double length;
    
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
        length = normal.getLength();
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
        response.getContactNormal().scale(sphere.getRadius() - vTmp2.getLength());
        
        return response;
    }

    public void draw(Graphics2D g, double scale) {
        g.setColor(Color.BLACK);
        g.drawLine((int) (scale * a.x), (int) (scale * a.y), (int) (scale * b.x), (int) (scale * b.y));
    }

    private final Vec3 da = new Vec3();
    private final Vec3 db = new Vec3();
    private final Vec3 camera = new Vec3(0, -50, -500);
    
    public void draw3D(Graphics2D g, double scale, double angle, Vec3 translate) {
        
        da.set(a);
        da.sub(translate);
        da.rotateY(-angle);
        da.scale(scale);
        da.add(camera);
        
        db.set(b);
        db.sub(translate);
        db.rotateY(-angle);
        db.scale(scale);
        db.add(camera);
        
        if (da.z >= -0.1) return;
        if (db.z >= -0.1) return;
        
//        double ax = (scale * (a.x + translate.x)); 
//        double ay = (scale * (a.y + translate.y));
//        double az = (scale * (a.z + translate.z));
//        double bx = (scale * (b.x + translate.x));
//        double by = (scale * (b.y + translate.y));
//        double bz = (scale * (b.z + translate.z));
        
        int sax = (int) (500 * (da.x / -da.z));
        int say = (int) (500 * (da.y / -da.z));
        int sbx = (int) (500 * (db.x / -db.z));
        int sby = (int) (500 * (db.y / -db.z));
        
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
