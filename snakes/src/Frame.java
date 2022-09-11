import javax.swing.JFrame;

/**
 * @author Sthita Pragya
 * Using the java swing API to create windown based GUI.
 */
public class Frame extends JFrame
{
    public Frame()
    {
        Game panel = new Game();
        this.add(panel);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}
