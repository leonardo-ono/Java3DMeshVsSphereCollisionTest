package test.partition;

import test.Vec3;

/**
 * Box class.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com)
 */
public class Box {

    protected final Vec3 min = new Vec3();
    protected final Vec3 max = new Vec3();
    protected final Vec3 length = new Vec3();

    public Box() {
    }

    
    public Box(Vec3 min, Vec3 max) {
        this.min.set(min);
        this.max.set(max);
        updateLengthInternal();
    }
    
    public void updateLength() {
        updateLengthInternal();
    }

    private void updateLengthInternal() {
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
        double xt2 = Math.abs(Math.max(max.x, box.max.x) 
                - Math.min(min.x, box.min.x));
        
        if (xt2 > xt1) {
            return false;
        }
        
        double yt1 = (box.length.y + length.y);
        double yt2 = Math.abs(Math.max(max.y, box.max.y) 
                - Math.min(min.y, box.min.y));
        
        if (yt2 > yt1) {
            return false;
        }
        
        double zt1 = (box.length.z + length.z);
        double zt2 = Math.abs(Math.max(max.z, box.max.z) 
                - Math.min(min.z, box.min.z));
        
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
    
}
