package me.mkweb.ipcalculator;

import me.mkweb.ipcalculator.controller.CalculatorController;
import me.mkweb.ipcalculator.model.IP;
import me.mkweb.ipcalculator.view.CalculatorView;

public class Main {

	public static void main(String[] args) {
		IP model = new IP();
		CalculatorView view = new CalculatorView();
		new CalculatorController(model, view);
	}

}
