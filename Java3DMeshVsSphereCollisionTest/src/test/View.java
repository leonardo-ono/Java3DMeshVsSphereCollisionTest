package test;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Java 3D Mesh vs Sphere Collision Test
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com);
 */
public class View extends JPanel {
    
    private final World world = new World();
    
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
        world.update();

    }
    
    private void draw(Graphics2D g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        //g.drawLine(0, 0, getWidth(), getHeight());

        g.translate(400, 300);
        g.scale(1, -1);

        world.draw(g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            View view = new View();
            view.setPreferredSize(new Dimension(800, 600));
            JFrame frame = new JFrame();
            frame.setTitle("Java 3D Mesh vs Sphere Collision - NEW");
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
