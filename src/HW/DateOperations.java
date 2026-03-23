package HW;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class DateOperations {
	
	public static String[] sortStringDates(String[] dates){
		if(Objects.isNull(dates))
			throw new IllegalArgumentException("Date array is null");
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("[yyyy-MM-dd][dd/MM/yyyy]");

		String[] result = Arrays.stream(dates)
								.filter(Objects::nonNull)
								.peek(date -> {
									try {
										LocalDate.parse(date,dateFormat);
									}catch(DateTimeParseException e) {
										throw new IllegalArgumentException("date '%s' from array can't be parsed".formatted(date));
									}
								})
								.sorted((date1, date2) -> LocalDate.parse(date1,dateFormat)
											.compareTo(LocalDate.parse(date2,dateFormat)))
								.toArray(String[]::new);
		
		return result;
	}
	
	public static int getAge(String birthDate, String currentDate) {
		LocalDate birth;
		LocalDate current;
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("[yyyy-MM-dd][dd/MM/yyyy]");
		
		if(Objects.isNull(currentDate))
			current = LocalDate.now();
		else {
			try {
				current = LocalDate.parse(currentDate, dateFormat);
			} catch (DateTimeParseException e) {
				throw new IllegalArgumentException("Illegal date format for current date");
			}
		}
		if(Objects.isNull(birthDate))
			throw new IllegalArgumentException("Birthdate is null");
		try {
			birth = LocalDate.parse(birthDate, dateFormat);
		}catch(DateTimeParseException e) {
			throw new IllegalArgumentException("Illegal date format for birthday date");
		}
		if(current.compareTo(birth) < 0)
			throw new IllegalArgumentException("current date is less then birthday date");
		return (int) ChronoUnit.YEARS.between(birth, current);
	}
	
	public static void printCurrentTime(String zoneSubstring) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		if(Objects.isNull(zoneSubstring))
			System.out.println("<%s> - ".formatted(ZoneId.systemDefault().getId()) + ZonedDateTime.now().format(dtf));
		else {
			
			Optional<String> zn = ZoneId.getAvailableZoneIds().stream().filter(zone -> zone.contains(zoneSubstring)).findFirst();
			if(zn.isEmpty())
				throw new IllegalArgumentException("Invalid format of zone");
			System.out.println("<%s> - ".formatted(zn.get()) + ZonedDateTime.now(ZoneId.of(zn.get())).format(dtf));
		}
		
	}
}
