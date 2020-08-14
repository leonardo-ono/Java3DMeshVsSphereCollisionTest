package test;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import test.Face;
import test.Vec3;

/**
 * MeshLoader class.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com)
 */
public class MeshLoader {
    
    private final List<Vec3> vertices = new ArrayList<>();
    private final List<Face> faces = new ArrayList<>();
    
    private double scaleFactor;
    private double translateX;
    private double translateY;
    private double translateZ;

    private Vec3 min = new Vec3();
    private Vec3 max = new Vec3();
    
    public MeshLoader() {
        min.set(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        max.set(-Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE);
    }

    public List<Vec3> getVertices() {
        return vertices;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public double getScaleFactor() {
        return scaleFactor;
    }

    public double getTranslateX() {
        return translateX;
    }

    public double getTranslateY() {
        return translateY;
    }

    public double getTranslateZ() {
        return translateZ;
    }

    public Vec3 getMin() {
        return min;
    }

    public void setMin(Vec3 min) {
        this.min = min;
    }

    public Vec3 getMax() {
        return max;
    }

    public void setMax(Vec3 max) {
        this.max = max;
    }
    
    public void load(String meshRes, double scaleFactor
            , double translateX, double translateY, double translateZ) throws Exception {
        
        vertices.clear();
        faces.clear();
        
        this.scaleFactor = scaleFactor;
        this.translateX = translateX;
        this.translateY = translateY;
        this.translateZ = translateZ;
        
        InputStream is = MeshLoader.class.getResourceAsStream(meshRes);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("v ")) {
                parseVertex(line);
            }
            else if (line.startsWith("f ")) {
                parseFace(line);
            }
        }
        br.close();
        
//        Vec3 tMin = new Vec3(-1, -1, -1);
//        Vec3 tMax = new Vec3(1, 1, 1);
//        
//        min.add(tMin);
//        max.add(tMax);
        
        //min.set(-100, -100, -100);
        //max.set(100, 100, 100);
    }

    private void parseVertex(String line) {
        String[] data = line.split(" ");
        double x = Double.parseDouble(data[1]);
        double y = Double.parseDouble(data[2]);
        double z = Double.parseDouble(data[3]);
        double stx = x * scaleFactor + translateX;
        double sty = y * scaleFactor + translateY;
        double stz = z * scaleFactor + translateZ;
        Vec3 v = new Vec3(stx, sty, stz);
        vertices.add(v);
        updateMinMax(v);
    }

    private void parseFace(String line) {
        String[] data = line.split(" ");
        //List<Vec3> ps = new ArrayList<>();
        Face face = new Face();
        for (int i = 1; i < data.length; i++) {
            String[] data2 = data[i].split("/");
            int index = Integer.parseInt(data2[0]);
            //ps.add(vertices.get(index - 1));
            Vec3 p = vertices.get(index - 1);
            face.addPoint(p);
        }
        face.close();
        faces.add(face);        
    }

    private void updateMinMax(Vec3 p) {
        if (p.x < min.x) {
            min.x = p.x;
        }
        if (p.y < min.y) {
            min.y = p.y;
        }
        if (p.z < min.z) {
            min.z = p.z;
        }
        if (p.x > max.x) {
            max.x = p.x;
        }
        if (p.y > max.y) {
            max.y = p.y;
        }
        if (p.z > max.z) {
            max.z = p.z;
        }
    }

    @Override
    public String toString() {
        return "MeshLoader{" + "vertices=" + vertices + ", faces=" + faces 
            + ", scaleFactor=" + scaleFactor + ", translateX=" + translateX 
            + ", translateY=" + translateY + '}';
    }
    
}
