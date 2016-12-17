/*
* Simple-ADB tool , a tool made to make the process of using adb/fastboot simpler, with GUI
* Copyright (C) 2016 mhashem6 > (Muhammad Hashim)
* 
* This program is free software: you can redistribute it and/or modify it under
* the terms of the GNU General Public License as published by the Free Software
* Foundation, either version 3 of the License, or (at your option) any later
* version.
* 
* This program is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
* details.
* 
* You should have received a copy of the GNU General Public License along with
* this program. If not, see <http://www.gnu.org/licenses/>.
* 
* for additional informations you can visit the main thread of this program > http://forum.xda-developers.com/android/software/revive-simple-adb-tool-t3417155
* you can contact me @ abohashem.com@gmail.com
* Source : https://sourceforge.net/p/sadb/
*
*/

package mhashem6.sadb.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mhashem6.commander.Command;
import mhashem6.commander.Executer;
import mhashem6.commander.exceptions.*;

import mhashem6.sadb.SideKick;
import mhashem6.sadb.exceptions.FileFailException;

@SuppressWarnings("serial")
public final class MainUI extends JFrame {

	private ImageIcon defaultIcon;

	private JPanel decisionPanel;
	private JPanel pushPanel;
	private JPanel uninstallPanel;
	private JPanel pcPanel;
	private JPanel phonePanel;
	private JPanel commandPanel;
	private JPanel outputPanel;

	private JMenuBar menubar;
	private JMenu menu;

	private JMenuItem save;
	private JMenuItem reset;
	private JMenuItem help;
	private JMenuItem donate;
	private JMenuItem about;
	private JMenuItem source;

	private JRadioButton adbRadio;
	private JRadioButton fastbootRadio;
	private JRadioButton commandRadio;

	private CommandsBox combo;

	private JTextField txt1;
	private JTextField txt2;
	private JTextField txt3;
	private JTextField txt4;
	private JTextField txt5;

	private JButton browseBtn;
	private JButton browseBtn2;
	private JButton activateBtn;
	private JButton abortBtn;
	private ButtonEvent buttonEvent;

	private mhashem6.commander.Appender textArea;

	private JScrollPane scroll;

	private Command command;
	private Executer executer;

	public MainUI() {

		// set frame options
		setTitle("Simple-ADB");
		defaultIcon = new ImageIcon(MainUI.class.getResource("/icon.png"));
		setIconImage(defaultIcon.getImage());
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// ======================================================================================

		initFields();

		// frame finish
		initLayout();
		resetFrame();
		// ======================================================================================

	}

