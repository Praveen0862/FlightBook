import java.util.ArrayList;
import java.util.List;

class User {
    
    private String username;
    private String password;
    private List<String> bookings;

    public User(String username , String password)
    {
        this.username = username;
        this.password = password;
        this.bookings = new ArrayList<>();
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public void addBooking(String booking)
    {
        bookings.add(booking);
    }

    public List<String> getBookings()
    {
        return bookings;
    }
}
