import entity.*;
import repository.*;
import services.*;
import controller.MenuController;
import utils.DataSeeder;

public class Main {

    public static void main(String[] args) {
        UserRepository userRepo = new UserRepository();
        ResourceRepository resourceRepo = new ResourceRepository();
        BookingRepository bookingRepo = new BookingRepository();

       
        DataSeeder.seedData(userRepo, resourceRepo);

        UserService userService = new UserService(userRepo);
        ResourceService resourceService = new ResourceService(resourceRepo, bookingRepo);
        BookingService bookingService = new BookingService(bookingRepo);
        ReportService reportService = new ReportService();

        MenuController menuController = new MenuController(userService, resourceService, bookingService, reportService);

        menuController.start();
    }
}
