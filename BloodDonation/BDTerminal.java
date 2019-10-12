package BloodDonation;
  
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.io.*;


public class BDTerminal {
	
	//VARIABLES
	static String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static String DB_URL = "jdbc:mysql://localhost:3306/BloodDonationDB";
	static String USERNAME = "root";
	static String PASSWORD = "ubuntu123456";
	
	static Connection conn;
	static Statement stmt;
	static ResultSet rs, rs2;

	String sql;
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	private void insertIntoPatient(long aadhar_card, String first_name, String last_name, int age, 
			String gender, long phone_number, String blood_group ) {
		
		sql = "INSERT INTO Patient VALUES ( "+aadhar_card+" , '"+first_name+"' , '"+last_name+"' , "+age+" , '"+gender+"' , "+phone_number+" , '"+blood_group+"' )";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void insertIntoDonor(long aadhar_card, String email_id, String password , String first_name, 
			String last_name, int age, String gender, long phone_number, 
			String address, String blood_group, int is_available) {
		 
		sql = "INSERT INTO Donor VALUES ( "+aadhar_card+" , '"+email_id+"' , '"+password+"' , '"+first_name+"' , '"+last_name+"' , "+age+" , '"+gender+"' , "+phone_number+" ,'"+address+"', '"+blood_group+"' , "+is_available+" )";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	private void insertIntoBloodPacket(long donor_aadhar_card, int dd, int mm, int yyyy, int branch_id) {
		
		String date = "STR_TO_DATE('"+dd+"-"+mm+"-"+yyyy+"', '%d-%m-%Y')";
		
		sql = "INSERT INTO BloodPacket VALUES ( "+donor_aadhar_card+" , "+date+" , "+branch_id+" )";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	private void insertIntoPendingOrder(long patient_aadhar_card) {
		
		sql = "INSERT INTO PendingOrder VALUES ( "+patient_aadhar_card+" )";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	private void insertIntoSuccessfulOrder(long patient_aadhar_card, long donor_aadhar_card, int dd, int mm, int yyyy) {
		
		String date = "STR_TO_DATE('"+dd+"-"+mm+"-"+yyyy+"', '%d-%m-%Y')";
		
		sql = "INSERT INTO SuccessfulOrder VALUES ( "+patient_aadhar_card+" , "+donor_aadhar_card+" , "+date+" )";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void openConnections(){
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			stmt = conn.createStatement();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void closeConnections(){
		try{
			rs.close();
			stmt.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//CONSTRUCTOR
	BDTerminal(){
		conn = null;
		stmt = null;
		openConnections();
	}

	//PUBLIC FUNCTIONS
	void registerPatient() throws IOException{
		
		String first_name;
		String last_name;
		int age;
		String gender;
		long aadhar_card;
		long phone_number;
		String blood_group;

		System.out.println("PATIENT REGISTRATION FORM");
		
		System.out.print("\nFirst Name ? ");
		first_name = br.readLine();
		
		System.out.print("\nLast Name ? ");
		last_name = br.readLine();
		
		do{
			System.out.print("\nAge (18-60) ? ");
			age = Integer.parseInt(br.readLine());
		}while(!Validator.age(age));
			
		do{
			System.out.println("\nGender (M/F/O) ? ");
			gender = br.readLine();
			gender.toUpperCase();
		}while(!Validator.gender(gender));
		
		do {
			System.out.print("\nAadhar Card ? ");
			aadhar_card = Long.parseLong(br.readLine());
		}while(!Validator.aadhar_number(aadhar_card));
		
		System.out.print("\nPhone Number ? ");
		phone_number = Long.parseLong(br.readLine());
		
		
		do {
			System.out.print("\nBlood Group ? ");
			blood_group = br.readLine();
		}while(!Validator.blood_group(blood_group));
		
		insertIntoPatient(aadhar_card,first_name,last_name,age,gender,phone_number,blood_group);
		
		insertIntoPendingOrder(aadhar_card);
		
	}
	
	void registerDonor() throws IOException{
		
		String first_name;
		String last_name;
		int age;
		String gender;
		long aadhar_card;
		long phone_number;
		String blood_group;
		String email_id;
		String password, password2;
		String address;
		int is_available;
		int branch_id;

		System.out.println("DONOR REGISTRATION FORM");
		
		System.out.print("\nFirst Name ? ");
		first_name = br.readLine();
		
		System.out.print("\nLast Name ? ");
		last_name = br.readLine();
		
		do{
			System.out.print("\nAge (18-60) ? ");
			age = Integer.parseInt(br.readLine());
		}while(!Validator.age(age));
			
		do{
			System.out.println("\nGender (M/F/O) ? ");
			gender = br.readLine();
			gender.toUpperCase();
		}while(!Validator.gender(gender));
		
		do {
			System.out.print("\nAadhar Card ? ");
			aadhar_card = Long.parseLong(br.readLine());
		}while(!Validator.aadhar_number(aadhar_card));
		
		System.out.print("\nPhone Number ? ");
		phone_number = Long.parseLong(br.readLine());

		do {
			System.out.print("\nEmail ID ? ");
			email_id = br.readLine();
		} while(!Validator.email_id(email_id));
		
		do {
			System.out.print("\nPassword ? ");
			password = br.readLine();
			System.out.print("\nPassword (again) ? ");
			password2 = br.readLine();
		}while(!(password.equals(password2)));
		
		System.out.print("\nAddress ? ");
		address = br.readLine();
		
		//NEEDS TO BE LOOKED INTO ASAP
		is_available = 1;
		
		do {
			System.out.print("\nBlood Group ? ");
			blood_group = br.readLine();
		}while(!Validator.blood_group(blood_group));
		
		//ASK USER IF HE/SHE WOULD LIKE TO BLOOD RIGHT NOW
		//NEEDS TO BE LOOKED INTO ASAP
		System.out.println("\nBranch ID ? ");
		branch_id = Integer.parseInt(br.readLine());
		
		insertIntoDonor(aadhar_card,email_id,password,first_name,last_name,
				age,gender,phone_number,address,
				blood_group,is_available); 
		
		LocalDate today = LocalDate.now();
		
		insertIntoBloodPacket(aadhar_card,
				today.getDayOfMonth(),today.getMonthValue(),today.getYear(),
				branch_id);
		
	}
	
	void loginToDonate() throws IOException {
		
		String email_id;
		String password;
		long aadhar_card = 0;
		String gender = "";
		int MINUS_DAYS = 0;
				
		try {
			
			//Enter Email
			do {
				System.out.print("\nEmail ID ? ");
				email_id = br.readLine();
			} while(!Validator.email_id(email_id));
			
			sql = "SELECT Aadhar_Card from Donor WHERE Email_ID = '"+email_id+"'";
			rs = stmt.executeQuery(sql);
			
			//Obtain Aadhar Card Using Email
			while(rs.next()) {
				aadhar_card = rs.getLong("Aadhar_Card");
			}
			
			//If Aadhar Card id Not found
			//that means email id is not registered
			if(aadhar_card==0) {
				System.out.println("Email ID not registered. ");
				System.out.println("Record Not Found");
				char choice;
				do{
					System.out.println("Would You Like to Register as a Donor (Y/N) ? ");
					choice = (char) br.read();
					@SuppressWarnings("unused")
					int temp_buffer = br.read();
				}while(!Validator.yesorno(choice));
				if(choice=='Y'||choice=='y')
					registerDonor();
				return;
			}
			
			//Enter Password
			System.out.println("\nPassword ? ");
			password = br.readLine();

			//Obtain password from the Database
			sql = "SELECT Password from Donor WHERE Aadhar_Card = "+aadhar_card;
			rs = stmt.executeQuery(sql);
			
			String pfdb = "";
			while(rs.next()) {
				pfdb = rs.getString("Password");
			}
			
			if(!password.equals(pfdb)){
				System.out.println("Incorrect Password. ");
				return;
			}
								
			Calendar last_blood_date = Calendar.getInstance();
			Calendar date_from_db = Calendar.getInstance();
			int yyyy,MM,dd;
			
			last_blood_date.set(1970, 1, 1);
							
			sql = "SELECT Date_Of_Entry FROM BloodPacket where Donor_Aadhar_Card = "+aadhar_card+" ";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				java.sql.Date temp = rs.getDate("Date_Of_Entry");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String strDate = dateFormat.format(temp);
				
				yyyy = Integer.parseInt(strDate.substring(0, 4));
				MM = Integer.parseInt(strDate.substring(5, 7));
				dd = Integer.parseInt(strDate.substring(8));
				
				date_from_db.set(yyyy, MM, dd);
				
				if( last_blood_date.before(date_from_db) )
					last_blood_date = date_from_db;

			}
						
			
			sql = "SELECT Date_Of_Entry FROM SuccessfulOrder where Donor_Aadhar_Card = "+aadhar_card+" ";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				java.sql.Date temp = rs.getDate("Date_Of_Entry");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String strDate = dateFormat.format(temp);
				
				yyyy = Integer.parseInt(strDate.substring(0, 4));
				MM = Integer.parseInt(strDate.substring(5, 7));
				dd = Integer.parseInt(strDate.substring(8));
				
				date_from_db.set(yyyy, MM, dd);
				
				if( last_blood_date.before(date_from_db) )
					last_blood_date = date_from_db;

			}
			

			sql = "SELECT Gender FROM Donor where Aadhar_Card = "+aadhar_card+" ";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				gender = rs.getString("Gender");
				MINUS_DAYS = (gender.equals("M"))?90:120;
			}
			
			
			
			LocalDate lpd = LocalDate.now().minusDays(MINUS_DAYS);
			yyyy = lpd.getYear();
			MM = lpd.getMonthValue();
			dd = lpd.getDayOfMonth();
			
			Calendar last_permitted_date = Calendar.getInstance();
			last_permitted_date.set(yyyy,MM,dd);
			
			if ( last_blood_date.before(last_permitted_date) ) {
				System.out.println("Permitted to donate blood");
				LocalDate today = LocalDate.now();
				
				System.out.println("\nBranch ID ? ");
				int branch_id = Integer.parseInt(br.readLine());
				
				insertIntoBloodPacket(aadhar_card,
						today.getDayOfMonth(),today.getMonthValue(),today.getYear(),
						branch_id);
				
				
				//Change Is Available Status
				sql = "UPDATE Donor "
						+ "SET Is_Available = 0 "
						+ "WHERE Aadhar_Card = "+aadhar_card;
				stmt.executeUpdate(sql);
			}
			else {
				System.out.println("Not Permitted to donate blood");
			}
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void matchPatientWithDonor() throws IOException {
		
		long patient_aadhar_card, donor_aadhar_card;
		int yyyy=1970, MM=1, dd=1;
		String blood_group = "";
		
		ArrayList <Long> patient_aadhar_cards = new <Long>ArrayList();
		ArrayList <Long> donor_aadhar_cards = new <Long>ArrayList();
		int srno = 0;
		
		try {
			
			//Go through Patients From PendingOrder
			sql = "Select p.First_Name, p.Last_Name, p.Blood_Group, p.Aadhar_Card "
					+ " from Patient p, PendingOrder po "
					+ " where p.Aadhar_Card = po.Patient_Aadhar_card ";
			rs = stmt.executeQuery(sql);
			
			
			//DISPLAY THE PATIENT LIST
			System.out.println("Patient List\n==============\n");
			srno = 0;
			while(rs.next()) {
				
				String first_name = rs.getString("First_Name");
				String last_name = rs.getString("Last_Name");
				blood_group = rs.getString("Blood_Group");
				long aadhar_card_from_db = rs.getLong("Aadhar_Card");
				patient_aadhar_cards.add(aadhar_card_from_db);
				
				System.out.println((++srno)+")\t"+first_name+' '+last_name+" :\t"+blood_group+"\n");
				
			}
			
			//Choose the patient
			System.out.println("Enter the SrNo of the Patient");
			srno = Integer.parseInt(br.readLine());
			srno--;
			patient_aadhar_card = patient_aadhar_cards.get(srno);
			
			//Get the patient's Blood_Group
			sql = "SELECT Blood_Group FROM Patient where Aadhar_Card = "+patient_aadhar_card+" ";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				blood_group = rs.getString("Blood_Group");
			}
			
			//Get the Vector of Compatible Blood Groups
			Vector<String> donor_blood_groups = BloodGroupMatrix.getCompatibleBloodGroups(blood_group);
				
			//DISPLAY the donor's list
			System.out.println("Donor List\n==============\n");
			srno = 0;
			
			
			//Display Donor's list with Matching Blood Type
			for (String donor_blood_group : donor_blood_groups)
			{
				sql = "Select d.Aadhar_Card, d.First_Name, d.Last_Name, d.Blood_Group, bp.Date_Of_Entry  from Donor d, BloodPacket bp  where d.Aadhar_Card = bp.Donor_Aadhar_Card and d.Blood_Group = '"+donor_blood_group+"' ";
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					
					String first_name = rs.getString("First_Name");
					String last_name = rs.getString("Last_Name");
					String blood_group_from_db = rs.getString("Blood_Group");
					long aadhar_card_from_db = rs.getLong("Aadhar_Card");
					donor_aadhar_cards.add(aadhar_card_from_db);
					
					System.out.println((++srno)+")\t"+first_name+' '+last_name+" :\t"+blood_group_from_db+"\n");
					
				}
			}
			
			//Match Patients and Donors
			
			//Choose the donor
			System.out.println("Enter the SrNo of the Patient");
			srno = Integer.parseInt(br.readLine());
			srno--;
			donor_aadhar_card = donor_aadhar_cards.get(srno);
			
			System.out.println("IT'S A MATCH");
			System.out.println("Patient - "+patient_aadhar_card);
			System.out.println("Donor - "+donor_aadhar_card);
			System.out.println("BG - "+blood_group);
			
			//Get Date
			sql = "SELECT Date_Of_Entry FROM BloodPacket WHERE Donor_Aadhar_Card = "+donor_aadhar_card;
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				java.sql.Date temp = rs.getDate("Date_Of_Entry");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String strDate = dateFormat.format(temp);
				yyyy = Integer.parseInt(strDate.substring(0, 4));
				MM = Integer.parseInt(strDate.substring(5, 7));
				dd = Integer.parseInt(strDate.substring(8));
			}
			
			sql = "DELETE FROM PendingOrder WHERE Patient_Aadhar_card = "+patient_aadhar_card;
			stmt.executeUpdate(sql);
			
			sql = "DELETE FROM BloodPacket WHERE Donor_Aadhar_Card = "+donor_aadhar_card;
			stmt.executeUpdate(sql);

			insertIntoSuccessfulOrder(patient_aadhar_card,donor_aadhar_card,dd,MM,yyyy);
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	//MENU DRIVEN PROGRAM
	void menuDriven() throws IOException {
		
		while(true)
		{
			System.out.println("\nBLOOD DONATION PROJECT\n");
			
			System.out.println("\nMenu Driven Program\n");
			System.out.println(" 1 - Register Donor");
			System.out.println(" 2 - Register Patient");
			System.out.println(" 3 - Login to Donate");
			System.out.println(" 4 - Match Patient and Donor");
			System.out.println(" 0 - Exit");
			System.out.print("\nEnter Your Choice : ");
			int choice = Integer.parseInt(br.readLine());
			switch(choice) {
				case 1:
					registerDonor();
					break;
				case 2:
					registerPatient();
					break;
				case 3:
					loginToDonate();
					break;
				case 4:
					matchPatientWithDonor();
					break;
				case 0:
					return;
				default:
					System.out.println("\nInvalid Choice, Enter the choice again.\n");
					menuDriven();
			}
		}
	}

	//MAIN PROGRAM
	public static void main(String[] args) throws IOException{
		BDTerminal obj = new BDTerminal();
		obj.menuDriven();
	}
	
	
}
