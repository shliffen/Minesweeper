import shliffen.sweeper.Box;
import shliffen.sweeper.Coord;
import shliffen.sweeper.Game;
import shliffen.sweeper.Ranges;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Minesweeper extends JFrame {

    private Game game;
    private JPanel panel;
    private JLabel label;
    private final int IMAGE_SIZE = 50;
    private final int COLUMNS = 12;
    private final int ROWS = 12;
    private final int BOMBS = 16;

    private Minesweeper() {
        game = new Game(COLUMNS, ROWS, BOMBS);
        game.startGame();
        Ranges.setSize(new Coord(COLUMNS,ROWS));
        initImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setVisible(true);
        setResizable(false);
        setIconImage(getImage("icon"));
        pack();
        setLocationRelativeTo(null);
    }

    private void initPanel() {
        panel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()){
                    g.drawImage((Image) game.getBox(coord).image,
                                coord.getX()*IMAGE_SIZE,
                                coord.getY()*IMAGE_SIZE
                            ,this);
                }

            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                // after clicking left button - open the box
                if (e.getButton()==MouseEvent.BUTTON1) game.pressLeftButton(coord);
                //after clicking middle button - restart the game
                if (e.getButton()==MouseEvent.BUTTON2) game.startGame();
                //after clicking right button - put the flag
                if (e.getButton()==MouseEvent.BUTTON3) game.pressRightButton(coord);
                label.setText(getMessage());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(Ranges.getSize().getX()*IMAGE_SIZE, Ranges.getSize().getY()*IMAGE_SIZE));
        add(panel);
    }

    private void initLabel(){
        label = new JLabel("Welcome to Minesweeper, which was made by Illya Makh =)");
        add(label,BorderLayout.SOUTH);
    }

    private Image getImage(String imageName){
        String filename = "images/" + imageName + ".png";
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(filename));
        return imageIcon.getImage();
    }

    private void initImages(){
        for (Box box: Box.values()){
            box.image = getImage(box.name().toLowerCase());
        }
    }

    private String getMessage(){
        switch (game.getGameState())
        {
            case PLAYED: return "Where are the 16 bombs??      Can you find everyone??";
            case BOMBED: return "Sorry but you LOSE =(     Be careful next time!!!      Press middle-button to restart";
            case WINNER: return "CONGRATULATIONS!!!     YOU WIN !! =)       Press middle-button to restart";
            default: return "You will never see this row =)";
        }
    }


    public static void main(String[] args) {
        new Minesweeper();
    }
}
