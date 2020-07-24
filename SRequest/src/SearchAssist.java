import java.io.*;
import java.util.*;

public class SearchAssist {
	private static final String csv_path = "logging.csv";

	public static void main(String[] args) throws IOException {
		String line;
		String[] data;

		BufferedReader csvReader = new BufferedReader(new FileReader(csv_path));
		line = csvReader.readLine();

		Map<String, Vector<String>> map = new HashMap<String, Vector<String>>();

		while ((line = csvReader.readLine()) != null) {
			data = line.split("\t"); // Eingelesene Zeile nach Delimiter trennen
			Vector<String> newEntry = new Vector<String>();

			if (!map.containsKey(data[0])) { // Pruefen, ob Shopkey bereits in Map enthalten
				map.put(data[0], newEntry); // Ansonsten neuen Key mit Vector als Value anlegen
			}
			map.get(data[0]).add(data[1]); // Ansonsten Query zu Vector hinzufuegen
		}

		System.out.println("Nach welchem Shopkey möchten sie suchen? (SHOPKEY ID)");

		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		findMostsearched(map.get(input)); // findMostsearched mit gewünschtem Array als Parameter aufrufen

		csvReader.close();
	}

	public static void findMostsearched(Vector<String> queryArray) {
		int tempInt = 0;
		String tempString;
		Map<String, Integer> queryMap = new HashMap<String, Integer>();

		for (int i = 0; i < queryArray.size(); i++) { // Array mit Queries durchgehen
			if (!queryMap.containsKey(queryArray.get(i).toLowerCase())) { // Checken ob Query bereits enthalten
				queryMap.put(queryArray.get(i).toLowerCase(), 1); // Falls nicht, Query mit einer Suchanfrage einfügen
			} else {
				queryMap.put(queryArray.get(i).toLowerCase(), queryMap.get(queryArray.get(i).toLowerCase()) + 1); // Ansonsten
																													// Suchanfragenanzahl
																													// um
																													// 1
																													// erhöhen
			}
		}

		String[] keys = queryMap.keySet().toArray(new String[0]); // Hashmap in zwei Arrays von key und value auftrennen
		Integer[] values = queryMap.values().toArray(new Integer[0]);

		// Sortiere absteigend
		for (int i = 0; i < keys.length; i++) {
			for (int j = i + 1; j < keys.length; j++) {
				if (values[i] < values[j]) {
					// Werte absteigend sortieren
					tempInt = values[i];
					values[i] = values[j];
					values[j] = tempInt;
					// Schlüssel gleich sortieren, damit Paare erhalten bleiben
					tempString = keys[i];
					keys[i] = keys[j];
					keys[j] = tempString;
				}
			}
		}

		// Array absteigend sortiert, daher sind die ersten 10 die am häufigsten
		// aufgerufenen
		for (int i = 0; i < 10; i++) {
			System.out.println(String.format("Query %-20s wurde %5s x aufgerufen!", keys[i], values[i]));
		}
	}
}
