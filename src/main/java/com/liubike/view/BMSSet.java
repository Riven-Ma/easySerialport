package com.liubike.view;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public abstract class BMSSet extends AbstractView{

	
	protected JTextField bmsSnTF;
	protected JTextField reduceTimeTF;
	protected JTextField batTTF;
	protected JTextField perReduceTF;
	protected JTextField batVTF;
	protected JTextField sohTF;
	protected JTextField batQTF;
	protected JButton conmitBT; 

	
	
	/**
	 * Create the application.
	 */
	public BMSSet() {
		super();
		initialize();
		initControl();
		binding();
	}

	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		frame.setBounds(500, 200, 600, 500); // 设置容器大小
		frame.setMinimumSize(new Dimension(600, 500));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("BMS设置");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{84, 0};
		gridBagLayout.rowHeights = new int[]{100,0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{1.0,1.0};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		gbc_panel.gridwidth=1;
		gbc_panel.gridheight=1;
		frame.getContentPane().add(panel, gbc_panel);
		
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{2,2, 1,2,98};
		gbl_panel.rowHeights = new int[]{0,0,0,0,0,0,0};
		gbl_panel.columnWeights = new double[]{0.0,0.0, 0.0,0.0,0.0};
		gbl_panel.rowWeights = new double[]{0.0,0.0,0.0,1,1,1,1};
		panel.setLayout(gbl_panel);
		
		JLabel bmsSnLB = new JLabel("BMS序列号");
		GridBagConstraints gbc_bmsSnLB = new GridBagConstraints();
		gbc_bmsSnLB.anchor = GridBagConstraints.EAST;
		gbc_bmsSnLB.insets = new Insets(0, 0, 5, 5);
		gbc_bmsSnLB.gridx = 0;
		gbc_bmsSnLB.gridy = 0;
		panel.add(bmsSnLB, gbc_bmsSnLB);
		
		bmsSnTF = new JTextField();
		bmsSnTF.setText("03LC30A213180409");
		GridBagConstraints gbc_bmsSnTF = new GridBagConstraints();
		gbc_bmsSnTF.gridwidth = 4;
		gbc_bmsSnTF.insets = new Insets(0, 0, 5, 0);
		gbc_bmsSnTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_bmsSnTF.gridx = 1;
		gbc_bmsSnTF.gridy = 0;
		panel.add(bmsSnTF, gbc_bmsSnTF);
		bmsSnTF.setColumns(10);
		
		
		
		
		
		JLabel batTLB = new JLabel("BMS温度（K）");
		GridBagConstraints gbc_batTLB = new GridBagConstraints();
		gbc_batTLB.anchor = GridBagConstraints.EAST;
		gbc_batTLB.insets = new Insets(0, 0, 5, 5);
		gbc_batTLB.gridx = 0;
		gbc_batTLB.gridy = 2;
		panel.add(batTLB, gbc_batTLB);
		
		
		
		batTTF = new JTextField();
		batTTF.setText("3013");
		GridBagConstraints gbc_batTTF = new GridBagConstraints();
		gbc_batTTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_batTTF.insets = new Insets(0, 0, 5, 5);
		gbc_batTTF.gridx = 1;
		gbc_batTTF.gridy = 2;
		panel.add(batTTF, gbc_batTTF);
		batTTF.setColumns(10);
		
		JLabel batQLB = new JLabel("电瓶电量(%)");
		GridBagConstraints gbc_batQLB = new GridBagConstraints();
		gbc_batQLB.anchor = GridBagConstraints.EAST;
		gbc_batQLB.insets = new Insets(0, 0, 5, 5);
		gbc_batQLB.gridx = 3;
		gbc_batQLB.gridy = 2;
		panel.add(batQLB, gbc_batQLB);
		
		batQTF = new JTextField();
		batQTF.setText("80");
		GridBagConstraints gbc_batQTF = new GridBagConstraints();
		gbc_batQTF.insets = new Insets(0, 0, 5, 0);
		gbc_batQTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_batQTF.gridx = 4;
		gbc_batQTF.gridy = 2;
		panel.add(batQTF, gbc_batQTF);
		batQTF.setColumns(10);
		
		JLabel batVLB = new JLabel("电瓶电压(mV)");
		GridBagConstraints gbc_batVLB = new GridBagConstraints();
		gbc_batVLB.anchor = GridBagConstraints.EAST;
		gbc_batVLB.insets = new Insets(0, 0, 5, 5);
		gbc_batVLB.gridx = 0;
		gbc_batVLB.gridy = 3;
		panel.add(batVLB, gbc_batVLB);
		
		batVTF = new JTextField();
		batVTF.setText("4973");
		GridBagConstraints gbc_batVTF = new GridBagConstraints();
		gbc_batVTF.insets = new Insets(0, 0, 5, 5);
		gbc_batVTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_batVTF.gridx = 1;
		gbc_batVTF.gridy = 3;
		panel.add(batVTF, gbc_batVTF);
		batVTF.setColumns(10);
		
		JLabel sohLB = new JLabel("电池健康指数");
		GridBagConstraints gbc_sohLB = new GridBagConstraints();
		gbc_sohLB.anchor = GridBagConstraints.EAST;
		gbc_sohLB.insets = new Insets(0, 0, 5, 5);
		gbc_sohLB.gridx = 3;
		gbc_sohLB.gridy = 3;
		panel.add(sohLB, gbc_sohLB);
		
		sohTF = new JTextField();
		sohTF.setText("90");
		GridBagConstraints gbc_sohTF = new GridBagConstraints();
		gbc_sohTF.insets = new Insets(0, 0, 5, 0);
		gbc_sohTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_sohTF.gridx = 4;
		gbc_sohTF.gridy = 3;
		panel.add(sohTF, gbc_sohTF);
		sohTF.setColumns(10);
		
		conmitBT = new JButton("确定");
		GridBagConstraints gbc_conmitBT = new GridBagConstraints();
		gbc_conmitBT.fill = GridBagConstraints.HORIZONTAL;
		gbc_conmitBT.insets = new Insets(0, 0, 0, 5);
		gbc_conmitBT.gridx = 1;
		gbc_conmitBT.gridy = 6;
		panel.add(conmitBT, gbc_conmitBT);

		frame.setVisible(true);
	}

}
