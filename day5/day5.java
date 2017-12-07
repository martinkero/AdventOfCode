import java.io.FileNotFoundException;
import java.util.List;

public class day5 {
    private static List<Integer> instructionList;
    private static Integer currentIndex = 0;

    public static void main(String[] args) {
        try {
            instructionList = utils.readInputAsList("day5/day5.in");
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


}