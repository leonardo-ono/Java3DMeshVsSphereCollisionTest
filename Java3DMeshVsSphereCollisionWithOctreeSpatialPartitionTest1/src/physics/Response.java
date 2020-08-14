package physics;


/**
 *
 * @author admin
 */
public class Response {

    private boolean collides;
    private final Vec3 contactPoint = new Vec3();
    private final Vec3 contactNormal = new Vec3();

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

    @Override
    public String toString() {
        return "Response{" + "collides=" + collides 
                + ", contactPoint=" + contactPoint 
                + ", contactNormal=" + contactNormal + '}';
    }
    
}
