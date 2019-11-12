import javax.swing.*;
import java.awt.*;

class CustomRectangle {
    private Rectangle rectangle;
    private boolean isComment;

    public CustomRectangle(Rectangle rect, boolean isComment){
        this.rectangle = rect;
        this.isComment = isComment;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public boolean isComment() {
        return isComment;
    }

    public void setComment(boolean comment) {
        isComment = comment;
    }
}
