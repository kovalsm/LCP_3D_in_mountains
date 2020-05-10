package sk.upjs.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

public class Analyza3DModelu_Form extends JFrame {

	private static final long serialVersionUID = 4521250931961455886L;

	private Analyza3DModelu_SwingWorker worker;

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
	private JLabel hodnotaPocBoduLabel;

	private JTextField hodnotaCielBoduText;
	private JLabel hodnotaCielBoduLabel;

	private JComboBox<String> algoritmyComboBox;
	private JButton algoritmyPouziButton;
	private JButton algoritmyZrusButton;
	private JLabel algoritmyLabel;


	public Analyza3DModelu_Form() {

		JPanel komponenty = new JPanel();
		komponenty.setLayout(new GridLayout(10, 1));
		komponenty.setBorder(new EmptyBorder(10, 10, 10, 10));

		// --------------------------------------
		// vybratie zdrojoveho suboru
		JLabel zdrojovySuborLabel = new JLabel("S˙bor s 3D modelom: ");
		zdrojovySuborLabel.setPreferredSize(new Dimension(150, 40));

		zdrojovySuborText = new JTextField();
		zdrojovySuborText.setPreferredSize(new Dimension(100, 25));
		zdrojovySuborText.setBounds(261, 68, 80, 20);
		zdrojovySuborText.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		zdrojovySuborText.setMaximumSize(new Dimension(Integer.MAX_VALUE, zdrojovySuborText.getPreferredSize().height));
		zdrojovySuborText.setEditable(false);
		zdrojovySuborText.setText(" Vyber subor PLY, s ktor˝m sa bude pracovaù ");

		zdrojovySuborButton = new JButton("Prehæad·vaù");
		zdrojovySuborButton.setBounds(261, 68, 80, 20);

		Box zdrojovySuborBox = Box.createHorizontalBox();
		zdrojovySuborBox.add(zdrojovySuborLabel);
		zdrojovySuborBox.add(Box.createHorizontalGlue());
		zdrojovySuborBox.add(zdrojovySuborText);
		zdrojovySuborBox.add(Box.createHorizontalStrut(5));
		zdrojovySuborBox.add(zdrojovySuborButton);

		komponenty.add(zdrojovySuborBox);

		// ----------------------
		// nacitanie suboru ply
		nacitanieLabel = new JLabel("");

		nacitanieZrusitButton = new JButton("Zruö naËÌtanie");
		nacitanieRobitButton = new JButton("NaËÌtaj");
		nacitanieRobitButton.setPreferredSize(new Dimension(107, 40));

		Box nacitanieBox = Box.createHorizontalBox();
		nacitanieBox.add(nacitanieLabel);
		nacitanieBox.add(Box.createHorizontalGlue());
		nacitanieBox.add(nacitanieZrusitButton);
		nacitanieBox.add(Box.createHorizontalStrut(5));
		nacitanieBox.add(nacitanieRobitButton);

		komponenty.add(nacitanieBox);

		// ----------------------
		// Vybratie cieloveho adresara
		JLabel cielovyAdresarLabel = new JLabel("Adres·r pre novÈ s˙bory: ");
		cielovyAdresarLabel.setPreferredSize(new Dimension(150, 40));

		cielovyAdresarText = new JTextField();
		cielovyAdresarText.setPreferredSize(new Dimension(100, 25));
		cielovyAdresarText.setBounds(195, 68, 166, 20);
		cielovyAdresarText.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		cielovyAdresarText
				.setMaximumSize(new Dimension(Integer.MAX_VALUE, cielovyAdresarText.getPreferredSize().height));
		cielovyAdresarText.setEditable(false);

		cielovyAdresarButton = new JButton("Prehæad·vaù");
		cielovyAdresarButton.setBounds(261, 68, 80, 20);

		Box cielovyAdresarBox = Box.createHorizontalBox();
		cielovyAdresarBox.add(cielovyAdresarLabel);
		cielovyAdresarBox.add(Box.createHorizontalGlue());
		cielovyAdresarBox.add(cielovyAdresarText);
		cielovyAdresarBox.add(Box.createHorizontalStrut(5));
		cielovyAdresarBox.add(cielovyAdresarButton);

		komponenty.add(cielovyAdresarBox);

		// ----------------------
		// Zadanie nazvu noveho suboru
		JLabel nazovNovehoSuboruTextLabel = new JLabel("N·zov novÈho s˙boru: ");
		nazovNovehoSuboruTextLabel.setPreferredSize(new Dimension(150, 40));

		nazovNovehoSuboruText = new JTextField();
		nazovNovehoSuboruText.setPreferredSize(new Dimension(100, 25));
		nazovNovehoSuboruText.setBounds(195, 68, 166, 20);
		nazovNovehoSuboruText.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		nazovNovehoSuboruText
				.setMaximumSize(new Dimension(Integer.MAX_VALUE, nazovNovehoSuboruText.getPreferredSize().height));
		nazovNovehoSuboruText.setEditable(true);

		nazovNovehoSuboruLabel = new JLabel("");
		nazovNovehoSuboruLabel.setPreferredSize(new Dimension(106, 40));

		Box nazovNovehoSuboruBox = Box.createHorizontalBox();
		nazovNovehoSuboruBox.add(nazovNovehoSuboruTextLabel);
		nazovNovehoSuboruBox.add(Box.createHorizontalGlue());
		nazovNovehoSuboruBox.add(nazovNovehoSuboruText);
		nazovNovehoSuboruBox.add(Box.createHorizontalStrut(5));
		nazovNovehoSuboruBox.add(nazovNovehoSuboruLabel);

		komponenty.add(nazovNovehoSuboruBox);

		//pociatocny bod Hodnota
		JLabel hodnotaPocBoduTextLabel = new JLabel("Hodnota poË. bodu: ");
		hodnotaPocBoduTextLabel.setPreferredSize(new Dimension(150, 40));

		hodnotaPocBoduText = new JTextField();
		hodnotaPocBoduText.setPreferredSize(new Dimension(100, 25));
		hodnotaPocBoduText.setBounds(195, 68, 166, 20);
		hodnotaPocBoduText.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		hodnotaPocBoduText
				.setMaximumSize(new Dimension(Integer.MAX_VALUE, hodnotaPocBoduText.getPreferredSize().height));
		hodnotaPocBoduText.setEditable(true);

		hodnotaPocBoduLabel = new JLabel("");
		hodnotaPocBoduLabel.setPreferredSize(new Dimension(106, 40));

		Box hodnotaPocBoduBox = Box.createHorizontalBox();
		hodnotaPocBoduBox.add(hodnotaPocBoduTextLabel);
		hodnotaPocBoduBox.add(Box.createHorizontalGlue());
		hodnotaPocBoduBox.add(hodnotaPocBoduText);
		hodnotaPocBoduBox.add(Box.createHorizontalStrut(5));
		hodnotaPocBoduBox.add(hodnotaPocBoduLabel);

		komponenty.add(hodnotaPocBoduBox);

		//ciel bod Hodnota
		JLabel hodnotaCielBoduTextLabel = new JLabel("Hodnota ciel. bodu: ");
		hodnotaCielBoduTextLabel.setPreferredSize(new Dimension(150, 40));

		hodnotaCielBoduText = new JTextField();
		hodnotaCielBoduText.setPreferredSize(new Dimension(100, 25));
		hodnotaCielBoduText.setBounds(195, 68, 166, 20);
		hodnotaCielBoduText.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		hodnotaCielBoduText
				.setMaximumSize(new Dimension(Integer.MAX_VALUE, hodnotaCielBoduText.getPreferredSize().height));
		hodnotaCielBoduText.setEditable(true);

		hodnotaCielBoduLabel = new JLabel("");
		hodnotaCielBoduLabel.setPreferredSize(new Dimension(106, 40));

		Box hodnotaCielBoduBox = Box.createHorizontalBox();
		hodnotaCielBoduBox.add(hodnotaCielBoduTextLabel);
		hodnotaCielBoduBox.add(Box.createHorizontalGlue());
		hodnotaCielBoduBox.add(hodnotaCielBoduText);
		hodnotaCielBoduBox.add(Box.createHorizontalStrut(5));
		hodnotaCielBoduBox.add(hodnotaCielBoduLabel);

		komponenty.add(hodnotaCielBoduBox);

		// ----------------------
		// obsluha algoritmov
		algoritmyComboBox = new JComboBox<>();
		algoritmyComboBox
				.setMaximumSize(new Dimension(Integer.MAX_VALUE, nazovNovehoSuboruText.getPreferredSize().height));
		algoritmyComboBox.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		algoritmyComboBox.setMaximumRowCount(9);

		algoritmyPouziButton = new JButton("Pouûi");
		algoritmyPouziButton.setPreferredSize(new Dimension(107, 40));

		Box algoritmyPouziBox = Box.createHorizontalBox();
		algoritmyPouziBox.add(algoritmyComboBox);
		algoritmyPouziBox.add(Box.createHorizontalGlue());
		algoritmyPouziBox.add(Box.createHorizontalStrut(5));
		algoritmyPouziBox.add(algoritmyPouziButton);

		komponenty.add(algoritmyPouziBox);


		// ----------------------
		// priebeh a zastavenie algoritmu
		algoritmyLabel = new JLabel("");

		algoritmyZrusButton = new JButton("Zruö akciu");
		algoritmyZrusButton.setPreferredSize(new Dimension(107, 40));

		Box algoritmyZrusBox = Box.createHorizontalBox();
		algoritmyZrusBox.add(algoritmyLabel);
		algoritmyZrusBox.add(Box.createHorizontalGlue());
		algoritmyZrusBox.add(Box.createHorizontalStrut(5));
		algoritmyZrusBox.add(algoritmyZrusButton);

		komponenty.add(algoritmyZrusBox);

		// ------------------------------

		add(komponenty);
		setSize(new Dimension(600, 400));
		setMinimumSize(new Dimension(600, 400));
		setTitle("N·kladovÈ anal˝zy na 3D modeloch");
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Analyza3DModelu_Form form = new Analyza3DModelu_Form();
			form.setIconImage(new javax.swing.ImageIcon(form.getClass().getResource("3Dmodel.png")).getImage());
			form.setVisible(true);
			form.obsluhaKomponentov();
		});
	}

	private void obsluhaKomponentov() {
		worker = new Analyza3DModelu_SwingWorker(zdrojovySuborText, zdrojovySuborButton, nacitanieLabel,
				nacitanieRobitButton, nacitanieZrusitButton, cielovyAdresarText, cielovyAdresarButton,
				nazovNovehoSuboruText, nazovNovehoSuboruLabel, hodnotaPocBoduText,
				hodnotaCielBoduText,  algoritmyComboBox, algoritmyPouziButton,
				algoritmyZrusButton, algoritmyLabel);
		worker.execute();
	}

}
