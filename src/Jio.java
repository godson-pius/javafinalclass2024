import java.io.File;
import java.io.FileWriter;

public class Jio {
    public static void main(String[] args) {
        try {
            String filename = "data.txt";
            File file = new File(filename);
            if (file.exists()) {
                FileWriter writer = new FileWriter(file);
                writer.write("Hello Java IO :) \n");
                writer.append("Joe");
                writer.close();
                System.out.println(file.getAbsolutePath());
            } else {
                boolean isCreated = file.createNewFile(); // true, false
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
