package com.ucjc;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.StringReader;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import com.ucjc.compiled.generated.Lexer;
import com.ucjc.compiled.generated.Parser;

import javax.swing.border.EtchedBorder;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ApplicationWIndow {

	private JFrame frame;
	private JTextField txtEnterYourSearch;
	private JTextField txtOutput;
	private JScrollBar scrollBar;
	private JPanel panel;
	private JPanel panel_1;
	private JScrollBar scrollBar_1;
	private JPanel panel_2;
	private JTextField txtErrorLog;
	private JPanel panel_3;
	private JScrollBar scrollBar_2;
	private JButton btnNewButton;

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
					parser.parse();
					txtOutput.setText(parser.result);
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});
		btnNewButton.setBounds(570, 18, 99, 38);
		panel_1.add(btnNewButton);

		panel_2 = new JPanel();
		panel_2.setBounds(10, 285, 1246, 387);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);

		JTextArea txtUsage = new JTextArea();
		txtUsage.setBounds(0, 0, 1228, 387);
		panel_2.add(txtUsage);
		txtUsage.setFont(new Font("Bodoni MT", Font.BOLD, 13));
		txtUsage.setTabSize(3);
		txtUsage.setWrapStyleWord(true);
		txtUsage.setText(
				"To use this search engine, follow these rules:\r\nAccepted Words/Values:\r\n•\tField names: SONG_NAME, ARTIST, ALBUM, RELEASE_DATE, NUMBER, MILLION_STREAMS\r\n•\tComparison operators: =, >, <, >=, <=\r\n•\tString values: Enclosed in double quotes.\r\n•\tNumerical values: Integer or decimal.\r\nHow to Form a Search:\r\n1.\tStart with a field (case-insensitive).\r\n2.\tSpecify the value:\r\n\t•\tFor SONG_NAME, ARTIST, ALBUM, RELEASE_DATE: Use '=' operator with double quotes for the string.\r\n\t•\tFor NUMBER and MILLION_STREAMS: Use any operator with a number (integer or decimal).\r\n\t\t•\tFor NUMBER, use an integer from 1 to 100.\r\n\t\t•\tFor MILLION_STREAMS and '=', be specific, as an exact match is necessary.\r\n\r\nExample:\r\n\r\nSongName=\"Blinding Lights\" \r\n\r\nThis query searches if the song \"Blinding Lights\" is within the top 100 most listened songs on Spotify.\r\n\r\nThis grammar enables users to construct queries for searching songs in the top 100 on Spotify based on various criteria.\r\n\r\nThis grammar allows users to construct queries for searching if a song is inside the top 100 most listened songs in Spotify, based on different criteria.");
		txtUsage.setLineWrap(true);
		txtUsage.setEditable(false);

		scrollBar_1 = new JScrollBar();
		scrollBar_1.setBounds(1229, 0, 17, 387);
		panel_2.add(scrollBar_1);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Output",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(4, 107, 679, 178);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		txtOutput = new JTextField();
		txtOutput.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String textToCopy = txtOutput.getText();
				copyToClipboard(textToCopy);
			}
		});
		txtOutput.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		txtOutput.setEditable(false);
		txtOutput.setBounds(6, 16, 640, 156);
		panel.add(txtOutput);
		txtOutput.setColumns(10);

		scrollBar = new JScrollBar();
		scrollBar.setBounds(656, 16, 17, 151);
		panel.add(scrollBar);

		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Error log",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(700, 110, 546, 177);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);

		txtErrorLog = new JTextField();
		txtErrorLog.setEditable(false);
		txtErrorLog.setBounds(10, 27, 505, 130);
		panel_3.add(txtErrorLog);
		txtErrorLog.setColumns(10);

		scrollBar_2 = new JScrollBar();
		scrollBar_2.setBounds(519, 32, 17, 125);
		panel_3.add(scrollBar_2);
	}

	private void copyToClipboard(String text) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection selection = new StringSelection(text);
		clipboard.setContents(selection, null);
	}
}
