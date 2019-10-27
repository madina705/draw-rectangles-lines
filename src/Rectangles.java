import java.awt.*;
import java.awt.Point;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Rectangles extends JPanel implements ActionListener {

    private int action;

    ArrayList<Rectangle> rectangles = new ArrayList<>();
    ArrayList<Line2D.Double> lines = new ArrayList<>();

    Rectangles() {
        this.setPreferredSize(new Dimension(500, 400));
        MouseHandler mouseHandler = new MouseHandler();
        this.addMouseListener(mouseHandler);

        JRadioButton rectangleButton = new JRadioButton("Rectangle");
        rectangleButton.addActionListener(this);
        rectangleButton.setBounds(50, 50, 100, 30);
        add(rectangleButton);

        JRadioButton lineButton = new JRadioButton("Line");
        lineButton.addActionListener(this);
        lineButton.setBounds(50, 100, 100, 30);
        add(lineButton);

        ButtonGroup group = new ButtonGroup();
        group.add(rectangleButton);
        group.add(lineButton);
    }


    public void actionPerformed(ActionEvent ae) {
        String str = ae.getActionCommand();
        if (str.equals("Rectangle"))
            action = 1;
        if (str.equals("Line"))
            action = 2;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (action == 1 && rectangles.size() != 0) {
            for (Rectangle rectangle : rectangles) {
                graphics.drawRect((int) rectangle.getX(), (int) rectangle.getY(), 50, 50);
            }
        }
        if (action == 2 && rectangles.size() != 0) {
            for (Rectangle value : rectangles) {
                graphics.drawRect((int) value.getX(), (int) value.getY(), 50, 50);
            }
        }
        if (lines.size() != 0) {
            for (Line2D.Double line : lines) {
                graphics.drawLine((int) line.getX1(), (int) line.getY1(), (int) line.getX2(), (int) line.getY2());
            }
        }
    }

    public Rectangle findRectangle(double x, double y) {
        Rectangle result = null;
        for (Rectangle rect : rectangles) {
            if (x >= (int) rect.getMinX() && x <= rect.getMaxX() && y >= rect.getMinY() && y <= rect.getMaxY()) {
                result = rect;
            }
        }
        return result;
    }

    private class MouseHandler extends MouseAdapter {

        boolean twoPoints = false;
        Point nextPoint, previousPoint;

        @Override
        public void mouseClicked(MouseEvent e) {
            Point p;
            if (action == 1) {
                p = e.getPoint();
                rectangles.add(new Rectangle(p.x, p.y, 50, 50));
            }
            repaint();
            if (action == 2) {
                if (!twoPoints) {
                    nextPoint = e.getPoint();
                    twoPoints = true;
                } else {
                    //Set previous to next from now on.
                    previousPoint = nextPoint;

                    //Get a new next point.
                    nextPoint = e.getPoint();
                    
                    Rectangle rectangle1 = findRectangle(previousPoint.getX(), previousPoint.getY());
                    Rectangle rectangle2 = findRectangle(nextPoint.getX(), nextPoint.getY());
                    if (rectangle1 != null && rectangle2 != null && rectangle1 != rectangle2) {
                        Point centerRec1 = new Point((int)rectangle1.getCenterX(), (int)rectangle1.getCenterY());
                        Point centerRec2 = new Point((int)rectangle2.getCenterX(), (int)rectangle2.getCenterY());
                        lines.add(new Line2D.Double(centerRec1, centerRec2));
                        repaint();
                        twoPoints = false;
                    }

                }

            }
        }
    }

}



