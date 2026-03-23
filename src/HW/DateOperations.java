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
								.map(date -> {
									if(Objects.isNull(date))
										throw new IllegalArgumentException("date is null");	
									try {
										LocalDate.parse(date,dateFormat);
										return date;
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
			String[] zones = zoneSubstring.trim().split(" ");
			Arrays.stream(zones)
				.filter(zoneStr -> {
					Optional<String> zn = ZoneId.getAvailableZoneIds().stream().filter(zone -> zone.contains(zoneStr)).findFirst();
					return zn.isPresent() ? true : false; 
				})
				.forEach(zoneStr -> {
					Optional<String> zn = ZoneId.getAvailableZoneIds().stream().filter(zone -> zone.contains(zoneStr)).findFirst();
					System.out.println("<%s> - ".formatted(zn.get()) + ZonedDateTime.now(ZoneId.of(zn.get())).format(dtf));
				});
		}
		
	}
}
