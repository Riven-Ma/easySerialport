package com.liubike.view;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.liubike.view.enums.CheckBitEmnu;
import com.liubike.view.enums.DecodeFormatEnum;
import com.liubike.view.enums.SeparateDisplayEnum;
import com.liubike.view.support.SuperTextArea;
import com.liubike.view.util.ComboBoxItem;
import com.liubike.view.util.SerialPortComboBoxItem;


public abstract class MainApp implements View{

	

	/**
	 * 调试界面用
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					MainApp window = new MainApp();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public MainApp() {
		super();
		initialize();
		binding();
	}
	

	
	private JFrame frame;
	
	public void show() {
		this.frame.setVisible(true);
	}
	
	
	//TODO
	
	private static final Integer zero = 0;
	protected JTextField mmTF;
	protected JTextField timesTF;
	protected JComboBox<SerialPortComboBoxItem> selectSerialCB;
	protected JButton refushSelectSerialBT;
	protected JComboBox<Integer> baudRateCB;
	protected JComboBox<ComboBoxItem<CheckBitEmnu>> checkBitCB;
	protected JComboBox<Integer> stopBitCB;
	protected JComboBox<Integer> dataBitCB;
	
	
	protected JButton openSerialBT;
	protected JRadioButton characterRB;
	protected JRadioButton hexadecimalRB;
	protected ButtonGroup showModelRadioGroup;
	
	
	protected JComboBox<ComboBoxItem<DecodeFormatEnum>> decodFormatCB;
	protected JComboBox<ComboBoxItem<SeparateDisplayEnum>> separateDisplayCB;
	protected JButton saveLogBT;
	protected ButtonGroup sendFormatRadioGroup;
	protected JComboBox<ComboBoxItem<DecodeFormatEnum>> sendDecodFormatCB;
	protected JCheckBox showSendCBox;
	
	protected JCheckBox repeatSendCBox;
	protected JCheckBox sendSeparateDisplayCBox;
	protected JComboBox<ComboBoxItem<SeparateDisplayEnum>> sendSeparateDisplayCB;
	
	
	
	protected JCheckBox showLineNumCBOX;
	protected JButton cleanReceiveBT;
	
	protected JButton cleanSendAreaBT;
	protected JTextArea sendTA;
	protected JButton sendBT;
	
	
	
	
	
	
	protected JButton bmsSetBT;

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("easy");
		frame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		GridBagLayout gbaglayout = new GridBagLayout(); // 创建GridBagLayout布局管理器
		gbaglayout.columnWidths = new int[] { 0, 500, 200 };
		gbaglayout.rowHeights = new int[] { 0, 0, 0, 0, 0 }; // 设置了总共有n行
		gbaglayout.columnWeights = new double[] { 0.0, 100.0, 0.0 }; // 设置了总共有3列,设置了列的宽度为容器宽度比例
		gbaglayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0 }; // 设置了总共有5行，第一行的高度占了容器的2份，第二行的高度占了容器的8份
		frame.getContentPane().setLayout(gbaglayout);

		////////////////////////////// left

		JPanel selectSerialJP = new JPanel();
		selectSerialJP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "串口设置：",
				TitledBorder.LEFT, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_selectSerialJP = new GridBagConstraints();
		gbc_selectSerialJP.fill = GridBagConstraints.BOTH;
		gbc_selectSerialJP.gridx = 0;
		gbc_selectSerialJP.gridy = 0;
		frame.getContentPane().add(selectSerialJP, gbc_selectSerialJP);
		GridBagLayout gbl_selectSerialJP = new GridBagLayout();
		gbl_selectSerialJP.columnWidths = new int[] { 20, 60 };
		gbl_selectSerialJP.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_selectSerialJP.columnWeights = new double[] { 0.0, 1.0 };
		gbl_selectSerialJP.rowWeights = new double[] { 0.0, 0.0, 0, 0, 0, 0, 0 };
		gbc_selectSerialJP.insets = new Insets(zero, zero, zero, zero);
		selectSerialJP.setLayout(gbl_selectSerialJP);

		this.selectSerial(selectSerialJP);

		this.baudRate(selectSerialJP);

		this.checkBit(selectSerialJP);

		this.stopBit(selectSerialJP);

		this.dataBit(selectSerialJP);

		this.openSerial(selectSerialJP);

		////////////////////////////////////////////// receiveSet///////////
		JPanel receiveSetJP = new JPanel();
		receiveSetJP.setMaximumSize(new Dimension(160, 120));
		receiveSetJP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "接收设置：",
				TitledBorder.LEFT, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_receiveSetJP = new GridBagConstraints();
		gbc_receiveSetJP.fill = GridBagConstraints.BOTH;
		gbc_receiveSetJP.insets = new Insets(zero, zero, 5, zero);
		gbc_receiveSetJP.gridx = 0;
		gbc_receiveSetJP.gridy = 1;
		frame.getContentPane().add(receiveSetJP, gbc_receiveSetJP);
		GridBagLayout gbl_receiveSetJP = new GridBagLayout();
		gbl_receiveSetJP.columnWidths = new int[] { zero, 86, zero };
		gbl_receiveSetJP.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_receiveSetJP.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_receiveSetJP.rowWeights = new double[] { 0, 0, 0, 0 };
		receiveSetJP.setLayout(gbl_receiveSetJP);

		this.showModel(receiveSetJP);

		this.separateDisplay(receiveSetJP);

		////////////////////////////////////// sendSet//////////////
		JPanel sendSetJP = new JPanel();
		sendSetJP.setMaximumSize(new Dimension(160, 120));
		sendSetJP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "发送设置：",
				TitledBorder.LEFT, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_sendSetJP = new GridBagConstraints();
		gbc_sendSetJP.fill = GridBagConstraints.BOTH;
		gbc_sendSetJP.insets = new Insets(zero, zero, zero,zero );
		gbc_sendSetJP.gridx = 0;
		gbc_sendSetJP.gridy = 2;
		frame.getContentPane().add(sendSetJP, gbc_sendSetJP);
		GridBagLayout gbl_sendSetJP = new GridBagLayout();
		gbl_sendSetJP.columnWidths = new int[] { zero, zero, zero, zero };
		gbl_sendSetJP.rowHeights = new int[] { zero, zero, zero, zero, zero };
		gbl_sendSetJP.columnWeights = new double[] { 0.0, 0, 0.0, 1.0 };
		gbl_sendSetJP.rowWeights = new double[] { 0, 0, 0, 0, 0 };
		sendSetJP.setLayout(gbl_sendSetJP);

		this.sendFormat(sendSetJP);
		this.repeatSend(sendSetJP);
		this.sendSeparateDisplay(sendSetJP);

/////////////////////////////////////////////receive area//////////////

		JPanel receiveJP = new JPanel();
		receiveJP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "接收区：",
				TitledBorder.LEFT, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_receiveJP = new GridBagConstraints();
		gbc_receiveJP.fill = GridBagConstraints.BOTH;
		gbc_receiveJP.gridx = 1;
		gbc_receiveJP.gridy = 0;
		gbc_receiveJP.gridheight = 4;
		gbc_receiveJP.insets = new Insets(0, 0, 0, 0);
		frame.getContentPane().add(receiveJP, gbc_receiveJP);
		GridBagLayout gbl_receiveJP = new GridBagLayout();
		gbl_receiveJP.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_receiveJP.rowHeights = new int[] { 0, 0 };
		gbl_receiveJP.columnWeights = new double[] { zero, zero, zero, zero, zero, zero,zero, 1.0};
		gbl_receiveJP.rowWeights = new double[] { 0.0, 1.0 };
		receiveJP.setLayout(gbl_receiveJP);
		
		this.receiveAreaTool(receiveJP);

//////////////////////////////////////////send area////////////////  

		sendJP = new JPanel();
		sendJP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "发送区：", TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_sendJP = new GridBagConstraints();
		gbc_sendJP.insets = new Insets(0, 0, 0, 0);
		gbc_sendJP.fill = GridBagConstraints.BOTH;
		gbc_sendJP.gridx = 0;
		gbc_sendJP.gridy = 4;
		gbc_sendJP.gridwidth = 2;
		frame.getContentPane().add(sendJP, gbc_sendJP);
		GridBagLayout gbl_sendJP = new GridBagLayout();
		gbl_sendJP.columnWeights = new double[] { 0.0,1.0 };
		gbl_sendJP.rowHeights = new int[] { 0, 80, 0 };
		sendJP.setLayout(gbl_sendJP);

		this.sendArea(sendJP);
//////////////////////////////////////////right////////////////        
		JPanel rightJP = new JPanel();
		rightJP.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_rightJP = new GridBagConstraints();
		gbc_rightJP.insets = new Insets(5, 0, 5, 0);
		gbc_rightJP.fill = GridBagConstraints.BOTH;
		gbc_rightJP.gridx = 2;
		gbc_rightJP.gridy = 0;
		gbc_rightJP.gridheight = 5;
		frame.getContentPane().add(rightJP, gbc_rightJP);
		GridBagLayout gbl_rightJP = new GridBagLayout();
		gbl_rightJP.columnWidths = new int[] { 0, 0 };
		gbl_rightJP.rowHeights = new int[] { 0, 0, 0 };
		gbl_rightJP.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_rightJP.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		rightJP.setLayout(gbl_rightJP);

	
		
		bmsSetBT = new JButton("BMS设置");
		GridBagConstraints gbc_bmsSetBT = new GridBagConstraints();
		gbc_bmsSetBT.gridx = 0;
		gbc_bmsSetBT.gridy = 0;
		rightJP.add(bmsSetBT, gbc_bmsSetBT);

////////////////////////////////////////////////// bottom/////////////
		JPanel bottomJP = new JPanel();
		FlowLayout flowLayout = (FlowLayout) bottomJP.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		bottomJP.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_bottomJP = new GridBagConstraints();
		gbc_bottomJP.fill = GridBagConstraints.BOTH;
		gbc_bottomJP.anchor = GridBagConstraints.WEST;
		gbc_bottomJP.insets = new Insets(0, 0, 0, 0);
		gbc_bottomJP.gridx = 0;
		gbc_bottomJP.gridy = 5;
		gbc_bottomJP.gridwidth = 3;
		frame.getContentPane().add(bottomJP, gbc_bottomJP);

		JLabel lblNewLabel = new JLabel("状态：");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		bottomJP.add(lblNewLabel);

		frame.setBounds(400, 100, 1000, 730); // 设置容器大小
		frame.setMinimumSize(new Dimension(1000, 730));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	

	/**
	 * 选串口
	 * 
	 * @param selectSerialJP
	 */
	private void selectSerial(JPanel selectSerialJP) {
		JLabel selectSerialLB = new JLabel("选串口：");
		GridBagConstraints gbc_selectSerialLB = new GridBagConstraints();
		gbc_selectSerialLB.insets = new Insets(zero, zero, zero,zero );
		gbc_selectSerialLB.gridx = 0;
		gbc_selectSerialLB.gridy = 0;
		selectSerialJP.add(selectSerialLB, gbc_selectSerialLB);
		
	

		selectSerialCB= new JComboBox<SerialPortComboBoxItem>();
		GridBagConstraints gbc_selectSerialCB = new GridBagConstraints();
		gbc_selectSerialCB.anchor = GridBagConstraints.WEST;
		gbc_selectSerialCB.fill=GridBagConstraints.BOTH;
//		gbc_selectSerialCB.insets = new Insets(zero, zero, zero,zero );
		gbc_selectSerialCB.gridx = 0;
		gbc_selectSerialCB.gridy = 1;
		gbc_selectSerialCB.gridwidth=2;
		selectSerialJP.add(selectSerialCB, gbc_selectSerialCB);
//		selectSerialCB.setPreferredSize(new Dimension(40, 25));
		
		refushSelectSerialBT = new JButton("刷新串口");
		GridBagConstraints gbc_refushSelectSerialBT = new GridBagConstraints();
		gbc_refushSelectSerialBT.fill = GridBagConstraints.BOTH;
		gbc_refushSelectSerialBT.insets = new Insets(zero, zero, zero,zero );
		gbc_refushSelectSerialBT.gridx = 1;
		gbc_refushSelectSerialBT.gridy = 0;
//		gbc_refushSelectSerialBT.gridwidth=2;
		selectSerialJP.add(refushSelectSerialBT, gbc_refushSelectSerialBT);
		refushSelectSerialBT.setHorizontalTextPosition(SwingConstants.LEFT);
	}
	
