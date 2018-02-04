package mhashim6.sadb.ui;

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
import javax.swing.JTextField;

import mhashim6.commander.main.Command;
import mhashim6.commander.main.CommandBuilder;
import mhashim6.commander.main.CommandExecutor;
import mhashim6.commander.main.ExecutionOutputPrinter;
import mhashim6.commander.main.ProcessMonitor;
import mhashim6.sadb.SideKick;
import mhashim6.sadb.exceptions.FileFailException;

/**
 * @author mhashim6
 */
@SuppressWarnings("serial")
public class MainUI extends JFrame {

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
    private String client;

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

    private OutputArea textArea;

    private JScrollPane scroll;

    private CommandBuilder cmdBuilder;
    private ExecutionOutputPrinter outputPrinter;
    private ProcessMonitor currentProcessContainer;
    private static final String COMMAND_FORMAT = "[%s]";

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

        menu = new JMenu("Options");

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

        adbRadio = new JRadioButton("ADB");
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
        combo.addItemListener(new Comboevent());
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
        activateBtn = new JButton("Execute");
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
        scroll = new JScrollPane(textArea);
        // ======================================================================================

        // Commander
        cmdBuilder = new CommandBuilder();
        outputPrinter = new ExecutionOutputPrinter(textArea);

        adbRadio.setSelected(true);
        client = "adb";
    }

    private void initLayout() {

        decisionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Execution Mode"));
        javax.swing.GroupLayout decisionPanelLayout = new javax.swing.GroupLayout(decisionPanel);
        decisionPanel.setLayout(decisionPanelLayout);
        decisionPanelLayout
                .setHorizontalGroup(
                        decisionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(decisionPanelLayout.createSequentialGroup().addContainerGap()
                                        .addGroup(decisionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(decisionPanelLayout.createSequentialGroup()
                                                        .addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE, 244,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(0, 0, Short.MAX_VALUE))
                                                .addGroup(decisionPanelLayout.createSequentialGroup().addComponent(adbRadio).addGap(10, 10, 10)
                                                        .addComponent(fastbootRadio).addGap(10, 10, 10).addComponent(commandRadio,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addContainerGap()));
        decisionPanelLayout
                .setVerticalGroup(decisionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(decisionPanelLayout.createSequentialGroup().addContainerGap()
                                .addGroup(decisionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(adbRadio).addComponent(fastbootRadio).addComponent(commandRadio))
                                .addGap(14, 14, 14).addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));

        pushPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("A file to install / flash / push"));

        javax.swing.GroupLayout pushPanelLayout = new javax.swing.GroupLayout(pushPanel);
        pushPanel.setLayout(pushPanelLayout);
        pushPanelLayout.setHorizontalGroup(pushPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pushPanelLayout.createSequentialGroup().addContainerGap()
                        .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(browseBtn)
                        .addContainerGap()));
        pushPanelLayout.setVerticalGroup(pushPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pushPanelLayout.createSequentialGroup().addContainerGap()
                        .addGroup(pushPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(browseBtn))
                        .addContainerGap()));

        uninstallPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("APK <package> name to uninstall"));

        javax.swing.GroupLayout uninstallPanelLayout = new javax.swing.GroupLayout(uninstallPanel);
        uninstallPanel.setLayout(uninstallPanelLayout);
        uninstallPanelLayout
                .setHorizontalGroup(uninstallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(uninstallPanelLayout.createSequentialGroup().addContainerGap()
                                .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        uninstallPanelLayout
                .setVerticalGroup(uninstallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(uninstallPanelLayout.createSequentialGroup().addContainerGap()
                                .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pcPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Directory (in PC)"));

        javax.swing.GroupLayout pcPanelLayout = new javax.swing.GroupLayout(pcPanel);
        pcPanel.setLayout(pcPanelLayout);
        pcPanelLayout.setHorizontalGroup(pcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pcPanelLayout.createSequentialGroup().addContainerGap()
                        .addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(browseBtn2)
                        .addContainerGap()));
        pcPanelLayout.setVerticalGroup(pcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pcPanelLayout.createSequentialGroup().addContainerGap()
                        .addGroup(pcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(browseBtn2))
                        .addContainerGap()));

        phonePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Directory (in phone)"));

        javax.swing.GroupLayout phonePanelLayout = new javax.swing.GroupLayout(phonePanel);
        phonePanel.setLayout(phonePanelLayout);
        phonePanelLayout.setHorizontalGroup(phonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(phonePanelLayout.createSequentialGroup().addContainerGap()
                        .addComponent(txt4, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        phonePanelLayout.setVerticalGroup(phonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(phonePanelLayout.createSequentialGroup().addContainerGap()
                        .addComponent(txt4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap()));

        outputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Output"));

        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        javax.swing.GroupLayout outputPanelLayout = new javax.swing.GroupLayout(outputPanel);
        outputPanel.setLayout(outputPanelLayout);
        outputPanelLayout
                .setHorizontalGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(outputPanelLayout.createSequentialGroup().addContainerGap()
                                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE).addContainerGap()));
        outputPanelLayout.setVerticalGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scroll, javax.swing.GroupLayout.Alignment.TRAILING));

        commandPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Your Command"));

        javax.swing.GroupLayout commandPanelLayout = new javax.swing.GroupLayout(commandPanel);
        commandPanel.setLayout(commandPanelLayout);
        commandPanelLayout
                .setHorizontalGroup(commandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(commandPanelLayout.createSequentialGroup().addContainerGap()
                                .addComponent(txt5, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        commandPanelLayout
                .setVerticalGroup(commandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(commandPanelLayout.createSequentialGroup().addContainerGap()
                                .addComponent(txt5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                        layout.createSequentialGroup().addComponent(activateBtn).addGap(18, 18, 18)
                                                .addComponent(abortBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 68,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(97, 97, 97))
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(phonePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)
                                                .addComponent(pcPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)
                                                .addComponent(uninstallPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)
                                                .addComponent(pushPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)
                                                .addComponent(decisionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)
                                                .addComponent(commandPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(outputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .addContainerGap()));
        layout
                .setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                        layout.createSequentialGroup().addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(outputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE)
                                                                .addContainerGap())
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(decisionPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(pushPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(uninstallPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(pcPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(phonePanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(commandPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(abortBtn).addComponent(activateBtn))
                                                                .addGap(0, 45, Short.MAX_VALUE)))));
        pack();
    }

    /********* Helpful methods ***********/

    /**
     * to disable the passed panel and all it's components
     */
    private void freezePanel(JPanel panel, boolean condition) {

        panel.setEnabled(!condition);

        Component[] com = panel.getComponents();

        for (int i = 0; i < com.length; i++) {

            if (!abortBtn.isEnabled()) if (com[i] instanceof JTextField) {
                ((JTextField) com[i]).setText("");
                com[i].requestFocus();
            }

            if (!com[i].equals(combo))
                com[i].setEnabled(!condition);
            else if (!commandRadio.isSelected()) com[i].setEnabled(!condition);
        }
    }

    /**
     * to reset the components
     */
    private void resetFrame() {
        renamePanel(pushPanel, "File to install / flash / push");
        freezePanel(pushPanel, true);
        freezePanel(uninstallPanel, true);
        freezePanel(pcPanel, true);
        renamePanel(phonePanel, "Directory (in phone)");
        freezePanel(phonePanel, true);
        freezePanel(commandPanel, true);

        activateBtn.setEnabled(false);
        abortBtn.setEnabled(false);
    }

    /**
     * to enable/disable the vital components during operations
     */
    private void operationOn(boolean freezeCondition) {

        abortBtn.setEnabled(freezeCondition);

        freezePanel(decisionPanel, freezeCondition);

        if (!commandRadio.isSelected()) activateBtn.setEnabled(!freezeCondition);

        save.setEnabled(!freezeCondition);
        reset.setEnabled(!freezeCondition);
    }

    /**
     * to enable/disable the components of passed panel temporarily
     */
    private void renamePanel(JPanel panel, String title) {
        panel.setBorder(BorderFactory.createTitledBorder(title));
    }

    private void fire() {
        operationOn(true);

        new Thread(() -> {
            try {
                if (txt5.isEnabled()) // your own command.
                    cmdBuilder.forCommandLine(txt5.getText());
                    // =====================================================

                else {
                    if (txt1.isEnabled())// is it install?
                        cmdBuilder.withOptions(txt1.getText());

                    if (txt4.isEnabled()) {
                        if (txt3.isEnabled())// pull?
                            cmdBuilder.withOptions(txt4.getText(), txt3.getText());

                        else // oh it's push.
                            cmdBuilder.withOptions(txt1.getText(), txt4.getText());
                    } else if (txt2.isEnabled()) cmdBuilder.withOptions(txt2.getText());
                }

                Command cmd = cmdBuilder.build();
                textArea.append(String.format(COMMAND_FORMAT, cmd.string()) + SideKick.NEW_LINE);

                currentProcessContainer = CommandExecutor.execute(cmd, null, outputPrinter);
                checkForErrors(currentProcessContainer.getExecutionReport().exitValue());
            } catch (IOException e1) {
                SideKick.showMessage(MainUI.this, e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                hangOn(100);

                textArea.flush();
                textArea.addSeparator();

                operationOn(false);
            }
        }).start();
    }

    private void checkForErrors(int exitValue) {
        if (exitValue < 0) SideKick.showMessage(MainUI.this, "command returned with exit code: " + exitValue, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void hangOn(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }
    // ======================================================================================

    class RadioEvent implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {

            resetFrame();
            combo.setEnabled(true);
            //			cmdBuilder = new CommandBuilder();

            if (e.getSource() == adbRadio) {

                textArea.setBackground(Color.decode("#1C2021"));
                client = "adb";

                combo.setComboElements(CommandsBox.ADB_ITEMS);
            }
            // =====================================================

            else if (e.getSource() == fastbootRadio) {

                textArea.setBackground(Color.decode("#203040"));
                client = "fastboot";

                combo.setComboElements(CommandsBox.FASTBOOT_ITEMS);
            }
            // =====================================================

            else if (e.getSource() == commandRadio) {

                txt5.requestFocus();
                combo.setEnabled(false);
                freezePanel(commandPanel, false);
                activateBtn.setEnabled(true);

                textArea.setBackground(Color.decode("#0C545C")); // #7D1424
                txt5.setText("");
            }
            // =====================================================
        }
    }
    // ======================================================================================

    /**
     * ComboBox ItemListener
     */
    class Comboevent implements ItemListener {

        /**
         * it will decide which panels will be enabled, based on the selected item,
         * and load the Command input that shall be executed later.
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
                    cmdBuilder.forCommandLine(client + " shell rm /data/system/gesture.key");
                }
                // =====================================================

                else if (combo.getSelectedItem().equals("busybox df -h")) {
                    cmdBuilder.forCommandLine(client + " shell " + SideKick.NEW_LINE + "busybox df -h");
                }
                // =====================================================

                else {
                    if (combo.getSelectedItem().equals("connect")) {

                        renamePanel(phonePanel, "Host");
                        freezePanel(phonePanel, false);
                    }
                    // =====================================================

                    else if (combo.getSelectedItem().equals("install")) {

                        renamePanel(pushPanel, "Select apk file to install");
                        freezePanel(pushPanel, false);
                    }
                    // =====================================================

                    else if (combo.getSelectedItem().equals("uninstall")) {

                        freezePanel(uninstallPanel, false);
                    }
                    // =====================================================

                    else if (combo.getSelectedItem().equals("push")) {

                        renamePanel(pushPanel, "Select any file to push");
                        freezePanel(phonePanel, false);
                        freezePanel(pushPanel, false);
                    }
                    // =====================================================

                    else if (combo.getSelectedItem().equals("pull")) {

                        freezePanel(pcPanel, false);
                        freezePanel(phonePanel, false);
                    }
                    // =====================================================

                    else if (((String) (combo.getSelectedItem())).contains("flash")) {

                        renamePanel(pushPanel, "Select a file to flash");
                        freezePanel(pushPanel, false);
                    }
                    // =====================================================
                    cmdBuilder.forCommandLine(client + " " + combo.getSelectedItem().toString());
                }

            }
        }

    }
    // ======================================================================================

    /**
     * buttons ActionListener
     */
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

                if (textArea.getText().equals(""))
                    SideKick.showMessage(MainUI.this, "No output", "Error", JOptionPane.ERROR_MESSAGE);
                else
                    try {
                        new FileSaveDialog(MainUI.this, textArea.getText());

                    } catch (FileFailException e1) {
                        SideKick.showMessage(MainUI.this, e1.fillInStackTrace().toString(), "Error", JOptionPane.ERROR_MESSAGE);

                    }
            }
            // =====================================================

            else if (e.getSource() == reset) {
                textArea.setText(null);

                combo.clearSelectedElement();
                resetFrame();
                adbRadio.setSelected(true);
            }
            // =====================================================

            else if (e.getSource() == about) {
                SideKick.showMessage(MainUI.this, SideKick.ABOUT_STRING.toString(), "About", JOptionPane.INFORMATION_MESSAGE);
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
                currentProcessContainer.abort();
                SideKick.showMessage(MainUI.this, "Execution aborted", "Aborted", JOptionPane.INFORMATION_MESSAGE);
            }
            // =====================================================

        }

    }
    // ======================================================================================

}