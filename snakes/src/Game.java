import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

/** Snake and Apple game
 * 
 * @author Sthita Pragya
 * Game functionality of Snake in this class.
 */
 



public class Game extends JPanel implements ActionListener
{   
    static final int WIDTHscreen = 1400;
    static final int HEIGHTscreen = 800;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (WIDTHscreen*HEIGHTscreen)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int [GAME_UNITS];
    int snake_body = 6;
    int Eaten;
    int fruitX;
    int fruitY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    //Constructor when Game object is created
    public Game()
    {
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTHscreen,HEIGHTscreen));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        start();
    }

    public void start()
    {
        newFruit();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics)
    {
        if (running)
        {
            
            graphics.setColor(new Color(255,215,0));
            graphics.fillOval(fruitX, fruitY, UNIT_SIZE, UNIT_SIZE);

            //Drawing head and body of snake
            for (int i =0; i< snake_body;i ++)
            {
                if (i ==0)
                {
                    graphics.setColor(new Color(44,44,43));
                    graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else
                {
                    graphics.setColor(new Color(255,215,0));
                    graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
        
            }
            graphics.setColor(Color.red);
            graphics.setFont(new Font("Ink Free",Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.drawString("Score: "+Eaten, (WIDTHscreen - metrics.stringWidth("Score: "+Eaten))/2, graphics.getFont().getSize());
   
   
    
    }
    else
    {
        Lost(graphics);
    }
    }

    public void newFruit()
    {
        fruitX = random.nextInt((int)(WIDTHscreen/UNIT_SIZE))*UNIT_SIZE;
        fruitY = random.nextInt((int)(HEIGHTscreen/UNIT_SIZE))*UNIT_SIZE;
    }

    public void move() 
    {
        for (int i = snake_body; i>0; i--)
        {
            x[i] = x[i-1];
            y[i] = y[i-1];

        }
        switch(direction)
        {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
                
        }
    }

    public void fruitCheck()
    {
        if((x[0] == fruitX) && (y[0] == fruitY))
        {
            snake_body++;
            Eaten++;
            newFruit();

        }
    }

    public void checkCollisions()
    {   //checks if head collides with body
        for (int i = snake_body; 0<i;i--)
        {
            if((x[0] == x[i]) && (y[0] == y[i]))
            {
                running = false;
            }
        
        }
        //checks if head collides with left border
        if (x[0] < 0)
        {
            running = false;
        }
        //check if head touches right border
        if (x[0] > WIDTHscreen)
        {
            running = false;
        }
        //check to see if head touches top border
        if (y[0] < 0)
        {
                running = false;
        }
        //check if head touches bottom border
        if (y[0] > HEIGHTscreen)
        {
            running = false;
        }
        
        if (!running)
        {
            timer.stop();
        }

    }




    public void Lost(Graphics graphics)
    {   // Score
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(graphics.getFont());
        graphics.drawString("Score: "+Eaten, (WIDTHscreen - metrics1.stringWidth("Score: "+Eaten))/2, graphics.getFont().getSize());
        // Game Over text
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over", (WIDTHscreen - metrics2.stringWidth("Game Over"))/2, HEIGHTscreen/2);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //TODO Auto-generated method stub
        if (running)
        {
            move();
            fruitCheck();
            checkCollisions();
        }
        repaint();


    }

    public class MyKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            switch(e.getKeyCode())
            {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R')
                    {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L')
                    {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D')
                    {
                        direction = 'U';
                    }
                case KeyEvent.VK_DOWN:
                    if (direction != 'U')
                    {
                        direction = 'D';
                    }





            }
        }
    }
}
