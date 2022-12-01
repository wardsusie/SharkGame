import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;


    public Image sharkPic;
    public Image fish1;
    public Image fish2;
    public Image backgroundPic;


    //Declare the objects used in the program
    //These are things that are made up of more than one variable type
    public Shark greatwhite;
    public Shark orangefish;
    public Shark bluefish;

    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public BasicGameApp() { // BasicGameApp constructor

        setUpGraphics();

        //variable and objects
        //create (construct) the objects needed for the game and load up

        sharkPic = Toolkit.getDefaultToolkit().getImage("Shark.png");
        greatwhite = new Shark("grey", 600, 500);
        greatwhite.width = 170;
        greatwhite.height = 170;

        fish1 = Toolkit.getDefaultToolkit().getImage("OrangeFish.png");
        orangefish = new Shark("orange", 200, 200);
        orangefish.width = 70;
        orangefish.height = 70;

        backgroundPic = Toolkit.getDefaultToolkit().getImage("Ocean.jpg");

        fish2 = Toolkit.getDefaultToolkit().getImage("BlueFish.png");
        bluefish = new Shark ("blue", 200, 200);
        bluefish.width = 60;
        bluefish.height = 60;
    } // end BasicGameApp constructor


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {

        //for the moment we will loop things forever.
        while (true) {
            moveThings();  //move all the game objects
            crash();
            render();  // paint the graphics
            pause(20); // sleep for 10 ms
        }
    }

    public void moveThings() {
        //calls the move( ) code in the objects
        greatwhite.bounce();
        orangefish.bounce();
        bluefish.bounce();

    }

    public void crash() {

        if (orangefish.rec.intersects(greatwhite.recleft) && orangefish.dx>0 && greatwhite.isCrashing == false) {
            greatwhite.didCrash = true;
            greatwhite.isCrashing = true;

            if(orangefish.didCrash == true) {
                orangefish.didCrash = false;
            } else{
                orangefish.didCrash = true;
            }
            System.out.println("CRASH");
            greatwhite.dx = greatwhite.dx;
            greatwhite.dy = greatwhite.dy;
//            orangefish.xpos = -100;
//            orangefish.ypos = -100;
        }
        if (!orangefish.rec.intersects(greatwhite.recleft) && greatwhite.isCrashing == true){
            greatwhite.didCrash = false;
            greatwhite.isCrashing = false;

        }



//        if (bluefish.rec.intersects(greatwhite.recleft)){
//            bluefish.didCrash=true;
//            System.out.println("CRASHED");
//            greatwhite.dx = greatwhite.dx;
//            greatwhite.dy = greatwhite.dy;
//        }

    }



    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause ( int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    //Graphics setup method
    private void setUpGraphics () {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }

    //Paints things on the screen using bufferStrategy
    private void render () {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw the image of the astronaut

        g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);


        g.drawImage(sharkPic, greatwhite.xpos, greatwhite.ypos, greatwhite.width, greatwhite.height, null);

        if (orangefish.didCrash==true){
//            g.drawImage(fish2, bluefish.xpos,bluefish.ypos, bluefish.width, bluefish.height, null);
            g.drawImage(fish2, orangefish.xpos, orangefish.ypos, orangefish.width, orangefish.height, null);

//            g.drawRect(bluefish.rec.x, bluefish.rec.y, bluefish.rec.width, bluefish.rec.height);
            g.drawRect(orangefish.rec.x, orangefish.rec.y, orangefish.rec.width, orangefish.rec.height);

        } else {
            g.drawImage(fish1, orangefish.xpos, orangefish.ypos, orangefish.width, orangefish.height, null);
            g.drawRect(orangefish.rec.x, orangefish.rec.y, orangefish.rec.width, orangefish.rec.height);
        }

        g.drawRect(greatwhite.rec.x, greatwhite.rec.y, greatwhite.rec.width, greatwhite.rec.height);

//    if (orangefish.didCrash==false){
//        g.drawImage (fish1, orangefish.xpos, orangefish.ypos, orangefish.width, orangefish.height, null);
//
//    }

        g.dispose();
        bufferStrategy.show();
    }

}

