package test;


import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import test.partition.Octree;

/**
 * World class.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com)
 */
public class World {

    private final List<Face> faces = new ArrayList<>();
    private final Player player = new Player();

    private final List<Response> collidedResponses = new ArrayList<>();
    private final Vec3 resultPush = new Vec3();
    
    private MeshLoader terrain;
    
    private Octree spatialPartition;
    private final List<Face> retrievedFaces = new ArrayList<>();
    
    public World() {
        createTest2();
    }

    private void createTest2() {
        terrain = new MeshLoader();
        try {
            //terrain.load("/res/terrain.obj", 20, 0, -5, 0);
            terrain.load("/res/princess_peaches_castle_(outside).obj", 20, 0, -100, 150);
            //terrain.load("/res/cs.obj", 1, 0, -5, 7);
            
        } catch (Exception ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        faces.addAll(terrain.getFaces());
        spatialPartition = new Octree(terrain.getMin(), terrain.getMax());
        spatialPartition.addFaces(terrain.getFaces());
        
        //player.getCollider().getPosition().set(25, -13, -26); // test for cs.obj
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
        face3.addPoint(-2, 2, -20.0);
        face3.addPoint(2, 2, -20.0);
        face3.close();
        faces.add(face3);

        Face face4 = new Face();
        face4.addPoint(-2, 0, -2);
        face4.addPoint(2, 2, -20.0);
        face4.addPoint(2, 0, -2);
        face4.close();
        faces.add(face4);
        
    }
    
    public void update() {
        player.update();
        
        // detect collision with polygons retrieved from spatial partition
        collidedResponses.clear();
        
        retrievedFaces.clear();
        spatialPartition.retrieveFaces(player.getBox(), retrievedFaces);
        
//        System.out.println("total faces:" + faces.size() 
//                + " / retrieved faces: " + retrievedFaces.size());
        
        for (Face face : retrievedFaces) {
            Response response = face.checkCollision(player.getCollider());
            if (response.isCollides()) {
                collidedResponses.add(response);
            }
        }
        
        resultPush.set(0, 0, 0);

        // collision response 
        if (!collidedResponses.isEmpty()) {
            collidedResponses.forEach((response) -> {
                resultPush.add(response.getContactNormal());
            });
            //resultPush.scale(1.0 / collidedResponses.size());
            player.getCollider().getPosition().add(resultPush);
        }        
        
        // fix velocity so the player can slide when colliding with walls
        if (!collidedResponses.isEmpty()) {
            resultPush.normalize();
            double dot = resultPush.dot(player.getVelocity());
            resultPush.scale(dot);
            player.getVelocity().sub(resultPush);
        }
        
        // update player position
        player.getCollider().getPosition().add(player.getVelocity());    
        
        System.out.println("player position: " + player.getCollider().getPosition());
    }

    public void draw(Graphics2D g) {
        double scale = 50;
        
        //player.draw(g, scale);
        //face.draw(g, scale);
        
        player.draw3D(g, scale);

//        for (int i = 0; i < 100; i++) {
//            Face face = faces.get(i);
//            face.draw3D(g, scale, player.getAngle(), player.getCollider().getPosition());
//        }
        
        faces.forEach((face) -> {
            face.draw3D(g, scale, player.getAngle()
                    , player.getCollider().getPosition());
        });
    }
    
}
