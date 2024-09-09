package bullscows;
import java.util.*;


public class Game {
    private final Scanner scanner;
    private static final int MAX_LENGTH_SYMBOLS = 36;
    public Game(Scanner scanner) {
        this.scanner = scanner;
    }

    public void play() {
        int[] lenAndSym = inputSecretLengthAndSymbols();
        if(lenAndSym == null) {
            return;
        }
        char[] secretCode = generateSecretCode(lenAndSym[0], lenAndSym[1]).toCharArray();
        String codeStr = new String(secretCode);
        System.out.println("Okay, let's start the game!");
        int i = 1;
        char[] guess = new char[MAX_LENGTH_SYMBOLS];
        while (!Arrays.equals(guess, secretCode)) {
            System.out.println("Turn " + i + ":");
            guess = scanner.nextLine().toCharArray();
            int[] cowsAndBulls = bullsCows(secretCode, guess);
            System.out.println(getResult(cowsAndBulls, codeStr));
            i++;
        }
    }

    private String getResult(int[] cowsAndBulls, String codeStr) {
        int cows = cowsAndBulls[0];
        int bulls = cowsAndBulls[1];

        return (bulls == 0 && cows == 0) ? ("Grade: None")
                : (bulls == codeStr.length() && cows == 0) ? ("Grade: " + codeStr.length() + " bull(s)\n" + "Congratulations! You guessed the secret code.")
                : (bulls == 0 && cows == 1) ? ("Grade: " + cows + " cow")
                : (bulls == 0 && cows > 1) ? ("Grade: " + cows + " cows")
                : (bulls == 1 && cows == 0) ? ("Grade: " + bulls + " bull")
                : (bulls > 1 && cows == 0) ? ("Grade: " + bulls + " bulls")
                : (bulls == 1 && cows == 1) ? ("Grade: " + bulls + " bull and " + cows + " cow" )
                : (bulls > 1 && cows == 1) ? ("Grade: " + bulls + " bulls and " + cows + " cow")
                : (bulls == 1 && cows > 1) ? ("Grade: " + bulls + " bull and " + cows + " cows")
                : (bulls > 1 && cows > 1) ? ("Grade: " + bulls + " bulls and " + cows + " cows")
                : "";
    }
    private int[] bullsCows(char[] secretCode, char[] guess) {
        int[] cowsBulls = new int[2];
        // Generate arrays to then calculate cows
        int[] secretArr = new int[MAX_LENGTH_SYMBOLS];
        int[] guessArr = new int[MAX_LENGTH_SYMBOLS];
        // Calculate bulls
        int bulls = 0;
        for (int i = 0; i < secretCode.length; i++) {

            if(Character.isDigit(secretCode[i])) {
                secretArr[secretCode[i] - '0'] += 1;
            } else {
                secretArr[secretCode[i] - 'a' + 10] += 1;
            }

            if (secretCode[i] == guess[i]) {
                bulls++;
            } else {
                if (Character.isDigit(guess[i])) {
                    guessArr[guess[i] - '0'] += 1;
                } else {
                    guessArr[guess[i] - 'a' + 10] += 1;
                }
            }
        }
        // Calculate Cows
        int cows = 0;
        for (int i = 0; i < MAX_LENGTH_SYMBOLS; i++) {
            if (secretArr[i] > 0 && guessArr[i] > 0) {
                cows += guessArr[i];
            }
        }
        // Move cows to first index and bulls to second index
        cowsBulls[0] = cows;
        cowsBulls[1] = bulls;

        return cowsBulls;
    }
    private int[] inputSecretLengthAndSymbols() {
        int[] lengthAndSymbols = new int[2];
        Scanner scanner = new Scanner(System.in);
        List<String> symbols = getSymbols();
        StringBuilder sbDots = new StringBuilder();

        System.out.println("Please, enter the secret code's length:");
        String length = scanner.nextLine();
        int len = 0;

        //Check length is a valid number
        try {
            if (!length.matches("\\d+")) {
                throw new NumberFormatException("Error: \"" + length + "\" isn't a valid number.");
            }
            len = Integer.parseInt(length);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        //Check if len is less than or equal to 0 or bigger than max length (36)
        if (len <= 0 || len > MAX_LENGTH_SYMBOLS) {
            System.out.println("Error: can't generate a secret number with a length of " + len + " because there aren't enough unique digits.\n");
            return null;
        }

        System.out.println("Input the number of possible symbols in the code:");
        int possibleSymbols = Integer.parseInt(scanner.nextLine());
        //Check if the number of possible unique symbols is less than len
        if (possibleSymbols < len) {
            System.out.println("Error: it's not possible to generate a code with a length of " + len + " with " + possibleSymbols + " unique symbols.");
            return null;
        }
        //Check if the possible symbols is bigger than the allowed symbols (36)
        if (possibleSymbols > MAX_LENGTH_SYMBOLS) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return null;
        }

        sbDots.append("*".repeat(len));
        int limitIdx;
        if (possibleSymbols <= 10 && possibleSymbols > 0) {
            limitIdx = possibleSymbols - 1;
            System.out.println("The secret is prepared: " + sbDots + " (0-" + limitIdx + ").");
        } else if (possibleSymbols > 10 && possibleSymbols <= MAX_LENGTH_SYMBOLS) {
            limitIdx = possibleSymbols - 1;
            System.out.println("The secret is prepared: " + sbDots + " (0-9, " + "a-" + symbols.get(limitIdx) + ").");
        }
        lengthAndSymbols[0] = len;
        lengthAndSymbols[1] = possibleSymbols;
        return lengthAndSymbols;
    }
    private String generateSecretCode(int len, int possibleSymbols) {
        Random rand = new Random();
        List<String> symbols = getSymbols();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int idx = rand.nextInt(possibleSymbols);
            String currElement = symbols.get(idx);
            builder.append(currElement);
            symbols.remove(idx);
            possibleSymbols--;
        }
        return builder.toString();
    }
    private List<String> getSymbols() {
        return new ArrayList<>(Arrays.asList(
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z"
        ));
    }
}
