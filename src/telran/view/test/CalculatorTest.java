package telran.view.test;

import java.util.ArrayList;

import telran.view.ConsoleInputOutput;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class CalculatorTest {

	public static void main(String[] args) {
		Menu menu = new Menu("Calculator", getItems());
		menu.perform(new ConsoleInputOutput());

	}

	private static Item[] getItems() {
		Item[] res = {
			Item.of("Add numbers", CalculatorTest::add),
			Item.of("Subtract numbers", CalculatorTest::subtract),
			Item.of("Multiply numbers", CalculatorTest::multiply),
			Item.of("Divide numbers", CalculatorTest::divide),
			Item.exit()
			
		};
		return res;
	}
	static void add(InputOutput io) {
		int numbers[] = enterNumbers(io);
		io.writeLine(numbers[0] + numbers[1]);
	}
	static void subtract(InputOutput io) {
		int numbers[] = enterNumbers(io);
		io.writeLine(numbers[0] - numbers[1]);
	}
	static void multiply(InputOutput io) {
		int numbers[] = enterNumbers(io);
		io.writeLine(numbers[0] * numbers[1]);
	}
	private static int[] enterNumbers(InputOutput io) {
		int res[] = new int[2];
		res[0] = io.readInt("enter first number", "no number");
		res[1] = io.readInt("enter second number", "no number");
		return res;
	}

	static void divide(InputOutput io) {
		int numbers[] = enterNumbers(io);
		io.writeLine(numbers[0] / numbers[1]);
	}

}
