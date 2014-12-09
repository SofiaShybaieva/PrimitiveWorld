/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package primitiveWorld.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import primitiveWorld.engine.Engine;
import primitiveWorld.interfaces.Enginable;
import primitiveWorld.interfaces.Visible;

/**
 *
 * @author drnewman
 */
public class BubbleWorldGUI extends JFrame implements KeyListener {

	protected static final String aboutString = "Made by Totally Spies Team";
	Enginable PrimitiveWorld = null;
	JPanel PrimitivePanel = new JPanel();
	Timer timer = null;

	JMenuBar menuBar = new JMenuBar();
	private LinkedList<String> recentFileNames = null;
	private BubbleWorldGUI that = this;
	private JMenu subMenu = null;
	private boolean isPaused = true;
	JTextArea resultTextArea;
	JPanel panel = new JPanel();
	private String welcome = "Primitive World 1.2\n\n";

	BubbleWorldGUI() {

		// reading recent file list
		try {
			this.loadRecentFileList();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		this.setSize(800, 620);
		this.setTitle("BubbleWorld");
		this.setJMenuBar(this.createMenuBar());
		this.setVisible(true);
		this.setFocusable(true);
		PrimitivePanel.setLocation(0, 0);
		PrimitivePanel.setSize(600, 600);
		PrimitivePanel.setPreferredSize(new Dimension(600, 600));
		PrimitivePanel.setBackground(Color.WHITE);
		PrimitivePanel.setVisible(true);

		PrimitivePanel.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent evt) {
				PrimitiveWorld.mousePress(new Point(evt.getX(), evt.getY()));
			}
		});
		PrimitivePanel
				.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
					@Override
					public void mouseMoved(java.awt.event.MouseEvent evt) {
						PrimitiveWorld.mouseMove(new Point(evt.getX(), evt
								.getY()));
					}
				});

		// PrimitivePanel.setMinimumSize(new Dimension(600, 600));
		this.add(PrimitivePanel);

		PrimitiveWorld = new Engine();
		PrimitiveWorld.setPanel(PrimitivePanel);

		this.addKeyListener(this);
		BubbleWorldGUI that = this;
		timer = new Timer(500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// System.out.println(PrimitiveWorld.nextStep());

				String result = PrimitiveWorld.nextStep();
				if (!result.isEmpty()) {
					that.resultTextArea.append(result + "\n");
					displayMessage(result);
				}
				PrimitiveWorld.redraw();
				that.panel.invalidate();
				if (result.contains("Victory") || result.contains("Defeat")) {
					that.timer.stop();
					that.resultTextArea.append("Game Over!\n");
				}

			}

			private void displayMessage(String result) {
				JOptionPane.showMessageDialog(that, result);
			}
		});

		addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				askAndExit();

			}
		});

		this.resultTextArea = new JTextArea(this.welcome, 125, 17);
		this.resultTextArea.setCaretPosition(this.resultTextArea.getDocument()
				.getLength());
		this.resultTextArea.setEditable(false);

		this.resultTextArea.setLineWrap(true);
		this.resultTextArea.setMinimumSize(new Dimension(200, 600));
		this.resultTextArea.setVisible(true);
		// this.setLocation(600,10);
		// this.setSize(200,600);
		// this.setPreferredSize(new Dimension (200,600));
		// this.add(this.resultTextArea);

		panel.setLocation(600, 0);
		panel.setSize(200, 600);
		panel.setPreferredSize(new Dimension(200, 600));
		panel.add(resultTextArea);
		panel.setVisible(true);
		this.add(panel);

	}

	protected void askAndExit() {
		System.exit(0);
		if (JOptionPane.showConfirmDialog(null,
				"Are you sure to close this window?", "Really Closing?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			try {
				that.saveRecentFileList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}
	}

	public JMenuBar createMenuBar() {

		JMenu menu, submenu;
		JMenuItem menuItem;

		BubbleWorldGUI that = this;

		// Build the first menu.
		menu = new JMenu("Primitive World");
		menu.setMnemonic(KeyEvent.VK_P);
		menu.getAccessibleContext().setAccessibleDescription(
				"Primitive World main menu");

//		menu.addMenuListener(new MenuListener() {
//			private boolean wasRunning = false;
//
//			@Override
//			public void menuSelected(MenuEvent e) {
//				wasRunning = false;
//				if (that.timer.isRunning()) {
//					wasRunning = true;
//					that.timer.stop();
//
//				}
//			}
//
//			@Override
//			public void menuDeselected(MenuEvent e) {
//				if (wasRunning) {
//					that.timer.start();
//
//				}
//				
//			}
//
//			@Override
//			public void menuCanceled(MenuEvent e) {
//				if (wasRunning) {
//					that.timer.start();
//
//				}  
//				
//			}
//		});

		menuBar.add(menu);

		// a group of JMenuItems
		menuItem = new JMenuItem("About...", KeyEvent.VK_A);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				that.onAbout();
			}
		});
		menu.add(menuItem);

		menuItem = new JMenuItem("Load Location...", KeyEvent.VK_L);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				that.onLoadLocation();
			}
		});
		menu.add(menuItem);

		// a submenu
		menu.addSeparator();

		if (this.recentFileNames.isEmpty()) {

		} else {
			submenu = new JMenu("Recent Locations");
			submenu.setMnemonic(KeyEvent.VK_R);
			int i = 1;
			for (String fileName : this.recentFileNames) {
				menuItem = new JMenuItem(fileName, KeyEvent.VK_1 + i);
				menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1
						+ i, ActionEvent.ALT_MASK));
				menuItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						that.onLoadLocation(e.getActionCommand());
						// System.err.println(e.getActionCommand());
					}
				});
				submenu.add(menuItem);
				i++;
				if (i > 5)
					break;
			}
			menu.add(submenu);
			this.subMenu = submenu;
		}

		menuItem = new JMenuItem("Exit", KeyEvent.VK_E);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				askAndExit();
			}
		});
		menu.add(menuItem);

		// Build second menu in the menu bar.
		// menu = new JMenu("Another Menu");
		// menu.setMnemonic(KeyEvent.VK_N);
		// menu.getAccessibleContext().setAccessibleDescription(
		// "This menu does nothing");
		// menuBar.add(menu);

		return menuBar;
	}

	private void loadRecentFileList() throws IOException {
		if (this.recentFileNames == null)
			this.recentFileNames = new LinkedList<String>();
		this.recentFileNames.clear();

		// System.err.println("Loading recent files");
		File recentFile = new File("PrimitiveWorld.recent");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(recentFile));
			String line = null;
			while ((line = br.readLine()) != null) {
				// System.err.println(line);
				this.recentFileNames.add(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			this.recentFileNames.clear();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			this.recentFileNames.clear();
		}

	}

	private void saveRecentFileList() throws IOException {

		if (this.recentFileNames == null)
			return;
		// System.err.println("Saving recent files");

		File recentFile = new File("PrimitiveWorld.recent");
		BufferedWriter br = null;
		try {
			br = new BufferedWriter(new FileWriter(recentFile));
			// loop the list in reverse to save the last 5 file names
			for (int i = this.recentFileNames.size() - 1; i >= 0; i--) {
				final String each = (String) this.recentFileNames.get(i);
				br.write(each);
				br.newLine();
				// System.err.println(each);
				if (this.recentFileNames.size() - i > 5)
					break;
			}
			// for (String f : this.recentFileNames) {
			// br.write(f);
			// br.newLine();
			// System.err.println(f);
			// }

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	protected void onLoadLocation() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter(
				"Primitive World 1.2 Location File", "location"));
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			this.recentFileNames.add(file.getAbsolutePath());
			this.menuBar.updateUI();
			PrimitiveWorld.loadLocation(file);
			PrimitiveWorld.redraw();
			this.resultTextArea.setText(this.welcome);
			if (!timer.isRunning())
				timer.start();
		}
	}

	protected void onLoadLocation(String fileName) {
		File file = new File(fileName);
		this.recentFileNames.add(fileName);
		this.menuBar.updateUI();
		PrimitiveWorld.loadLocation(file);
		PrimitiveWorld.redraw();
		this.resultTextArea.setText(this.welcome);
		if (!timer.isRunning())
			timer.start();
	}

	protected void onAbout() {
		Window parentWindow = SwingUtilities.windowForComponent(this);
		JDialog dialog = new JDialog(parentWindow, "About PrimitiveWorld");
		// dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		JPanel pane = new JPanel(new BorderLayout());
		JLabel label = new JLabel(this.aboutString);
		label.setLocation(20, 20);
		pane.setLocation(20, 20);
		pane.add(label);
		dialog.add(pane);
		int x = this.getSize().width / 2 - 200;
		int y = this.getSize().height / 2 - 150;
		dialog.setLocation(x, y);
		dialog.setMinimumSize(new Dimension(400, 300));
		dialog.setResizable(false);
		dialog.pack();

		dialog.setVisible(true);

	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {

		BubbleWorldGUI GUI = new BubbleWorldGUI();
		// GUI.setVisible(true);
		// GUI.PrimitiveWorld.setGraphics(GUI.PrimitivePanel.getGraphics());

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		// javax.swing.SwingUtilities.invokeLater(new Runnable() {
		// public void run() {
		// createAndShowGUI();
		// }
		//
		// });

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		// pause/resume on space press
		if (e.getKeyCode() == 32) {
			if (timer.isRunning()) {
				timer.stop();

			} else {
				timer.start();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
