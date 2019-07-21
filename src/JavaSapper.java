import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sapper.Box;
import sapper.Coord;
import sapper.Game;
import sapper.Ranges;

public class JavaSapper extends JFrame {

    private Game game;

    private JPanel panel;
    private JLabel label;

    private int COLS;
    private int ROWS;
    private int BOMBS;
    private final int IMAGE_SIZE = 50;

    public JavaSapper(){

        if (AuxiliaryWindow.easy){
            COLS = 9;
            ROWS = 9;
            BOMBS = 10;
        }
        else if (AuxiliaryWindow.medium){
            COLS = 13;
            ROWS = 13;
            BOMBS = 35;
        }
        else{
            COLS = 16;
            ROWS = 16;
            BOMBS = 52;
        }

        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initPanel();
        initLabel();
        initFrame();
    }

    private void initLabel(){
        label = new JLabel("Welcome! There are " + game.bombsLeft + " bombs left!");
        add(label, BorderLayout.SOUTH);
    }

    private void initPanel(){
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords())
                    g.drawImage((Image)game.getBox(coord).image,
                            coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    game.pressRightButton(coord);
                }
                label.setText(getMessage());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE)) ;
        add(panel);
    }

    private String getMessage(){
        switch (game.getState()){
            case PLAYED : return "Think twice! There are " + game.bombsLeft + " bombs left!";
            case BOMBED : return "Game Over! Press left mouse button to regame!";
            case WINNER : return "Congratulations! You're winner!";
            default     : return "Utopia";
        }
    }

    private void initFrame(){
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        if (AuxiliaryWindow.easy)
            setTitle("SapperGame : easy");
        else if (AuxiliaryWindow.medium)
            setTitle("SapperGame : medium");
        else setTitle("SapperGame : difficult");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setIconImage(getImage("icon"));
    }

    private Image getImage(String name){
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }

    private void setImages(){
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }
}
