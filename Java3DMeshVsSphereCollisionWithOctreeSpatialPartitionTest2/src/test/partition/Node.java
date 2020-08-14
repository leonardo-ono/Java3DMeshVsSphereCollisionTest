package test.partition;

import java.util.ArrayList;
import java.util.List;
import test.Face;
import test.Vec3;

/**
 * Node class.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com)
 */
public class Node extends Box {
    
    protected List<FaceBox> faces = new ArrayList<>();
    protected Node[] children = new Node[8];
    protected FaceBox boxTmp = new FaceBox();
    protected boolean partitioned;
    
    public Node(Vec3 min, Vec3 max) {
        super(min, max);
    }
    
    protected void partition() {
        Vec3 b1Min = new Vec3(min.x, min.y, min.z);
        
        Vec3 b1Max = new Vec3(min.x + length.x / 2
                , min.y + length.y / 2, min.z + length.z / 2);
        
        Vec3 b2Min = new Vec3(min.x + length.x / 2, min.y, min.z);
        
        Vec3 b2Max = new Vec3(min.x + length.x
                , min.y + length.y / 2, min.z + length.z / 2);
        
        Vec3 b3Min = new Vec3(min.x, min.y, min.z + length.z / 2);
        
        Vec3 b3Max = new Vec3(min.x + length.x / 2
                , min.y + length.y / 2, min.z + length.z);
        
        Vec3 b4Min = new Vec3(min.x + length.x / 2
                , min.y, min.z + length.z / 2);
        
        Vec3 b4Max = new Vec3(min.x + length.x
                , min.y + length.y / 2, min.z + length.z);

        Vec3 b5Min = new Vec3(min.x, min.y + length.y / 2, min.z);
        
        Vec3 b5Max = new Vec3(min.x + length.x / 2
                , min.y + length.y, min.z + length.z / 2);
        
        Vec3 b6Min = new Vec3(min.x + length.x / 2
                , min.y + length.y / 2, min.z);
        
        Vec3 b6Max = new Vec3(min.x + length.x
                , min.y + length.y, min.z + length.z / 2);
        
        Vec3 b7Min = new Vec3(min.x
                , min.y + length.y / 2, min.z + length.z / 2);
        
        Vec3 b7Max = new Vec3(min.x + length.x / 2
                , min.y + length.y, min.z + length.z);
        
        Vec3 b8Min = new Vec3(min.x + length.x / 2
                , min.y + length.y / 2, min.z + length.z / 2);
        
        Vec3 b8Max = new Vec3(min.x + length.x
                , min.y + length.y, min.z + length.z);
        
        Vec3[][] minMax = {
            {b1Min, b1Max},
            {b2Min, b2Max},
            {b3Min, b3Max},
            {b4Min, b4Max},
            {b5Min, b5Max},
            {b6Min, b6Max},
            {b7Min, b7Max},
            {b8Min, b8Max},
        };
        
        for (int i = 0; i < 8; i++) {
            children[i] = new Node(minMax[i][0], minMax[i][1]);
        }
        
        partitioned = true;
    }

    public void addFace(Face face) {
        if (!partitioned) {
            partition();
        }
        
        boolean addFaceInThisNode = true;
        
        boxTmp.setFace(face);
        for (Node node : children) {
            if (boxTmp.isCompletelyInside(node)) {
                addFaceInThisNode = false;
                node.addFace(face);
                break;
            }
        }
        
        if (addFaceInThisNode) {
            faces.add(new FaceBox(face));
        }
    }
    
    public void retrieveFaces(Box box, List<Face> result) {
        if (partitioned) {
            for (Node node : children) {
                if (node.intersects(box)) {
                    node.retrieveFaces(box, result);
                }
            }
        }
        if (intersects(box)) {
            for (FaceBox faceBox : faces) {
                if (faceBox.intersects(box)) {
                    result.add(faceBox.getFace());
                }
            }
        }
    }    
    
}
