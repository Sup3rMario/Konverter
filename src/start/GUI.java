package start;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI extends Frame implements ActionListener {
	// Eigenschaften definieren
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private TextField txt1 = new TextField("./");
	private TextField txt2 = new TextField("./Felder.txt");
	private TextField txt3 = new TextField("./Import.txt");
	private static Label labelStatus = new Label("Bitte Datei(en) auswählen!");

	private static String backupDateiName;
	private static String felderDateiName = "./Felder.txt";
	private static String importDateiName = "./Import.txt";
	public static boolean status = true;

	public static String getBackupDateiName() {
		return backupDateiName;
	}

	public static String getImportDateiName() {
		return importDateiName;
	}

	public static String getFelderDateiName() {
		return felderDateiName;
	}

	public static void setLabelStatus(String txtStatus, Color farbe) {
		labelStatus.setForeground(farbe);
		labelStatus.setText(txtStatus);
	}

	// Konstruktor GUI
	public GUI() {
		super("Datenkonverter");

		// Festlegen, dass Layout "GridBagLayout" verwendet wird
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();

		// Festlegen, dass die GUI-Elemente die Gitterfelder in
		// waagerechter Richtung ausfüllen:
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Die Abstände der einzelnen GUI-Elemente zu den gedachten
		// Gitterlinien festgelegen:
		gbc.insets = new Insets(3, 3, 3, 3);

		gbc.gridx = 0; // x-Position im gedachten Gitter
		gbc.gridy = 0; // y-Position im gedachten Gitter
		gbc.gridheight = 1; // Zeilenhöhe im gedachten Gitter
		Label label1 = new Label("1. Backup-Datei vom CWP auswählen:");
		gbl.setConstraints(label1, gbc);
		add(label1);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbl.setConstraints(txt1, gbc);
		add(txt1);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		btn1 = new Button("öffnen");
		btn1.addActionListener(this);
		gbl.setConstraints(btn1, gbc);
		add(btn1);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridheight = 1;
		Label label2 = new Label(
				"2. Felder-Zuordnungsdatei auswählen: (optional)");
		gbl.setConstraints(label2, gbc);
		add(label2);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridheight = 1;
		gbl.setConstraints(txt2, gbc);
		add(txt2);

		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridheight = 1;
		btn2 = new Button("ändern");
		btn2.addActionListener(this);
		btn2.setEnabled(false);
		gbl.setConstraints(btn2, gbc);
		add(btn2);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridheight = 1;
		Label label3 = new Label(
				"3. Import-Datei erzeugen im Verzeichnis: (optional)");
		gbl.setConstraints(label3, gbc);
		add(label3);

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridheight = 1;
		gbl.setConstraints(txt3, gbc);
		add(txt3);

		gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.gridheight = 1;
		btn3 = new Button("speichern unter");
		btn3.addActionListener(this);
		btn3.setEnabled(false);
		gbl.setConstraints(btn3, gbc);
		add(btn3);

		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridheight = 1;
		Label label4 = new Label("Status:");
		gbl.setConstraints(label4, gbc);
		add(label4);

		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.gridheight = 1;
		gbl.setConstraints(labelStatus, gbc);
		add(labelStatus);

		gbc.gridx = 1;
		gbc.gridy = 10;
		gbc.gridheight = 1;
		btn4 = new Button("START");
		btn4.addActionListener(this);
		btn4.setEnabled(false);
		gbl.setConstraints(btn4, gbc);
		add(btn4);

		this.setLocation(300, 200);
		pack();

		// inner anonyme Klasse für das Beenden des Programms über Fenster-X
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		});
	}

	// Actionlistener für Buttons
	public void actionPerformed(ActionEvent e) {
		// Button Öffnen Verarbeitung 
		if (e.getSource() == btn1) {
			JFileChooser dateiAuswahl = new JFileChooser(".");
			FileFilter type1 = new FileNameExtensionFilter("TXT files", "txt");
			dateiAuswahl.addChoosableFileFilter(type1);
			dateiAuswahl.setFileFilter(type1); // Initial filter setting
			dateiAuswahl.setCurrentDirectory(new java.io.File(txt1.getText()));
			int result = dateiAuswahl.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				backupDateiName = dateiAuswahl.getSelectedFile().toString();
				txt1.setForeground(Color.BLACK);
				txt1.setText(backupDateiName);
				labelStatus.setForeground(Color.BLACK);
				labelStatus.setText("Start drücken, um die Konvertierung zu starten ->");
				btn2.setEnabled(true);
				btn3.setEnabled(true);
				btn4.setEnabled(true);
			} else {
				txt1.setForeground(Color.RED);
				txt1.setText("Bitte Datei auswählen!");
			}
		}
		// Button Ändern Verarbeitung 
		if (e.getSource() == btn2) {
			JFileChooser dateiAuswahl = new JFileChooser(".");
			FileFilter type1 = new FileNameExtensionFilter("TXT files", "txt");
			dateiAuswahl.addChoosableFileFilter(type1);
			dateiAuswahl.setFileFilter(type1); // Initial filter setting
			dateiAuswahl.setCurrentDirectory(new java.io.File(txt2.getText()));
			int result = dateiAuswahl.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				felderDateiName = dateiAuswahl.getSelectedFile().toString();
				txt2.setForeground(Color.BLACK);
				txt2.setText(felderDateiName);
				labelStatus.setForeground(Color.BLACK);
				labelStatus.setText("Start drücken, um die Konvertierung zu starten ->");
			} else {
				txt2.setForeground(Color.RED);
				txt2.setText("Bitte Datei auswählen!");
			}
		}
		// Button SpeichernUnter Verarbeitung 
		if (e.getSource() == btn3) {
			JFileChooser dateiAuswahl = new JFileChooser(".");
			dateiAuswahl.setCurrentDirectory(new java.io.File(txt3.getText()));
			int result = dateiAuswahl.showSaveDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				importDateiName = dateiAuswahl.getSelectedFile().toString();
				if (!importDateiName.endsWith(".txt"))
					importDateiName += ".txt";
				txt3.setForeground(Color.BLACK);
				txt3.setText(importDateiName);
				labelStatus.setForeground(Color.BLACK);
				labelStatus.setText("Start drücken, um die Konvertierung zu starten ->");
			} else {
				txt3.setForeground(Color.RED);
				txt3.setText("Bitte Datei auswählen!");
			}
		}
		// Button Start Verarbeitung 
		if (e.getSource() == btn4) {
			labelStatus.setForeground(Color.BLACK);
			labelStatus.setText("Überprüfe die Dateien .....");
			backupDateiName = txt1.getText().toString();
			felderDateiName = txt2.getText().toString();
			importDateiName = txt3.getText().toString();

			// Felder-Datei in String-Array einlesen
			Felder.einlesen(felderDateiName);

			if (status) {
				// Backup-Datei in String-Array einlesen
				IO work = new IO();
				String[] oldFile = work.readFile(backupDateiName);

				if (status) {
					// Analyse starten, auswerten, in Datei schreiben
					Analyse start = new Analyse(oldFile);
					start.auswerten();

					labelStatus.setForeground(Color.GREEN);
					labelStatus.setText("*** FERTIG ***");
					btn1.setEnabled(false);
					btn2.setEnabled(false);
					btn3.setEnabled(false);
					btn4.setEnabled(false);
				}
				
			} else {
				GUI.setLabelStatus("falsche Felder-Datei!", Color.RED);
			}
		} // endif btn4
	} // end actionPerformed
} // end class GUI