	/**
	 * 波特率
	 * 
	 * @param selectSerialJP
	 */
	private void baudRate(JPanel selectSerialJP) {
		JLabel baudRateLB = new JLabel("波特率：");
		GridBagConstraints gbc_baudRateLB = new GridBagConstraints();
		gbc_baudRateLB.insets = new Insets(zero, zero, zero,zero );
		gbc_baudRateLB.gridx = 0;
		gbc_baudRateLB.gridy = 2;
		selectSerialJP.add(baudRateLB, gbc_baudRateLB);

		Integer[] baudRateArray = new Integer[] { 300, 1200, 2400, 4800, 9600, 14400, 19200, 28800, 38400, 57600, 74880,
				115200, 230400 };
		baudRateCB = new JComboBox<Integer>(baudRateArray);
		baudRateCB.setSelectedIndex(4);
		GridBagConstraints gbc_baudRateCB = new GridBagConstraints();
		gbc_baudRateCB.fill = GridBagConstraints.HORIZONTAL;
//		gbc_baudRateCB.gridwidth = 2;
		gbc_baudRateCB.insets = new Insets(zero, zero, zero, zero);
		gbc_baudRateCB.gridx = 1;
		gbc_baudRateCB.gridy = 2;
		selectSerialJP.add(baudRateCB, gbc_baudRateCB);
	}

