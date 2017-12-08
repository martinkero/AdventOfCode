import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class utils {
    public static List<String> readInputAsList(String file) throws FileNotFoundException {
        Scanner s = new Scanner(new File(file)).useDelimiter("\\n");
        List<String> inputList = new ArrayList<>();
        while (s.hasNext()) {
            inputList.add(s.next());
        }
        s.close();
        return inputList;
    }
}