import java.awt.*;

public class Shark {
    public String color;               //name of the hero
    public int xpos;                  //the x position
    public int ypos;                  //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;                 //the width of the hero image
    public int height;                //the height of the hero image
    public Rectangle rec;
    public boolean didCrash = false;
    public Rectangle recleft;
    public boolean isCrashing = false;


    public Shark(String pColor, int pXpos, int pYpos) {
        color = pColor;
        xpos = pXpos;
        ypos = pYpos;
        dx = (int) (Math.random() * 10 + 4);
        dy = (int) (Math.random() * 10 + 4);
        width = 150;
        height = 100;
        rec = new Rectangle(xpos, ypos, width, height);
        recleft = new Rectangle(xpos, ypos, 10, height);

    }

    public void move() { // move
        xpos = xpos + dx;
        ypos = ypos + dy;
    }

    public void bounce() {
        xpos = xpos + dx;
        ypos = ypos + dy;

        if (xpos >= 1000 - width || xpos <= 0) { // right or left wall
            dx = -dx;
        }

        if (ypos >= 700 - height || ypos <= 0) {
            dy = -dy;
        }
        rec = new Rectangle(xpos, ypos, width, height);
        recleft = new Rectangle(xpos, ypos, 10, height);

    }

    public void wrap() {
        xpos = xpos + dx;
        ypos = ypos + dy;

        if (xpos >= 1000 && dx > 0) { // teleport right to left
            xpos = -width;

        }

        if (xpos <= 0 - width && dx < 0) { // teleport left to right
            xpos = 1000;
        }

        if (ypos >= 700 && dy > 0) { // teleport bottom to top
          ypos = 0 - height;
        }

            if (ypos <= 0 && dy < 0) {

           ypos = 1000;

       }
            rec = new Rectangle(xpos, ypos, width, height);
            recleft = new Rectangle(xpos, ypos, 10, height);

        }
    }




