import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

  public class Reversi extends JPanel
{
    private JFrame frame;
    public static boolean turn = true;
    public int score1 = 2;
    public int score2 = 2;
    public JLabel s1;
    public JLabel s2;
    public JTextField name1;
    public JTextField name2;
    public JLabel p1;
    public JLabel p2;
    public String n1;
    public String n2;
    public JLabel statusBar;
    public JPanel info;
    public JPanel gameBoard;
    public JButton buttons[][];
    
    public Reversi()
    {
        
        frame = new JFrame("Reversi");
        
        frame.pack();
        frame.setSize(700,600);
        frame.setVisible(true);

        frame.add(makeScoreBoard());
        frame.add(makeGrid());
        
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, info, gameBoard);
        
        frame.getContentPane().add(split);
        
        frame.setSize(500,500);
 
    }
    public JPanel makeGrid(){
        
        int cols = 8;
        int rows = 8;

        buttons = new JButton [cols][rows];
     
        for(int i =0; i<cols; i++){
            
            for(int j = 0; j< rows; j++){
               
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(new Move());
                buttons[i][j].setPreferredSize(new Dimension(10,10));
                buttons[i][j].setBackground(Color.GRAY);
                gameBoard.add(buttons[i][j]);
               
            }
            
        }
        
        buttons[3][3].setBackground(Color.WHITE);
        buttons[3][4].setBackground(Color.BLACK);
        buttons[4][3].setBackground(Color.BLACK);
        buttons[4][4].setBackground(Color.WHITE);
        buttons[3][3].setText("W");
        buttons[3][4].setText("B");
        buttons[4][4].setText("W");
        buttons[4][3].setText("B");
        
        gameBoard.setLayout(new GridLayout(8,8));
        
        return gameBoard;

    }
    public JPanel makeScoreBoard(){
        
        info = new JPanel();
        gameBoard = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        info.setLayout(gridBag);
        c.fill = GridBagConstraints.HORIZONTAL;
        
        
        p1 = new JLabel("Player 1");
        c.gridx = 0;
        c.gridy = 0;
        gridBag.setConstraints(p1, c);
        
        info.add(p1);
        
        p2 = new JLabel("Player 2");
        c.gridx = 1;
        c.gridy = 0;
        gridBag.setConstraints(p2, c);
        info.add(p2);
        
        name1 = new JTextField();
        c.gridx= 0;
        c.gridy= 2;
        c.weighty = 0.5;
        gridBag.setConstraints(name1, c);
        info.add(name1);
        
        name2 = new JTextField();
        c.gridx = 1;
        c.gridy = 2;
        c.weighty = 0.5;
        gridBag.setConstraints(name2, c);
        info.add(name2);
        
        s1 = new JLabel();
        c.gridx = 0;
        c.gridy = 1;
        gridBag.setConstraints(s1, c);
        info.add(s1);
        
        s2 = new JLabel();
        c.gridx = 1;
        c.gridy = 1;
        gridBag.setConstraints(s2, c);
        info.add(s2);
        
        statusBar = new JLabel();
        c.gridx = 0;
        c.gridy = 4;
        gridBag.setConstraints(statusBar, c);
        info.add(statusBar);
        
        JButton start = new JButton("Start");
        c.gridx =0;
        c.gridy = 3;
        gridBag.setConstraints(start, c);
        info.add(start);
        start.setPreferredSize(new Dimension(70,70));
        start.addActionListener(new start());
        
        JButton reset = new JButton("Reset");
        c.gridx = 1;
        c.gridy = 3;
        gridBag.setConstraints(reset, c);
        
        reset.setPreferredSize(new Dimension(70,70));
        info.add(reset);
        reset.addActionListener(new reset());
        
        
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        JMenu fileMenu = new JMenu("File");
        menubar.add(fileMenu);
        
        JMenuItem saveItem = new JMenuItem("save");
        fileMenu.add(saveItem);
        saveItem.addActionListener(new saveGame());
        
        JMenuItem loadItem = new JMenuItem("load");
        fileMenu.add(loadItem);
        loadItem.addActionListener(new load());
        
        
        JMenu changeSizeMenu = new JMenu("Change Size");
        menubar.add(changeSizeMenu);
        
        return info;
        
    }
    public class Move implements ActionListener{
        
        public void actionPerformed(ActionEvent e){
            
            String text = (String)e.getActionCommand();
            
            if (text.equals("") && turn == true){
                
                ((JButton)e.getSource()).setBackground(Color.BLACK);
                ((JButton)e.getSource()).setText("B");
                score1 ++;
                s1.setText(String.valueOf(score1));               
                statusBar.setText(p2.getText() + "'s turn");
                turn = false;
                
            } else if(text.equals("") && turn == false){
                
                ((JButton)e.getSource()).setBackground(Color.WHITE);
                ((JButton)e.getSource()).setText("W");
                score2++;
                s2.setText(String.valueOf(score2));
                statusBar.setText(p1.getText() + "'s turn");
                turn = true;
                
            }
        }
    }
        public class reset implements ActionListener{
             public void actionPerformed(ActionEvent e){
 
             }
            
        }
        public class saveGame implements ActionListener{
             public void actionPerformed(ActionEvent e){
            
                    try{
                      FileOutputStream saveFile=new FileOutputStream("SaveObj.sav");
                      ObjectOutputStream save = new ObjectOutputStream(saveFile);
                      save.writeObject(score1);
                      save.writeObject(score2);
                      save.writeObject(n1);
                      save.writeObject(n2);
                      save.writeObject(buttons);
                      
                      save.close();
                      
                      JOptionPane.showMessageDialog(frame, "Game saved!");
                    }catch(Exception exc)
                    {
                        exc.printStackTrace();
                    }
             }
            
        }
    public class load implements ActionListener{
        public void actionPerformed(ActionEvent e){
            
                try{
                
                FileInputStream saveFile = new FileInputStream("SaveObj.sav");
                ObjectInputStream save = new ObjectInputStream(saveFile);
                score1 = (int) save.readObject();
                score2 = (int) save.readObject();
                n1  = (String) save.readObject();
                n2 = (String) save.readObject();
                buttons = (JButton[][]) save.readObject();
                
                p1.setText(n1);
                p2.setText(n2);
                s1.setText(String.valueOf(score1));
                s2.setText(String.valueOf(score2));

                save.close();
                
                } catch(Exception exc){
                    exc.printStackTrace();
                }
            }
            
        }
    
    public class start implements ActionListener{
        public void actionPerformed(ActionEvent e){
            
            String text = (String)e.getActionCommand();
            n1 = name1.getText();
            p1.setText(n1);
            n2 = name2.getText();
            p2.setText(n2);
            name1.setText("");
            name2.setText("");
            
        }
    }
}
