package test;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class World.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com)
 */
public class World {

    private final List<Face> faces = new ArrayList<>();
    private final Player player = new Player();

    private final List<Response> collidedResponses = new ArrayList<>();
    private final Vec3 resultPush = new Vec3();
    
    private MeshLoader terrain;
    
    public World() {
        createTest2();
    }

    private void createTest2() {
        terrain = new MeshLoader();
        try {
            terrain.load("/res/terrain.obj", 20, 0, -5, 0);
        } catch (Exception ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        faces.addAll(terrain.getFaces());
    }
    
    private void createTest() {
        Face face = new Face();
        face.addPoint(2, 0, 2);
        face.addPoint(-2, 0, -2);
        face.addPoint(2, 0, -2);
        face.close();
        faces.add(face);

        Face face2 = new Face();
        face2.addPoint(-2, 0, 2);
        face2.addPoint(-2, 0, -2);
        face2.addPoint(2, 0, 2);
        face2.close();
        faces.add(face2);

        Face face3 = new Face();
        face3.addPoint(-2, 0, -2);
        face3.addPoint(-2, 2, -2.0);
        face3.addPoint(2, 2, -2.0);
        face3.close();
        faces.add(face3);

        Face face4 = new Face();
        face4.addPoint(-2, 0, -2);
        face4.addPoint(2, 2, -2.0);
        face4.addPoint(2, 0, -2);
        face4.close();
        faces.add(face4);
        
    }
    
    public void update() {
                
        player.update();
        
        // detect collision with all polygons
        collidedResponses.clear();
        for (Face face : faces) {
            Response response = face.checkCollision(player.getCollider());
            if (response.isCollides()) {
                collidedResponses.add(response);
            }
        }
        
        resultPush.set(0, 0, 0);

        // collision response 
        if (!collidedResponses.isEmpty()) {
            for (Response response : collidedResponses) {
                resultPush.add(response.getContactNormal());
            }
            player.getCollider().getPosition().add(resultPush);
        }        
        
        // if colliding, fix velocity so the player can 
        // slide when colliding with walls
        if (!collidedResponses.isEmpty()) {
            resultPush.normalize();
            double dot = resultPush.dot(player.getVelocity());
            resultPush.scale(dot);
            player.getVelocity().sub(resultPush);
        }
        
        // update player position
        player.getCollider().getPosition().add(player.getVelocity());    
    }

    public void draw(Graphics2D g) {
        player.draw3D(g, Camera.SCALE);
        faces.forEach((face) -> {
            face.draw3D(g, Camera.SCALE
                , player.getAngle(), player.getCollider().getPosition());
        });
    }
    
}
