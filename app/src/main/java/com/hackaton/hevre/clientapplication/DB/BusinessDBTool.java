import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
/**
 *  singelton. implements interactions with the database of all businesses.
 */
public class BusinessDBTool
{
    /* constants */
    public static final String DBNAME = "businesses.db";
    public static final String BUSINESS_IN_RANGE_STATEMENT = "select * from Locations"
            + " where (lon between %1$f AND %2$f) AND (lat between %3$f AND %4$f)";
    // distance calculations
    public static final int EARTH_RADIUS = 6378;
    public static final double EARTH_CIRCUMFERENCE = 2 * Math.PI * EARTH_RADIUS;
    public static final double KM_PER_DEGREE = EARTH_CIRCUMFERENCE / 360;

    // range indices
    public static final int LON_EAST_INDEX = 0;
    public static final int LON_WEST_INDEX = 1;
    public static final int LAT_SOUTH_INDEX = 2;
    public static final int LAT_NORTH_INDEX = 3;

    /* static members */
    private static BusinessDBTool instance = null;

    /* data members */
    private Connection connection;
     
    /* static methods */
    /**
     *  convert celsius to radians
     *  @param celsiusDegree degree in celsius units.
     */
    private static double toRadians(double celsiusDegree) {
        return (celsiusDegree / 180) * Math.PI;
    }

    /**
     * calculates the the range of longitudes and latitudes (degrees) in order to span distance of the given range
     * from each side of the given center coordinates.
     *  @param lonCenter the longitude of the point to be the center of the range.
     *  @param latCenter the latitude of the point to be the center of the range.
     *  @param range the range should be expanded to each side from the given center point. units are in 'km'
     *  @return array of flots contains the eastern and western longitudes and after that the southern and northern latitudes.
     */
    private static double[] calculateRange(double lonCenter, double latCenter, double range) {
        double  latDiff = range / KM_PER_DEGREE;
        double lonDiff = range / (KM_PER_DEGREE * Math.cos(toRadians(latCenter)));

        double[] result = new double[4];
        result[LON_EAST_INDEX] = lonCenter - lonDiff;
        result[LON_WEST_INDEX] = lonCenter + lonDiff;
        result[LAT_SOUTH_INDEX] = latCenter - latDiff;
        result[LAT_NORTH_INDEX] = latCenter + latDiff;

        return result;
    }

    /**
     *  instance getter method
     *  @return the single instance of this object
     */
    public static BusinessDBTool getInstance() throws ClassNotFoundException {
        if (instance == null) {
            instance = new BusinessDBTool(DBNAME);
        }

        return instance;
    }

    /* Ctors */
    private BusinessDBTool(String dbname) throws ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");
        this.connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:businesses.db");
        }
        catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        // finally
        // {
        // try
        // {
        // if(connection != null)
        // connection.close();
        // }
        // catch(SQLException e)
        // {
        // connection close failed.
        // System.err.println(e);
        // }
        // }
        // }
    }
    
    /* methods */
    /**
     *  retrieving all businesses from the database in the range around the given center point coordinates (lon, lat)
     *  @param lon the center point longitude.
     *  @param lat the center point latitude.
     *  @param squareRange the range to expand around the center point. units in km.
     *  @return list of the names of the relevant busineses.
     */
    public ArrayList<String> getBusinessesInRange(double lon, double lat, double squareRange) throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.

        double[] range = calculateRange(lon, lat, squareRange);
        String query = String.format(BUSINESS_IN_RANGE_STATEMENT, range[LON_EAST_INDEX], range[LON_WEST_INDEX],  range[LAT_SOUTH_INDEX], range[LAT_NORTH_INDEX]);
        System.out.println(query);
        ResultSet rs = statement.executeQuery(query);

        ArrayList<String> results = new ArrayList<>();
        while(rs.next()) {
            results.add(rs.getString("name"));
        }

        return results;
    }

    /**
     *  example of use
     */
    public static void test(String[] args) throws ClassNotFoundException, SQLException {

        BusinessDBTool dbTool = BusinessDBTool.getInstance();
        ArrayList<String> businesses = dbTool.getBusinessesInRange(34.91188640000001, 32.4384243, 0.1);
        for (String businessName : businesses) {
            System.out.println(businessName);
        }
    }
}