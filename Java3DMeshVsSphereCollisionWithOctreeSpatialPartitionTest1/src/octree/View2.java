package octree;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import partition.Box;
import partition.Octree;
import physics.Face;
import physics.Input;
import physics.Vec3;

/**
 * Java 3D Terrain Mesh vs Ball Player Collision Test
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com);
 */
public class View2 extends JPanel {
    
    private final Box box1 = new Box(new Vec3(-50, -50, -50), new Vec3(50, 150, 50), Color.BLACK);
    private Box box2; // = new Box(new Vec3(-20, -20, -20), new Vec3(20, 100, 20), Color.BLUE);
    
    private List<Box> boxes = new ArrayList<>(); // = new Box(new Vec3(-20, -20, -20), new Vec3(20, 100, 20), Color.BLUE);
    
    private Box playerBox = new Box(new Vec3(-5, -5, -5), new Vec3(5, 5, 5), Color.BLACK);

    private MeshLoader terrain = new MeshLoader();
    
    private Octree octree;
    
    private List<Face> retrievedFaces = new ArrayList<>();
    
    private Player player = new Player();
    
    private int selectedPlayer = 1;
    
    public View2() {
    }
    
    public void start() {
        addKeyListener(new Input());
        
        terrain = new MeshLoader();
        try {
            //terrain.load("/res/terrain.obj", 200, 0, -5, 0);
            terrain.load("/res/terrain.obj", 200, 0, -5, 0);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
        //box2 = new Box(terrain.getFaces().get(0));
        for (Face face : terrain.getFaces()) {
            boxes.add(new Box(face, Color.GRAY));
        }
        
        box2 = new Box(terrain.getMin(), terrain.getMax(), Color.RED);
        
        octree =  new Octree(terrain.getMin(), terrain.getMax());
        
        for (int i = 0; i < terrain.getFaces().size(); i++) {
            Face face = terrain.getFaces().get(i);
            octree.addFace(face);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        update();
        draw((Graphics2D) g);
        try {
            Thread.sleep(1000 / 60);
        } catch (InterruptedException ex) {
        }
        repaint();
    }
    
    private void update() {
        if (Input.isKeyPressed(KeyEvent.VK_0)) {
            selectedPlayer = 0;
        }
        else if (Input.isKeyPressed(KeyEvent.VK_1)) {
            selectedPlayer = 1;
        }
        
        if (selectedPlayer == 0) {
            updatePlayer();
        }
        else if (selectedPlayer == 1) {
            updatePlayerBox();
        }
    }
    
    private void updatePlayer() {
        player.update();
        //updatePlayerBox();
        retrievedFaces.clear();
        octree.retrieveFaces(player.getBox(), retrievedFaces);
    }

    private void updatePlayerBox() {
        if (Input.isKeyPressed(KeyEvent.VK_LEFT)) {
            playerBox.translate(-1, 0, 0);
        }
        else if (Input.isKeyPressed(KeyEvent.VK_RIGHT)) {
            playerBox.translate(1, 0, 0);
        }

        if (Input.isKeyPressed(KeyEvent.VK_UP)) {
            playerBox.translate(0, 0, -1);
        }
        else if (Input.isKeyPressed(KeyEvent.VK_DOWN)) {
            playerBox.translate(0, 0, 1);
        }

        if (Input.isKeyPressed(KeyEvent.VK_Q)) {
            playerBox.translate(0, 1, 0);
        }
        else if (Input.isKeyPressed(KeyEvent.VK_A)) {
            playerBox.translate(0, -1, 0);
        }
        retrievedFaces.clear();
        octree.retrieveFaces(playerBox, retrievedFaces);
    }
    
    private void draw(Graphics2D g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        //g.drawLine(0, 0, getWidth(), getHeight());
        
        AffineTransform at = g.getTransform();

        // front
        g.setTransform(at);
        g.translate(getWidth() / 4, getHeight() / 2);
        g.setColor(Color.BLACK);
        g.drawString("Front", 0, -230);
        g.scale(1, -1);
        drawFront(g);

        // top
        g.setTransform(at);
        g.translate(3 * getWidth() / 4, getHeight() / 2);
        g.setColor(Color.BLACK);
        g.drawString("Top", 0, -230);
        g.scale(1, 1);
        drawTop(g);
        
        // box inside ?
        g.setTransform(at);
        boolean completelyInside = box2.isCompletelyInside(box1);
        boolean intersects = box2.intersects(box1);
        g.setColor(Color.RED);
        g.drawString("completely inside? " + completelyInside, 50, 50);
        g.drawString("intersects? " + intersects, 50, 70);

        // retrieved faces
        g.drawString("retrieved faces = " + retrievedFaces.size(), 50, 90);
        
        if (selectedPlayer == 0) {
            g.drawString("selected: player", 50, 110);
        }
        else if (selectedPlayer == 1) {
            g.drawString("selected: playerBox", 50, 110);
        }
        
    }

    private void drawFront(Graphics2D g) {
        if (selectedPlayer == 1) {
            playerBox.drawFront(g);
        }
        
        //box2.drawFront(g);
        //for (Box box : boxes) {
        //    box.drawFront(g);
        //}
        
        
        octree.drawPartitionsFront(g);
        
        for (Face face : retrievedFaces) {
            face.drawFront(g, Color.GREEN);
        }
        
        if (selectedPlayer == 0) {
            player.getBox().drawFront(g);
        }
    }

    private void drawTop(Graphics2D g) {
        if (selectedPlayer == 1) {
            playerBox.drawTop(g);
        }
        //box2.drawTop(g);
        //for (Box box : boxes) {
        //    box.drawTop(g);
        //}
        
        octree.drawPartitionsTop(g);
        
        for (Face face : retrievedFaces) {
            face.drawTop(g, Color.GREEN);
        }
        
        
        if (selectedPlayer == 0) {
            player.getBox().drawTop(g);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            View2 view = new View2();
            view.setPreferredSize(new Dimension(1000, 600));
            JFrame frame = new JFrame();
            frame.setTitle("Java Octree Test");
            frame.getContentPane().add(view);
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            view.requestFocus();
            view.start();
        });
    }

}
