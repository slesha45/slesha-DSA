
import javax.swing.*;
import java.awt.*;

public class SocialNetworkGraphApp extends JFrame {
    private JPanel canvas;

    public SocialNetworkGraphApp() {
        initComponents();
    }

    private void initComponents() {
        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw nodes and edges here
            }
        };

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Social Network Graph");

        JButton addNodeButton = new JButton("Add Node");
        JButton addEdgeButton = new JButton("Add Edge");
        JButton selectModeButton = new JButton("Select Mode");

        JPanel toolbar = new JPanel();
        toolbar.add(addNodeButton);
        toolbar.add(addEdgeButton);
        toolbar.add(selectModeButton);

        add(canvas, BorderLayout.CENTER);
        add(toolbar, BorderLayout.NORTH);

        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SocialNetworkGraphApp app = new SocialNetworkGraphApp();
            app.setVisible(true);
        });
    }
}