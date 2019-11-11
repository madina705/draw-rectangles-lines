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
    ArrayList<Rectangle> comments = new ArrayList<>();
    ArrayList<Rectangle> commentsAndRectangles = null;
    ArrayList<Line2D.Double> lines = new ArrayList<>();
    ArrayList<Text> texts = new ArrayList<>();
    JRadioButton rectangleButton = new JRadioButton("Rectangle");
    JRadioButton lineButton = new JRadioButton("Line");
    JRadioButton commentButton = new JRadioButton("Comment");
    JRadioButton textButton = new JRadioButton("Text");
    final JTextField textField = new JTextField();
    final static float dash1[] = {10.0f};

    final static BasicStroke dashed = new BasicStroke(1.0f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

    Rectangles() {
        this.setPreferredSize(new Dimension(500, 400));
        MouseHandler mouseHandler = new MouseHandler();
        this.addMouseListener(mouseHandler);

        rectangleButton.addActionListener(this);
        rectangleButton.setBounds(50, 50, 100, 30);
        add(rectangleButton);

        lineButton.addActionListener(this);
        lineButton.setBounds(50, 100, 100, 30);
        lineButton.setEnabled(false);
        add(lineButton);

        commentButton.addActionListener(this);
        commentButton.setBounds(50, 100, 100, 30);
        add(commentButton);

        textButton.addActionListener(this);
        textButton.setBounds(50, 100, 100, 30);
        add(textButton);

        textField.setColumns(10);
        add(textField);

        ButtonGroup group = new ButtonGroup();
        group.add(rectangleButton);
        group.add(lineButton);
        group.add(commentButton);
        group.add(textButton);
    }


    public void actionPerformed(ActionEvent ae) {
        String str = ae.getActionCommand();
        if (str.equals("Rectangle"))
            action = 1;
        if (str.equals("Line"))
            action = 2;
        if (str.equals("Comment"))
            action = 3;
        if (str.equals("Text"))
            action = 4;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        Stroke temp = g2.getStroke();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (rectangles.size() != 0) {
            for (Rectangle rectangle : rectangles) {
                int x = (int) rectangle.getX();
                int y = (int) rectangle.getY();
                graphics.drawRect(x, y, 80, 50);
            }
        }
        if (comments.size() != 0) {
            for (Rectangle comment : comments) {
                g2.setStroke(dashed);
                int x = (int) comment.getX();
                int y = (int) comment.getY();
                graphics.drawRect(x, y, 80, 50);
            }
        }
        if (lines.size() != 0) {
            g2.setStroke(temp);
            for (Line2D.Double line : lines) {
                graphics.drawLine((int) line.getX1(), (int) line.getY1(), (int) line.getX2(), (int) line.getY2());
            }
        }
        if (texts.size() != 0) {
            for (Text text : texts) {
                int x = text.getX();
                int y = text.getY();
                graphics.drawString(textField.getText(), x, y);
            }
        }
    }

    private Rectangle findRectangle(double x, double y) {
        Rectangle result = null;
        for (Rectangle rect : rectangles) {
            if (x >= (int) rect.getMinX() && x <= rect.getMaxX() && y >= rect.getMinY() && y <= rect.getMaxY()) {
                result = rect;
            }
        }
        return result;
    }

    private Rectangle getRectangle(double x, double y) {
        Rectangle result = null;
        commentsAndRectangles = new ArrayList<>(rectangles);
        commentsAndRectangles.addAll(comments);
        for (Rectangle rect : commentsAndRectangles) {
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
                if (rectangles.size() > 1) lineButton.setEnabled(true);
            }
            if (action == 3) {
                p = e.getPoint();
                comments.add(new Rectangle(p.x, p.y, 50, 50));
            }
            if (action == 4 && !textField.getText().equals("")) {
                p = e.getPoint();
                texts.add(new Text(textField.getText(), p.x, p.y));
            }
            if (action == 2) {
                if (!twoPoints) {
                    nextPoint = e.getPoint();
                    twoPoints = true;
                } else {
                    //Set previous to next from now on.
                    previousPoint = nextPoint;

                    //Get a new next point.
                    nextPoint = e.getPoint();

                    Rectangle rectangle1 = getRectangle(previousPoint.getX(), previousPoint.getY());
                    Rectangle rectangle2 = getRectangle(nextPoint.getX(), nextPoint.getY());
                    if (rectangle1 != null && rectangle2 != null && rectangle1 != rectangle2) {
                        Point centerRec1 = new Point((int) rectangle1.getCenterX(), (int) rectangle1.getCenterY());
                        Point centerRec2 = new Point((int) rectangle2.getCenterX(), (int) rectangle2.getCenterY());
                        lines.add(new Line2D.Double(centerRec1, centerRec2));
                        repaint();
                        twoPoints = false;
                    }

                }

            }
            repaint();
        }
    }

}



