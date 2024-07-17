import java.util.HashSet;
import java.lang.Math;

import javax.swing.*;
import java.awt.*;

public class decToFrac extends Game {

  private double fracAnswer;
  private double numerator;
  private double denominator;
 
  private HashSet<Double> guessSet = new HashSet();
  boolean[] resultsArr = new boolean[10]; // each game is 10 rounds (can change later)

  // create the labels for displaying decimal

  JLabel decimalLabel = new JLabel("", SwingConstants.CENTER);
  JLabel questionLabel = new JLabel("");
  JLabel seperatorLabel = new JLabel("___________", SwingConstants.CENTER);
  JTextField numField = new JTextField();
  JTextField denomField = new JTextField();

  JFrame gameFrame = new JFrame();

  // constructor will generate an answer then set up the neccessary JLabel text
  public decToFrac(JFrame gameFrame, JLabel questionLabel) {

    this.gameFrame = gameFrame;
    this.questionLabel = questionLabel;
    this.numField = numField;
    this.denomField = denomField;

    fracAnswer = getDecimalQuestion(decimalLabel); // called for only the first decimal question

    System.out.println("cheat line: " + fracAnswer);// cheat line (temporary)

    // set decimal label. will be passed as parameters for objects. changes often
    decimalLabel.setBounds(80, 145, 150, 50);
    decimalLabel.setFont(new Font("Symbol", Font.BOLD, 40));
    gameFrame.add(decimalLabel);

    // set seperator line. never changes
    seperatorLabel.setBounds(245, 135, 110, 50);
    seperatorLabel.setFont(new Font("Symbol", Font.BOLD, 20));
    gameFrame.add(seperatorLabel);

    // set numerator label. user guesses their numerator in this label
    numField.setBounds(250, 115, 100, 50);
    numField.setFont(new Font("Symbol", Font.PLAIN, 40));
    gameFrame.add(numField);

    // set denominator label. user guesses their denominator in this label
    denomField.setBounds(250, 175, 100, 50);
    denomField.setFont(new Font("Symbol", Font.PLAIN, 40));
    gameFrame.add(denomField);

    // set question label
    questionLabel.setBounds(100, 270, 400, 100);
    questionLabel.setFont(new Font("TimesRoman", Font.PLAIN, 20));
    questionLabel.setText("<html><body>What is the above decimal in fraction form?        (type answer in x/y form)");
    gameFrame.add(questionLabel);

  }

  // starts new round/question
  public void nextRound() {

    // get a new answer
    fracAnswer = getDecimalQuestion(decimalLabel);

    // clear/reset outdated text
    questionLabel.setText("<html><body>What is the above decimal in fraction form?        (type answer in x/y form)");

    // clears previous guesses
    guessSet.clear();

    System.out.println("cheat line: " + fracAnswer);// cheat line (temporary)

  }

  /**
   * This method checks to see if the user's guess is correct, was alreadgy
   * guessed, or if it's a valid number (< 1.0)
   *
   * @param validityLabel, guessesLabel, guessField
   * @return boolean true or false (if the user is correct or not)
   */
  public boolean checkGuess(JLabel validityLabel, JLabel guessesLabel, JTextField guessField) {
   
    double guessedNum = Double.parseDouble(numField.getText());
    numField.setText("");
   
    double guessedDenom = Double.parseDouble(denomField.getText());
    denomField.setText("");

    double guess = guessedNum / guessedDenom;

    if((Math.round(guess * 100.0) / 100.0) == fracAnswer){ //correct
      validityLabel.setFont(new Font("TimesRoman", Font.PLAIN, 30));
      validityLabel.setForeground(Color.GREEN);
      validityLabel.setText("Correct answer!");

        super.incrementCorrect();
     
      return true;
    }
    else{ //incorrect
      validityLabel.setFont(new Font("TimesRoman", Font.PLAIN, 15));
      validityLabel.setForeground(Color.RED);
      validityLabel.setText("Incorrect, try again.");

      guessSet.add(guess);
      guessesLabel.setText("You have made " +
      guessSet.size() + " guesses");                      

      return false;
    }
   
  }

  /**
   *
   *
   * @param
   * @return
   */
  public static double getDecimalQuestion(JLabel decimalLabel) {

    double fraction;
    // generate and round random decimal
    double decimal = ((Math.random() * (0 - 1)) + 1);
    decimal = Math.round(decimal * 100.0) / 100.0;

    // change decimal label
    decimalLabel.setText(Double.toString(decimal) + " =");

    /*
    //converting decimal to fraction
    fraction = decimal * 100;

    for(int i = 0; i < 100; i++){
      if(fraction % i == 0 && 100 % i == 0){
        numerator = fraction/i;
        denominator = 100/i;
 
        return 0;
      }
      else{
        numerator = fraction;
        denominator = 100;
   
        return 0;
      }

    }
*/
    return decimal;
  }

  /*
   * This method clears the labels specific to decToFrac
   *
   *
   * @return void
   */
  public void clear() {
    decimalLabel.setText("");
    seperatorLabel.setVisible(false);
    numField.setVisible(false);
    denomField.setVisible(false);
  }

  /*
   * Accessor method for fracAnswer (to be used outside class)
   *
   *
   * @return fracAnswer
   */
  public double getAnswer() {
 
    return fracAnswer;
  }
}
