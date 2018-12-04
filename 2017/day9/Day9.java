import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class Day9 {

    public static void main(String[] args) {
        testProcessGroupScore();

        String input = null;
        try {
            input = utils.readInputAsList("day9/Day9.in", "\n").toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println(processGroupScore(input));
    }

    private static int processGroupScore(String stream) {
        int groupScore = 0;
        int depth = 0;
        boolean inGarbage = false;
        boolean skipNext = false;

        for (int i = 0; i < stream.length(); i++) {
            String currentChar = String.valueOf(stream.charAt(i));

            if (skipNext) {
                skipNext = false;
                continue;
            }
            if (inGarbage && (!currentChar.equals(">") && !currentChar.equals("!"))) {
                continue;
            }

            switch (currentChar) {
                case "{":
                    depth++;
                    break;
                case "}":
                    groupScore += depth;
                    depth--;
                    break;
                case "<":
                    inGarbage = true;
                    break;
                case ">":
                    inGarbage = false;
                    break;
                case "!":
                    if (inGarbage) {
                        skipNext = true;
                    }
                    break;
            }
        }
        return groupScore;
    }

    private static void testProcessGroupScore() {
        Map<String, Integer> inputAndExpectedResults = new HashMap<>();
        inputAndExpectedResults.put("{}", 1);
        inputAndExpectedResults.put("{{{}}}", 6);
        inputAndExpectedResults.put("{{},{}}", 5);
        inputAndExpectedResults.put("{{{},{},{{}}}}", 16);
        inputAndExpectedResults.put("{<a>,<a>,<a>,<a>}", 1);
        inputAndExpectedResults.put("{{<ab>},{<ab>},{<ab>},{<ab>}}", 9);
        inputAndExpectedResults.put("{{<!!>},{<!!>},{<!!>},{<!!>}}", 9);
        inputAndExpectedResults.put("{{<a!>},{<a!>},{<a!>},{<ab>}}", 3);

        for (Map.Entry<String, Integer> inputAndExpectedResult : inputAndExpectedResults.entrySet()) {
            String input = inputAndExpectedResult.getKey();
            int expectedResult = inputAndExpectedResult.getValue();
            int result = processGroupScore(input);
            if (result != expectedResult) {
                System.out.println("Test failed, input string: " + input + "\ngot " + result + ", expected " + expectedResult);
                System.exit(1);
            }
        }
    }
}