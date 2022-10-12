package test;

/**
 * (Collision) Response class.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com)
 */
public class Response {

    private boolean collides;
    private final Vec3 contactPoint = new Vec3();
    private final Vec3 contactNormal = new Vec3();
    private boolean edge;

    public boolean isCollides() {
        return collides;
    }

    public void setCollides(boolean collides) {
        this.collides = collides;
    }

    public Vec3 getContactPoint() {
        return contactPoint;
    }

    public Vec3 getContactNormal() {
        return contactNormal;
    }

    public boolean isEdge() {
        return edge;
    }

    public void setIsEdge(boolean edge) {
        this.edge = edge;
    }

    @Override
    public String toString() {
        return "Response{" + "collides=" + collides 
                + ", contactPoint=" + contactPoint 
                + ", contactNormal=" + contactNormal + '}';
    }
    
}
