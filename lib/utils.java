import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class utils {
    public static List<String> readInputAsList(String file) throws FileNotFoundException {
        Scanner s = new Scanner(new File(file));
        List<String> inputList = new ArrayList<>();
        while (s.hasNext()) {
            inputList.add(s.next());
        }
        s.close();
        return inputList;
    }
    public static List<String> splitStringByWhitespace(String string) {
        String[] stringArray = string.split("\\s+");
        return Arrays.asList(stringArray);
    }
    public static List<Integer> stringListToIntegerList(List<String> stringList) {
        return stringList.stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}