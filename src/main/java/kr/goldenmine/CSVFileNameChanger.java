package kr.goldenmine;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

public class CSVFileNameChanger {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("route csv: ");
        String csvFileName = scan.nextLine();

        System.out.print("route folder: ");
        String folderName = scan.nextLine();

        try (CSVReader reader = new CSVReader(new FileReader(csvFileName))) {
            Map<String, String> map = reader
                    .readAll()
                    .stream()
                    .collect(Collectors.toMap(it->it[0], it->it[1]));

            File[] listFiles = new File(folderName).listFiles();

            if(listFiles == null) {
                System.out.println("the folder does not exist");
                return;
            }

            for(File file : listFiles) {
                String fileName = file.getName();

                if(file.isFile() && map.containsKey(fileName)) {
                    File newFile = new File(folderName + "/" + map.get(fileName));
//                    System.out.println(newFile.getPath());
                    changeFileName(file, newFile);
                }
                // 확장자 미포함
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    public static void changeFileName(File oldFile, File newFile) throws IOException {
        Path source = Paths.get(oldFile.getPath());
        Path newdir = Paths.get(newFile.getPath());
        Files.move(source, newdir);
    }
}
