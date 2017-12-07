import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class utils {
    public static List<Integer> readInputAsList(String file) throws FileNotFoundException {
        Scanner s = new Scanner(new File(file));
        List<Integer> inputList = new ArrayList<>();
        while (s.hasNext()) {
            inputList.add(Integer.parseInt(s.next()));
        }
        s.close();
        return inputList;
    }
}