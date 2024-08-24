package chucknorris;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
      //textToChuck();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Please input operation (encode/decode/exit):");
            String input = scanner.nextLine();
            if (input.equals("encode")) {
                String inputString = reader();
                System.out.println("Encoded string:");
                String result = textToChuck(inputString);
                System.out.println(result + "\n");
            } else if (input.equals("decode")) {
                System.out.println("Input encoded string:");
                String inputChuck = scanner.nextLine();
                if(inputChuck.matches("^(00|0)\\s0+(\\s(00|0)\\s0+)*$") && chuckToBinary(inputChuck).length() % 7 == 0) {
                    String result1 = chuckToText(inputChuck);
                    System.out.println("Decoded String:");
                    System.out.println(result1 + "\n");
                } else {
                    System.out.println("Encoded string is not valid.\n");
                }

            } else if (input.equals("exit")) {
                System.out.println("Bye!");
                break;
            } else {
                System.out.println("There is no '" + input + "' operation\n");
            }
        }
    }

    public static String textToChuck(String input) {
        /* Transform string to binary string */
        String binaryText = textToBinary(input);
        /* Separate sequences in binary string and store each of them separately in a list */
        ArrayList<String> sequenceList = binarySequenceSeparator(binaryText);
        /* Transform the sequences to Chuck Norris code and join them for the final result */
        String result = sequenceListToChuck(sequenceList);
        /* Print the result */
        return result;
    }

    public static String chuckToText(String input) {
        String inputBinary =  chuckToBinary(input);
        return binaryToText(inputBinary);
    }

    public static String reader() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input string:");
        String input = scanner.nextLine();
        return input;
    }

    public static String textToBinary(String input) {
        String currBinary = "";
        StringBuilder builder = new StringBuilder();
        char[] inputArray = input.toCharArray();
        for(int i = 0; i < inputArray.length; i++) {
            char currChar = inputArray[i];
            currBinary = String.format("%7s", Integer.toBinaryString(currChar)).replace(' ', '0');
            builder.append(currBinary);
        }
        return builder.toString();
    }

    public static ArrayList<String> binarySequenceSeparator(String binaryStr) {
        char[] binaryArray = binaryStr.toCharArray();
        ArrayList<String> sequenceList = new ArrayList<>();
        StringBuilder builder1 = new StringBuilder();
        char prevNum = binaryArray[0];
        int j = 0;
        for (int i = 0; i < binaryArray.length; i++) {
            char currNum = binaryArray[i];
            if (prevNum == currNum) {
                builder1.append(binaryArray[i]);
            } else {
                sequenceList.add(builder1.toString());
                j++;
                builder1.setLength(0);
                builder1.append(binaryArray[i]);
            }
            prevNum = currNum;
        }
        sequenceList.add(builder1.toString());
        return sequenceList;
    }

    public static String sequenceListToChuck(ArrayList<String> sequenceList) {
        StringBuilder builder2 = new StringBuilder();
        for (int i = 0; i < sequenceList.size(); i++) {
            char[] currSeq = sequenceList.get(i).toCharArray();
            if (currSeq[0] == '1') {
                builder2.append("0 ");
                for (int j = 0; j < currSeq.length; j++) {
                    builder2.append("0");
                }
                builder2.append(" ");
            } else {
                builder2.append("00 ");
                for (int j = 0; j < currSeq.length; j++) {
                    builder2.append("0");
                }
                builder2.append(" ");
            }
        }
        return builder2.toString();
    }

    public static String chuckToBinary(String str) {
        char[] chuckArray = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while(i < chuckArray.length - 1) {
            if(chuckArray[i] == '0' && chuckArray[i + 1] == ' '){
                i += 2;
                while (chuckArray[i] == '0'){
                    builder.append("1");
                    if (i == chuckArray.length - 1) {
                        break;
                    }
                    i++;
                }
                if(i < chuckArray.length - 1) {
                    i++;
                }
            } else {
                i += 3;
                while (chuckArray[i] == '0') {
                    builder.append("0");
                    if (i == chuckArray.length - 1) {
                        break;
                    }
                        i++;
                }
                if (i < chuckArray.length - 1) {
                    i++;
                }

            }
        }
        return builder.toString();
    }

    public static String binaryToText(String input) {
        char[] inputArray = input.toCharArray();
        StringBuilder builder = new StringBuilder();
        ArrayList<String> sequenceList = new ArrayList<>();
        for (int i = 0; i < inputArray.length; i++) {
            if (i % 7 != 0 || i == 0) {
                builder.append(inputArray[i]);
                if (i == inputArray.length - 1) {
                    sequenceList.add(builder.toString());
                    builder.setLength(0);
                }

            } else if (i % 7 == 0 && i != 0) {
                sequenceList.add(builder.toString());
                builder.setLength(0);
                builder.append(inputArray[i]);
                if(i == inputArray.length - 1) {
                    sequenceList.add(builder.toString());
                }
            }
        }
        builder.setLength(0);

        for (int i = 0; i < sequenceList.size(); i++) {
            String currSequence = sequenceList.get(i);
            String sevenDigitSeq = String.format("%7s", currSequence).replace(' ', '0');
            sequenceList.set(i, sevenDigitSeq);
        }
        for(int i = 0; i < sequenceList.size(); i++) {
            String currSequence = sequenceList.get(i);
            int sequenceNum = Integer.parseInt(currSequence, 2);
            char currChar = (char) sequenceNum;
            builder.append(currChar);
        }
        String text = builder.toString();
        return text;
    }


}

