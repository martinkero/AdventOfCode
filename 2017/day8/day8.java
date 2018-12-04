import java.io.FileNotFoundException;
import java.util.*;

public class day8 {

    private class Instruction {
        String register;
        String action;
        int actionValue;
        String condition;

        private Instruction(String rawInstruction) {
            String[] instructionArray = rawInstruction.split("\\s+");
            this.register = instructionArray[0];
            this.action = instructionArray[1];
            this.actionValue = Integer.parseInt(instructionArray[2]);
            this.condition = rawInstruction.split("if")[1].trim();
        }

        private boolean evaluateCondition() throws IllegalArgumentException {
            String[] conditionArray = this.condition.split("\\s+");
            int leftValue;
            try {
                leftValue = Integer.parseInt(conditionArray[0]);
            } catch (NumberFormatException e) {
                leftValue = getRegisterValue(conditionArray[0]);
            }
            int rightValue;
            try {
                rightValue = Integer.parseInt(conditionArray[2]);
            } catch (NumberFormatException e) {
                rightValue = getRegisterValue(conditionArray[2]);
            }
            String operator = conditionArray[1];
            switch (operator) {
                case "==":
                    return leftValue == rightValue;
                case "!=":
                    return leftValue != rightValue;
                case ">":
                    return leftValue > rightValue;
                case ">=":
                    return leftValue >= rightValue;
                case "<":
                    return leftValue < rightValue;
                case "<=":
                    return leftValue <= rightValue;
                default:
                    throw new IllegalArgumentException();
            }
        }

        private void doAction() {
            int currentValue = registers.get(this.register);
            switch (this.action) {
                case "inc":
                    registers.put(this.register, currentValue + this.actionValue);
                    break;
                case "dec":
                    registers.put(this.register, currentValue - this.actionValue);
                    break;
            }
        }
    }

    private Map<String, Integer> registers = new HashMap<>();

    public static void main(String[] args) {
        day8 day8 = new day8();
        day8.testInstructions();
        List<String> rawInstructions = new ArrayList<>();
        try {
            rawInstructions = utils.readInputAsList("day8/day8.in", "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println(day8.processInstructions(rawInstructions));
    }

    private Integer processInstructions(List<String> rawInstructions) {
        List<Instruction> instructions = new ArrayList<>();
        for (String rawInstruction : rawInstructions) {
            Instruction instruction = new Instruction(rawInstruction);
            instructions.add(instruction);
            registers.put(instruction.register, 0);
        }

        for (Instruction instruction : instructions) {
            try {
                if (instruction.evaluateCondition()) {
                    instruction.doAction();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal argument while evaluation condition");
            }
        }

        int highestValue = 0;
        for (Integer registerValue : registers.values()) {
            if (registerValue > highestValue) {
                highestValue = registerValue;
            }
        }
        return highestValue;
    }

    private int getRegisterValue(String registerToFind) throws IllegalArgumentException {
        for (Map.Entry<String, Integer> register : registers.entrySet()) {
            if (register.getKey().equals(registerToFind)) {
                return register.getValue();
            }
        }
        throw new IllegalArgumentException();
    }

    private void testInstructions() {
        List<String> instructions = new ArrayList<>();
        instructions.add("b inc 5 if a > 1");
        instructions.add("a inc 1 if b < 5");
        instructions.add("c dec -10 if a >= 1");
        instructions.add("c inc -20 if c == 10");

        if (processInstructions(instructions) != 1) {
            System.out.println("FAILED TEST");
            System.exit(1);
        }
    }
}