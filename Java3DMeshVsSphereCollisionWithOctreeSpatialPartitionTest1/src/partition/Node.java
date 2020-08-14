package partition;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import physics.Face;
import physics.Vec3;

/**
 *
 * @author admin
 */
public class Node extends Box {
    
    protected int depth;
    protected List<Face> faces = new ArrayList<>();
    protected Node[] children = new Node[8];
    protected Box boxTmp = new Box();
    protected boolean partitioned;
    
    public Node(int depth, Vec3 min, Vec3 max) {
        super(min, max, Color.RED);
        this.depth = depth;
    }
    
    protected void partition() {
        Vec3 b1Min = new Vec3(min.x, min.y, min.z);
        Vec3 b1Max = new Vec3(min.x + length.x / 2, min.y + length.y / 2, min.z + length.z / 2);
        
        Vec3 b2Min = new Vec3(min.x + length.x / 2, min.y, min.z);
        Vec3 b2Max = new Vec3(min.x + length.x, min.y + length.y / 2, min.z + length.z / 2);
        
        Vec3 b3Min = new Vec3(min.x, min.y, min.z + length.z / 2);
        Vec3 b3Max = new Vec3(min.x + length.x / 2, min.y + length.y / 2, min.z + length.z);
        
        Vec3 b4Min = new Vec3(min.x + length.x / 2, min.y, min.z + length.z / 2);
        Vec3 b4Max = new Vec3(min.x + length.x, min.y + length.y / 2, min.z + length.z);

        Vec3 b5Min = new Vec3(min.x, min.y + length.y / 2, min.z);
        Vec3 b5Max = new Vec3(min.x + length.x / 2, min.y + length.y, min.z + length.z / 2);
        
        Vec3 b6Min = new Vec3(min.x + length.x / 2, min.y + length.y / 2, min.z);
        Vec3 b6Max = new Vec3(min.x + length.x, min.y + length.y, min.z + length.z / 2);
        
        Vec3 b7Min = new Vec3(min.x, min.y + length.y / 2, min.z + length.z / 2);
        Vec3 b7Max = new Vec3(min.x + length.x / 2, min.y + length.y, min.z + length.z);
        
        Vec3 b8Min = new Vec3(min.x + length.x / 2, min.y + length.y / 2, min.z + length.z / 2);
        Vec3 b8Max = new Vec3(min.x + length.x, min.y + length.y, min.z + length.z);
        
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
            children[i] = new Node(depth + 1, minMax[i][0], minMax[i][1]);
        }
        
        partitioned = true;
    }
    

    public void addFace(Face face) {
        
        if (!partitioned) {
            partition();
        }
        
        boolean addFaceInThisNode = true;
        
        //if (depth < 10) {
            boxTmp.set(face);
            for (Node node : children) {
                if (boxTmp.isCompletelyInside(node)) {
                    addFaceInThisNode = false;
                    node.addFace(face);
                    break;
                }
            }
        //}
        
        if (addFaceInThisNode) {
            faces.add(face);
            System.out.println("added face " + face + " depth=" + depth + " faces.size()=" + faces.size());
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
            result.addAll(faces);
        }
    }    
    
    public void drawPartitionsTop(Graphics2D g) {
        drawTop(g);
        if (partitioned) {
            for (Node child : children) {
                child.drawPartitionsTop(g);
            }
        }
        
        for (Face face : faces) {
            face.drawTop(g, Color.BLUE);
        }
    }

    public void drawPartitionsFront(Graphics2D g) {
        drawFront(g);
        if (partitioned) {
            for (Node child : children) {
                child.drawPartitionsFront(g);
            }
        }

        for (Face face : faces) {
            face.drawFront(g, Color.BLUE);
        }
    }
    
}
