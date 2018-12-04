import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 {
    class Stepper {
        int posX = 0;
        int posY = 0;

        void step(String direction) {
            switch (direction) {
                case "ne":
                    this.posX++;
                    this.posY++;
                    break;
                case "sw":
                    this.posX--;
                    this.posY--;
                    break;
                case "se":
                    this.posX++;
                    break;
                case "nw":
                    this.posX--;
                    break;
                case "n":
                    this.posY++;
                    break;
                case "s":
                    this.posY--;
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Day11 day11 = new Day11();
        day11.testGetNumberOfSteps();

        String input = null;
        try {
            input = utils.readInputAsList("day11/Day11.in", "\n").get(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println(day11.getNumberOfSteps(input));

    }

    private int getNumberOfSteps(String path) {
        Stepper stepper = new Stepper();
        List<String> steps = Arrays.asList(path.split(","));

        for (String step : steps) {
            stepper.step(step);
        }

        int shortestAmountOfSteps;
        if (Math.signum(0 - stepper.posX) == Math.signum(0 - stepper.posY)) {
            shortestAmountOfSteps = Math.max(Math.abs(0 - stepper.posX), Math.abs(0 - stepper.posY));
        } else {
            shortestAmountOfSteps = Math.abs(stepper.posX) + Math.abs(0 - stepper.posY);
        }

        return shortestAmountOfSteps;
    }


    private void testGetNumberOfSteps() {
        Map<String, Integer> testInputsAndExpectedResults = new HashMap<>();
        testInputsAndExpectedResults.put("ne,ne,ne", 3);
        testInputsAndExpectedResults.put("ne,ne,sw,sw", 0);
        testInputsAndExpectedResults.put("ne,ne,s,s", 2);
        testInputsAndExpectedResults.put("se,sw,se,sw,sw", 3);

        for (Map.Entry<String, Integer> testInputAndExpectedResult : testInputsAndExpectedResults.entrySet()) {
            int result = getNumberOfSteps(testInputAndExpectedResult.getKey());
            int expectedResult = testInputAndExpectedResult.getValue();
            if (result != expectedResult) {
                System.out.println(testInputAndExpectedResult.getKey());
                System.out.println("Test failed, got " + result + ", expected " + expectedResult);
                System.exit(1);
            }
            System.out.println("");
        }
    }
}