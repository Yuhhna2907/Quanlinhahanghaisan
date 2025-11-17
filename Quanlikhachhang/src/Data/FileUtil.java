package Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static void writeLines(String filePath, List<String> lines, boolean append) {
        try {
            File file = new File(filePath);

            // Tạo folder nếu chưa tồn tại
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, append))) {
            for(String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Đọc toàn bộ file thành List<String>
    public static List<String> readLines(String filePath) {
        List<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
