import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class day5 {
    private static List<Integer> instructionList;
    private static Integer currentIndex = 0;

    public static void main(String[] args) {
        try {
            instructionList = readInputAsList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Integer nrOfJumps = 0;
        while (true) {
            try {
                jump();
                nrOfJumps++;
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }

        System.out.println(nrOfJumps);
    }

    static void jump() {
        Integer jumpValue = instructionList.get(currentIndex);
        instructionList.set(currentIndex, jumpValue + 1);
        currentIndex = currentIndex + jumpValue;
    }

    private static List<Integer> readInputAsList() throws FileNotFoundException {
        Scanner s = new Scanner(new File("day5.in"));
        List<Integer> inputList = new ArrayList<>();
        while (s.hasNext()) {
            inputList.add(Integer.parseInt(s.next()));
        }
        s.close();
        return inputList;
    }
}