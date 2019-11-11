import java.awt.*;

public class Text {
    String text;
    int x, y;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    Text(String text, int x, int y){
        this.text = text;
        this.x = x;
        this.y = y;
    }
}
