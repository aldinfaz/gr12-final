import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Main extends JFrame {

  //jswing components that first appear are initialized
  JFrame menuFrame = new JFrame("Fractions Practice Game");
  JLabel welcomeLabel = new JLabel("Welcome! ", SwingConstants.CENTER);
  JLabel instructionsLabel = new JLabel("<html><body>Please select the amount of rounds you'd like to play and a gamemode.");
  JLabel spinnerLabel = new JLabel("Rounds:", SwingConstants.CENTER);
  JButton fracdecButton = new JButton("Fraction to Decimal");
  JButton decfracButton = new JButton("Decimal to Fraction");
  JButton scoresButton = new JButton("View Past Scores");
  JSpinner roundSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 20, 1)); //parameters: start value, min, max, steps

  JFrame scoresFrame = new JFrame("Personal Scoreboard");
  JTextArea scoresArea = new JTextArea();
  JScrollPane scoreScroller = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


  //file will be set up in this class, written to in Frame class, then print the information from this class
  FileWriter fileWriter;
  Scanner scanner;
  File file;

  /* Main constructor sets up MenuFrame, welcomeLabel and each game mode button (fracDec and decFrac)
  *
  */
  public Main() {
    //scores file is created
    createScoresFile();

    //setting menuFrame components below
    menuFrame.setSize(600, 600);
    menuFrame.setBackground(Color.GRAY);
    menuFrame.setLayout(null);
    menuFrame.setVisible(true);
menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    welcomeLabel.setBounds(70, 25, 450, 100);
    welcomeLabel.setFont(new Font("TimesRoman", Font.PLAIN, 20));
    menuFrame.add(welcomeLabel);

    instructionsLabel.setBounds(115, 50, 450, 150);
    instructionsLabel.setFont(new Font("TimesRoman", Font.PLAIN, 20));
    menuFrame.add(instructionsLabel);

    spinnerLabel.setBounds(105, 100, 400, 200);
    spinnerLabel.setFont(new Font("TimesRoman", Font.PLAIN, 20));
    menuFrame.add(spinnerLabel);
   
    //set roundSpinner. takes value and is used for how many rounds user plays. can delete if it doesnt work out. also we will need a label
    roundSpinner.setBounds(270, 220, 70, 70);
    roundSpinner.setFont(new Font("Symbol", Font.PLAIN, 30));
    menuFrame.add(roundSpinner);

    // set fraction to decimal button. will pass int 1 to its created Frame to indicate the choice that has been made
    fracdecButton.setBackground(new Color(0, 0, 30));
    fracdecButton.setForeground(Color.WHITE);
    fracdecButton.setBounds(50, 330, 220, 50);
    fracdecButton.setFont(new Font("Symbol", Font.PLAIN, 18));
    fracdecButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        //frame object of game mode 1. only grabbing spinner value when gme is created
        Frame frame = new Frame(1, (Integer)roundSpinner.getValue());


        if (scoresFrame != null) scoresFrame.dispose(); //close scoresFrame if user didn't do it themself

      }
    });
    menuFrame.add(fracdecButton);

    // set decimal to fraction button. will pass int 2 to its created Frame to indicate the choice that has been made
    decfracButton.setBackground(new Color(0, 0, 30));
    decfracButton.setForeground(Color.WHITE);
    decfracButton.setBounds(300, 330, 220, 50);
    decfracButton.setFont(new Font("Symbol", Font.PLAIN, 18));
    decfracButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        Frame frame = new Frame(2, (Integer)roundSpinner.getValue());

        if (scoresFrame != null) scoresFrame.dispose();

      }
    });
    menuFrame.add(decfracButton);
 
    scoresButton.setBackground(new Color(0, 0, 30));
    scoresButton.setForeground(Color.WHITE);
    scoresButton.setBounds(180, 400, 220, 50);
    scoresButton.setFont(new Font("Symbol", Font.PLAIN, 18));
    scoresButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        createScoresFrame();
      }
    });
    menuFrame.add(scoresButton);

    //scoresFrame components being set below
    scoresFrame.setSize(600, 400);
    scoresFrame.setBackground(Color.GRAY);
    scoresFrame.setVisible(false);

    scoresArea.setBounds(50, 50, 300, 450);
    scoresArea.setFont(new Font("TimesRoman", Font.PLAIN, 15));
    scoresArea.setEditable(false);
    scoresArea.setBounds(50, 50, 300, 450);
    scoresArea.setVisible(true);
    scoresFrame.add(scoresArea);
    //scroller added onto abovearea
    scoreScroller.setViewportView(scoresArea);
    scoresFrame.add(scoreScroller);
  }

  /* Creates the "scores.txt" file. is only called once (at the start of a run)
   *
   *
   * @return void
   */
  public void createScoresFile() {
    try {
      fileWriter = new FileWriter(new File("scores.txt"));

    } catch (Exception e) {
      System.out.println("An error occurred while creating scores.txt.");
    }
   
  }

  /* Reads the text from our scores file and appends that on a text area, before setting the whole frame visible
  * to be called when past scores button is triggered.
  *
  * @return void
  */
  public void createScoresFrame () {
    scoresArea.setText(""); //text area is cleared and all past scores are rewritten for each button press
     
    try {
      scanner = new Scanner(new File("scores.txt"));

      //while file still has text to read, append it to the area
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        scoresArea.append(line + "\n");
      }

      scanner.close();
    }catch(Exception e) {
      System.out.println("An error occurred retreiving past scores.");
    }

    scoresFrame.setVisible(true);
  }


  /* Creating the GUI in main method
   *
   * @return void
   */
  public static void main(String[] args) {
    Main main = new Main();
  }

}
