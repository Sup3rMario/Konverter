package start;

import java.util.ArrayList;

public class Analyse {

	private String[] oldFile;
	private ArrayList<String> newFile;

	public Analyse(String[] oldFile) {
		this.oldFile = oldFile;
	}
	// hier wird das backup-File (String-Array) analysiert und in eine Struktur für das import-File gebracht
	public void auswerten() {

		/**** PART 1: erste Zeile erzeugen *****************************************************************/

		newFile = new ArrayList<String>();
		// erste Zeile wird immer dieselbe sein:
		newFile.add("\"Group\",\"Account\",\"Login Name\",\"Password\",\"Web Site\",\"Comments\"");

		/**** PART 2: testen, ordnen, in ArrayList schreiben. **********************************************/

		// eine Zeile wird aus diesen Infos bestehen:
		String group = "";
		String account = "";
		String login = "";
		String password = "";
		String website = "";
		String comments = "";

		String[] loginName = Felder.getLoginName();
		String[] passwordName = Felder.getPasswordName();
		String[] websiteName = Felder.getWebsiteName();
		String[] commentsName = Felder.getCommentsName();
		
		// Zeile für Zeile wird jetzt analysiert und mit anderen Zeilen verglichen und verkettet
		for (int x = 0; x < oldFile.length - 2; x++) {

			if (oldFile[x].contains("*---")
					&& oldFile[x + 1].contains("Ordner")
					&& oldFile[x + 2].contains("*---")) {
				// Name des Ordners speichern:
				group = oldFile[x + 1].trim().replace(" > ", "\\\\").substring(8);

			} else if (oldFile[x].contains("*---")
					&& !(oldFile[x + 1].isEmpty())
					&& oldFile[x + 2].contains("*---")) {
				// Account-Name speichern:
				account = oldFile[x + 1].trim();

				// 3 Zeilen weitergehen: ab da geht Info fuer diesen Account los:
				x = x + 3;
				login = "";
				password = "";
				website = "";
				comments = "";

				// Infos fuer diesen Account einsammeln: login / password / website / comments
				// (while-loop geht gesamten Info-Part durch)
				while (!oldFile[x].contains("*---")) {
					String selectedLine = oldFile[x].trim();
					// diese eine Zeile wird jetzt untersucht, bei "ENDE" wird der letzte Datenteil abgespeichert
					if (selectedLine.equals("ENDE")) {
						String oneLine = String.format(
								"\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
								group, account, login, password, website,
								comments);
						newFile.add(oneLine);
						break;
					// jetzt die Daten den jeweiligen neuen Felder zuordnen	
					} else if (selectedLine.contains(":")) {
						String[] parts = selectedLine.split(":", 2);
						String part1 = parts[0].trim();
						String part2 = parts[1].trim();
						int y = 0;

						for (int i = 0; i < commentsName.length; i++) {
							if (part1.equals(commentsName[i])) {
								comments = comments + selectedLine;
								y = 1;
							}
						}

						for (int i = 0; i < websiteName.length; i++) {
							if (part1.equals(websiteName[i])) {
								website = part2;
								y = 1;
							}
						}

						for (int i = 0; i < passwordName.length; i++) {
							if (part1.equals(passwordName[i])) {
								password = part2;
								y = 1;
							}
						}

						for (int i = 0; i < loginName.length; i++) {
							if (part1.equals(loginName[i])) {
								login = part2;
								y = 1;
							}
						}

						// wenn keine Zordungen erfolgt ist, ganze Zeile in den
						// Notizen ablegen
						if (y == 0) {
							comments = comments + selectedLine + '\n';
							y = 0;
						}

					} else {
						// wenn kein Trennzeichen ":" da ist, Daten in die
						// Notizen ablegen
						if (!selectedLine.equals("")) {
							comments = comments + selectedLine + '\n';
						}

					}
					x++;
				} // while end

				x--;

				// komplette Ordner-Sektion ist durchgecheckt, alle Infos sind gesammelt & geordnet
				// --> das ganze jetzt in die nächste Zeile abspeichern. 
				String oneLine = String.format(
						"\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"", group,
						account, login, password, website, comments);
				newFile.add(oneLine);

			} else {
				account = "";
			} // end if

		}// end for

		/**** PART 3: ArrayList in die import-Datei schreiben*********************************************/

		// wir haben jetzt die konvertierten Daten in einer ArrayList
		// -> diese jetzt in String-Array umwandeln damit der Dateischreiber diesen durchgehen kann.
		IO.writeFile(GUI.getImportDateiName(),
				newFile.toArray(new String[newFile.size()]));

	}

}
