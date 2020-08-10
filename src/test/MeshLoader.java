package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

    public MeshLoader() {
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
    
    public void load(String meshRes, double scaleFactor, double translateX
            , double translateY, double translateZ) throws Exception {
        
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
    }

    private void parseFace(String line) {
        String[] data = line.split(" ");
        Face face = new Face();
        for (int i = 1; i < data.length; i++) {
            String[] data2 = data[i].split("/");
            int index = Integer.parseInt(data2[0]);
            face.addPoint(vertices.get(index - 1));
        }
        face.close();
        faces.add(face);        
    }

    @Override
    public String toString() {
        return "MeshLoader{" + "vertices=" + vertices + ", faces=" + faces 
            + ", scaleFactor=" + scaleFactor + ", translateX=" + translateX 
            + ", translateY=" + translateY + '}';
    }
    
}
