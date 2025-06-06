package Finall;
import java.util.Scanner;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Easy {
	//colors to be used for the game
    static String RESET = "\u001B[0m";
    static String RED = "\u001B[31m";
    static String GREEN = "\u001B[32m";
    static String YELLOW = "\u001B[33m";
    static String CYAN = "\u001B[36m";
    static String PINK = "\u001B[35m";
    static String HOT_PINK = "\u001B[38;2;255;105;180m";

    //variables to know the time and date of when the user played easy
    static LocalDateTime today = LocalDateTime.now();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    static String DateTime = today.format(formatter);

    //initialization of score, wpm, and accuracy
    static long score = 0;
    static long wpm = 0;
    static long accuracy = 0;
    
    //method to get the score from easy
    static long easyGetScore() {
		return score;
	}
  
    //method to get the wpm from easy
    static long easyGetWPM() {
    	return wpm;
    }
    //method to get the accuracy from easy
    static long easyGetAccuracy() {
		return accuracy;
	}
    
    //method for easy game
    static void Easy() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Using ArrayList for words
        ArrayList<String> words = new ArrayList<>();
        words.add("Lorein");
        words.add("Lenard");
        words.add("Leander");
        words.add("Denmark");
        words.add("Ydrey");
        words.add("Skibidi");
        words.add("Rainbow");
        words.add("Isopropyl");
        
        //initialization of local variables from the Easy() method
        int totalRounds = 5;
        int timeLimit = 25000; 
        int correctWords = 0;
        long wordsTyped = 0;
        long aveScore = 0;
        long time = 0;
        
        //start of the game
        System.out.println(CYAN + "============================================" + RESET);
        System.out.println(CYAN + "               EASY LEVEL GAME              " + RESET);
        System.out.println(CYAN + "============================================" + RESET);
        System.out.println("Type each word correctly!");
        System.out.println("You have a total of 25 seconds for all rounds.");
        System.out.print("Press enter to continue");
		scanner.nextLine();
        System.out.println(CYAN + "============================================" + RESET);
        //start of timer
        long startTime = System.currentTimeMillis();

        //game loop
        for (int round = 1; round <= totalRounds; round++) {
            long elapsedTime = System.currentTimeMillis() - startTime;

            //logic for time limit, and starts once the user presses enter
            if (elapsedTime >= timeLimit) {
                System.out.println("\n⏰ " + RED + "Time's up!" + RESET);
                break;
            }
            
            //gets random words
            String word = words.get(random.nextInt(words.size()));
            System.out.println("Round " + round + ":");
            System.out.println("Type the word: " + CYAN + word + RESET);
            System.out.print("Type here: " + HOT_PINK);
            String input = scanner.nextLine();
            System.out.println(RESET);

            elapsedTime = System.currentTimeMillis() - startTime;
            time = elapsedTime/1000;
            
            //if-else statements if the user's time is up, user's input is correct or wrong, or if the user doesn't input anything
            if (elapsedTime > timeLimit) {
                System.out.println("\n⏱ " + RED + "Time's up!" + RESET);
                break;
            }
            if (input.trim().isEmpty()) {
                System.out.println(YELLOW + "No input entered, let's try the next one!" + RESET);
                continue;
            }

            if (input.equals(word)) {
                System.out.println(GREEN + "Correct! ✅" + RESET);
                correctWords++;
                aveScore++;
                wordsTyped++;
            } else {
                System.out.println(RED + "Oops, that's not right." + RESET);
                wordsTyped++;
            }
            System.out.println(CYAN + "============================================" + RESET);
        }

        int totalElapsedSeconds = (int)((System.currentTimeMillis() - startTime) / 1000);
        int remainingTime = Math.max(0, (timeLimit / 1000) - totalElapsedSeconds);
        long totalPoints = aveScore + remainingTime;

        //overall results for easy
        System.out.println("\n" + CYAN + "============================================" + RESET);
        System.out.println(CYAN + "Correct Words: " + RESET + YELLOW + correctWords + RESET);
        System.out.println(CYAN + "Points: " + RESET + YELLOW + aveScore + RESET);
        System.out.println(CYAN + "Bonus (Time Left): " + RESET + YELLOW + remainingTime + RESET);
        System.out.println(CYAN + "Total Points: " + RESET + YELLOW + totalPoints + RESET);
        System.out.println(CYAN + "Date and time played: " + DateTime + RESET);
        score = score + aveScore;
		accuracy = (long)(((double)correctWords/wordsTyped)*100);
		wpm = (long)(((double)correctWords/time)*60);
		System.out.println(CYAN + "Accuracy: " + accuracy);
		System.out.println("WPM: " + wpm + RESET);
		
		//identifier whether the user continues of not
        String resultMsg;
        String resultColor;
        if (aveScore >= 3) {
            resultMsg = "PROCEED TO THE NEXT LEVEL!";
            resultColor = GREEN;
        } else if (aveScore > 0) {
            resultMsg = "NICE TRY! Give it another go!";
            resultColor = YELLOW;
        } else {
            resultMsg = "GAME OVER. TRY AGAIN!";
            resultColor = RED;
        }

        System.out.println(CYAN + "Result: " + resultColor + resultMsg + RESET);
        System.out.println(CYAN + "============================================" + RESET);
        System.out.println("");
    }
}
