import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Zapis {
    private static final String PLIK = "woda.txt";

  
    public static Map<LocalDate, Integer> wczytaj_() {
        Map<LocalDate, Integer> historia = new HashMap<>();
        File file = new File(PLIK);
        if (!file.exists()) return historia;

        try (BufferedReader plik = new BufferedReader(new FileReader(file))) {
            String linia;
            while ((linia = plik.readLine()) != null) {
                String[] parts = linia.split(" ");
                if (parts.length == 2) {
                    LocalDate data = LocalDate.parse(parts[0]);
                    int ilosc = Integer.parseInt(parts[1]);
                    historia.put(data, ilosc);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return historia;
    }

    public static void zapiszdzisiaj_(LocalDate data, int suma) {
        Map<LocalDate, Integer> historia = wczytaj_();
        historia.put(data, suma);

        try (FileWriter writer = new FileWriter(PLIK, false)) {
            for (Map.Entry<LocalDate, Integer> entry : historia.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



