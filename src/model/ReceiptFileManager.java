package model;

import service.StoreService;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReceiptFileManager {
        private static final String FOLDER_PATH = "receipts";

        public static void writeToFile(Receipt receipt) throws IOException {
            String fileName = FOLDER_PATH + "/receipt_" + receipt.getId() + ".ser";
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
                out.writeObject(receipt);
            }
        }

        public static Set<Receipt> readReceiptsFromFile() throws IOException, ClassNotFoundException {
            Set<Receipt> receipts = new HashSet<>();
            File folder = new File(FOLDER_PATH);

            if (!folder.exists() || !folder.isDirectory()) {
                return receipts;
            }

            for (File file : Objects.requireNonNull(folder.listFiles())) {
                if (file.getName().endsWith(".ser")) {
                    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                        Receipt receipt = (Receipt) in.readObject();
                        receipts.add(receipt);
                    }
                }
            }
            return receipts;
        }
}
