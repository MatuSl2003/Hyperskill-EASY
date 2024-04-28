package lastpencil;

import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;




public class Main {
    private static final int MIN_REMOVABLE = 1;
    private static final int MAX_REMOVABLE = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many pencils would you like to use:");
        String pencilsString = scanner.nextLine();
        int pencils = pencilCheck(pencilsString);

        System.out.println("Who will be the first (John, Jack):");
        String name1 = nameCheck();
        String name2 = name1.equals("Jack") ? "John" : "Jack";

        StartGame(pencils, name1, name2);
    }

    public static void StartGame(int pencils, String name1, String name2) {
        Scanner scanner = new Scanner(System.in);
        int turn = 1;
        while (true) {
            String nameTurn = turn % 2 != 0 ? name1 : name2;
            if (pencils == 0) {
                System.out.println(nameTurn + " won!");
                break;
            }
            for (int i = 0; i < pencils; i++) {
                System.out.print("|");
            }
            System.out.println();
            if (nameTurn.equals("Jack")) {
                System.out.println(nameTurn + "'s turn:");
            } else {
                System.out.println(nameTurn + "'s turn!");
            }
            turn++;
            String pencilsRemovedStr = "";
            if (nameTurn.equals("Jack")) {
                pencilsRemovedStr = cotoBot(pencils);
                System.out.println(pencilsRemovedStr);
            } else {
                pencilsRemovedStr = scanner.nextLine();
            }

            while (!isNumeric(pencilsRemovedStr)) {
                System.out.println("Possible values: '1', '2' or '3'");
                pencilsRemovedStr = scanner.nextLine();
            }

            while (Integer.parseInt(pencilsRemovedStr) < MIN_REMOVABLE || Integer.parseInt(pencilsRemovedStr) > MAX_REMOVABLE) {
                System.out.println("Possible values: '1', '2' or '3'");
                pencilsRemovedStr = scanner.nextLine();
                while(!isNumeric(pencilsRemovedStr)) {
                    System.out.println("Possible values: '1', '2' or '3'");
                    pencilsRemovedStr = scanner.nextLine();
                }
            }
            while(Integer.parseInt(pencilsRemovedStr) > pencils) {
                System.out.println("Too many pencils were taken");
                pencilsRemovedStr = scanner.nextLine();
                while(!isNumeric(pencilsRemovedStr)) {
                    System.out.println("Possible values: '1', '2' or '3'");
                    pencilsRemovedStr = scanner.nextLine();
                }
            }
            pencils -= Integer.parseInt(pencilsRemovedStr);

        }
    }

    public static boolean isNumeric (String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int pencilCheck(String pencilsString) {
        Scanner scanner = new Scanner(System.in);
        while(pencilsString.isEmpty()) {
            System.out.println("The number of pencils should be numeric");
            pencilsString = scanner.nextLine();
        }
        while (!isNumeric(pencilsString) || pencilsString.equals("0")) {
            while (!isNumeric(pencilsString)) {
                System.out.println("The number of pencils should be numeric");
                pencilsString = scanner.nextLine();
            }
            while (pencilsString.equals("0")) {
                System.out.println("The number of pencils should be positive");
                pencilsString = scanner.nextLine();
            }
        }
        int pencils1 = Integer.parseInt(pencilsString);
        return pencils1;
    }

    public static String nameCheck() {
        Scanner scanner = new Scanner(System.in);
        String name1 = scanner.nextLine();
        while (!name1.equals("John") && !name1.equals("Jack")) {
            System.out.println("Choose between 'John' and 'Jack'");
            name1 = scanner.nextLine();
        }
        return name1;
    }

//    Take random number of pencils from 1 to 3 if(numPencils % 4 == 1)
//    Take 3 pencils if(numPencils % 4 == 0)
//    Take 2 pencils if((numPencils - 3) % 4 == 0)
//    Take 1 pencil if((number - 2) % 4 == 0)
    public static String cotoBot(int numPencils) {
        Random rand = new Random();
        if(numPencils % 4 == 1 && numPencils != 1) {
            return String.valueOf(rand.nextInt(3)+1);
        } else if (numPencils == 1) {
            return "1";
        }
        if(numPencils % 4 == 0) {
            return "3";
        }
        if((numPencils - 3) % 4 == 0) {
            return "2";
        }
        return "1";

    }
}
