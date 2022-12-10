package telran.view;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.junit.jupiter.api.*;

class InputOutputTest {
	InputOutput io = new ConsoleInputOutput();

	@Test
	@Disabled
	void readObjectTest() {
		Integer[] array = io.readObject("Enter numbers separated by space", "no number ", s -> {

			String strNumbers[] = s.split(" ");
			return Arrays.stream(strNumbers).map(str -> Integer.parseInt(str)).toArray(Integer[]::new);

		});
		io.writeLine(Arrays.stream(array).collect(Collectors.summarizingInt(x -> x)));

	}

	@Test
	@Disabled
	void readIntMinMax() {
		Integer res = io.readInt("Enter any number in range [1, 40]", "no number ", 1, 40);
		io.writeLine(res);
	}

	@Test
	@Disabled
	void readOptionTest() {
		List<String> departments = Arrays.asList("QA", "Management", "Development");
		String department = io.readOption("Enter department from " + departments, "Wrong department", departments);
		assertTrue(departments.contains(department));
	}
	@Test
	@Disabled
	void readPredicateTest() {
		String ipAddress = io.readPredicate("Enter IP address", "Wrong IP addres", s -> 
		s.matches(ipV4Regex()));
		assertTrue(ipAddress.matches(ipV4Regex()));
	}
	

	 String ipOctetRegex() {
		// string expression of number 0-255 with possible leading zeros
		// \\d == [0-9]
		return "\\d\\d?|[0,1]\\d\\d|2[0-4]\\d|25[0-5]";
	}

	 String ipV4Regex() {

		return String.format("((%1$s)\\.){3}(%1$s)", ipOctetRegex());
	}
	 @Test
	 @Disabled
	 void readDateISOTest() {
		 LocalDate dateAs = io.readDate("Enter any date YYYY-MM-DD",
				 "no date in ISO format");
		 io.writeLine(dateAs + " has been entered");
		 
		 
	 }
	 @Test
	 //@Disabled
	 void readDateTest() {
		 String format = "d/M/y";
		 LocalDate birthdateAS = LocalDate.of(1799, 6, 6);
		 LocalDate date = io.readDate("Enter birthdate of Pushkin " + format,
				 "no date in format " + format, format);
		io.writeLine(String.format("Entered date %s is %s Pushkin's birthdate ",  date, 
				date.equals(birthdateAS) ? "" : "not" ));
		 
	 }

}
