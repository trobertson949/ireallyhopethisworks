// utility
import java.util.ArrayList;
import java.util.Random;

// graphics
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;

// events
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// swing
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SpaceInvaders extends JPanel implements ActionListener, KeyListener, Runnable {

    private final int canvasWidth;
    private final int canvasHeight;
    private final Color backgroundColor;

    private final int framesPerSecond = 25;
    private final int msPerFrame = 1000 / framesPerSecond;
    private Timer timer;
    private int frame = 0;

    private Player me;

    private Alien josh;

    private Projectile shooty;

    private ArrayList<GraphicsObject> objects;
    // FIXME list your game objects here
    

    /* Constructor for a Space Invaders game
     */
    public SpaceInvaders() {
        // fix the window size and background color
        this.canvasWidth = 600;
        this.canvasHeight = 400;
        this.backgroundColor = Color.BLACK;
        setPreferredSize(new Dimension(this.canvasWidth, this.canvasHeight));

        // set the drawing timer
        this.timer = new Timer(msPerFrame, this);

        this.me = new Player(300, 375);

        while (num_aliens < 20) {
            this.josh = new Alien(300, 0);
            num_aliens += 1;
        }

        this.shooty = new Projectile(me.x, me.y);
        this.objects = new ArrayList<GraphicsObject>();
        // FIXME initialize your game objects
    }


    /* Start the game
     */
    @Override
    public void run() {
        // show this window
        display();

        // set a timer to redraw the screen regularly
        this.timer.start();
    }

    /* Create the window and display it
     */
    private void display() {
        JFrame jframe = new JFrame();
        jframe.addKeyListener(this);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setContentPane(this);
        jframe.pack();
        jframe.setVisible(true);
    }

    /* Run all timer-based events
     *
     * @param e  An object describing the timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // update the game objects
        update();
        // draw every object (this calls paintComponent)
        repaint(0, 0, this.canvasWidth, this.canvasHeight);
        // increment the frame counter
        this.frame++;
    }

    /* Paint/Draw the canvas.
     *
     * This function overrides the paint function in JPanel. This function is
     * automatically called when the panel is made visible.
     *
     * @param g The Graphics for the JPanel
     */
    @Override
    protected void paintComponent(Graphics g) {
        // clear the canvas before painting
        clearCanvas(g);
        if (hasWonGame()) {
            paintWinScreen(g);
        } else if (hasLostGame()) {
            paintLoseScreen(g);
        } else {
            paintGameScreen(g);
        }
    }

    /* Clear the canvas
     *
     * @param g The Graphics representing the canvas
     */
    private void clearCanvas(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(this.backgroundColor);
        g.fillRect(0, 0, this.canvasWidth, this.canvasHeight);
        g.setColor(oldColor);
    }

    /* Respond to key release events
     *
     * A key release is when you let go of a key
     * 
     * @param e  An object describing what key was released
     */
    public void keyReleased(KeyEvent e) {
        // you can leave this function empty
    }

    /* Respond to key type events
     *
     * A key type is when you press then let go of a key
     * 
     * @param e  An object describing what key was typed
     */
    public void keyTyped(KeyEvent e) {
        // you can leave this function empty
    }

    /* Respond to key press events
     *
     * A key type is when you press then let go of a key
     * 
     * @param e  An object describing what key was typed
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            me.speed_x = -7;
            shooty.speed_x = -7;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            me.speed_x = 7;
            shooty.speed_x = 7;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            shooty.speed_y = -7;
            // FIXME what happens when space bar is pressed
        }
    }

    /* Update the game objects
     */
    private void update() {
        me.update(this.canvasWidth, this.canvasHeight, frame);
        shooty.update(this.canvasWidth, this.canvasHeight, frame);
        for (GraphicsObject obj : this.objects) {
            obj.update(this.canvasWidth, this.canvasHeight, frame);
        }
        // FIXME update game objects here
    }

    /* Check if the player has lost the game
     * 
     * @returns  true if the player has lost, false otherwise
     */
    private boolean hasLostGame() {
        return false; // FIXME delete this when ready
    }

    /* Check if the player has won the game
     * 
     * @returns  true if the player has won, false otherwise
     */
    private boolean hasWonGame() {
        return false; // FIXME delete this when ready
    }

    /* Paint the screen during normal gameplay
     *
     * @param g The Graphics for the JPanel
     */

    private void paintGameScreen(Graphics g) {
        // FIXME draw game objects here
        this.me.draw(g);
        this.josh.draw(g);
        this.shooty.draw(g);

    }

    /* Paint the screen when the player has won
     *
     * @param g The Graphics for the JPanel
     */
    private void paintWinScreen(Graphics g) {
        super.paintComponent(g);
        Graphics win = (Graphics) g;
        win.setColor(Color.yellow);
        win.fillRect(0, 0, canvasWidth, canvasHeight);
        win.setColor(Color.black);
        win.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        win.drawString("Congratulations!", canvasWidth/6, canvasHeight/4);
        win.drawString(":)))) You Win!!!!!", canvasWidth/8, canvasHeight/2);

        // FIXME draw the win screen here
    }

    /* Paint the screen when the player has lost
     *
     * @param g The Graphics for the JPanel
     */
    private void paintLoseScreen(Graphics g) {
        Font tr = new Font("Dialog", Font.PLAIN, 20);
        Font trb = new Font("Dialog", Font.BOLD, 48);
        g.setColor(Color.yellow);
        g.fillOval(0, 0, canvasWidth, canvasHeight);
        g.setColor(Color.BLACK);
        g.setFont(trb);
        g.drawString("CONGRATULATIONS!!", 8, 180);
        g.setFont(tr);
        g.drawString("You lost :(", 250, 220);
        // FIXME draw the game over screen here
    }

    public static void main(String[] args) {
        SpaceInvaders invaders = new SpaceInvaders();
        EventQueue.invokeLater(invaders);
    }
}
