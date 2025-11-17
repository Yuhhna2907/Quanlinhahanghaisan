package Data;

import Model.Table;

import java.util.ArrayList;
import java.util.List;

public class TableRepository {
    private String filePath = "data/tables.csv";

    public void saveTable(Table t) {
        String line = t.getId() + "," + t.getStatus() + "," + t.getCapacity();
        FileUtil.writeLines(filePath, List.of(line), true);
    }

    public List<Table> getAllTables() {
        List<Table> tables = new ArrayList<>();
        List<String> lines = FileUtil.readLines(filePath);
        for(String line : lines) {
            String[] parts = line.split(",");
            Table t = new Table(Integer.parseInt(parts[0]), Integer.parseInt(parts[2]));
            if(parts[1].equals("Reserved")) t.reserve();
            else if(parts[1].equals("Occupied")) t.occupy();
            tables.add(t);
        }
        return tables;
    }
}
