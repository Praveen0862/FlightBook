import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main
{

    private static List<User> users = new ArrayList<>();
    private static List<Flight> flights = new ArrayList<>();
    private static List<String> allBookings = new ArrayList<>();
    private static Admin admin = new Admin("admin","admin123");

    public static void main(String args[])
    {

        initializeFlights();
        initializeUsers();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Flight Booking!...");

        while(true)
        {
            System.out.println();
            System.out.println("Select User Type:");
            System.out.println("    a. User");
            System.out.println("    b. Admin");
            System.out.println("    e. Exit");
            System.out.println();
            System.out.print("Enter your Choice: ");
            String userType = scanner.nextLine();

            if (userType.equalsIgnoreCase("a"))
            {
                handleUser(scanner);
            }
            else if (userType.equalsIgnoreCase("b"))
            {
                handleAdmin(scanner);
            }
            else if (userType.equalsIgnoreCase("e"))
            {
                System.out.println("Thank you for using our Flight Booking!...");
                break;
            }
            else 
            {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void handleUser(Scanner scanner)
    {
        User currentUser = loginUser(scanner);

        if (currentUser == null)
        {
            return;
        }

        while (true)
        {
            System.out.println();
            System.out.println("User Menu:");
            System.out.println("    1. Search Flights");
            System.out.println("    2. Book a Flight");
            System.out.println("    3. My Bookings");
            System.out.println("    4. Logout");
            System.out.println();
            System.out.print("Enter your choice: ");

            int userChoice  = scanner.nextInt();
            scanner.nextLine();

            switch(userChoice)
            {
                case 1:
                    searchFlights(scanner);
                    break;
                case 2:
                    bookFlight(scanner, currentUser);
                    break;
                case 3:
                    displayUserBookings(currentUser);
                    break;
                case 4:
                    System.out.println("Logged out successfully");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }

        }
    }

    private static void handleAdmin(Scanner scanner)
    {
        Admin currentAdmin = loginAdmin(scanner);
        if (currentAdmin == null)
        {
            return;
        }

        while (true)
        {
            System.out.println();
            System.out.println("Admin Menu:");
            System.out.println("    1. Add Flight");
            System.out.println("    2. Remove Flight");
            System.out.println("    3. View Bookings");
            System.out.println("    4. Logout");
            System.out.println();
            System.out.print("Enter your choice: ");
            int adminChoice = scanner.nextInt();
            scanner.nextLine();

            switch(adminChoice)
            {
                case 1:
                    addFlight(scanner);
                    break;
                case 2:
                    removeFlight(scanner);
                    break;
                case 3:
                    viewBookings(scanner);
                    break;
                case 4:
                    System.out.println("Logged out successfully");
                    return ;
                default:
                    System.out.println("Invalid choice. Try Again");
                    break;
            }
        }
    }

    private static User loginUser(Scanner scanner)
    {
        System.out.println();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users)
        {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
            {
                System.out.println("Logged in successfully as " + username);
                return user;
            }
        }

        System.out.println("Invalid username or password. Login Failed.");
        return null;
    }
    
    private static Admin loginAdmin(Scanner scanner)
    {
        System.out.println();
        System.out.print("Enter username : ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (admin.getUsername().equals(username) && admin.getPassword().equals(password))
        {
            System.out.println("Logged in successfully as " + username);
            return admin;
        }

        System.out.println("Invalid username or password. Login failed.");
        return null;
    }

    private static void searchFlights(Scanner scanner)
    {
        System.out.println();
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter time (HH:MM): ");
        String time = scanner.nextLine();

        System.out.println("Available Flights on " + date + " " + time + ":");
        for (Flight flight : flights)
        {
            System.out.println(flight.getId() + " - " + flight.getSource() + " to " + flight.getDestination() + " "  + flight.getPrice() + " rupees.");
        }
    }

    public static void bookFlight(Scanner scanner,User user)
    {
        System.out.print("Enter flight ID: ");
        String flightId = scanner.nextLine();

        Flight selectedFlight = null;
        for (Flight flight : flights)
        {
            if (flight.getId().equals(flightId))
            {
                selectedFlight = flight;
                break;
            }
        }

        if (selectedFlight == null)
        {
            System.out.println("Invalid Flight ID. Booking failed.");
            return;
        }

        int seatCount = selectedFlight.getSeatCount();

        if (seatCount == 0)
        {
            System.out.println("No seats available on the selected flight.");
            return ;
        }

        if (seatCount == 1)
        {
            System.out.println("There is only 1 seat left.");
        }
        else
        {
            System.out.println("There are " + seatCount + " seats left.");
        }
        
        System.out.print("Enter the number of seats to Book : ");
        int neededSeats = scanner.nextInt();
        scanner.nextLine();

        if(neededSeats <= 0 || neededSeats > seatCount)
        {
            System.out.println("Sorry! Seats not available.");
            return;
        }

        selectedFlight.setSeatCount(seatCount-neededSeats);
        String booking = user.getUsername() + " - " + selectedFlight.getId() + " - " + Integer.toString(neededSeats) + " seats.";
        allBookings.add(booking);
        user.addBooking(booking);

        System.out.println();
        System.out.println("Booking successful.");
        System.out.println("Flight ID : " + selectedFlight.getId() + 
                ", Source : " + selectedFlight.getSource() + 
                ", Destination : " + selectedFlight.getDestination() + 
                ", Seats : " + neededSeats + 
                ", Price : " + neededSeats * (selectedFlight.getPrice()) 
                + " Rupees.");

    }

    private static void displayUserBookings(User user)
    {
        System.out.println();
        System.out.println("My bookings : ");
        List<String> bookings = user.getBookings();
        if (bookings.isEmpty())
        {
            System.out.println("No Bookings found.");
        }
        else
        {
            for (String booking : bookings)
            {
                System.out.println(booking);
            }
        }
    }

    private static void addFlight(Scanner scanner)
    {
        System.out.println();
        System.out.print("Enter flight ID : ");
        String flightId = scanner.nextLine();
        System.out.print("Enter source : ");
        String source = scanner.nextLine();
        System.out.print("Enter destination : ");
        String destination = scanner.nextLine();
        System.out.print("Enter price : ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter seat count : ");
        int seatCount = scanner.nextInt();
        scanner.nextLine();

        Flight newFlight = new Flight(flightId,source,destination,price,seatCount);
        flights.add(newFlight);

        System.out.println("Flight added successfully.");
    }

    public static void removeFlight(Scanner scanner)
    {
        System.out.println();
        System.out.println("Enter flight ID : ");
        String flightId = scanner.nextLine();

        Flight selectedFlight = null;
        for (Flight flight : flights)
        {
            if (flight.getId().equals(flightId))
            {
                selectedFlight = flight;
                break;
            }
        }

        if (selectedFlight == null)
        {
            System.out.println("Invalid flight ID. Removal failed.");
            return ;
        }

        flights.remove(selectedFlight);

        System.out.println("Flight removed successfully");
    }

    private static void viewBookings(Scanner scanner)
    {
        System.out.println();
        System.out.println("Enter flight ID : ");
        String flightId = scanner.nextLine();
        
        System.out.println("Bookings for Flight ID " + flightId + " : ");
        
        for (String booking : allBookings)
        {
            if (booking.contains(flightId))
            {
                System.out.println("    " + booking);
            }
        }
    }

    public static void initializeUsers()
    {
        User user1 = new User("user1","password");
        User user2 = new User("user2","password");
        User user3 = new User("user3","password");

        users.add(user1);
        users.add(user2);
        users.add(user3);
    }

    public static void initializeFlights()
    {
        Flight flight1 = new Flight("001","Delhi","Bengaluru",5000,56);
        Flight flight2 = new Flight("002","Delhi","Kolkata",3250,58);
        Flight flight3 = new Flight("003","Chennai","Mumbai",3100,48);
        Flight flight4 = new Flight("004","Hyderabad","Coimbatore",2800,52);
        Flight flight5 = new Flight("005","Pune","Lucknow",3600,60);

        flights.add(flight1);
        flights.add(flight2);
        flights.add(flight3);
        flights.add(flight4);
        flights.add(flight5);

    }

}