package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static boolean isNotError = true;
    private static int lengthOfTheSecretCode;
    private static int numberOfPossibleSymbols;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        startGame();
        if (isNotError) {
            printSecretCodeParameters(lengthOfTheSecretCode, numberOfPossibleSymbols);
            System.out.println("Okay, let's start a game!");
            countBullsAndCows(lengthOfTheSecretCode, numberOfPossibleSymbols, scanner);
        }
    }

    private static void startGame() {

        System.out.println("Input the length of the secret code:");
        String length = scanner.nextLine();
        if (length.matches("\\d+")) {
            lengthOfTheSecretCode = Integer.parseInt(length);
        } else {
            System.out.printf("Error: \"%s\" isn't a valid number.%n", length);
            isNotError = false;
            return;
        }

        if (lengthOfTheSecretCode < 1 || lengthOfTheSecretCode > 36) {
            System.out.println("Error: The length of the secret code must be greater than 0 and less than or equal to 36.");
            isNotError = false;
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        String number = scanner.nextLine();

        if (length.matches("\\d+")) {
            numberOfPossibleSymbols = Integer.parseInt(number);
        } else {
            System.out.printf("Error: \"%s\" isn't a valid number.%n", number);
            isNotError = false;
            return;
        }

        if (numberOfPossibleSymbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            isNotError = false;
            return;
        }

        if (numberOfPossibleSymbols < lengthOfTheSecretCode) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.%n", lengthOfTheSecretCode, numberOfPossibleSymbols);
            isNotError = false;
            return;
        }
    }

    private static void printSecretCodeParameters (int length, int number) {
        StringBuilder secretCodeParameters = new StringBuilder("The secret is prepared: ");

        for (int i = 0; i < length; i++) {
            secretCodeParameters.append('*');
        }
        if (number <= 10) {
            secretCodeParameters.append(String.format(" (0-%d)", number - 1));
        } else {
            secretCodeParameters.append(String.format(" (0-9, a-%c).", (char) (number + 86)));
        }
        System.out.println(secretCodeParameters.toString());
    }

    private static void countBullsAndCows (int length, int number, Scanner scanner) {
        int cows;
        int bulls = 0;
        int count = 0;

        String cod = generateSecretCode(length, number);
        char[] secretCode = cod.toCharArray();

        while (bulls != length) {
            cows = 0;
            bulls = 0;
            count++;
            System.out.printf("Turn %d:%n", count);
                String word = scanner.nextLine();
                if (isntError(word, length)) {
                    char[] inputCode = word.toCharArray();
                    for (int i = 0; i < length; i++) {
                        for (int j = 0; j < length; j++) {
                            if (secretCode[i] == inputCode[j]) {
                                cows++;
                                if (i == j) {
                                    bulls++;
                                    cows--;
                                }
                            }
                        }
                    }
                }
            if (bulls == 0 && cows == 0) {
                System.out.println("Grade: None.");
            } else if (bulls == length){
                System.out.printf("Grade: %d bulls%nCongratulations! You guessed the secret code.%n", bulls);
            } else {
                System.out.printf("Grade: %s bull(s) and %s cow(s).%n", bulls, cows);
            }
        }
    }

    private static boolean isntError (String word, int length) {
        boolean result = true;
        if (!word.matches("[0-9a-z]*")) {
            System.out.printf("Error: \"%s\" contains invalid characters. %n", word);
            result = false;
        } else if (word.length() != length) {
            System.out.printf("The length of the secret code is %d. Please try again.%n", length);
            result = false;
        }
        return result;
    }

    private static String generateSecretCode (int length, int number) {
        String tempChar;
        StringBuilder secretCode = new StringBuilder();
        Random random = new Random();

        while (secretCode.length() != length) {
            int pseudoRandomNumber = random.nextInt(number);
            tempChar = String.valueOf(SYMBOLS.charAt(pseudoRandomNumber));
            for (int i = 0; i < length; i++) {
                if (secretCode.length() == length) {
                    break;
                }
                if (!secretCode.toString().contains(tempChar)) {
                    secretCode.append(tempChar);
                }
            }
        }
        return secretCode.toString();
    }
}


