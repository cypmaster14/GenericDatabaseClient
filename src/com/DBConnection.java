package com;

public class DBConnection {

	public final String username="STUDENT";
	public final String password="STUDENT";
	public final String url="jdbc:oracle:thin:@localhost:1522:XE";
	
	private static DBConnection dbConnection;
	
	private DBConnection()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Driverul nu a putut fi instalat");
			e.printStackTrace();
			
			System.exit(0);
		}
	}
	
	public static DBConnection getInstance()
	{
		if(dbConnection==null)
			dbConnection= new DBConnection();
		
		return dbConnection;
	}

}
