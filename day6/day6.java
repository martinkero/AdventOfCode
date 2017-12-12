import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class day6 {

    public static void main(String[] args) {
        List<String> input = new ArrayList<>();
        try {
            input = utils.readInputAsList("day6/day6.in", "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        List<Integer> config = Arrays.stream(input.get(0).split("\\s+")).map(Integer::parseInt).collect(Collectors.toList());

        List<String> seenConfigs = new ArrayList<>();
        seenConfigs.add(config.toString());

        int cycles = 0;
        while (true) {
            reallocate(config);
            cycles++;
            if (configIsSeen(seenConfigs, config.toString())) {
                break;
            }
            seenConfigs.add(config.toString());
        }

        System.out.println(cycles);
    }
    private static boolean configIsSeen(List<String> seenConfigs, String config) {
        for (String seenConfig : seenConfigs) {
            if (seenConfig.equals(config)) {
                return true;
            }
        }
        return false;
    }

    private static void reallocate(List<Integer> config) {
        int highestValue = 0;
        int highestValueIndex = 0;
        for (int i = 0; i<config.size(); i++) {
            int value = config.get(i);
            if (value > highestValue) {
                highestValue = value;
                highestValueIndex = i;
            }
        }

        config.set(highestValueIndex, 0);
        int startingPoint = highestValueIndex+1;
        while (highestValue>0) {
            for (int i = startingPoint; i<config.size() && highestValue>0; i++) {
                int currentValue = config.get(i);
                config.set(i, currentValue+1);
                highestValue--;
            }
            startingPoint = 0;
        }
    }
}