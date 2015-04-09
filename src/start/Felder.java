package start;

import java.awt.Color;
import java.io.*;

public class Felder {

	// Methode zum Einlesen der Datei, Zeile fuer Zeile
	// & speichern in einem String-Array

	private static String[] loginName;
	private static String[] passwordName;
	private static String[] websiteName;
	private static String[] commentsName;

	public static String[] getLoginName() {
		return loginName;
	}

	public static String[] getPasswordName() {
		return passwordName;
	}

	public static String[] getWebsiteName() {
		return websiteName;
	}

	public static String[] getCommentsName() {
		return commentsName;
	}

	public static void einlesen(String filename) {

		BufferedReader bufferedReader;
		GUI.status = true;

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename), "UTF-16"));

			String[] parts = bufferedReader.readLine().split("=", 2);
			String check = parts[0].toString();
			String loginNameLine = parts[1];
			loginName = loginNameLine.replace("\"", "").split(",");

			String passwordNameLine = bufferedReader.readLine().split("=")[1];
			passwordName = passwordNameLine.replace("\"", "").split(",");

			String websiteNameLine = bufferedReader.readLine().split("=")[1];
			websiteName = websiteNameLine.replace("\"", "").split(",");

			String commentsNameLine = bufferedReader.readLine().split("=")[1];
			commentsName = commentsNameLine.replace("\"", "").split(",");

			if (check == "loginName") {
				GUI.status = false;
				GUI.setLabelStatus("falsche Felder-Datei!", Color.RED);
			}
			bufferedReader.close();

		} catch (Exception e) {
			GUI.setLabelStatus("Fehler in der Felder-Datei!", Color.RED);
			GUI.status = false;

		}

	}
}