	/**
	 * 校验位
	 * 
	 * @param selectSerialJP
	 */
	private void checkBit(JPanel selectSerialJP) {
		JLabel checkBitLB = new JLabel("校验位：");
		GridBagConstraints gbc_checkBitLB = new GridBagConstraints();
		gbc_checkBitLB.insets = new Insets(zero, zero, zero,zero );
		gbc_checkBitLB.gridx = 0;
		gbc_checkBitLB.gridy = 3;
		selectSerialJP.add(checkBitLB, gbc_checkBitLB);

		@SuppressWarnings("unchecked")
		ComboBoxItem<CheckBitEmnu>[] checkBitItem = new ComboBoxItem[3];
		checkBitItem[0] = new ComboBoxItem<CheckBitEmnu>(CheckBitEmnu.None);
		checkBitItem[1] = new ComboBoxItem<CheckBitEmnu>(CheckBitEmnu.Odd);
		checkBitItem[2] = new ComboBoxItem<CheckBitEmnu>(CheckBitEmnu.Even);
		checkBitCB = new JComboBox<ComboBoxItem<CheckBitEmnu>>(checkBitItem);

		GridBagConstraints gbc_checkBitCB = new GridBagConstraints();
		gbc_checkBitCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBitCB.insets = new Insets(zero, zero, zero,zero );
		gbc_checkBitCB.gridx = 1;
		gbc_checkBitCB.gridy = 3;
//		gbc_checkBitCB.gridwidth = 2;
		selectSerialJP.add(checkBitCB, gbc_checkBitCB);
	}

