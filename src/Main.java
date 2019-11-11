import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Rectangles and Line");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);
        Rectangles rectangles = new Rectangles();
        frame.add(rectangles);
        frame.pack();
        frame.setVisible(true);
    }
}
