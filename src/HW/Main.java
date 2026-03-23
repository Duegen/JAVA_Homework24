package HW;

public class Main {
	public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        logger1.log("Application started");
        logger2.log("User logged in");
        logger1.log("Page opened");

        System.out.println("logger1 == logger2 -> " + (logger1 == logger2));
        System.out.println();

        System.out.println("Logs through logger1:");
        logger1.printLogs();

        System.out.println();
        System.out.println("Logs through logger2:");
        logger2.printLogs();
    }
}
