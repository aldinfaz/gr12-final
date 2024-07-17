import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.*;  


public class Frame extends JFrame {

  //questionCount will keep track of how many questions have been played
  int questionCount = 1;
  int roundNumber;
  public String scoreStr;

  FileWriter fileWriter;
  BufferedWriter buffer;

  // initialize all jswing/graphic components. These all exist in either game mode, as the Frame class represents the frame that either game is played on
  JFrame gameFrame = new JFrame("Fractions practice game");
  JLabel questionLabel = new JLabel(""); //in this class it is always either blank or displaying the score
  JLabel guessesLabel = new JLabel("You have made 0 guesses", SwingConstants.CENTER);
  JLabel validityLabel = new JLabel("", SwingConstants.CENTER);
  JTextField guessField = new JTextField();
  JButton guessButton = new JButton("Guess");
  JButton giveUpButton = new JButton("Give Up");
  JButton nextButton = new JButton("NEXT");
  JButton againButton = new JButton("Play Again");

  //initialize Game object, to be defined later
  Game myGame;

  public int mode;

  /* Frame constructor
  *
  * @param mode an int of either 1 or 2, to represent the user's choice of game mode
  */
  public Frame(int mode, int roundNumber) {

    this.mode = mode;
    this.roundNumber = roundNumber;

    // define myGame object based on game mode
    if (mode == 1){
       myGame = new fracToDec(gameFrame, questionLabel);
    }
    if(mode == 2){ //this one eliminates normal guessField settings
      myGame = new decToFrac(gameFrame, questionLabel);
      guessField.setVisible(false);
    }

    // set gameFrame
    gameFrame.setSize(600, 600);
    gameFrame.setBackground(Color.GRAY);
    gameFrame.setLayout(null);
    gameFrame.setVisible(true);
gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // set guesses counter label. displays as soon as game is being played (0), and
    // will increase after each valid guess. resets on next round.
    guessesLabel.setBounds(50, 350, 500, 50);
    guessesLabel.setFont(new Font("TimesRoman", Font.PLAIN, 16));
    gameFrame.add(guessesLabel);

    // set valid guess label
    // will display "you have already guessed _", "You have not entered a decimal",
    // "Incorrect, try again" and "Correct answer!"
    validityLabel.setBounds(65, 440, 500, 50);
    validityLabel.setFont(new Font("TimesRoman", Font.PLAIN, 15));
    gameFrame.add(validityLabel);
    validityLabel.setForeground(Color.BLACK);

    // set guess field
    guessField.setBounds(200, 400, 200, 50);
    guessField.setFont(new Font("Symbol", Font.PLAIN, 15));
    gameFrame.add(guessField);

    // set give up button. on action, enable/disable neccessary buttons and reset questionLabel to display answer
    giveUpButton.setBackground(new Color(0, 0, 30));
    giveUpButton.setForeground(Color.WHITE);
    giveUpButton.setBounds(50, 400, 150, 50);
    giveUpButton.setFont(new Font("Symbol", Font.PLAIN, 18));
    giveUpButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //necessary enabled settings
        guessButton.setEnabled(false);
        giveUpButton.setEnabled(false);
        nextButton.setEnabled(true);
       
        questionLabel.setText("The correct answer was " + myGame.getAnswer());

      }
    });
    gameFrame.add(giveUpButton);

    // set guess button. on action, use Game object's checkGuess and set enabled buttons according to boolean return
    guessButton.setBackground(new Color(0, 0, 30));
    guessButton.setForeground(Color.WHITE);
    guessButton.setBounds(400, 400, 150, 50);
    guessButton.setFont(new Font("Symbol", Font.PLAIN, 18));
    guessButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        boolean correct = myGame.checkGuess(validityLabel, guessesLabel, guessField); // bool correct represents if guess was correct
       
        nextButton.setEnabled(correct);
        giveUpButton.setEnabled(!correct);
        guessButton.setEnabled(!correct);
      }
    });
    gameFrame.add(guessButton);

    // set next button. on action, use Game object's nextRound method, reset outdated text, set enabled buttons to original status', increment question count, and finally check if rounds limit has been passed
    nextButton.setBackground(new Color(204, 204, 204));
    nextButton.setForeground(Color.BLACK);
    nextButton.setBounds(450, 455, 100, 50);
    nextButton.setFont(new Font("Monospaced", Font.BOLD, 15));
    nextButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        myGame.nextRound();
        validityLabel.setText("");
        guessesLabel.setText("You have made 0 guesses.");
       
        giveUpButton.setEnabled(true);
        guessButton.setEnabled(true);
        nextButton.setEnabled(false);

        questionCount++;

        // ends round once specified amount of questions have passed
        if (questionCount > roundNumber) {

          endRound();

        }
      }
    });
    gameFrame.add(nextButton);

    // set again button
    againButton.setBackground(new Color(0, 0, 30));
    againButton.setForeground(Color.WHITE);
    againButton.setBounds(225, 150, 150, 50);
    againButton.setFont(new Font("Symbol", Font.PLAIN, 18));
    againButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // delete previous frame/round
        gameFrame.dispose();
      }
    });
    // gameFrame.add(againButton);
    nextButton.setEnabled(false); // auto disabled
  }

  /* This method clears all necessary labels, displays the "Play Again" button on the frame, disables the "next", "guess", and "give up" buttons, updates necessary text
   *
   *
   * @return void
   */
  public void endRound() {

    myGame.clear();

    // displaying play again button
    gameFrame.add(againButton);

    // disabling buttons
    nextButton.setEnabled(false);
    guessButton.setEnabled(false);
    giveUpButton.setEnabled(false);


    // string that represents the final score of this frame. to be used outside Frame
     scoreStr = (myGame.getResults() + "/" + roundNumber);
   
    // updating text
    guessesLabel.setText("");
    questionLabel.setText("Your score was: " + scoreStr);

    storeScores();

  }

  /* making a string of the game instance's details
  * @return string
  */
  public String getFrameString () {

    //combine scoreStr (made in endRound) with the game mode and the current time
    if(mode == 1) return (scoreStr + " in game mode Fractions-to-Decimals at " + java.time.LocalTime.now(ZoneId.of("America/New_York")));
    else if(mode == 2) return (scoreStr + " in game mode Decimals-to-Fractions at " + java.time.LocalTime.now(ZoneId.of("America/New_York")));
    else return "[an error has occured]";
  }

  /*write the score to our scores file. to be called at a game's end
  *
  */
  public void storeScores() {

    try {
      buffer = new BufferedWriter(new FileWriter("scores.txt", true));
     
      buffer.write(getFrameString() + "\n");
      if (buffer != null) buffer.close();
    } catch (Exception e) {
      System.out.println("An error occurred with writing to scores.txt.");
    }

  }
}
