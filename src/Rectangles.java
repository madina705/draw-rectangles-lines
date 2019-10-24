import java.awt.*;
import java.awt.Point;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Rectangles extends JPanel implements ActionListener {

    protected BufferedImage canvas = new BufferedImage(500, 400,
            BufferedImage.TYPE_INT_ARGB);

    JRadioButton rec,line;
    ButtonGroup group;
    int action;

    boolean lineEnabled= false;
    MouseHandler mouseHandler = new MouseHandler();

    Point p1 = new Point(0, 0);
    Point p2 = new Point(0, 0);

    ArrayList<Rectangle> rect = new ArrayList<>();
    ArrayList<Line2D.Double> lines = new ArrayList<>();


    Rectangles(){
        this.setPreferredSize(new Dimension(500, 400));
        this.addMouseListener(mouseHandler);

        rec=new JRadioButton("Rectangle");
        rec.addActionListener(this);
        rec.setBounds(50,50,100,30);
        add(rec);

        line=new JRadioButton("Line");
        line.addActionListener(this);
        line.setBounds(50,100,100,30);
        add(line);

        group=new ButtonGroup();
        group.add(rec);
        group.add(line);
    }


    public  void actionPerformed(ActionEvent ae)
    {
        String str=ae.getActionCommand();

        if(str=="Rectangle")
            action=1;

        if(str=="Line")
            action=2;
    }

    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        if(action==1)
        {   if(rect.size() != 0){
            for(int i = 0; i < rect.size(); i++) {
                graphics.drawRect((int) rect.get(i).getX(), (int) rect.get(i).getY(), 50, 50);
            }}
            if(lines.size() != 0){
                for ( Line2D.Double line : lines)
                {
                    graphics.drawLine ( (int)line.getX1(), (int)line.getY1(),(int)line.getX2(), (int)line.getY2());
                }
            }
        }
        if(action==2 && rect.size() != 0)
        {
            if(rect.size() != 0){
                for(int i = 0; i < rect.size(); i++) {
                    graphics.drawRect((int) rect.get(i).getX(), (int) rect.get(i).getY(), 50, 50);
                }}
            if(lines.size() != 0){
                for ( Line2D.Double line : lines)
                {
                    graphics.drawLine ( (int)line.getX1(), (int)line.getY1(),(int)line.getX2(), (int)line.getY2());
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Rectangles and Line");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(500,500);
        f.setLocationRelativeTo(null);
        f.add(new Rectangles());
        f.pack();
        f.setVisible(true);
    }


    private class MouseHandler extends MouseAdapter {

        boolean twoPoints = false;
        Point nextPoint, previousPoint;

        @Override
        public void mouseClicked(MouseEvent e) {
            if(action==1) {
                p1 = e.getPoint();
                p2 = p1;
                rect.add(new Rectangle(p1.x, p1.y, 50,50));
            }
            repaint();
            if(action==2) {
                   if(!twoPoints){
                        nextPoint = e.getPoint();
                        twoPoints = true;
                    }
                    else{
                        //Set previous to next from now on.
                        previousPoint = nextPoint;

                        //Get a new next point.
                        nextPoint = e.getPoint();

                        //Helper method will draw the line each time.
                       Rectangle rectangle1 = findRectangle(previousPoint.getX(), previousPoint.getY());
                       Rectangle rectangle2 = findRectangle(previousPoint.getX(), previousPoint.getY());
                       if(rectangle1 != null && rectangle2 != null) {
                           lines.add(new Line2D.Double(previousPoint, nextPoint));
                           repaint();
                           twoPoints = false;
                       }

                    }

                }
            }
        }

        public Rectangle findRectangle(double x, double y){
            Rectangle result = null;
            for(Rectangle rec: rect){
                if ( x >= (int)rec.getMinX() && x <= rec.getMaxX() && y >= rec.getMinY() && y <= rec.getMaxY()){
                    result = rec;
                }
            }
            return result;
        }

}



