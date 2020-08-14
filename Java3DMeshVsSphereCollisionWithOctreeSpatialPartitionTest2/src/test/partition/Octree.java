package test.partition;

import java.util.List;
import test.Face;
import test.Vec3;

/**
 * Octree class.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com)
 */
public class Octree extends Node {
    
    public Octree(Vec3 min, Vec3 max) {
        super(min, max);
    }

    public void addFaces(List<Face> facesAdd) {
        facesAdd.forEach((faceAdd) -> {
            addFace(faceAdd);
        });
    }
    
}
