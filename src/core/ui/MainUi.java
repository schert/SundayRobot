package core.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

import action.PressKey;
import action.PressMouse;
import action.ReleaseKey;
import action.ReleaseMouse;
import core.Constants;
import core.SundayRobot;

public class MainUi extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JButton buttonPlay, buttonClear, buttonStore,buttonRestore;
	private JCheckBox checkLoop;
	private SpringLayout layout;
	private Container panel;
	private JSpinner spinnerMin;
	private JSpinner spinnerSec;

	public MainUi(SundayRobot robot) {
		
		setTitle(Constants.UI.TITLE);		
		
		panel = getContentPane();
        layout = new SpringLayout();
        panel.setLayout(layout);

		JButton buttonRec = new JButton();
		buttonRec.setText(Constants.UI.REGISTRA);
		
		buttonRec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(!robot.isRunRecord()) {
            		buttonPlay.setEnabled(false);
            		buttonClear.setEnabled(false);
            		buttonStore.setEnabled(false);
            		buttonRestore.setEnabled(false);
            		checkLoop.setEnabled(false);
            		buttonRec.setText(Constants.UI.STOP_REC);
            		robot.record();
            	}
        		else {
        			robot.stopRecord();
        			
        			if(e.getModifiers() == InputEvent.MOUSE_EVENT_MASK) {
        				robot.removeAction(robot.getLastActionType(PressMouse.class,robot.sizeAction(),false));
            			robot.removeAction(robot.getLastActionType(ReleaseMouse.class,robot.sizeAction(),false));
        			}
        			
        			if(e.getModifiers() == KeyEvent.VK_UNDEFINED) {
        				robot.removeAction(robot.getLastActionType(PressKey.class,robot.sizeAction(),false));
            			robot.removeAction(robot.getLastActionType(ReleaseKey.class,robot.sizeAction(),false));
        			}
        			
        			buttonRec.setText(Constants.UI.REGISTRA);
        			buttonPlay.setEnabled(true);
        			buttonClear.setEnabled(true);
        			buttonStore.setEnabled(true);
        			buttonRestore.setEnabled(true);
        			checkLoop.setEnabled(true);
        		}
            }
        });
		
		buttonPlay = new JButton();
		buttonPlay.setText(Constants.UI.PLAY);
		buttonPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(!robot.isRunAction()) {
            		robot.start();
            		buttonPlay.setText(Constants.UI.STOP);
            	}
        		else {
        			robot.stopRun();
        			buttonPlay.setText(Constants.UI.PLAY);
        		}
            }
        });
		
		buttonClear = new JButton();
		buttonClear.setText(Constants.UI.PULISCI);
		buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	robot.clear();
            }
        });
		
		buttonStore = new JButton();
		buttonStore.setText(Constants.UI.SALVA);
		buttonStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	JFileChooser fileChooser = new JFileChooser();
            	if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            	  File file = fileChooser.getSelectedFile();
            	  robot.storeActions(file);
            	}
            }
        });
		
		buttonRestore = new JButton();
		buttonRestore.setText(Constants.UI.CARICA);
		buttonRestore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	JFileChooser fileChooser = new JFileChooser();
            	if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            	  File file = fileChooser.getSelectedFile();
            	  robot.restoreActions(file);
            	}
            }
        });
		
		checkLoop = new JCheckBox();
		checkLoop.setText(Constants.UI.RIPETI);
		checkLoop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JCheckBox cb = (JCheckBox) e.getSource();
            	robot.setLoop(cb.isSelected());
            }
        });

		SpinnerModel  monthModel =  new SpinnerNumberModel(0, 0, 99, 1);        
		spinnerMin = new JSpinner(monthModel);
		monthModel =  new SpinnerNumberModel(0, 0, 60, 1);        
		spinnerSec = new JSpinner(monthModel);
		
		JLabel label = new JLabel(":");
		
		JPanel panelLoop = new JPanel();
		panelLoop.add(checkLoop);
		panelLoop.add(spinnerMin);
		panelLoop.add(label);
		panelLoop.add(spinnerSec);
		panelLoop.setBorder(BorderFactory.createTitledBorder(Constants.UI.ETI_LOOP));
		
		JPanel panelFile = new JPanel();
		panelFile.add(buttonStore);
		panelFile.add(buttonRestore);
		panelFile.setBorder(BorderFactory.createTitledBorder(Constants.UI.ETI_FILE));
		
		JPanel panelControl = new JPanel();
		panelControl.add(buttonRec);
		panelControl.add(buttonPlay);
		panelControl.add(buttonClear);
		panelControl.setBorder(BorderFactory.createTitledBorder(Constants.UI.ETI_CONTROL));
		
		panel.add(panelControl);
		panel.add(panelLoop);
		panel.add(panelFile);
		
		setConstratint(panelControl);
		setConstratint(panelLoop);
		setConstratint(panelFile);
		
		layout.putConstraint(SpringLayout.EAST, panel,5,SpringLayout.EAST, panelFile);
		layout.putConstraint(SpringLayout.SOUTH, panel,5,SpringLayout.SOUTH, panelFile);

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		WindowListener exitListener = new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	String[] buttons = {Constants.UI.SI, Constants.UI.NO};
		        int confirm = JOptionPane.showOptionDialog(
		             null, Constants.UI.EXIT_SICURO, 
		             Constants.UI.EXIT_SICURO_TITILE, JOptionPane.YES_NO_OPTION,
		             JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[1]);
		        if (confirm == 0) {
		           robot.stopRecord();
		           robot.stopRun();
		           System.exit(0);
		        }
		    }
		};
		addWindowListener(exitListener);
		
		pack();
		setVisible(true);
		setResizable(false);
	}

	public JButton getButtonPlay() {
		return buttonPlay;
	}
	
	private Component preComp;
	private void setConstratint(Component c) {
				
		if(preComp==null) {
			layout.putConstraint(SpringLayout.WEST, c,5,SpringLayout.WEST, panel);
		} else {
			layout.putConstraint(SpringLayout.WEST, c,5,SpringLayout.EAST, preComp);
		}

		layout.putConstraint(SpringLayout.NORTH, c,5,SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.SOUTH, panel,5,SpringLayout.SOUTH, c);
		
		c.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				pack();
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				pack();
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				pack();
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				pack();
			}
		});
		
		preComp = c;
	}

	public JSpinner getSpinnerMin() {
		return spinnerMin;
	}

	public JSpinner getSpinnerSec() {
		return spinnerSec;
	}

}