	/**
	 * 停止位
	 * 
	 * @param selectSerialJP
	 */
	private void stopBit(JPanel selectSerialJP) {
		JLabel stopBitLB = new JLabel("停止位：");
		GridBagConstraints gbc_stopBitLB = new GridBagConstraints();
		gbc_stopBitLB.insets = new Insets(zero, zero, zero,zero );
		gbc_stopBitLB.gridx = 0;
		gbc_stopBitLB.gridy = 4;
		selectSerialJP.add(stopBitLB, gbc_stopBitLB);

		stopBitCB = new JComboBox<Integer>(new Integer[] { 1, 2 });
		GridBagConstraints gbc_stopBitCB = new GridBagConstraints();
		gbc_stopBitCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_stopBitCB.insets = new Insets(zero, zero, zero,zero );
		gbc_stopBitCB.gridx = 1;
		gbc_stopBitCB.gridy = 4;
//		gbc_stopBitCB.gridwidth = 2;
		selectSerialJP.add(stopBitCB, gbc_stopBitCB);
	}

	/**
	 * 数据位
	 * 
	 * @param selectSerialJP
	 */
	private void dataBit(JPanel selectSerialJP) {
		JLabel dataBitLB = new JLabel("数据位：");
		GridBagConstraints gbc_dataBitLB = new GridBagConstraints();
		gbc_dataBitLB.insets = new Insets(zero, zero, zero,zero );
		gbc_dataBitLB.gridx = 0;
		gbc_dataBitLB.gridy = 5;
		selectSerialJP.add(dataBitLB, gbc_dataBitLB);

		dataBitCB = new JComboBox<Integer>(new Integer[] { 8, 7, 6, 5 });
		GridBagConstraints gbc_dataBitCB = new GridBagConstraints();
		gbc_dataBitCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_dataBitCB.insets = new Insets(zero, zero, zero,zero );
		gbc_dataBitCB.gridx = 1;
		gbc_dataBitCB.gridy = 5;
//		gbc_dataBitCB.gridwidth = 2;
		selectSerialJP.add(dataBitCB, gbc_dataBitCB);
	}

	/**
	 * 打开串口
	 * 
	 * @param selectSerialJP
	 */
	private void openSerial(JPanel selectSerialJP) {
		JLabel lblNewLabel_4 = new JLabel("状态");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(zero, zero, zero,zero );
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 6;
		selectSerialJP.add(lblNewLabel_4, gbc_lblNewLabel_4);

		openSerialBT = new JButton("打开串口");
		GridBagConstraints gbc_openSerialBT = new GridBagConstraints();
		gbc_openSerialBT.fill = GridBagConstraints.HORIZONTAL;
		gbc_openSerialBT.insets = new Insets(zero, zero, zero,zero );
//		gbc_openSerialBT.gridwidth = 2;
		gbc_openSerialBT.gridx = 1;
		gbc_openSerialBT.gridy = 6;
		selectSerialJP.add(openSerialBT, gbc_openSerialBT);
	}

