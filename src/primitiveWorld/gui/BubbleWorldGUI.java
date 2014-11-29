/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package primitiveWorld.gui;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import primitiveWorld.engine.Engine;
import primitiveWorld.interfaces.Enginable;

/**
 *
 * @author drnewman
 */
public class BubbleWorldGUI extends JFrame {

	Enginable PrimitiveWorld = null;
	JPanel PrimitivePanel = new JPanel();
	Timer timer = null;
	JTextField fileNameInput = new JTextField();
	JButton loadLocationButton = new JButton("Load location");
	JButton nextStepButton = new JButton("Step");
	JButton redrawButton = new JButton("Redraw");
	JButton nextStepAndRedrawButton = new JButton("Step+Redraw");
	JButton startTimerButton = new JButton("Start timer");

	BubbleWorldGUI() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(1000, 620);
		this.setTitle("BubbleWorld");
		this.setVisible(true);
		PrimitivePanel.setLocation(10, 10);
		PrimitivePanel.setSize(800, 600);
		this.add(PrimitivePanel);
		PrimitivePanel.setBackground(Color.WHITE);
		PrimitivePanel.setVisible(true);
		PrimitiveWorld = new Engine();
		PrimitiveWorld.setPanel(PrimitivePanel);
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
		fileNameInput.setLocation(820, 10);
		fileNameInput.setSize(150, 20);
		fileNameInput.setText("example.location");
		this.add(fileNameInput);
		fileNameInput.setVisible(true);
		loadLocationButton.setLocation(820, 40);
		loadLocationButton.setSize(150, 20);
		this.add(loadLocationButton);
		loadLocationButton.setVisible(true);
		loadLocationButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						PrimitiveWorld.loadLocation(new File(fileNameInput
								.getText()));
					}
				});
		nextStepButton.setLocation(820, 70);
		nextStepButton.setSize(150, 20);
		this.add(nextStepButton);
		nextStepButton.setVisible(true);
		nextStepButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println(PrimitiveWorld.nextStep());
			}
		});
		redrawButton.setLocation(820, 100);
		redrawButton.setSize(150, 20);
		this.add(redrawButton);
		redrawButton.setVisible(true);
		redrawButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				PrimitiveWorld.redraw();
			}
		});
		nextStepAndRedrawButton.setLocation(820, 130);
		nextStepAndRedrawButton.setSize(150, 20);
		this.add(nextStepAndRedrawButton);
		nextStepAndRedrawButton.setVisible(true);
		nextStepAndRedrawButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						System.out.println(PrimitiveWorld.nextStep());
						PrimitiveWorld.redraw();
					}
				});
		timer = new Timer(200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// System.out.println(PrimitiveWorld.nextStep());
				PrimitiveWorld.nextStep();
				PrimitiveWorld.redraw();
			}
		});
		startTimerButton.setLocation(820, 160);
		startTimerButton.setSize(150, 20);
		this.add(startTimerButton);
		startTimerButton.setVisible(true);
		startTimerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (timer.isRunning()) {
					timer.stop();
					startTimerButton.setText("Start timer");
				} else {
					timer.start();
					startTimerButton.setText("Stop timer");
				}
			}
		});
		// auto-load default location and draw
		PrimitiveWorld.loadLocation(new File(fileNameInput.getText()));
		PrimitiveWorld.redraw();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		BubbleWorldGUI GUI = new BubbleWorldGUI();
		// GUI.setVisible(true);
		// GUI.PrimitiveWorld.setGraphics(GUI.PrimitivePanel.getGraphics());
	}
}
