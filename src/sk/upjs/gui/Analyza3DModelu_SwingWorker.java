package sk.upjs.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;


import sk.upjs.gui.ulohy.VykonavacUloh;
import sk.upjs.pracaSDatami.VyrobcaANacitavacSuboruPLY;
import sk.upjs.service.AnalyzaServiceImplementacia;

/**
 * Obsluhuje komponenty v okne aplikacie
 */
public class Analyza3DModelu_SwingWorker extends SwingWorker<Void, Integer> {

	private AnalyzaServiceImplementacia service;
	private VykonavaSaUlohaAStav vykonavaSaUloha;
	private ExecutorService exekutor;

	private String nazovZdrojovehoSuboru = "";
	private String prebiehaNacitanieSuboruSprava = "NaËÌtava sa ";
	private String prebiehaAlgoritmusSprava = "Prebieha v˝poËet ";

	private boolean vykonavaSaNacitanie = false;
	private boolean vykonavaSaAlgoritmus = false;
	private boolean vykonavaSaAlgoritmusObjemu = false;
	
	private String[] nazvyAlgoritmov = { "Dijkstra algoritmus", "najkratsia cesta Irmischer", "najkratsia cesta Naismith", "najkratsia cesta Tobler" };
	private int idxVybranehoAlgoritmu = 0;

	private JTextField zdrojovySuborText;
	private JButton zdrojovySuborButton;

	private JLabel nacitanieLabel;
	private JButton nacitanieRobitButton;
	private JButton nacitanieZrusitButton;

	private JTextField cielovyAdresarText;
	private JButton cielovyAdresarButton;

	private JTextField nazovNovehoSuboruText;
	private JLabel nazovNovehoSuboruLabel;

	private JTextField hodnotaPocBoduText;


	private JTextField hodnotaCielBoduText;


	private JComboBox<String> algoritmyComboBox;
	private JButton algoritmyPouziButton;
	private JButton algoritmyZrusButton;
	private JLabel algoritmyLabel;


	public Analyza3DModelu_SwingWorker(JTextField zdrojovySuborText, JButton zdrojovySuborButton, JLabel nacitanieLabel,
			JButton nacitanieRobitButton, JButton nacitanieZrusitButton, JTextField cielovyAdresarText,
			JButton cielovyAdresarButton, JTextField nazovNovehoSuboruText, JLabel nazovNovehoSuboruLabel,
									   JTextField hodnotaPocBoduText, JTextField hodnotaCielBoduText,
			JComboBox<String> algoritmyComboBox, JButton algoritmyPouziButton, JButton algoritmyZrusButton,
			JLabel algoritmyLabel) {

		super();
		this.zdrojovySuborText = zdrojovySuborText;
		this.zdrojovySuborButton = zdrojovySuborButton;
		this.nacitanieLabel = nacitanieLabel;
		this.nacitanieRobitButton = nacitanieRobitButton;
		this.nacitanieZrusitButton = nacitanieZrusitButton;
		this.cielovyAdresarText = cielovyAdresarText;
		this.cielovyAdresarButton = cielovyAdresarButton;
		this.nazovNovehoSuboruText = nazovNovehoSuboruText;
		this.nazovNovehoSuboruLabel = nazovNovehoSuboruLabel;
		this.hodnotaPocBoduText = hodnotaPocBoduText;
		this.hodnotaCielBoduText = hodnotaCielBoduText;
		this.algoritmyComboBox = algoritmyComboBox;
		this.algoritmyPouziButton = algoritmyPouziButton;
		this.algoritmyZrusButton = algoritmyZrusButton;
		this.algoritmyLabel = algoritmyLabel;

		inicializaciaKomponentov();
	}

