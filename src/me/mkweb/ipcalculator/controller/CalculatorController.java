package me.mkweb.ipcalculator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import me.mkweb.ipcalculator.exception.IPNotValidException;
import me.mkweb.ipcalculator.model.IP;
import me.mkweb.ipcalculator.view.CalculatorView;

public class CalculatorController {

	private IP model;
	private CalculatorView view;

	public CalculatorController(IP model, CalculatorView view) {
		super();
		this.model = model;
		this.view = view;

		view.addCalcListener(new CalculateListener());
		view.addDelListenere(new DeleteListener());
	}

	/**
	 * shows the user all the information that the model has calculated
	 */
	private void fillView() {
		view.setIptxt(model.getSymbolicIP());
		view.setSubnettxt(model.getSymbolicSubnetmask());
		view.setCidrtxt(Integer.toString(model.getSubnetBits()));
		view.setNumiptxt(Long.toString(model.getNumericIP()));
		view.setNumsubnettxt(Long.toString(model.getNumericSubnetmask()));
		view.setHostpnettxt(Long.toString(model.getHostspernet()));
		view.setIpcidrtxt(model.getSymbolicIpCidr());
	}

	/**
	 * handles the input data from the user
	 * case 1 => ip and subnetaddress are available
	 * case 2 => ip and subnetbits are available
	 * case 3 => ip with cidr ist available
	 * 
	 * if one thing of case 1 or 2 is missing a textbox will be shown
	 * or if the users gives no input an alert pups off
	 * 
	 * if all is ok then the model will getting the information from the userinput
	 */
	class CalculateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (!view.getIptxt().isEmpty()) {

				try {
					model.setSymbolicIP(view.getIptxt());
					if (!view.getSubnettxt().isEmpty()) {

						model.setSymbolicSubnetmask(view.getSubnettxt());

						fillView();

					} else if (!view.getCidrtxt().isEmpty()) {

						model.setSubnetBitsAsString(view.getCidrtxt());

						fillView();

					} else {
						view.showMessageBox("F�llen Sie bitte das Feld Subnetzmaske oder CIDR-Notation aus!");
					}

				} catch (IPNotValidException ex) {
					view.showMessageBox(ex.getMessage());
				}

			} else if (!view.getIpcidrtxt().isEmpty()) {
				try {
					model.validateIpCidr(view.getIpcidrtxt());
					fillView();
				} catch (IPNotValidException ex) {
					view.showMessageBox(ex.getMessage());
				}
				

			} else {
				view.showMessageBox("F�llen Sie bitte gen�gend Felder aus!");
			}

		}
	}
	
	/**
	 * Listener to set all textfield values to null
	 */
	class DeleteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			view.setIptxt("");
			view.setSubnettxt("");
			view.setCidrtxt("");
			view.setNumiptxt("");
			view.setNumsubnettxt("");
			view.setHostpnettxt("");
			view.setIpcidrtxt("");			
		}
		
	}
}
