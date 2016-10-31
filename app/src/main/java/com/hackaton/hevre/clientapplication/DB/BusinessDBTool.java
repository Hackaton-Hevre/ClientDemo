package com.hackaton.hevre.clientapplication.DB;

import java.sql.SQLException;
/**
 *  singelton. implements interactions with the database of all businesses.
 */
public class BusinessDBTool
{
    /* constants */
    private static final String BUSINESS_IN_RANGE_STATEMENT = "select * from Locations"
            + " where (lon between %1$f AND %2$f) AND (lat between %3$f AND %4$f)";
//    public static final String DBPATH = "/src/main/res/assets/database/businesses.db";

    // distance calculations
    private static final int EARTH_RADIUS = 6378;
    private static final double EARTH_CIRCUMFERENCE = 2 * Math.PI * EARTH_RADIUS;
    private static final double KM_PER_DEGREE = EARTH_CIRCUMFERENCE / 360;

    // range indices
    private static final int LON_EAST_INDEX = 0;
    private static final int LON_WEST_INDEX = 1;
    private static final int LAT_SOUTH_INDEX = 2;
    private static final int LAT_NORTH_INDEX = 3;

    /* static members */
//    private static BusinessDBTool instance = null;

    /* data members */
//    private Connection connection;
    /* the absolute path to the app main package */
//    private String appAbsDir;
     
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
     *  @param radius the range should be expanded to each side from the given center point. units are in 'km'
     *  @return array of flots contains the eastern and western longitudes and after that the southern and northern latitudes.
     */
    private static double[] calculateRange(double lonCenter, double latCenter, double radius) {
        double  latDiff = radius / KM_PER_DEGREE;
        double lonDiff = radius / (KM_PER_DEGREE * Math.cos(toRadians(latCenter)));

        double[] result = new double[4];
        result[LON_EAST_INDEX] = lonCenter - lonDiff;
        result[LON_WEST_INDEX] = lonCenter + lonDiff;
        result[LAT_SOUTH_INDEX] = latCenter - latDiff;
        result[LAT_NORTH_INDEX] = latCenter + latDiff;

        return result;
    }

    /**
     * build full query in ordert to get all businesses in given range from database
     * @param lon center longitude
     * @param lat center latitude
     * @param radius radius of the incircle (incircle of square).
     * @return query string.
     */
    public static String getBusinessesInRangeQuery(double lon, double lat, double radius) {
        double[] range = calculateRange(lon, lat, radius);
        String query = String.format(BUSINESS_IN_RANGE_STATEMENT, range[LON_EAST_INDEX], range[LON_WEST_INDEX],  range[LAT_SOUTH_INDEX], range[LAT_NORTH_INDEX]);
        return query;
    }
//    /**
//     *  instance getter method
//     *  @return the single instance of this object
//     */
//    public static BusinessDBTool getInstance(String appDirPath) throws ClassNotFoundException, SQLException {
//        if (instance == null) {
//            instance = new BusinessDBTool(appDirPath);
//        }
//        else if (instance.connection == null) {
//            instance.startConnection();
//        }
//        return instance;
//    }

//    public static BusinessDBTool getInstance(Activity activity) throws ClassNotFoundException, SQLException {
////        String appDirPath = activity.getFilesDir().getAbsolutePath();
//        String appDirPath = activity.getBaseContext().getApplicationInfo().dataDir;
//        System.out.println(appDirPath);
//        return getInstance(appDirPath);
//    }

    /* Ctors */
    private BusinessDBTool(String appAbsDir) throws ClassNotFoundException, SQLException {
//        try {
//            DriverManager.registerDriver((Driver) Class.forName("org.sqldroid.SQLDroidDriver").newInstance());
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to register SQLDroidDriver");
//        }
//        this.appAbsDir = appAbsDir;
//        this.startConnection();
    }

//    public void startConnection() {
//        String jdbcUrl = "jdbc:sqldroid:" + this.appAbsDir + DBPATH;
//        try {
//            this.connection = DriverManager.getConnection(jdbcUrl);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public void close() {
//        try {
//            if(connection != null)
//                connection.close();
//        }
//        catch(SQLException e)
//        {
//            //connection close failed.
//            System.err.println(e);
//        }
//
//    }

//    protected void finalize() throws Throwable{
//        this.close();
//        System.err.println("BusinessDbTool hasn't been closed properly: close() method hasn't been called!");
//    }
//
    /* methods */


    /**
     *  example of use
     */
//    public static void test(String[] args) throws ClassNotFoundException, SQLException {
//        String currentDirectory;
//        File file = new File(".");
//        currentDirectory = file.getAbsolutePath();
//        BusinessDBTool dbTool = BusinessDBTool.getInstance(currentDirectory);
//        ArrayList<String> businesses = dbTool.getBusinessesInRange(34.91188640000001, 32.4384243, 0.1);
//        for (String businessName : businesses) {
//            System.out.println(businessName);
//        }
//    }
}