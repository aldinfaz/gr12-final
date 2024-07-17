import java.util.HashSet;
import java.lang.Math;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

public class fracToDec extends Game {

  private double decAnswer;
  private HashSet<Double> guessSet = new HashSet();

  // create the labels for displaying fraction
  JLabel numeratorLabel = new JLabel("", SwingConstants.CENTER);
  JLabel seperatorLabel = new JLabel("________", SwingConstants.CENTER);
  JLabel denominatorLabel = new JLabel("", SwingConstants.CENTER);
  JLabel questionLabel = new JLabel("");  
 
  JFrame gameFrame = new JFrame();
 
  // constructor will generate an answer then set up the neccessary JLabel text
  public fracToDec(JFrame gameFrame, JLabel questionLabel) {

    this.gameFrame = gameFrame;
    this.questionLabel = questionLabel;

    decAnswer = getFractionQuestion(numeratorLabel, denominatorLabel); // called for only the first fraction question

    System.out.println("cheat line: " + decAnswer);// cheat line (temporary)

    //subclass' unique jswing objects below
    // set numerator label. will be passed as parameters for objects. changes often
    numeratorLabel.setBounds(250, 130, 100, 50);
    numeratorLabel.setFont(new Font("Symbol", Font.BOLD, 40));
    gameFrame.add(numeratorLabel);

    // set seperator line. never changes
    seperatorLabel.setBounds(250, 145, 100, 50);
    seperatorLabel.setFont(new Font("TimesRoman", Font.PLAIN, 20));
    gameFrame.add(seperatorLabel);

    // set denominator label. similar to numerator
    denominatorLabel.setBounds(250, 180, 100, 50);
    denominatorLabel.setFont(new Font("Symbol", Font.BOLD, 40));
    gameFrame.add(denominatorLabel);
   
    // set question label. This label will either display the question or the correct answer
    questionLabel.setBounds(100, 270, 500, 100);
    questionLabel.setFont(new Font("TimesRoman", Font.PLAIN, 20));
    questionLabel.setText("<html><body>What is the above fraction in decimal form?        (round to 2 decimal places)");
    gameFrame.add(questionLabel);

  }

  /* starts new round/question. first gets a new fraction question, then resets question label, then clears guessSet
   *
   * @return void
   */
  public void nextRound() {

    //get a new answer
    decAnswer = getFractionQuestion(numeratorLabel, denominatorLabel);
   
    //clear/reset outdated text
    questionLabel.setText("<html><body>What is the above fraction in decimal form?        (round to 2 decimal places)");

    // clears previous guesses
    guessSet.clear();

    System.out.println("cheat line: " + decAnswer);// cheat line (temporary)

  }

   /**
   * This method checks to see if the user's guess is correct, was alreadgy guessed, or if it's a valid number (< 1.0)
   *
   * @param validityLabel, guessesLabel, guessField
   * @return boolean true or false (if the user is correct or not)
   */
  public boolean checkGuess(JLabel validityLabel, JLabel guessesLabel, JTextField guessField) {
    System.out.println("checkGuess: " + decAnswer);

    // take user input then clear field
    double guessedDec = Double.parseDouble(guessField.getText());
    guessField.setText("");

    // did they already guess this
    if (guessSet.contains(guessedDec)) {
     
      validityLabel.setFont(new Font("TimesRoman", Font.PLAIN, 15));
validityLabel.setForeground(Color.BLACK);
      validityLabel.setText("You have already guessed " + guessedDec);
    }

    // following only runs for decimals and 1
    else if (guessedDec <= 1 && guessedDec > 0) {

      if (guessedDec == decAnswer) { // correct answer
        validityLabel.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        validityLabel.setForeground(Color.GREEN);
        validityLabel.setText("Correct answer!");

        super.incrementCorrect(); //call super method, as Game stores correctCounter

        return true;
      }

      else { // incorrect answer
        validityLabel.setFont(new Font("TimesRoman", Font.PLAIN, 15));
    validityLabel.setForeground(Color.RED);
        validityLabel.setText("Incorrect, try again.");

        //add to guessSet, then display new info
        guessSet.add(guessedDec);
        guessesLabel.setText("You have made " + guessSet.size() + " guesses");
      }
    } else { // when answer is not a decimal
      validityLabel.setFont(new Font("TimesRoman", Font.PLAIN, 15));
      validityLabel.setForeground(Color.BLACK);
      validityLabel.setText("You have not entered a decimal.");
    }

    return false;
  }
 
  /**
   * This method generates and assigns a numerator and denonminator to their respective labels. It also calculates the answer of the fraction rounded to 2 decimal places
   *
   * @param numeratorLabel, denominatorLabel
   * @return decAnswer (answer rounded to 2 decimal places)
   */
  public static double getFractionQuestion(JLabel numeratorLabel, JLabel denominatorLabel) {

    double decimal;
    // initialize decimal variable

    int num1 = (int) ((Math.random() * (10 - 1)) + 1);
    int num2 = (int) ((Math.random() * (10 - 1)) + 1);
    // generate 2 random integers from 1-10

    if (num1 < num2) {
      numeratorLabel.setText(Integer.toString(num1));
      denominatorLabel.setText(Integer.toString(num2));
      // setting label text to their respective values

      decimal = Double.valueOf(num1) / Double.valueOf(num2);
      // converts num1 and num2 to doubles and divdes num1 by num2

      return Math.round(decimal * 100.0) / 100.0;
      // returns decimal rounded to 2 decimals
    }

    else if (num1 > num2) {
      numeratorLabel.setText(Integer.toString(num2));
      denominatorLabel.setText(Integer.toString(num1));
      // setting label text to their respective values

      decimal = Double.valueOf(num2) / Double.valueOf(num1);
      // converts num1 and num2 to doubles and divdes num2 by num1

      return Math.round(decimal * 100.0) / 100.0;
      // returns decimal rounded to 2 decimal places
    }

   
    else { // if num1 and num2 are equal
      numeratorLabel.setText(Integer.toString(num1));
      denominatorLabel.setText(Integer.toString(num2));
      // setting label text to their respective values

      return 1.00;
      // returns 1 since both numerator and denominator are equal
    }
  }
 
 
  /* This method clears the labels specific to fractoDec
   *
   * @return void
   */
  public void clear() {
    numeratorLabel.setText("");
    seperatorLabel.setText("");
    denominatorLabel.setText("");
  }

 
  /* Accessor method for decAnswer (to be used outside class)
   *
   *
   * @return decAnswer
   */
  public double getAnswer() {
    return decAnswer;
  }
}
