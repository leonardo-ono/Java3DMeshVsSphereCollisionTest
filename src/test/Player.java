package test;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * Player class.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com)
 */
public class Player {

    private Sphere collider = new Sphere();
    private double angle;
    private final Vec3 velocity = new Vec3();

    public Player() {
        collider = new Sphere(0.5, 0, 1, -1);
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
            //sphere.getPosition().x -= 0.03;
            angle -= 0.025;
        }
        else if (Input.isKeyPressed(KeyEvent.VK_RIGHT)) {
            //sphere.getPosition().x += 0.03;
            angle += 0.025;
        }
        double speed = 0;
        if (Input.isKeyPressed(KeyEvent.VK_UP)) {
            speed = -0.1;
            //sphere.getPosition().y += 0.03;
        }
        else if (Input.isKeyPressed(KeyEvent.VK_DOWN)) {
            speed = 0.1;
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
        }
        
        // gravity
        velocity.y -= 0.005;
    }
    
    public void draw3D(Graphics2D g, double scale) {
        collider.draw3D(g, scale, angle, collider.getPosition());
    }
    
}