	private void inicializaciaKomponentov() {

		// zdrojovySuborText.setEnabled(false);
		zdrojovySuborButton.setEnabled(true);
		// nacitanieLabel.setEnabled(false);
		nacitanieRobitButton.setEnabled(true);
		nacitanieZrusitButton.setEnabled(false);
		cielovyAdresarText.setEnabled(true);
		cielovyAdresarButton.setEnabled(true);
		nazovNovehoSuboruText.setEnabled(true);
		// nazovNovehoSuboruLabel.setEnabled(false);
		hodnotaCielBoduText.setEnabled(true);
		hodnotaPocBoduText.setEnabled(true);
		algoritmyComboBox.setEnabled(true);
		algoritmyPouziButton.setEnabled(true);
		algoritmyZrusButton.setEnabled(false);
		// algoritmyLabel.setEnabled(false);
		vykonavaSaUloha = new VykonavaSaUlohaAStav();

		zdrojovySuborButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setAcceptAllFileFilterUsed(false);
				jfc.setFileFilter(new FileFilter() {

					public String getDescription() {
						return "PLY Files (*.ply)";
					}

					public boolean accept(File f) {
						if (f.isDirectory()) {
							return true;
						} else {
							String filename = f.getName().toLowerCase();
							return filename.endsWith(".ply");
						}
					}
				});
				int vratilo = jfc.showDialog(null, "Select file");
				if (vratilo == JFileChooser.APPROVE_OPTION) {
					File subor = jfc.getSelectedFile();
					zdrojovySuborText.setText(subor.getAbsolutePath());
				}
			}
		});

		nacitanieLabel.setForeground(Color.GRAY);
		nacitanieLabel.setText("Treba naËÌtaù s˙bor");

		nacitanieRobitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				File zdrojovySubor = new File(zdrojovySuborText.getText());
				if (zdrojovySubor.exists()) {
					nacitanieLabel.setForeground(Color.BLACK);
					nazovZdrojovehoSuboru = zdrojovySubor.getName();
					service = new AnalyzaServiceImplementacia(zdrojovySubor);
					exekutor = Executors.newSingleThreadExecutor();
					exekutor.execute(new VykonavacUloh(vykonavaSaUloha, service));
					vykonavaSaUloha.setPrebiehaVykonavanie(true);
					vykonavaSaNacitanie = true;
					zdrojovySuborButton.setEnabled(false);
					nacitanieRobitButton.setEnabled(false);
					nacitanieZrusitButton.setEnabled(true);
				} else {
					nacitanieLabel.setForeground(Color.RED);
					nacitanieLabel.setText("Vyberte s˙bor!");
				}
			}
		});

		nacitanieZrusitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (vykonavaSaNacitanie) {
					exekutor.shutdownNow();
				} else {
					nacitanieLabel.setForeground(Color.GRAY);
					nacitanieLabel.setText("Treba naËÌtaù s˙bor");
					zdrojovySuborButton.setEnabled(true);
					nacitanieRobitButton.setEnabled(true);
					nacitanieZrusitButton.setEnabled(false);
					algoritmyLabel.setText("");
				}
			}
		});

		cielovyAdresarText.setText(System.getProperty("user.dir"));
		cielovyAdresarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
				jfc.setDialogTitle("Choose output directory");
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int vratilo = jfc.showDialog(null, "Choose");
				if (vratilo == JFileChooser.APPROVE_OPTION) {
					File subor = jfc.getSelectedFile();
					cielovyAdresarText.setText(subor.getAbsolutePath());
				}
			}
		});

		nazovNovehoSuboruText.setText("novySubor");
		nazovNovehoSuboruLabel.setText(".ply");
		hodnotaPocBoduText.setText("1");
		hodnotaCielBoduText.setText("546879");

		for (int i = 0; i < nazvyAlgoritmov.length; i++) {
			algoritmyComboBox.addItem(nazvyAlgoritmov[i]);
		}
		algoritmyComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String vybranyAlgoritmus = (String) algoritmyComboBox.getSelectedItem();
				algoritmyLabel.setText("");

				if (vybranyAlgoritmus.equals(nazvyAlgoritmov[0])) {
					// lokalne minima celeho
					idxVybranehoAlgoritmu = 0;
					return;
				}
				if (vybranyAlgoritmus.equals(nazvyAlgoritmov[1])) {
					// lokalne minima celeho
					idxVybranehoAlgoritmu = 1;
					return;
				}

				if (vybranyAlgoritmus.equals(nazvyAlgoritmov[2])) {
					// lokalne minima celeho
					idxVybranehoAlgoritmu = 2;
					return;
				}

				if (vybranyAlgoritmus.equals(nazvyAlgoritmov[3])) {
					// lokalne minima celeho
					idxVybranehoAlgoritmu = 3;
					return;
				}
			}

		});

		algoritmyPouziButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String cielovyAdresar = cielovyAdresarText.getText();
				File cielovyAdresarFile = new File(cielovyAdresar);
				if (!cielovyAdresarFile.isDirectory()) {
					nazovNovehoSuboruLabel.setForeground(Color.RED);
					nazovNovehoSuboruLabel.setText("Nezn·my adres·r!");
					return;
				}

				String nazovSuboru = nazovNovehoSuboruText.getText();

				int bodZac = Integer.parseInt(hodnotaPocBoduText.getText());
				int bodCiel = Integer.parseInt(hodnotaCielBoduText.getText());


				if (!VyrobcaANacitavacSuboruPLY.suLenPovoleneZnaky(nazovSuboru)) {
					algoritmyLabel.setForeground(Color.RED);
					algoritmyLabel.setText("N·zov s˙boru nesmie obsahovaù tieto znaky: \\ / : * ? \" < > |");
					return;
				}

				String vybranyAlgoritmus = (String) algoritmyComboBox.getSelectedItem();

				if (vybranyAlgoritmus.equals(nazvyAlgoritmov[0])) {
					// Dijkstra

					exekutor = Executors.newSingleThreadExecutor();
					exekutor.execute(new VykonavacUloh(0, vykonavaSaUloha, service, cielovyAdresar, nazovSuboru));
					vykonavaSaUloha.setPrebiehaVykonavanie(true);
					vykonavaSaAlgoritmus = true;

					zdrojovySuborButton.setEnabled(false);
					nacitanieRobitButton.setEnabled(false);
					nacitanieZrusitButton.setEnabled(false);
					algoritmyZrusButton.setEnabled(true);
					return;
				}

				if (vybranyAlgoritmus.equals(nazvyAlgoritmov[1])) {
					// Gauss + Dijkstra

					exekutor = Executors.newSingleThreadExecutor();
					exekutor.execute(new VykonavacUloh(1, vykonavaSaUloha, service, cielovyAdresar, nazovSuboru));
					vykonavaSaUloha.setPrebiehaVykonavanie(true);
					vykonavaSaAlgoritmus = true;

					zdrojovySuborButton.setEnabled(false);
					nacitanieRobitButton.setEnabled(false);
					nacitanieZrusitButton.setEnabled(false);
					algoritmyZrusButton.setEnabled(true);
					return;
				}

				if (vybranyAlgoritmus.equals(nazvyAlgoritmov[2])) {
					// Naismith

					exekutor = Executors.newSingleThreadExecutor();
					exekutor.execute(new VykonavacUloh(2, vykonavaSaUloha, service, cielovyAdresar, nazovSuboru));
					vykonavaSaUloha.setPrebiehaVykonavanie(true);
					vykonavaSaAlgoritmus = true;

					zdrojovySuborButton.setEnabled(false);
					nacitanieRobitButton.setEnabled(false);
					nacitanieZrusitButton.setEnabled(false);
					algoritmyZrusButton.setEnabled(true);
					return;
				}

				if (vybranyAlgoritmus.equals(nazvyAlgoritmov[3])) {
					// Tobler

					exekutor = Executors.newSingleThreadExecutor();
					exekutor.execute(new VykonavacUloh(3, vykonavaSaUloha, service, cielovyAdresar, nazovSuboru));
					vykonavaSaUloha.setPrebiehaVykonavanie(true);
					vykonavaSaAlgoritmus = true;

					zdrojovySuborButton.setEnabled(false);
					nacitanieRobitButton.setEnabled(false);
					nacitanieZrusitButton.setEnabled(false);
					algoritmyZrusButton.setEnabled(true);
					return;
				}

			}
		});

		algoritmyZrusButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (vykonavaSaAlgoritmus) {
					exekutor.shutdownNow();
				}
				if (vykonavaSaAlgoritmusObjemu) {
					exekutor.shutdownNow();
				}
			}
		});

	}

	@Override
	protected Void doInBackground() throws Exception {

		int pocetBodiek = 1;
		while (!Thread.currentThread().isInterrupted()) {
			if (vykonavaSaNacitanie) {
				algoritmyLabel.setForeground(Color.BLACK);
				while (vykonavaSaUloha.isPrebiehaVykonavanie()) {
					switch (pocetBodiek) {
					case 1:
						nacitanieLabel.setText(prebiehaNacitanieSuboruSprava + ".");
						break;
					case 2:
						nacitanieLabel.setText(prebiehaNacitanieSuboruSprava + "..");
						break;
					case 3:
						nacitanieLabel.setText(prebiehaNacitanieSuboruSprava + "...");
						break;
					}
					pocetBodiek = (pocetBodiek + 1) % 3 + 1;
					Thread.sleep(200);
				}
				if (vykonavaSaUloha.isVysledokVykonania()) {
					nacitanieLabel.setText("NaËÌtan˝ s˙bor: " + nazovZdrojovehoSuboru);
					vykonavaSaNacitanie = false;
				} else {
					nacitanieLabel.setText("Nepodarilo sa naËÌtaù s˙bor: " + nazovZdrojovehoSuboru);
					vykonavaSaNacitanie = false;
					zdrojovySuborButton.setEnabled(true);
					nacitanieRobitButton.setEnabled(true);
					nacitanieZrusitButton.setEnabled(false);
				}
			}

			if (vykonavaSaAlgoritmus) {
				algoritmyLabel.setForeground(Color.BLACK);
				while (vykonavaSaUloha.isPrebiehaVykonavanie()) {
					switch (pocetBodiek) {
					case 1:
						algoritmyLabel.setText(prebiehaAlgoritmusSprava + ".");
						break;
					case 2:
						algoritmyLabel.setText(prebiehaAlgoritmusSprava + "..");
						break;
					case 3:
						algoritmyLabel.setText(prebiehaAlgoritmusSprava + "...");
						break;
					}
					pocetBodiek = (pocetBodiek + 1) % 3 + 1;
					Thread.sleep(200);
				}
				if (vykonavaSaUloha.isVysledokVykonania()) {
					algoritmyLabel.setForeground(Color.BLACK);
					algoritmyLabel.setText("V˝poËet prebehol ˙speöne");
					vykonavaSaAlgoritmus = false;
					nacitanieZrusitButton.setEnabled(true);
				} else {
					algoritmyLabel.setForeground(Color.BLACK);
					algoritmyLabel.setText("Nepodarilo sa dokonËiù v˝poËet");
					vykonavaSaAlgoritmus = false;

					nacitanieZrusitButton.setEnabled(true);
				}
			}

		}
		return null;
	}
}
