package telran.net.test;

import telran.view.InputOutput;
import telran.view.Item;

public class CalculatorMenu {
	private static Calculator calculator;

	public static Item[] getCalculatorItems(Calculator calculator) {
		CalculatorMenu.calculator = calculator;
		Item[] res = { Item.of("Add numbers", CalculatorMenu::add),
				Item.of("Subtract numbers", CalculatorMenu::subtract),
				Item.of("Multiply numbers", CalculatorMenu::multiply),
				Item.of("Divide numbers", CalculatorMenu::divide),

		};
		return res;

	}

	static void add(InputOutput io) {
		double numbers[] = enterNumbers(io);
		io.writeLine(calculator.add(numbers[0], numbers[1]));
	}

	static void subtract(InputOutput io) {
		double numbers[] = enterNumbers(io);
		io.writeLine(calculator.subtract(numbers[0], numbers[1]));
	}

	static void multiply(InputOutput io) {
		double numbers[] = enterNumbers(io);
		io.writeLine(calculator.multiply(numbers[0], numbers[1]));
	}

	private static double[] enterNumbers(InputOutput io) {
		double res[] = new double[2];
		res[0] = io.readDouble("enter first number", "no number");
		res[1] = io.readDouble("enter second number", "no number");
		return res;
	}

	static void divide(InputOutput io) {
		double numbers[] = enterNumbers(io);
		io.writeLine(calculator.divide(numbers[0], numbers[1]));
	}

}
