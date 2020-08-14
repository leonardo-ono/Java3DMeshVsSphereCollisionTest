package partition;

import java.util.List;
import physics.Face;
import physics.Vec3;

/**
 *
 * @author admin
 */
public class Octree extends Node {
    
    public Octree(Vec3 min, Vec3 max) {
        super(0, min, max);
    }

    public void addFaces(List<Face> facesAdd) {
        for (Face faceAdd : facesAdd) {
            addFace(faceAdd);
        }
    }
    
}
