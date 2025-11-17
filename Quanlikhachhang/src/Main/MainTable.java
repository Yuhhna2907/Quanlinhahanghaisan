package Main;

import Data.TableRepository;
import Model.Table;

import java.util.List;

public class MainTable {
    public static void main(String[] args) {
        TableRepository tableRepo = new TableRepository();

        Table t1 = new Table(1, 4);
        Table t2 = new Table(2, 6);

        tableRepo.saveTable(t1);
        tableRepo.saveTable(t2);

        List<Table> tables = tableRepo.getAllTables();
        System.out.println("=== Danh sách bàn ===");
        for (Table t : tables) {
            System.out.println("Bàn " + t.getId() + " - Sức chứa: " + t.getCapacity() + " - Trạng thái: " + t.getStatus());
        }
    }
}