	/************** one method to initialize them all ******************/
	private void initFields() {

		/***** buttons events ****/
		buttonEvent = new ButtonEvent();
		// ======================================================================================

		// Menus
		menubar = new JMenuBar();

		menu = new JMenu("•options");

		save = new JMenuItem("Save output");
		save.addActionListener(buttonEvent);
		save.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));

		reset = new JMenuItem("Reset frame");
		reset.addActionListener(buttonEvent);
		reset.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));

		help = new JMenuItem("Help");
		help.addActionListener(buttonEvent);
		help.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));

		donate = new JMenuItem("Donate");
		donate.setForeground(Color.decode("#AC193D"));
		donate.addActionListener(buttonEvent);

		about = new JMenuItem("About");
		about.addActionListener(buttonEvent);

		source = new JMenuItem("Source");
		source.addActionListener(buttonEvent);

		menu.add(save);
		menu.add(reset);
		menu.add(help);
		menu.add(about);
		menu.add(source);
		menu.add(donate);

		menubar.add(menu);
		menubar.setBackground(Color.white);
		setJMenuBar(menubar);
		// ======================================================================================

		// decision panel
		decisionPanel = new JPanel();

		RadioEvent radioEvent = new RadioEvent();

		adbRadio = new JRadioButton("ADB", true);
		adbRadio.setFocusable(false);

		adbRadio.addItemListener(radioEvent);

		fastbootRadio = new JRadioButton("Fastboot");
		fastbootRadio.setFocusable(false);

		fastbootRadio.addItemListener(radioEvent);

		commandRadio = new JRadioButton("Your own command");
		commandRadio.setFocusable(false);

		commandRadio.addItemListener(radioEvent);

		ButtonGroup group = new ButtonGroup();
		group.add(adbRadio);
		group.add(fastbootRadio);
		group.add(commandRadio);

		combo = new CommandsBox();
		combo.addItemListener(new comboevent());
		// ======================================================================================

		// push/install/flash panel
		pushPanel = new JPanel();

		txt1 = new JTextField();

		browseBtn = new JButton("/...");
		browseBtn.addActionListener(buttonEvent);
		// ======================================================================================

		// un-install panel
		uninstallPanel = new JPanel();

		txt2 = new JTextField();
		// ======================================================================================

		// pc panel
		pcPanel = new JPanel();

		txt3 = new JTextField();

		browseBtn2 = new JButton("/...");
		browseBtn2.addActionListener(buttonEvent);
		// ======================================================================================

		// phone panel
		phonePanel = new JPanel();

		txt4 = new JTextField();
		// ======================================================================================

		// command panel
		commandPanel = new JPanel();

		txt5 = new JTextField();
		txt5.addActionListener(e -> {

			fire();
		});
		// ======================================================================================

		// Activate button
		activateBtn = new JButton("Activate");
		activateBtn.setEnabled(false);
		activateBtn.setForeground(Color.decode("#AC193D"));
		activateBtn.addActionListener(buttonEvent);
		// ======================================================================================

		// Abort button
		abortBtn = new JButton("Abort");
		abortBtn.setEnabled(false);
		abortBtn.setForeground(Color.decode("#AC193D"));
		abortBtn.addActionListener(buttonEvent);
		// ======================================================================================

		// scroll-able textArea
		outputPanel = new JPanel();

		textArea = new OutputArea();
		scroll = new JScrollPane((JTextArea) textArea);
		// ======================================================================================

		command = new Command();
		command.setClient("adb");
		executer = new Executer(textArea);
	}

	private void initLayout() {

		decisionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Tough desicion"));
		javax.swing.GroupLayout decisionPanelLayout = new javax.swing.GroupLayout(decisionPanel);
		decisionPanel.setLayout(decisionPanelLayout);
		decisionPanelLayout
				.setHorizontalGroup(
						decisionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(decisionPanelLayout
										.createSequentialGroup().addContainerGap()
										.addGroup(decisionPanelLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(decisionPanelLayout.createSequentialGroup()
														.addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE,
																244, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(0, 0, Short.MAX_VALUE))
												.addGroup(decisionPanelLayout.createSequentialGroup()
														.addComponent(adbRadio).addGap(10, 10, 10)
														.addComponent(fastbootRadio).addGap(10, 10, 10).addComponent(
																commandRadio, javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
										.addContainerGap()));
		decisionPanelLayout.setVerticalGroup(decisionPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(decisionPanelLayout.createSequentialGroup().addContainerGap()
						.addGroup(decisionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(adbRadio).addComponent(fastbootRadio).addComponent(commandRadio))
						.addGap(14, 14, 14)
						.addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		pushPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("A file to install / flash / push"));

		javax.swing.GroupLayout pushPanelLayout = new javax.swing.GroupLayout(pushPanel);
		pushPanel.setLayout(pushPanelLayout);
		pushPanelLayout
				.setHorizontalGroup(
						pushPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(pushPanelLayout.createSequentialGroup().addContainerGap()
										.addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 198,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(browseBtn, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addContainerGap()));
		pushPanelLayout.setVerticalGroup(pushPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pushPanelLayout.createSequentialGroup().addContainerGap()
						.addGroup(pushPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(browseBtn))
						.addContainerGap()));

		uninstallPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("APK <package> name to uninstall"));

		javax.swing.GroupLayout uninstallPanelLayout = new javax.swing.GroupLayout(uninstallPanel);
		uninstallPanel.setLayout(uninstallPanelLayout);
		uninstallPanelLayout
				.setHorizontalGroup(uninstallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(uninstallPanelLayout.createSequentialGroup().addContainerGap()
								.addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, 198,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		uninstallPanelLayout
				.setVerticalGroup(uninstallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(uninstallPanelLayout.createSequentialGroup().addContainerGap()
								.addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pcPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("A directory (in PC)"));

		javax.swing.GroupLayout pcPanelLayout = new javax.swing.GroupLayout(pcPanel);
		pcPanel.setLayout(pcPanelLayout);
		pcPanelLayout.setHorizontalGroup(pcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pcPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE, 194,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(browseBtn2)
						.addContainerGap()));
		pcPanelLayout.setVerticalGroup(pcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pcPanelLayout.createSequentialGroup().addContainerGap()
						.addGroup(pcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(browseBtn2))
						.addContainerGap()));

		phonePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("A directory (in phone)"));

		javax.swing.GroupLayout phonePanelLayout = new javax.swing.GroupLayout(phonePanel);
		phonePanel.setLayout(phonePanelLayout);
		phonePanelLayout
				.setHorizontalGroup(phonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(phonePanelLayout.createSequentialGroup().addContainerGap()
								.addComponent(txt4, javax.swing.GroupLayout.PREFERRED_SIZE, 196,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		phonePanelLayout
				.setVerticalGroup(phonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(phonePanelLayout.createSequentialGroup().addContainerGap()
								.addComponent(txt4, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));

		outputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Output"));

		scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		javax.swing.GroupLayout outputPanelLayout = new javax.swing.GroupLayout(outputPanel);
		outputPanel.setLayout(outputPanelLayout);
		outputPanelLayout
				.setHorizontalGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(outputPanelLayout.createSequentialGroup().addContainerGap()
								.addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
								.addContainerGap()));
		outputPanelLayout
				.setVerticalGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(scroll, javax.swing.GroupLayout.Alignment.TRAILING));

		commandPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Thy command shall be Excuted"));

		javax.swing.GroupLayout commandPanelLayout = new javax.swing.GroupLayout(commandPanel);
		commandPanel.setLayout(commandPanelLayout);
		commandPanelLayout
				.setHorizontalGroup(commandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(commandPanelLayout.createSequentialGroup().addContainerGap()
								.addComponent(txt5, javax.swing.GroupLayout.PREFERRED_SIZE, 191,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		commandPanelLayout
				.setVerticalGroup(commandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(commandPanelLayout.createSequentialGroup().addContainerGap()
								.addComponent(txt5, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
										javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
												.addComponent(activateBtn)
												.addGap(18, 18, 18)
												.addComponent(abortBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 68,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(97, 97, 97))
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(phonePanel, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(pcPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(uninstallPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(pushPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(decisionPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(commandPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
								.addComponent(outputPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(outputPanel,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addContainerGap())
						.addGroup(layout.createSequentialGroup()
								.addComponent(decisionPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(pushPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(uninstallPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(pcPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(phonePanel, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(commandPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(abortBtn).addComponent(activateBtn))
								.addGap(0, 45, Short.MAX_VALUE)))));
		pack();
	}

	/********* Helpful methods ***********/

	/** to disable the passed panel and all it's components */
	private void freezePanel(JPanel panel, boolean condition) {

		panel.setEnabled(!condition);

		Component[] com = panel.getComponents();

		for (int i = 0; i < com.length; i++) {

			if (!abortBtn.isEnabled())
				if (com[i] instanceof JTextField) {
					((JTextField) com[i]).setText("");
					com[i].requestFocus();
				}

			if (!com[i].equals(combo))
				com[i].setEnabled(!condition);
			else if (!commandRadio.isSelected())
				com[i].setEnabled(!condition);

		}
	}

	/** to reset the components */
	private void resetFrame() {

		preparePanel(pushPanel, true, "A file to install / flash / push");
		freezePanel(uninstallPanel, true);
		freezePanel(pcPanel, true);
		preparePanel(phonePanel, true, "A directory (in phone)");
		freezePanel(commandPanel, true);

		activateBtn.setEnabled(false);
		abortBtn.setEnabled(false);

		command.clear();
		executer.clearOutput();

	}

	/** to enable/disable the vital components during operations */
	private void operationOn(boolean freezeCondition) {

		abortBtn.setEnabled(freezeCondition);

		freezePanel(decisionPanel, freezeCondition);

		if (!commandRadio.isSelected())
			activateBtn.setEnabled(!freezeCondition);

		save.setEnabled(!freezeCondition);
		reset.setEnabled(!freezeCondition);
	}

	/** to enable/disable the components of passed panel temporarily */
	private void preparePanel(JPanel panel, boolean freezeCondition, String title) {

		panel.setBorder(BorderFactory.createTitledBorder(title));
		freezePanel(panel, freezeCondition);

	}

	/**
	 * the method that prepares to the execution; if command radio is enabled >
	 * then the input will be loaded with the text in the textArea as an input
	 * that will be executed. if text boxes are enabled, their contents of text
	 * will be added to the input that shall be executed.
	 * 
	 */
	private void fire() {
		operationOn(true);
		new Thread(() -> {

			try {

				if (txt5.isEnabled()) {
					// your own command.
					command.setCommand(txt5.getText().split(" "));
					executer.execute(command);
				}
				// =====================================================

				else {

					command.clearArguments();

					if (txt1.isEnabled())
						// is it install?
						command.appendArgument(txt1.getText());

					if (txt4.isEnabled())
						// oh it's push
						command.appendArgument(txt4.getText());

					else if (txt3.isEnabled() && txt4.isEnabled()) {
						// pull
						command.appendArgument(txt4.getText());
						command.appendArgument(txt3.getText());
					}

					else if (txt2.isEnabled())
						command.appendArgument(txt2.getText());

					executer.execute(command);
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(MainUI.this, e1.fillInStackTrace(), "Error", JOptionPane.ERROR_MESSAGE);

			} finally {

				operationOn(false);

			}
		}).start();

	}

	// ======================================================================================

	/**
	 * if adb radio is selected > combo box will be loaded with adb commands. if
	 * fastboot radio is selected > combo box will be loaded with fastboot
	 * commands. if Your own command is selected > combo will be disabled, and
	 * textArea will be editable.
	 */
	class RadioEvent implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {

			resetFrame();
			combo.setEnabled(true);

			if (e.getSource() == adbRadio) {

				((JTextArea) textArea).setBackground(Color.decode("#1C2021"));

				command.setClient("adb");

				combo.setComboElements(CommandsBox.ADB_ITEMS);
			}
			// =====================================================

			else if (e.getSource() == fastbootRadio) {

				((JTextArea) textArea).setBackground(Color.decode("#203040"));

				command.setClient("fastboot");

				combo.setComboElements(CommandsBox.FASTBOOT_ITEMS);

			}
			// =====================================================

			else if (e.getSource() == commandRadio) {

				command.setClient(null);

				txt5.requestFocus();
				combo.setEnabled(false);
				freezePanel(commandPanel, false);
				activateBtn.setEnabled(true);

				((JTextArea) textArea).setBackground(Color.decode("#0C545C")); // #7D1424
				txt5.setText("");
			}
			// =====================================================

		}

	}

	// ======================================================================================

	/** ComboBox ItemListener */
	class comboevent implements ItemListener {

		/**
		 * it will decide which panels will be enabled, based on the selected
		 * item, and load the Command input that shall be executed later.
		 */

		@Override
		public void itemStateChanged(ItemEvent e) {

			if (combo.getSelectedItem() != null) {

				resetFrame();
				activateBtn.setEnabled(true);

				if (combo.getSelectedItem().equals("rm /data/system/gesture.key")) {

					// command.setCommand(new String[] { "shell", "rm",
					// "/data/system/gesture.key" }); //for some reason it's not
					// working ?!!
					command.setCommand("shell");
					command.appendArgument("rm");
					command.appendArgument("/data/system/gesture.key");

				}
				// =====================================================

				else if (combo.getSelectedItem().equals("busybox df -h")) {

					command.setCommand(
							new String[] { "shell", System.getProperty("line.separator") + "busybox df -h" });

				}
				// =====================================================

				else {

					if (combo.getSelectedItem().equals("connect")) {

						preparePanel(phonePanel, false, "Host");

					}
					// =====================================================

					else if (combo.getSelectedItem().equals("install")) {

						preparePanel(pushPanel, false, "Select apk file to install");

					}
					// =====================================================

					else if (combo.getSelectedItem().equals("uninstall")) {

						freezePanel(uninstallPanel, false);

					}
					// =====================================================

					else if (combo.getSelectedItem().equals("push")) {

						preparePanel(pushPanel, false, "Select any file to push");
						freezePanel(phonePanel, false);

					}
					// =====================================================

					else if (combo.getSelectedItem().equals("pull")) {

						freezePanel(pcPanel, false);
						freezePanel(phonePanel, false);

					}
					// =====================================================

					else if (((String) (combo.getSelectedItem())).contains("flash")) {

						preparePanel(pushPanel, false, "Select a file to flash");

					}
					// =====================================================

					command.setCommand(combo.getSelectedItem().toString().split(" "));
				}

			}
		}

	}

	// ======================================================================================

	/** buttons ActionListener */
	class ButtonEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == donate) {

				SideKick.visitAddress(SideKick.DONATE_URL);

			} else if (e.getSource() == help) {

				SideKick.visitAddress(SideKick.HELP_URL);

			} else if (e.getSource() == source) {

				SideKick.visitAddress(SideKick.SOURCE_URL);

			} else if (e.getSource() == save) {

				try {
					new FileSaveDialog(MainUI.this, executer.getOutput());

				} catch (ExecNotFinishedException | FileFailException | NoOutputException e1) {
					JOptionPane.showMessageDialog(MainUI.this, e1.fillInStackTrace(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			// =====================================================

			else if (e.getSource() == reset) {

				((JTextArea) textArea).setText(null);

				combo.clearSelectedElement();
				resetFrame();
				adbRadio.setSelected(true);

			}
			// =====================================================

			else if (e.getSource() == about) {

				JOptionPane.showMessageDialog(MainUI.this, SideKick.ABOUT_STRING, "About",
						JOptionPane.INFORMATION_MESSAGE);

			}
			// =====================================================

			else if (e.getSource() == browseBtn) {
				FileExplorer explorer = new FileExplorer(MainUI.this);
				txt1.setText(explorer.getFileName());
			}
			// =====================================================

			else if (e.getSource() == browseBtn2) {
				FileExplorer explorer2 = new FileExplorer(MainUI.this);
				txt3.setText(explorer2.getFileName());
			}
			// =====================================================

			else if (e.getSource() == activateBtn) {
				fire();

			}
			// =====================================================

			else if (e.getSource() == abortBtn) {

				try {
					MainUI.this.executer.abort();

				} catch (CmdAbortedException | IOException e1) {
					JOptionPane.showMessageDialog(MainUI.this, e1.fillInStackTrace(), "Aborted",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
			// =====================================================

		}

	}
	// ======================================================================================

}