	/**
	 * 显示模式
	 * 
	 * @param receiveSetJP
	 */
	private void showModel(JPanel receiveSetJP) {
		JLabel showModelLB = new JLabel("显示模式：");
		GridBagConstraints gbc_showModelLB = new GridBagConstraints();
		gbc_showModelLB.insets = new Insets(zero, zero, zero, zero);
		gbc_showModelLB.gridx = 0;
		gbc_showModelLB.gridy = 0;
		receiveSetJP.add(showModelLB, gbc_showModelLB);

		characterRB = new JRadioButton("字符");
		
		GridBagConstraints gbc_characterRB = new GridBagConstraints();
		gbc_characterRB.insets = new Insets(zero, zero, zero, zero);
		gbc_characterRB.gridx = 1;
		gbc_characterRB.gridy = 0;
		receiveSetJP.add(characterRB, gbc_characterRB);

		hexadecimalRB = new JRadioButton("16进制");
		hexadecimalRB.setSelected(true);
		GridBagConstraints gbc_hexadecimalRB = new GridBagConstraints();
		gbc_hexadecimalRB.insets = new Insets(zero, zero, zero, zero);
		gbc_hexadecimalRB.gridx = 2;
		gbc_hexadecimalRB.gridy = 0;
		receiveSetJP.add(hexadecimalRB, gbc_hexadecimalRB);

		showModelRadioGroup = new ButtonGroup();
		showModelRadioGroup.add(characterRB);
		showModelRadioGroup.add(hexadecimalRB);

		@SuppressWarnings("unchecked")
		ComboBoxItem<DecodeFormatEnum>[] decodeFormatItem = new ComboBoxItem[3];
		decodeFormatItem[0] = new ComboBoxItem<DecodeFormatEnum>(DecodeFormatEnum.ASCII);
		decodeFormatItem[1] = new ComboBoxItem<DecodeFormatEnum>(DecodeFormatEnum.UTF8);
		decodeFormatItem[2] = new ComboBoxItem<DecodeFormatEnum>(DecodeFormatEnum.GBK);

		JLabel decodFormatLB = new JLabel("编码格式：");
		GridBagConstraints gbc_decodFormatLB = new GridBagConstraints();
		gbc_decodFormatLB.insets = new Insets(zero, zero, zero, zero);
		gbc_decodFormatLB.gridx = 0;
		gbc_decodFormatLB.gridy = 1;
		receiveSetJP.add(decodFormatLB, gbc_decodFormatLB);
		decodFormatCB = new JComboBox<ComboBoxItem<DecodeFormatEnum>>(
				decodeFormatItem);
		GridBagConstraints gbc_decodFormatCB = new GridBagConstraints();
		gbc_decodFormatCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_decodFormatCB.insets = new Insets(zero, zero, zero, zero);
		gbc_decodFormatCB.gridx = 1;
		gbc_decodFormatCB.gridy = 1;
		gbc_decodFormatCB.gridwidth = 2;
		receiveSetJP.add(decodFormatCB, gbc_decodFormatCB);

	}

	/**
	 * 分隔显示
	 * 
	 * @param receiveSetJP
	 */
	public void separateDisplay(JPanel receiveSetJP) {
		JCheckBox separateDisplayCBox = new JCheckBox("分隔符");
		GridBagConstraints gbc_separateDisplayCBox = new GridBagConstraints();
		gbc_separateDisplayCBox.insets = new Insets(zero, zero, zero, zero);
		gbc_separateDisplayCBox.gridx = 0;
		gbc_separateDisplayCBox.gridy = 2;
		receiveSetJP.add(separateDisplayCBox, gbc_separateDisplayCBox);

		@SuppressWarnings("unchecked")
		ComboBoxItem<SeparateDisplayEnum>[] separateDisplayItem = new ComboBoxItem[3];
		separateDisplayItem[0] = new ComboBoxItem<SeparateDisplayEnum>(SeparateDisplayEnum.CTRL);
		separateDisplayItem[1] = new ComboBoxItem<SeparateDisplayEnum>(SeparateDisplayEnum.CR);
		separateDisplayItem[2] = new ComboBoxItem<SeparateDisplayEnum>(SeparateDisplayEnum.LF);
		separateDisplayCB = new JComboBox<ComboBoxItem<SeparateDisplayEnum>>(separateDisplayItem);
		GridBagConstraints gbc_separateDisplayCB = new GridBagConstraints();
		gbc_separateDisplayCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_separateDisplayCB.insets = new Insets(zero, zero, zero, zero);
		gbc_separateDisplayCB.gridx = 1;
		gbc_separateDisplayCB.gridy = 2;
		gbc_separateDisplayCB.gridwidth = 2;
		receiveSetJP.add(separateDisplayCB, gbc_separateDisplayCB);

		saveLogBT = new JButton("保存日志");
		GridBagConstraints gbc_saveLogBT = new GridBagConstraints();
		gbc_saveLogBT.fill = GridBagConstraints.HORIZONTAL;
		gbc_saveLogBT.insets = new Insets(zero, zero, zero, zero);
		gbc_saveLogBT.gridx = 0;
		gbc_saveLogBT.gridy = 3;
		gbc_saveLogBT.gridwidth = 3;
		receiveSetJP.add(saveLogBT, gbc_saveLogBT);
	}

