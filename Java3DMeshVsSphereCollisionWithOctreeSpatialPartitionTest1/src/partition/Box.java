package partition;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import physics.Face;
import physics.Vec3;

/**
 *
 * @author admin
 */
public class Box {

    protected final Vec3 min = new Vec3();
    protected final Vec3 max = new Vec3();
    
    
    protected final Vec3 length = new Vec3();

    protected Color color;
    
    private Face face;
    
    public Box() {
    }

    public Box(Face face, Color color) {
        setInternal(face, color);
    }

    public void set(Face face) {
        setInternal(face, color);
    }

    private void setInternal(Face face, Color color) {
        this.color = color;
        this.face = face;
        
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double minZ = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE;
        double maxY = -Double.MAX_VALUE;
        double maxZ = -Double.MAX_VALUE;
        for (Vec3 point : face.getPoints()) {
            if (point.x < minX) {
                minX = point.x;
            }
            if (point.y < minY) {
                minY = point.y;
            }
            if (point.z < minZ) {
                minZ = point.z;
            }
            
            if (point.x > maxX) {
                maxX = point.x;
            }
            if (point.y > maxY) {
                maxY = point.y;
            }
            if (point.z > maxZ) {
                maxZ = point.z;
            }
        }
        min.set(minX, minY, minZ);
        max.set(maxX, maxY, maxZ);

        length.set(max);
        length.sub(min);
        length.x = Math.abs(length.x);
        length.y = Math.abs(length.y);
        length.z = Math.abs(length.z);
    }
        
    public Box(Vec3 min, Vec3 max, Color color) {
        this.color = color;
        this.min.set(min);
        this.max.set(max);
        
        length.set(max);
        length.sub(min);
        length.x = Math.abs(length.x);
        length.y = Math.abs(length.y);
        length.z = Math.abs(length.z);
    }
    
    public void set(Vec3 min, Vec3 max) {
        this.min.set(min);
        this.max.set(max);
        
        length.set(max);
        length.sub(min);
        length.x = Math.abs(length.x);
        length.y = Math.abs(length.y);
        length.z = Math.abs(length.z);
    }

    public void updateLength() {
        length.set(max);
        length.sub(min);
        length.x = Math.abs(length.x);
        length.y = Math.abs(length.y);
        length.z = Math.abs(length.z);
    }
    
    public Vec3 getMin() {
        return min;
    }

    public Vec3 getMax() {
        return max;
    }

    public Vec3 getLength() {
        return length;
    }

    public boolean intersects(Box box) {
        double xt1 = (box.length.x + length.x);
        double xt2 = Math.abs(Math.max(max.x, box.max.x) - Math.min(min.x, box.min.x));
        if (xt2 > xt1) {
            return false;
        }
        
        double yt1 = (box.length.y + length.y);
        double yt2 = Math.abs(Math.max(max.y, box.max.y) - Math.min(min.y, box.min.y));
        if (yt2 > yt1) {
            return false;
        }
        
        double zt1 = (box.length.z + length.z);
        double zt2 = Math.abs(Math.max(max.z, box.max.z) - Math.min(min.z, box.min.z));
        return zt2 <= zt1;
    }

    public boolean isCompletelyInside(Box box) {
        return min.x >= box.min.x && min.x <= box.max.x
                && max.x >= box.min.x && max.x <= box.max.x
                && min.y >= box.min.y && min.y <= box.max.y
                && max.y >= box.min.y && max.y <= box.max.y
                && min.z >= box.min.z && min.z <= box.max.z
                && max.z >= box.min.z && max.z <= box.max.z;
    }
    
    @Override
    public String toString() {
        return "Box{" + "min=" + min + ", max=" + max + '}';
    }
    
    private final Rectangle rect = new Rectangle();
    
    public void drawTop(Graphics2D g) {
        if (face != null) {
            face.drawTop(g, Color.BLUE);
        }

        g.setColor(color);
        rect.setBounds((int) min.x, (int) min.z, (int) (max.x - min.x), (int) (max.z - min.z));
        //g.drawString("(" + min.x + "," + min.z + ")", (int) min.x, (int) (min.z));
        //g.drawString("(" + max.x + "," + max.z + ")", (int) max.x, (int) (max.z));
        g.draw(rect);
        
    }

    public void drawFront(Graphics2D g) {
        if (face != null) {
            face.drawFront(g, Color.BLUE);
        }

        g.setColor(color);
        rect.setBounds((int) min.x, (int) min.y, (int) (max.x - min.x), (int) (max.y - min.y));
        //g.drawString("(" + min.x + "," + min.y + ")", (int) min.x, (int) (min.y));
        //g.drawString("(" + max.x + "," + max.y + ")", (int) max.x, (int) (max.y));
        g.draw(rect);

    }

    public void translate(double tx, double ty, double tz) {
        min.x += tx;
        min.y += ty;
        min.z += tz;
        max.x += tx;
        max.y += ty;
        max.z += tz;
    }
    
}
