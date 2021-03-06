package org.asl.socketserver.games;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.asl.socketserver.AbstractGame;
import org.asl.socketserver.BestScore;
import org.asl.socketserver.GameInfo;
import org.asl.socketserver.Servable;

/***
 * Reference implementation of a Servable class.
 * 
 * @author K. Collins
 * @version Fall 2017
 *
 */
@GameInfo(authors = {
		"Kent Collins" }, version = "Fall, 2017", gameTitle = "The Secret Word", description = "Guess the secret word and win!")
public class SecretWord extends AbstractGame implements Servable {

	private static final int GUESS_LIMIT = 5;
	private final String secret = "the secret word";
	private final String prompt = "Guess the secret word or enter 'q' to quit";
	private int numGuesses = 0; // explicit initialization -- would be 0 by default

	@Override
	public void serve(BufferedReader input, PrintWriter output)
			throws IOException {
		output.println("Welcome to the Secret Word game.");
		output.println(prompt);
		String userInput = input.readLine().trim();
		while (!userInput.equals("q") && numGuesses < GUESS_LIMIT) {
			numGuesses++;
			if (userInput.equals(secret)) {
				output.println(getRandomMessage(kudos));
				output.println("Enter your intials to join the high score board!");
				String inits = input.readLine().trim();
				if (getBestScore()!=null) {
					setBestScore(getBestScore().getScore()+1, inits);
				} else setBestScore(1, inits);
				return; // end the serve method, thus ending the game
			} else {
				output.println("You said '" + userInput
						+ "' but that is not the secret word.");
				output.println(prompt);
				userInput = input.readLine().trim();
			}
		}
		output.println(getRandomMessage(goodbyes));
	}

	private String getRandomMessage(String[] array) {
		int randomChoice = (int) (Math.random() * array.length);
		String s = array[randomChoice];
		return s;
	}

	private final String[] kudos = {
			"Yay -- you won!  Way to go, you!!",
			"Amazing.  You're a clever one, you are.",
			"Did you figure that out by yourself or did you have help?  You WON!",
			"Brilliant -- but shhhhhh.  Don't give away the secret. ;-)" };
	private final String[] goodbyes = {
			"Oh well, better luck next time.",
			"Take a break -- come back and try again.",
			"Sorry, but you didn't win.  Have a go again, later.",
			"Don't over think it.  You can get this.",
			"Hint: Follow my instructions to the letter ;-)" };
}
