package com.ucjc;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListModel;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import com.ucjc.compiled.generated.Lexer;
import com.ucjc.compiled.generated.Parser;
import com.ucjc.utils.TError;

import javax.swing.border.EtchedBorder;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JTextPane;

public class ApplicationWIndow {

	private JFrame frame;
	private JTextField txtEnterYourSearch;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_3;
	private JButton btnNewButton;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JTextArea help;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationWIndow window = new ApplicationWIndow();
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
	public ApplicationWIndow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Input",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(4, 33, 679, 67);
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
		panel.setBounds(4, 107, 679, 178);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 659, 156);
		panel.add(scrollPane);

		JList output = new JList();
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

		scrollPane.setViewportView(output);

		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Error log",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(693, 107, 546, 178);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 526, 155);
		panel_3.add(scrollPane_1);

		JTextPane errorLog = new JTextPane();
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
				Lexer lexer = new Lexer(new BufferedReader(new StringReader(text)));
				Parser parser = new Parser(lexer);

				try {
					// Store the previous error counts
					int prevLexicalErrorCount = parser.SyntaxErrorTable.size();
					int prevGrammaticalErrorCount = lexer.LexicalErrorTable.size();

					parser.parse();

					String[] listData = (parser.result != null) ? parser.result.split(System.lineSeparator())
							: new String[] { "null" };
					output.setListData(listData);

					// Get the current error counts
					int currentLexicalErrorCount = parser.SyntaxErrorTable.size();
					int currentGrammaticalErrorCount = lexer.LexicalErrorTable.size();

					// Check if new entries exist before updating the error log
					if (currentLexicalErrorCount > prevLexicalErrorCount
							|| currentGrammaticalErrorCount > prevGrammaticalErrorCount) {
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

						errorLog.setText(combinedText.toString());
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});
		btnNewButton.setBounds(570, 18, 99, 38);
		panel_1.add(btnNewButton);

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scrollPane_2.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane_2.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPane_2.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		scrollPane_2.setBounds(4, 296, 1252, 376);
		frame.getContentPane().add(scrollPane_2);

		SwingUtilities.invokeLater(() -> {
			JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
			verticalScrollBar.setValue(0);
		});

		help = new JTextArea();
		help.setAlignmentY(Component.TOP_ALIGNMENT);
		help.setAlignmentX(Component.LEFT_ALIGNMENT);
		help.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		help.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 13));
		help.setLineWrap(true);
		help.setWrapStyleWord(true);
		help.setTabSize(3);
		help.setText(
				"To use this search engine, follow these rules:\r\n\r\nAccepted Words/Values:\r\n•\tField names: SONG_NAME, ARTIST, ALBUM, RELEASE_DATE, NUMBER, MILLION_STREAMS\r\n•\tComparison operators: =, >, <, >=, <=\r\n•\tString values: Enclosed in double quotes.\r\n•\tNumerical values: Integer or decimal.\r\n\r\nHow to Form a Search:\r\n1.\tStart with a field (case-insensitive).\r\n2.\tSpecify the value:\r\n\t•\tFor SONG_NAME, ARTIST, ALBUM, RELEASE_DATE: Use '=' operator with double quotes for the string.\r\n\t•\tFor NUMBER and MILLION_STREAMS: Use any operator with a number (integer or decimal).\r\n\t\t•\tFor NUMBER, use an integer from 1 to 100.\r\n\t\t•\tFor MILLION_STREAMS and '=', be specific, as an exact match is necessary.\r\n\r\nExample:\r\n\r\nSongName=\"Blinding Lights\" \r\n\r\nThis query searches if the song \"Blinding Lights\" is within the top 100 most listened songs on Spotify.\r\n\r\nThis grammar enables users to construct queries for searching songs in the top 100 on Spotify based on various criteria.\r\n\r\nAdditional tip: If you specify \"ALL\" as the target value, the program will retrieve all records from the database.");
		help.setEditable(false);
		scrollPane_2.setViewportView(help);
	}

	private void copyToClipboard(String text) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection selection = new StringSelection(text);
		clipboard.setContents(selection, null);
	}
}
