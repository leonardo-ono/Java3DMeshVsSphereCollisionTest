package octree;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import partition.Box;
import physics.Input;
import physics.Vec3;

/**
 * Java 3D Terrain Mesh vs Ball Player Collision Test
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com);
 */
public class View extends JPanel {
    
    private final Box box1 = new Box(new Vec3(-50, -50, -50), new Vec3(50, 150, 50), Color.BLACK);
    private final Box box2 = new Box(new Vec3(-20, -20, -20), new Vec3(20, 100, 20), Color.BLUE);
    
    public View() {
    }
    
    public void start() {
        addKeyListener(new Input());
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
        if (Input.isKeyPressed(KeyEvent.VK_LEFT)) {
            box2.translate(-1, 0, 0);
        }
        else if (Input.isKeyPressed(KeyEvent.VK_RIGHT)) {
            box2.translate(1, 0, 0);
        }

        if (Input.isKeyPressed(KeyEvent.VK_UP)) {
            box2.translate(0, 0, -1);
        }
        else if (Input.isKeyPressed(KeyEvent.VK_DOWN)) {
            box2.translate(0, 0, 1);
        }

        if (Input.isKeyPressed(KeyEvent.VK_Q)) {
            box2.translate(0, 1, 0);
        }
        else if (Input.isKeyPressed(KeyEvent.VK_A)) {
            box2.translate(0, -1, 0);
        }
    }
    
    private void draw(Graphics2D g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawLine(0, 0, getWidth(), getHeight());
        
        AffineTransform at = g.getTransform();

        // front
        g.setTransform(at);
        g.translate(getWidth() / 4, getHeight() / 2);
        g.setColor(Color.BLACK);
        g.drawString("Front", 0, -200);
        g.scale(1, -1);
        drawFront(g);

        // top
        g.setTransform(at);
        g.translate(3 * getWidth() / 4, getHeight() / 2);
        g.setColor(Color.BLACK);
        g.drawString("Top", 0, -200);
        drawTop(g);
        
        // box inside ?
        g.setTransform(at);
        boolean completelyInside = box2.isCompletelyInside(box1);
        boolean intersects = box2.intersects(box1);
        g.setColor(Color.RED);
        g.drawString("completely inside? " + completelyInside, 50, 50);
        g.drawString("intersects? " + intersects, 50, 70);
        
    }

    private void drawFront(Graphics2D g) {
        box1.drawFront(g);
        box2.drawFront(g);
    }

    private void drawTop(Graphics2D g) {
        box1.drawTop(g);
        box2.drawTop(g);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            View view = new View();
            view.setPreferredSize(new Dimension(800, 600));
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
