class Flight {

    private String id;
    private String source;
    private String destination;
    private double price;
    private int seatCount = 60;

    public Flight(String id,String source,String destination,double price,int seatcount)
    {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.price = price;
        this.seatCount = seatcount;
    }
    
    public String getId()
    {
        return id;
    }

    public String getSource()
    {
        return source;
    }

    public String getDestination()
    {
        return destination;
    }

    public double getPrice()
    {
        return price;
    }

    public int getSeatCount()
    {
        return seatCount;
    }

    public void setSeatCount(int seatCount)
    {
        this.seatCount = seatCount;
    }
}
