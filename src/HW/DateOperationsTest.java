package HW;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

class DateOperationsTest {

	String[] dates = {"2000-12-01", "10/12/2000", "1970-08-12", "2010-10-05"};
	String[] expectedDates = {"1970-08-12", "2000-12-01", "10/12/2000", "2010-10-05"};
	
	@Test
	public void datesSortTestIllegalArg() {
		assertThrows(IllegalArgumentException.class, () -> {
			DateOperations.sortStringDates(null);
		});
		
		String[] datesWithNull = {"2000-12-01", null};
		assertThrows(IllegalArgumentException.class, () -> {
			DateOperations.sortStringDates(datesWithNull);
		});
		
		String[] datesIllegal = {"2000-12-01", "01.4.1900"};
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			DateOperations.sortStringDates(datesIllegal);
		});
		assertEquals(exception.getMessage(), "date '01.4.1900' from array can't be parsed");
	}
	
	@Test
	public void datesSortTestEmpty()
	{
		String[] empty = {};
		String[] actual = DateOperations.sortStringDates(empty);
		assertArrayEquals(empty, actual);
	}
	
	@Test
	public void datesSortTest()
	{
		String[] actual = DateOperations.sortStringDates(dates);
		assertArrayEquals(expectedDates, actual);
	}
	
	@Test
	public void ageTestIllegalArg()
	{
		assertThrows(IllegalArgumentException.class, () -> {
			DateOperations.getAge(null, null);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			DateOperations.getAge("01.02.1988", null);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			DateOperations.getAge("12/04/1961", "01.02.1988");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			DateOperations.getAge("12/04/1961", "2/04/1960");
		});
	}
	
	@Test
	public void ageTest()
	{
		String birthDate = "12/04/1961";
		String currentDate = "11/11/2018";
		assertEquals(57, DateOperations.getAge(birthDate, currentDate));
		
		birthDate = "1961-12-12";
		currentDate = "2018-11-11";
		assertEquals(56, DateOperations.getAge(birthDate, currentDate));
		
		assertEquals(64, DateOperations.getAge(birthDate, null));
		assertEquals(0, DateOperations.getAge(birthDate, birthDate));

	}
	
//	@Test
//	public void printCurrentTimeForZonesTestIllegalArg() {
//		assertThrows(IllegalArgumentException.class, () -> {
//			DateOperations.printCurrentTime("zone");
//		});
//	}
	
	@Test
	public void printCurrentTimeForZonesTestNull()
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		System.setOut(new PrintStream(outputStream));
		DateOperations.printCurrentTime(null);
		
		assertEquals("<%s> - ".formatted(ZoneId.systemDefault().getId()) + ZonedDateTime.now().format(dtf)
				, outputStream.toString().trim());
		System.setOut(System.out);
	}
	
	@Test
	public void printCurrentTimeForZonesTest()
	{
		//ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		//System.setOut(new PrintStream(outputStream));
		DateOperations.printCurrentTime("Samara London New_York");
		
//		ZoneId zn = ZoneId.of("Europe/Samara");
//		assertEquals("<%s> - ".formatted("Europe/Samara") + ZonedDateTime.now(zn).format(dtf), outputStream.toString().trim());
//		System.setOut(System.out);
	}

}
