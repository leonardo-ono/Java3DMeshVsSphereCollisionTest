package octree;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import partition.Box;
import physics.Input;
import physics.Sphere;
import physics.Vec3;


/**
 *
 * @author admin
 */
public class Player {

    private Sphere collider = new Sphere();
    private double angle;
    private final Vec3 velocity = new Vec3();
    private final Box box = new Box();
    
    public Player() {
        collider = new Sphere(5, 0, 0, 0);
    }

    public Sphere getCollider() {
        return collider;
    }

    public double getAngle() {
        return angle;
    }

    public Vec3 getVelocity() {
        return velocity;
    }

    public void update() {
        if (Input.isKeyPressed(KeyEvent.VK_LEFT)) {
            System.out.println("left pressed");
            //sphere.getPosition().x -= 0.03;
            angle -= 0.025;
        }
        else if (Input.isKeyPressed(KeyEvent.VK_RIGHT)) {
            //sphere.getPosition().x += 0.03;
            angle += 0.025;
        }
        double speed = 0;
        if (Input.isKeyPressed(KeyEvent.VK_UP)) {
            speed = -1;
            //sphere.getPosition().y += 0.03;
        }
        else if (Input.isKeyPressed(KeyEvent.VK_DOWN)) {
            speed = 1;
            //sphere.getPosition().y -= 0.03;
        }
        velocity.x = 0;
        velocity.z = 0;
        
        if (speed != 0) {
            double dx = speed * Math.cos(angle + Math.toRadians(90));
            double dz = speed * Math.sin(angle + Math.toRadians(90));
            velocity.x = dx;
            velocity.z = dz;
        }

        if (Input.isKeyPressed(KeyEvent.VK_A)) {
            speed = 0.05;
            double dx = speed * Math.cos(angle - Math.toRadians(180));
            double dz = speed * Math.sin(angle - Math.toRadians(180));
            velocity.x = dx;
            velocity.z = dz;
        }
        else if (Input.isKeyPressed(KeyEvent.VK_D)) {
            speed = -0.05;
            double dx = speed * Math.cos(angle - Math.toRadians(180));
            double dz = speed * Math.sin(angle - Math.toRadians(180));
            velocity.x = dx;
            velocity.z = dz;
        }
        
        if (Input.isKeyPressed(KeyEvent.VK_SPACE)) {
            velocity.y += 0.05;
            if (velocity.y > 0.2) {
                velocity.y = 0.2;
            }
            //sphere.getPosition().y -= 0.03;
        }
        
        // gravity
        //velocity.y -= 0.005;


        
//        if (Input.isKeyPressed(KeyEvent.VK_Q)) {
//            collider.getPosition().y -= 0.025;
//        }
//        else if (Input.isKeyPressed(KeyEvent.VK_A)) {
//            collider.getPosition().y += 0.025;
//        }
        collider.getPosition().add(velocity);    
        
        System.out.println("player position: " + collider.getPosition());
        
    }
    
    public void draw(Graphics2D g, double scale) {
        collider.draw(g, scale);
    }

    public void draw3D(Graphics2D g, double scale) {
        collider.draw3D(g, scale, angle, collider.getPosition());
    }

    public Box getBox() {
        box.getMin().set(collider.getPosition());
        box.getMin().x -= collider.getRadius();
        box.getMin().y -= collider.getRadius();
        box.getMin().z -= collider.getRadius();
        box.getMax().set(collider.getPosition());
        box.getMax().x += collider.getRadius();
        box.getMax().y += collider.getRadius();
        box.getMax().z += collider.getRadius();
        box.updateLength();
        return box;
    }
    
    public void drawTop(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        Box box = getBox();
        box.drawTop(g);
    }

    public void drawFront(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        Box box = getBox();
        box.drawFront(g);
    }
    
}
