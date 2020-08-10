package test;

/**
 * class Vec3.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com)
 */
public class Vec3 {

    public double x;
    public double y;
    public double z;

    public Vec3() {
    }

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3(Vec3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public Vec3(Vec3 a, Vec3 b) {
        this.x = b.x - a.x;
        this.y = b.y - a.y;
        this.z = b.z - a.z;
    }
    
    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vec3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }
    
    public void add(Vec3 v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
    }

    public void sub(Vec3 v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
    }

    public void scale(double s) {
        scale(s, s, s);
    }

    public void scale(double sx, double sy, double sz) {
        this.x *= sx;
        this.y *= sy;
        this.z *= sz;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y + z * z);
    }
    
    public void normalize() {
        double length = getLength();
        if (length != 0) {
            x /= length;
            y /= length;
            z /= length;
        }
    }

    public double dot(Vec3 v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public double dot2D(Vec3 v) {
        return x * v.x + z * v.z;
    }
    
    public void cross(Vec3 v) {
        double cx = y * v.z - z * v.y;
        double cy = z * v.x - x * v.z;
        double cz = x * v.y - y * v.x;       
        x = cx;
        y = cy;
        z = cz;
    }
    
    public double cross2D(Vec3 v) {
        return x * v.z - z * v.x;
    }
    
    public void rotateY(double angle) {
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        double nx = x * c - z * s;
        double nz = x * s + z * c;
        set(nx, y, nz);
    }
    
    @Override
    public String toString() {
        return "Vec3{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
    
}