	public void sendFormat(JPanel sendSetJP) {
		JLabel sendFormatLB = new JLabel("发送格式：");
		GridBagConstraints gbc_sendFormatLB = new GridBagConstraints();
		gbc_sendFormatLB.anchor = GridBagConstraints.EAST;
		gbc_sendFormatLB.insets = new Insets(zero, zero, zero, zero);
		gbc_sendFormatLB.gridx = 0;
		gbc_sendFormatLB.gridy = 0;
		sendSetJP.add(sendFormatLB, gbc_sendFormatLB);

		JRadioButton sendCharacterRB = new JRadioButton("字符");
		GridBagConstraints gbc_sendCharacterRB = new GridBagConstraints();
		gbc_sendCharacterRB.insets = new Insets(zero, zero, zero, zero);
		gbc_sendCharacterRB.gridx = 1;
		gbc_sendCharacterRB.gridy = 0;
		sendSetJP.add(sendCharacterRB, gbc_sendCharacterRB);

		JRadioButton sendHexadecimalRB = new JRadioButton("16进制");
		sendHexadecimalRB.setSelected(true);
		GridBagConstraints gbc_sendHexadecimalRB = new GridBagConstraints();
		gbc_sendHexadecimalRB.insets = new Insets(zero, zero, zero, zero);
		gbc_sendHexadecimalRB.gridx = 2;
		gbc_sendHexadecimalRB.gridy = 0;
		sendSetJP.add(sendHexadecimalRB, gbc_sendHexadecimalRB);

		sendFormatRadioGroup = new ButtonGroup();
		sendFormatRadioGroup.add(sendCharacterRB);
		sendFormatRadioGroup.add(sendHexadecimalRB);

		@SuppressWarnings("unchecked")
		ComboBoxItem<DecodeFormatEnum>[] decodeFormatItem = new ComboBoxItem[3];
		decodeFormatItem[0] = new ComboBoxItem<DecodeFormatEnum>(DecodeFormatEnum.ASCII);
		decodeFormatItem[1] = new ComboBoxItem<DecodeFormatEnum>(DecodeFormatEnum.UTF8);
		decodeFormatItem[2] = new ComboBoxItem<DecodeFormatEnum>(DecodeFormatEnum.GBK);
		sendDecodFormatCB = new JComboBox<ComboBoxItem<DecodeFormatEnum>>(
				decodeFormatItem);

		GridBagConstraints gbc_sendDecodFormatCB = new GridBagConstraints();
		gbc_sendDecodFormatCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_sendDecodFormatCB.insets = new Insets(zero, zero, zero, zero);
		gbc_sendDecodFormatCB.gridx = 0;
		gbc_sendDecodFormatCB.gridy = 1;
		gbc_sendDecodFormatCB.gridwidth = 4;
		sendSetJP.add(sendDecodFormatCB, gbc_sendDecodFormatCB);
	}

