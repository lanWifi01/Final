package Finall;

import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;

public class Extreme {
	//colors to be used for the game
	static String RESET = "\u001B[0m";
	static String RED = "\u001B[31m";
	static String GREEN = "\u001B[32m";
	static String YELLOW = "\u001B[33m";
	static String CYAN = "\u001B[36m";
	static String PINK = "\u001B[35m";
	static String HOT_PINK = "\u001B[38;2;255;105;180m";
	
	//variables to know the time and date of when the user played average
	static LocalDateTime today = LocalDateTime.now();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    static String DateTime = today.format(formatter);
    
    //initialization of score, wpm, and accuracy
    static long score = 0;
    static long wpm = 0;
    static long accuracy = 0;
    
    //method to get the score from extreme
    static long exGetScore() {
		return score;
	}
    
    //method to get the wpm from extreme
    static long exGetWPM() {
    	return wpm;
    }
    
    //method to get the accuracy from extreme
    static long exGetAccuracy() {
		return accuracy;
	}
	
    //method for average game
	static void Extreme() {
		Scanner s = new Scanner(System.in);
		Random r = new Random();
		ArrayList <String> p = new ArrayList<>();

		//list of phrases to be used for average
		p.add("Kapag magulo na ang mundo, ikaw ang payapang hinahanap hanap ko, tumakbo ka rin patungo sa'kin \n"
				+ "kapag bumibigat na ang iyong dibdib, \n"
				+ "ikaw ay sasalubungin");

		//initialization of local variables from the Extreme() method
		long aveScore = 0;
		int totalRounds = 1;
		int timeLimit = 30;
		long correctWords = 0;
		long wordsTyped = 0;
		long timeSec = 0;
		
		//start of the game
		System.out.println(CYAN + "================================================================================================" + RESET);
		System.out.println(CYAN + "                                     EXTREME LEVEL GAME                                         " + RESET);
        System.out.println(CYAN + "================================================================================================" + RESET);
        System.out.println("Type each word correctly!");
        System.out.println("You have a total of 30 seconds to type the random phrase.");
        System.out.print("Press enter to continue");
		s.nextLine();
		System.out.println(CYAN + "================================================================================================" + RESET);

		//game loop
		for (int round = 0; round < totalRounds; round++) {
			long tempScore = 0;
			int random = r.nextInt(p.size());
			String phrase = p.get(random);
			//cuts the randomized phrases into an array of words
			String word[] = phrase.split(" ");
			
			System.out.println("Round " + (round+1)+ ":");
			System.out.println("Type the phrase: " + CYAN + phrase + RESET);
			
			//timer starts once the user presses enter
			long start = System.currentTimeMillis();
			System.out.print("Type here: " + HOT_PINK);
			String user = s.nextLine();
			System.out.print(RESET);
			//cuts user inputed phrase into an array of words
			String newUser[] = user.split(" ");
			
			//timer ends
			long end = System.currentTimeMillis();
			
			//logic to count the remaining time left
			long time = (end - start)/1000;
			long overTime = timeLimit - time;
			if (newUser.length == word.length && !user.trim().isEmpty() && overTime > 0) {
				tempScore += overTime;
			}
			
			int minimum = Math.min(word.length, newUser.length);
			
			//loop if ever the user's time is up, wrong inputs and correct inputs, and adding of scores to the local variables
			for (int j = 0; j < minimum; j++) {
				if (user.isEmpty()) {
					//empty input
	                System.out.println(YELLOW + "No input entered?? But this is the last one yet you chose to gave up?" + RESET);
	                continue;
	            }
				if (newUser[j].equals(word[j])) {
					//correct words typed
					System.out.println(RESET +"Word " + (j+1) + ": " + GREEN + newUser[j] + " is correct! ✅"+ RESET);
					tempScore++;
					correctWords++;
					wordsTyped++;
				}
				else if (newUser[j] != (word[j])) {
					//incorrect words typed
					System.out.println(RESET +"Word " + (j+1) + ": " + RED + newUser[j]+ " is incorrect"+RESET);
					tempScore--;
					wordsTyped++;
				}
			}
			
			//results per round
			System.out.println(CYAN + "================================================================================================" + RESET);
			System.out.println("Accumulated score: " + tempScore + " pts");
			System.out.println("Your time spent: " + time + " secs");
			if (overTime < 0) {
				overTime = 0;
			}
			System.out.println("Bonus (Time left): " + overTime + " secs");
			aveScore = aveScore + tempScore;
			timeSec = timeSec + time;
			System.out.println(CYAN + "================================================================================================" + RESET);
		}
		
		//overall results for extreme
		System.out.println(CYAN + "================================================================================================" + RESET);
		System.out.println(CYAN + "Correct Words: " + RESET + YELLOW + correctWords + RESET);
        System.out.println(CYAN + "Total Points: " + RESET + YELLOW + aveScore + RESET);
		System.out.println(CYAN + "Date and time played: " + DateTime + RESET);
		score = score + aveScore;
		accuracy = (long)(((double)correctWords/wordsTyped)*100);
		wpm = (long)(((double)correctWords/timeSec)*60);
		System.out.println(CYAN + "Accuracy: " + accuracy);
		System.out.println("WPM: " + wpm + RESET);
		
		//identifier whether the user continues of not
		String resultMsg;
        String resultColor;
        if (score >= 20) {
            resultMsg = "WOW SO GALING!";
            resultColor = GREEN;
        } else if (aveScore >= 15) {
            resultMsg = "YOU DID GREAT FOR MAKING IT THIS FAR!";
            resultColor = YELLOW;
        } else {
            resultMsg = "IT DOESN'T ALWAYS END IN A HAPPY ENDING";
            resultColor = RED;
        }
        System.out.println(CYAN + "Result: " + resultColor + resultMsg + RESET);
		System.out.println(CYAN + "================================================================================================" + RESET);
		System.out.println("");
	}
	
}
