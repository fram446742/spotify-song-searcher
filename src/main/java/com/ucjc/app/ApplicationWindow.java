package com.ucjc.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.ucjc.generated.Lexer;
import com.ucjc.generated.Lexer2;
import com.ucjc.generated.Parser;
import com.ucjc.generated.Parser2;
import com.ucjc.utils.TError;

public class ApplicationWindow {

	private JFrame frame;
	private JTextField txtEnterYourSearch;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_3;
	private JButton btnNewButton;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JTextArea help;
	private JPanel panel_2;
	private Boolean biggerDatabaseBoolean = false;
	private JPanel panel_4;
	private JScrollPane scrollPane_3;
	private JTextPane errorLog_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationWindow window = new ApplicationWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ApplicationWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Input",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(4, 37, 679, 64);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		txtEnterYourSearch = new JTextField();
		txtEnterYourSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					btnNewButton.doClick();
				}
			}
		});

		txtEnterYourSearch.setToolTipText("Enter your search term");
		txtEnterYourSearch.setBounds(10, 18, 550, 38);
		panel_1.add(txtEnterYourSearch);
		txtEnterYourSearch.setColumns(10);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Output",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(4, 111, 679, 175);
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);

		JList output = new JList();
		scrollPane.setViewportView(output);
		output.setToolTipText("Left Click to copy selected \r\nRight Click to copy all");
		output.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) { // Left-click
					// Get the selected value from the list
					String selectedValue = (String) output.getSelectedValue();

					// Ensure that a value is selected before attempting to copy
					if (selectedValue != null) {
						copyToClipboard(selectedValue);
					}
				} else if (e.getButton() == MouseEvent.BUTTON3) { // Right-click
					// Get all values from the list
					ListModel<String> model = output.getModel();
					int size = model.getSize();

					// Construct a string by concatenating all values
					StringBuilder allText = new StringBuilder();
					for (int i = 0; i < size; i++) {
						allText.append(model.getElementAt(i));
						if (i < size - 1) {
							allText.append(System.lineSeparator()); // Add a newline between items
						}
					}

					// Copy the constructed string to the clipboard
					copyToClipboard(allText.toString());
				}
			}
		});

		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Error log",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(693, 296, 563, 174);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		scrollPane_1 = new JScrollPane();
		panel_3.add(scrollPane_1);

		JTextPane errorLog = new JTextPane();
		errorLog.setText("=== Lexical Errors ===\r\n\r\n=== Grammatical Errors ===\r\n");
		errorLog.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String errors = errorLog.getText();
				copyToClipboard(errors);
			}
		});

		errorLog.setToolTipText("Error log: Click to copy");
		errorLog.setEditable(false);
		scrollPane_1.setViewportView(errorLog);

		btnNewButton = new JButton("Search");
		btnNewButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnNewButton.doClick();
				}
			}
		});

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = txtEnterYourSearch.getText();

				if (biggerDatabaseBoolean) {
					System.out.println("Using Spotify_2023 table");

					Lexer2 lexer = new Lexer2(new BufferedReader(new StringReader(text)));
					Parser2 parser = new Parser2(lexer);

					try {
						parser.parse();

						String[] listData = (parser.result != null) ? parser.result.split(System.lineSeparator())
								: new String[] { "null" };
						output.setListData(listData);
						// Clear the JTextArea
						errorLog.setText("");

						LinkedList<TError> lexicalErrors = parser.SyntaxErrorTable;
						LinkedList<TError> grammaticalErrors = lexer.LexicalErrorTable;

						StringBuilder combinedText = new StringBuilder();

						// Display Lexical Errors
						combinedText.append("=== Lexical Errors ===").append(System.lineSeparator());
						for (TError lexicalError : lexicalErrors) {
							combinedText.append("Lexical Error: ").append(lexicalError.getDescription())
									.append(" at Line ").append(lexicalError.getLine())
									.append(", Column ").append(lexicalError.getColumn())
									.append(System.lineSeparator());
						}

						// Add a subdivision between Lexical and Grammatical Errors
						combinedText.append(System.lineSeparator()).append("=== Grammatical Errors ===")
								.append(System.lineSeparator());

						// Display Grammatical Errors
						for (TError grammaticalError : grammaticalErrors) {
							combinedText.append("Grammatical Error: ").append(grammaticalError.getDescription())
									.append(" at Line ").append(grammaticalError.getLine())
									.append(", Column ").append(grammaticalError.getColumn())
									.append(System.lineSeparator());
						}

						parser.clearTable();
						lexer.clearTable();

						errorLog.setText(combinedText.toString());

					} catch (Exception ex) {
						ex.printStackTrace();
					}

				} else {
					System.out.println("Using Spotify table");

					Lexer lexer = new Lexer(new BufferedReader(new StringReader(text)));
					Parser parser = new Parser(lexer);

					try {
						parser.parse();

						String[] listData = (parser.result != null) ? parser.result.split(System.lineSeparator())
								: new String[] { "null" };
						output.setListData(listData);
						// Clear the JTextArea
						errorLog.setText("");

						LinkedList<TError> lexicalErrors = parser.SyntaxErrorTable;
						LinkedList<TError> grammaticalErrors = lexer.LexicalErrorTable;

						StringBuilder combinedText = new StringBuilder();

						// Display Lexical Errors
						combinedText.append("=== Lexical Errors ===").append(System.lineSeparator());
						for (TError lexicalError : lexicalErrors) {
							combinedText.append("Lexical Error: ").append(lexicalError.getDescription())
									.append(" at Line ").append(lexicalError.getLine())
									.append(", Column ").append(lexicalError.getColumn())
									.append(System.lineSeparator());
						}

						// Add a subdivision between Lexical and Grammatical Errors
						combinedText.append(System.lineSeparator()).append("=== Grammatical Errors ===")
								.append(System.lineSeparator());

						// Display Grammatical Errors
						for (TError grammaticalError : grammaticalErrors) {
							combinedText.append("Grammatical Error: ").append(grammaticalError.getDescription())
									.append(" at Line ").append(grammaticalError.getLine())
									.append(", Column ").append(grammaticalError.getColumn())
									.append(System.lineSeparator());
						}

						parser.clearTable();
						lexer.clearTable();

						errorLog.setText(combinedText.toString());

					} catch (Exception ex) {
						ex.printStackTrace();
					}

				}
			}
		});

		btnNewButton.setBounds(570, 18, 99, 38);
		panel_1.add(btnNewButton);

		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Instructions", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(4, 296, 679, 376);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setAutoscrolls(true);
		panel_2.add(scrollPane_2, BorderLayout.CENTER);
		scrollPane_2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scrollPane_2.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane_2.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPane_2.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		help = new JTextArea();
		scrollPane_2.setViewportView(help);
		help.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		help.setAlignmentY(Component.TOP_ALIGNMENT);
		help.setAlignmentX(Component.LEFT_ALIGNMENT);
		help.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		help.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 13));
		help.setLineWrap(true);
		help.setWrapStyleWord(true);
		help.setTabSize(3);
		help.setText(
				"To utilize the search engine effectively, adhere to the following rules:\r\n\r\nAccepted Input Criteria:\r\n•\tField Names: As declared in the database separators.\r\n•\tComparison Operators: =, >, <, >=, <=\r\n•\tString Values: Enclosed in double quotes.\r\n•\tNumerical Values: Integers exclusively.\r\n\r\nConstructing a Search:\r\nTo ensure the acceptance of your query, follow these syntax guidelines:\r\n•\tCommence with a Field: Begin with the specification of a field (case-insensitive).\r\n•\tSelect the Operator (if Numeric): For numeric queries, choose the appropriate operator. For non-numeric queries, omit this step.\r\n•\tSpecify the Value: Depending on the nature of the query, provide the value in adherence to the type (String or Number).\r\n\r\nFor instance, consider the query:\r\n\r\nSongName \"Blinding Lights\" or I want the song called \"blinding lights\"  (note that in this case the words \"I\", \"want\", \"the\", \"called\" are ignored)\r\n\r\nThis query searches if the song \"Blinding Lights\" is listed on the database.\r\n\r\nThis syntax enables users to formulate precise queries for retrieving information about songs within the top 100 on Spotify, based on diverse criteria. Alternatively, users may opt to search for songs within another database containing 954 songs.\r\n\r\nYou can make multiple requests, but you cannot mix multiple fields or keywords like: \r\n\r\nI want a song that belongs to the album \"After Hours\", because \'song\' and \'album\' are both keywords.\r\n\r\nAdditional tip: If you specify \"ALL\" as the target value, the program will retrieve all records from the database.");
		help.setEditable(false);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Use a bigger database?");
		chckbxNewCheckBox.setToolTipText("Use a bigger database?");
		chckbxNewCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				System.out.println("Changing table...");
				biggerDatabaseBoolean = chckbxNewCheckBox.isSelected();

				if (biggerDatabaseBoolean) {
					errorLog_1.setText(
							"We welcome you to our music database search system. To facilitate your experience, we provide the following search criteria:\r\n\r\nSONG_NAME: Name of the song. (String **some names may be corrupted**)\r\nARTIST: Name of the artist. (String **some names may be corrupted**)\r\nNUMBER: Number of the song on the database. (Integer)\r\nARTIST_COUNT: Number of artists associated with the song. (Integer)\r\nRELEASED_YEAR: Release year. (Integer)\r\nRELEASED_MONTH: Release month. (Integer)\r\nRELEASED_DAY: Release day. (Integer)\r\nSTREAMS: Total number of views. (Integer)\r\nBPM: Beats per minute. (Integer with 2 or 3 digits)\r\nKEY: Key of the song. (String: C, G, D, A, E, B, F, C#, G#, D#, A#, F#)\r\nMODE: Song mode. (Major or Minor)\r\nDANCEABILITY: Percentage of danceability. (Integer 0 to 100)\r\nVALENCE: Percentage of \"positiveness\" (Integer 0 to 100).\r\nENERGY: Percentage of energy. (Integer 0 to 100)\r\nACOUSTICNESS: Percentage of acoustics. (Integer 0 to 100)\r\nINSTRUMENTALNESS: Percentage of instrumentality. (Integer 0 to 100)\r\nLIVENESS: Percentage of liveliness. (Integer 0 to 100)\r\nSPEECHINESS: Percentage of speech. (Integer 0 to 100)\r\n\r\nPlease note that the IN_SPOTIFY_PLAYLISTS, IN_SPOTIFY_CHARTS, IN_APPLE_PLAYLISTS, IN_APPLE_CHARTS, IN_DEEZER_PLAYLISTS, IN_DEEZER_CHARTS, and IN_SHAZAM_CHARTS fields are not enabled for searches.");
				} else {
					errorLog_1.setText(
							"We welcome you to our music database search system. To facilitate your experience, we provide the following search criteria:\r\n\r\nSONG_NAME: Name of the song. (String)\r\nARTIST: Name of the artist. (String)\r\nALBUM: Name of the album. (String)\r\nNUMBER: Number of the song on the database. (Integer 1 to 100)\r\nMILLION_STREAMS: Number of millions of views. (Integer from 0 to 4)\r\n\r\nPlease note that the RELEASE_DATE field is not enabled for searches.");
				}

				SwingUtilities.invokeLater(() -> {
					JScrollBar verticalScrollBar2 = scrollPane_3.getVerticalScrollBar();
					verticalScrollBar2.setValue(0);
				});
			}
		});

		chckbxNewCheckBox.setBounds(693, 53, 141, 31);
		frame.getContentPane().add(chckbxNewCheckBox);

		JTextArea txtrWarningThisDatabase = new JTextArea();
		txtrWarningThisDatabase.setEditable(false);
		txtrWarningThisDatabase.setFont(new Font("Roboto", Font.PLAIN, 13));
		txtrWarningThisDatabase.setOpaque(false);
		txtrWarningThisDatabase.setForeground(Color.WHITE);
		txtrWarningThisDatabase.setLineWrap(true);
		txtrWarningThisDatabase.setWrapStyleWord(true);
		txtrWarningThisDatabase.setText(
				"Warning, this database is bigger, but some values may be incomplete or incorrect due to the way they were compiled (kaggle).");
		txtrWarningThisDatabase.setBounds(840, 48, 415, 38);
		frame.getContentPane().add(txtrWarningThisDatabase);

		panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Database separators", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(693, 111, 563, 174);
		frame.getContentPane().add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		scrollPane_3 = new JScrollPane();
		panel_4.add(scrollPane_3, BorderLayout.CENTER);

		errorLog_1 = new JTextPane();
		errorLog_1.setToolTipText("This shows the column separators from the database");
		errorLog_1.setText(
				"We welcome you to our music database search system. To facilitate your experience, we provide the following search criteria:\r\n\r\nSONG_NAME: Name of the song. (String)\r\nARTIST: Name of the artist. (String)\r\nALBUM: Name of the album. (String)\r\nNUMBER: Number of the song on the album. (Integer)\r\nMILLION_STREAMS: Number of millions of views. (Integer)\r\n\r\nPlease note that the RELEASE_DATE field is not enabled for searches.");
		errorLog_1.setEditable(false);
		scrollPane_3.setViewportView(errorLog_1);

		JTextPane textPane = new JTextPane();
		textPane.setForeground(Color.WHITE);
		textPane.setOpaque(false);
		textPane.setText("𝕊𝕡𝕠𝕥𝕚𝕗𝕪 𝕊𝕠𝕟𝕘 𝕊𝕖𝕒𝕣𝕔𝕙𝕖𝕣");
		textPane.setBounds(1115, 653, 141, 19);
		frame.getContentPane().add(textPane);

		SwingUtilities.invokeLater(() -> {
			JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
			JScrollBar verticalScrollBar1 = scrollPane_2.getVerticalScrollBar();
			JScrollBar verticalScrollBar2 = scrollPane_3.getVerticalScrollBar();
			verticalScrollBar.setValue(0);
			verticalScrollBar1.setValue(0);
			verticalScrollBar2.setValue(0);
		});
	}

	private void copyToClipboard(String text) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection selection = new StringSelection(text);
		clipboard.setContents(selection, null);
	}

	// TODO
	// public static class ConsoleCapture {
	// public static void main(String[] args) {
	// // Create a new PrintStream that writes to a ByteArrayOutputStream
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// PrintStream customOut = new PrintStream(baos);

	// // Redirect System.out to the custom PrintStream
	// System.setOut(customOut);

	// // Retrieve the captured output
	// String capturedOutput = baos.toString();
	// System.out.println("Captured output:\n" + capturedOutput);
	// }
	// }
}
