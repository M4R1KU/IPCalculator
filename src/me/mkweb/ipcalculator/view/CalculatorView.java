package me.mkweb.ipcalculator.view;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.Color;

@SuppressWarnings("serial")
public class CalculatorView extends JFrame {
	private JButton btnBerechnen;
	private JButton btnLschen; 
	private JTextField iptxt;
	private JTextField subnettxt;
	private JTextField cidrtxt;
	private JTextField numiptxt;
	private JTextField numsubnettxt;
	private JTextField hostpnettxt;
	private JTextField ipcidrtxt;

	public CalculatorView() {
		super();
		initUi();		
	}

	/**
	 * inits the User Interface
	 */
	public void initUi() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		setBackground(Color.DARK_GRAY);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(454, 446);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("Panel.background"));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblIpAddresse = new JLabel("IP Addresse");
		lblIpAddresse.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIpAddresse.setBounds(30, 66, 162, 24);
		panel.add(lblIpAddresse);
		
		JLabel lblSubnetzmaske = new JLabel("Subnetzmaske");
		lblSubnetzmaske.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSubnetzmaske.setBounds(30, 95, 107, 24);
		panel.add(lblSubnetzmaske);
		
		JLabel lblCidr = new JLabel("CIDR-Notation");
		lblCidr.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCidr.setBounds(30, 124, 94, 24);
		panel.add(lblCidr);
		
		JLabel lblHostsProNetz = new JLabel("Hosts pro Netz");
		lblHostsProNetz.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblHostsProNetz.setBounds(30, 237, 162, 24);
		panel.add(lblHostsProNetz);
		
		JLabel lblNumerischeIpAddresse = new JLabel("Numerische IP Addresse");
		lblNumerischeIpAddresse.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNumerischeIpAddresse.setBounds(30, 179, 162, 24);
		panel.add(lblNumerischeIpAddresse);
		
		JLabel lblNumerischeSubnetzmaske = new JLabel("Numerische Subnetzmaske");
		lblNumerischeSubnetzmaske.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNumerischeSubnetzmaske.setBounds(30, 208, 183, 24);
		panel.add(lblNumerischeSubnetzmaske);
		
		JLabel lblIpMitCidr = new JLabel("Netzadresse mit CIDR");
		lblIpMitCidr.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIpMitCidr.setBounds(30, 294, 162, 24);
		panel.add(lblIpMitCidr);
		
		btnBerechnen = new JButton("Berechnen");
		
		btnBerechnen.setBounds(30, 349, 97, 25);
		panel.add(btnBerechnen);
		
		iptxt = new JTextField();
		iptxt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		iptxt.setBounds(249, 66, 160, 24);
		panel.add(iptxt);
		
		subnettxt = new JTextField();
		subnettxt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		subnettxt.setBounds(249, 95, 160, 24);
		panel.add(subnettxt);
		
		cidrtxt = new JTextField();
		cidrtxt.setToolTipText("CIDR-Notation in Format: z.B. 24 oder 8");
		cidrtxt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cidrtxt.setBounds(249, 124, 160, 24);
		panel.add(cidrtxt);
		
		numiptxt = new JTextField();
		numiptxt.setEnabled(false);
		numiptxt.setEditable(false);
		numiptxt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		numiptxt.setBounds(249, 179, 160, 24);
		panel.add(numiptxt);
		
		numsubnettxt = new JTextField();
		numsubnettxt.setEnabled(false);
		numsubnettxt.setEditable(false);
		numsubnettxt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		numsubnettxt.setBounds(249, 208, 160, 24);
		panel.add(numsubnettxt);
		
		hostpnettxt = new JTextField();
		hostpnettxt.setEnabled(false);
		hostpnettxt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		hostpnettxt.setEditable(false);
		hostpnettxt.setBounds(249, 239, 160, 24);
		panel.add(hostpnettxt);
		
		ipcidrtxt = new JTextField();
		ipcidrtxt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ipcidrtxt.setBounds(249, 294, 160, 24);
		panel.add(ipcidrtxt);
		
		JLabel lblIpCalculator = new JLabel("IP Calculator");
		lblIpCalculator.setFont(new Font("Courier New", Font.BOLD, 26));
		lblIpCalculator.setHorizontalAlignment(SwingConstants.CENTER);
		lblIpCalculator.setBounds(0, 13, 448, 30);
		panel.add(lblIpCalculator);
		
		btnLschen = new JButton("L\u00F6schen");
		btnLschen.setBounds(312, 349, 97, 25);
		panel.add(btnLschen);
		
		setVisible(true);
		
	}
	
	
	/*
	 * getters and setters for the controller to show the data of the model 
	 * and to get the userinput
	 */
	public String getIptxt() {
		return iptxt.getText();
	}

	public void setIptxt(String ip) {
		this.iptxt.setText(ip);
	}

	public String getSubnettxt() {
		return subnettxt.getText();
	}

	public void setSubnettxt(String subnetmask) {
		this.subnettxt.setText(subnetmask);
	}

	public String getCidrtxt() {
		return cidrtxt.getText();
	}

	public void setCidrtxt(String cidr) {
		this.cidrtxt.setText(cidr);
	}

	public String getIpcidrtxt() {
		return ipcidrtxt.getText();
	}

	public void setIpcidrtxt(String ipcidr) {
		this.ipcidrtxt.setText(ipcidr);
	}
	
	public void setNumiptxt(String numip) {
		this.numiptxt.setText(numip);
	}

	public void setNumsubnettxt(String numsubnetmask) {
		this.numsubnettxt.setText(numsubnetmask);
	}

	public void setHostpnettxt(String hostpernet) {
		this.hostpnettxt.setText(hostpernet);
	}

	public void showMessageBox(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	public void addCalcListener(ActionListener al) {
		btnBerechnen.addActionListener(al);
	}
	public void addDelListenere(ActionListener al) {
		btnLschen.addActionListener(al);
	}
}
