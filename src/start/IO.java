package start;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IO {

	private static PrintWriter pw;

	// Methode zum Einlesen der backup-Datei, Zeile fuer Zeile & speichern in einem String-Array
	public String[] readFile(String filename) {

		BufferedReader bufferedReader;
		List<String> lines = new ArrayList<String>();
		GUI.setLabelStatus("Die Backup-Datei hat keine Codewallet-Kennung!",Color.RED);
		GUI.status = false;

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename), "UTF-16")); // WICHTIG, da alle backup-Dateien in UCS-2 sind?
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
				if (line.contains("CodeWallet Pro Wallet Export")) {
					GUI.setLabelStatus("Analyse beginnt: .........", Color.BLUE);
					GUI.status = true;
				}
			}
			bufferedReader.close();
			lines.add("*---");
			lines.add("ENDE");

		} catch (Exception e) {
			GUI.setLabelStatus("Fehler in der Backup-Datei!", Color.RED);
			GUI.status = false;
		}
		return lines.toArray(new String[lines.size()]);
	}

	// Methode zum Schreiben der import-Datei, String-Array - Zeile für Zeile
	public static void writeFile(String filename, String[] newFile) {
		try {
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filename), "UTF-8")));
			for (String oneLine : newFile) {
				pw.println(oneLine);
			}
			pw.close();
			GUI.status = true;

		} catch (Exception e) {
			GUI.setLabelStatus("Fehler beim Schreiben in die Import-Datei!",Color.RED);
			GUI.status = false;
		}
	}
}
