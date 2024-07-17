import javax.swing.*;


//mostly blank class to use as a superclass to the two game modes. The really important methods in here are incrementCorrect and getResults
public class Game {

  double answer;
  public int correctCounter;

  public Game() {
  }

  /* Increments correctCounter. method to be used in subclasses when user makes a correct guess
  *
  * @return void
  */
  public void incrementCorrect() {
    correctCounter++;
  }

  /* accessor for result
   *
   * @return correctCounter thats been incrementing for each correct guess
   */
  public int getResults() {
    return correctCounter;
  }


  /* starts new round/question
  *
  * @return void
  */
  public void nextRound() {
  }

 
  /* accessor method for answer (to be used outside class)
  *
  * @return answer
  */
  public double getAnswer() {
    return answer;
  }

 /* check the guess (auto false)
  *
  * @return boolean representing if guess was correct or not
  */  
  public boolean checkGuess(JLabel validityLabel, JLabel guessesLabel, JTextField guessField) {
    return false;
  }

 /* get the question
  *
  * @return double
  */  
  public static double getQuestion() {
    return 1.00;
  }

  /* clear certain elements
   *
   * @return void
   */
  public void clear() {
  }


}