	public void repeatSend(JPanel sendSetJP) {
		repeatSendCBox = new JCheckBox("启用循环发送");
		GridBagConstraints gbc_repeatSendCBox = new GridBagConstraints();
		gbc_repeatSendCBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_repeatSendCBox.gridwidth = 2;
		gbc_repeatSendCBox.insets = new Insets(zero, zero, zero, zero);
		gbc_repeatSendCBox.gridx = 0;
		gbc_repeatSendCBox.gridy = 2;
		sendSetJP.add(repeatSendCBox, gbc_repeatSendCBox);

		mmTF = new JTextField();
		mmTF.setEnabled(false);
		mmTF.setText("500");
		mmTF.setColumns(4);
		GridBagConstraints gbc_mmTF = new GridBagConstraints();
		gbc_mmTF.fill = GridBagConstraints.BOTH;
		gbc_mmTF.anchor = GridBagConstraints.EAST;
		gbc_mmTF.insets = new Insets(zero, zero, zero, zero);
		gbc_mmTF.gridx = 0;
		gbc_mmTF.gridy = 3;
		sendSetJP.add(mmTF, gbc_mmTF);

		JLabel mmLB = new JLabel("mm");
		GridBagConstraints gbc_mmLB = new GridBagConstraints();
		gbc_mmLB.anchor = GridBagConstraints.WEST;
		gbc_mmLB.insets = new Insets(zero, zero, zero, zero);
		gbc_mmLB.gridx = 1;
		gbc_mmLB.gridy = 3;
		sendSetJP.add(mmLB, gbc_mmLB);

		timesTF = new JTextField();
		GridBagConstraints gbc_timesTF = new GridBagConstraints();
		gbc_timesTF.fill = GridBagConstraints.BOTH;
		gbc_timesTF.insets = new Insets(zero, zero, zero, zero);
		gbc_timesTF.gridx = 2;
		gbc_timesTF.gridy = 3;
		sendSetJP.add(timesTF, gbc_timesTF);
		timesTF.setColumns(4);

		JLabel timeLB = new JLabel("次数");
		GridBagConstraints gbc_timeLB = new GridBagConstraints();
		gbc_timeLB.insets = new Insets(zero, zero, zero, zero);
		gbc_timeLB.gridx = 3;
		gbc_timeLB.gridy = 3;
		sendSetJP.add(timeLB, gbc_timeLB);
	}

	public void sendSeparateDisplay(JPanel sendSetJP) {
		sendSeparateDisplayCBox = new JCheckBox("尾部自动带上");
		GridBagConstraints gbc_sendSeparateDisplayCBox = new GridBagConstraints();
		gbc_sendSeparateDisplayCBox.anchor = GridBagConstraints.NORTHEAST;
		gbc_sendSeparateDisplayCBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_sendSeparateDisplayCBox.gridwidth = 2;
		gbc_sendSeparateDisplayCBox.insets = new Insets(zero, zero, zero, zero);
		gbc_sendSeparateDisplayCBox.gridx = 0;
		gbc_sendSeparateDisplayCBox.gridy = 4;
		sendSetJP.add(sendSeparateDisplayCBox, gbc_sendSeparateDisplayCBox);

		@SuppressWarnings("unchecked")
		ComboBoxItem<SeparateDisplayEnum>[] separateDisplayItem = new ComboBoxItem[3];
		separateDisplayItem[0] = new ComboBoxItem<SeparateDisplayEnum>(SeparateDisplayEnum.CTRL);
		separateDisplayItem[1] = new ComboBoxItem<SeparateDisplayEnum>(SeparateDisplayEnum.CR);
		separateDisplayItem[2] = new ComboBoxItem<SeparateDisplayEnum>(SeparateDisplayEnum.LF);
		sendSeparateDisplayCB = new JComboBox<ComboBoxItem<SeparateDisplayEnum>>(separateDisplayItem);
		GridBagConstraints gbc_sendSeparateDisplayCB = new GridBagConstraints();
		gbc_sendSeparateDisplayCB.fill = GridBagConstraints.BOTH;
		gbc_sendSeparateDisplayCB.gridwidth = 2;
		gbc_sendSeparateDisplayCB.insets = new Insets(zero, zero, zero, zero);
		gbc_sendSeparateDisplayCB.gridx = 2;
		gbc_sendSeparateDisplayCB.gridy = 4;
		sendSetJP.add(sendSeparateDisplayCB, gbc_sendSeparateDisplayCB);
	}
	
