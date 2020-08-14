package test.partition;

import test.Face;
import test.Vec3;

/**
 * FaceBox class.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com)
 */
public class FaceBox extends Box {
    
    private Face face;

    public FaceBox() {
    }
    
    public FaceBox(Face face) {
        setFaceInternal(face);
    }

    public Face getFace() {
        return face;
    }

    public void setFace(Face face) {
        setFaceInternal(face);
    }

    private void setFaceInternal(Face face) {
        this.face = face;
        
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double minZ = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE;
        double maxY = -Double.MAX_VALUE;
        double maxZ = -Double.MAX_VALUE;
        for (Vec3 point : face.getPoints()) {
            if (point.x < minX) {
                minX = point.x;
            }
            if (point.y < minY) {
                minY = point.y;
            }
            if (point.z < minZ) {
                minZ = point.z;
            }
            
            if (point.x > maxX) {
                maxX = point.x;
            }
            if (point.y > maxY) {
                maxY = point.y;
            }
            if (point.z > maxZ) {
                maxZ = point.z;
            }
        }
        min.set(minX, minY, minZ);
        max.set(maxX, maxY, maxZ);
        updateLength();
    }
    
}