	public void receiveAreaTool(JPanel receiveJP) {
		JLabel fontSizeLB = new JLabel("字体：");
		GridBagConstraints gbc_fontSizeLB = new GridBagConstraints();
		gbc_fontSizeLB.fill = GridBagConstraints.BOTH;
		gbc_fontSizeLB.insets = new Insets(zero, zero, zero, zero);
		gbc_fontSizeLB.gridx = 0;
		gbc_fontSizeLB.gridy = 0;
		receiveJP.add(fontSizeLB, gbc_fontSizeLB);
		
		JButton fontAddBT = new JButton("+");
		GridBagConstraints gbc_fontAddBT = new GridBagConstraints();
		gbc_fontAddBT.fill = GridBagConstraints.BOTH;
		gbc_fontAddBT.gridx = 1;
		gbc_fontAddBT.gridy = 0;
		receiveJP.add(fontAddBT, gbc_fontAddBT);

		
		JButton fontrReduceBT = new JButton("-");
		GridBagConstraints gbc_fontrReduceBT = new GridBagConstraints();
		gbc_fontrReduceBT.insets = new Insets(zero, zero, zero, zero);
		gbc_fontrReduceBT.fill = GridBagConstraints.BOTH;
		gbc_fontrReduceBT.gridx = 2;
		gbc_fontrReduceBT.gridy = 0;
		receiveJP.add(fontrReduceBT, gbc_fontrReduceBT);

		
		JCheckBox autoScrollCBOX = new JCheckBox("自动滚屏");
		GridBagConstraints gbc_autoScrollCBOX = new GridBagConstraints();
		gbc_autoScrollCBOX.insets = new Insets(zero, zero, zero, zero);
		gbc_autoScrollCBOX.gridx = 3;
		gbc_autoScrollCBOX.gridy = 0;
		receiveJP.add(autoScrollCBOX, gbc_autoScrollCBOX);
		
		showLineNumCBOX = new JCheckBox("显示行号");
		GridBagConstraints gbc_showLineNumCBOX = new GridBagConstraints();
		gbc_showLineNumCBOX.insets = new Insets(zero, zero, zero, zero);
		gbc_showLineNumCBOX.gridx = 4;
		gbc_showLineNumCBOX.gridy = 0;
		receiveJP.add(showLineNumCBOX, gbc_showLineNumCBOX);
		
		JCheckBox showTimeCBox = new JCheckBox("显示时间");
		GridBagConstraints gbc_showTimeCBox = new GridBagConstraints();
		gbc_showTimeCBox.anchor = GridBagConstraints.WEST;
		gbc_showTimeCBox.insets = new Insets(zero, zero, zero, zero);
		gbc_showTimeCBox.gridx = 5;
		gbc_showTimeCBox.gridy = 0;
		receiveJP.add(showTimeCBox, gbc_showTimeCBox);
		
		cleanReceiveBT = new JButton("清空接收区域");
		GridBagConstraints gbc_cleanReceiveBT = new GridBagConstraints();
		gbc_cleanReceiveBT.anchor = GridBagConstraints.EAST;
		gbc_cleanReceiveBT.insets = new Insets(zero, zero, zero, zero);
		gbc_cleanReceiveBT.gridx = 7;
		gbc_cleanReceiveBT.gridy = 0;
		receiveJP.add(cleanReceiveBT, gbc_cleanReceiveBT);

//		receiveTA = new JTextArea();
		superReceive = new SuperTextArea();
		receiveTA=superReceive.getTextArea();
		receiveTA.setLineWrap(true);
//		JPanel receiveSLP = new JPanel();
//		receiveSLP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//		receiveSLP.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_receiveSLP = new GridBagConstraints();
		gbc_receiveSLP.insets = new Insets(0, 0, 0, 0);
		
		gbc_receiveSLP.gridwidth = 8;
		gbc_receiveSLP.gridheight=1;
		gbc_receiveSLP.gridx = 0;
		gbc_receiveSLP.gridy = 1;
		gbc_receiveSLP.fill = GridBagConstraints.BOTH;
		receiveJP.add(superReceive, gbc_receiveSLP);
	}
	
	protected JTextArea receiveTA;
	protected SuperTextArea superReceive;
	private JPanel sendJP;

	public void sendArea(JPanel sendJP) {
		showSendCBox = new JCheckBox("显示发送");
		GridBagConstraints gbc_showSendCBox = new GridBagConstraints();
		gbc_showSendCBox.anchor = GridBagConstraints.WEST;
		gbc_showSendCBox.gridx = 0;
		gbc_showSendCBox.gridy = 0;
		sendJP.add(showSendCBox, gbc_showSendCBox);

		cleanSendAreaBT = new JButton("清空发送区");
		GridBagConstraints gbc_cleanSendAreaBT = new GridBagConstraints();
		gbc_cleanSendAreaBT.anchor = GridBagConstraints.EAST;
		gbc_cleanSendAreaBT.gridx = 1;
		gbc_cleanSendAreaBT.gridy = 0;
		sendJP.add(cleanSendAreaBT, gbc_cleanSendAreaBT);

		sendTA = new JTextArea();
		sendTA.setLineWrap(true);
		JScrollPane sendSLP = new JScrollPane(sendTA);
		sendSLP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sendSLP.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_sendSLP = new GridBagConstraints();
		gbc_sendSLP.gridwidth = 2;
		gbc_sendSLP.gridx = 0;
		gbc_sendSLP.gridy = 1;
		gbc_sendSLP.fill = GridBagConstraints.BOTH;
		sendJP.add(sendSLP, gbc_sendSLP);

		sendBT = new JButton("手动发送");
		GridBagConstraints gbc_sendBT = new GridBagConstraints();
		gbc_sendBT.anchor = GridBagConstraints.EAST;
		gbc_sendBT.gridx = 1;
		gbc_sendBT.gridy = 2;
		sendJP.add(sendBT, gbc_sendBT);
	}
	

	
}
