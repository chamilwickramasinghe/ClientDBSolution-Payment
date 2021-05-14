package model;

import java.sql.*; 

public class Payment {

private static Connection connect() {
		
		Connection con = null;
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver"); 
			//Provide the correct details: DBServer/DBName, username, password 
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/dbsolution-payment", "root", "root"); 
			
			System.out.println("succsess");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
		
	}
	
	//insert
	public String insertpayment(String payment_code, String payment_type ,String customer_name, String customer_contact , String total_amount, String card_no) 
	 { 
		 String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {
				 return "Error while connecting to the database for inserting."; 
			 } 
		 	 	 // create a prepared statement
			 	 String query = "INSERT INTO payment (paymentCode, paymentType, customerName, customerContact, totalAmount, cardNo) VALUES (?, ?, ?, ?, ?, ?)"; 
			 	 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 					 
				 
				 // binding values
//				 preparedStmt.setInt(1, 0);
				 preparedStmt.setString(1, payment_code);
				 preparedStmt.setString(2, payment_type);
				 preparedStmt.setString(3, customer_name);
				 preparedStmt.setString(4, customer_contact);
				 preparedStmt.setDouble(5, Double.parseDouble(total_amount));
				 preparedStmt.setString(6, card_no);
				
				 				 
				 preparedStmt.execute(); 
				 con.close(); 
				 String newPayment = readpayment(); 
				 output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";
				 //output = "Inserted successfully"; 
		 } 
		 catch (Exception e) 
		 { 
			 //output = "Error while inserting the Payment.";
			 output = "{\"status\":\"error\", \"data\": \"Error while inserting the payment.\"}";
			 System.err.println(e.getMessage());
			 System.out.println(e.getMessage());
				System.out.println(e);
				e.printStackTrace();
			
		 } 
	 	return output; 
	 } 
	
	//Read Payment
	 public String readpayment() 
	 { 
		 String output = ""; 

		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {
				 return "Error while connecting to the database for reading."; 
			 } 
			 
			 // Prepare the html table to be displayed
			 output = "<table border='1'>"+ "<tr><th>payment ID</th>" + 
			 "<th>Payment Code</th>" + 
			 "<th>Payment Type</th>" + 
			 "<th>Customer Name</th>" + 
			 "<th>Customer Contact</th>" +
			 "<th>Total Amount</th>" +
			 "<th>Card No</th>" +
			
			 "<th>Update</th><th>Remove</th></tr>"; 
		 
			 
			 String query = "SELECT * FROM payment"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
				 String paymentID = Integer.toString(rs.getInt("paymentID")); 
				 String paymentCode = rs.getString("paymentCode"); 
				 String paymentType = rs.getString("paymentType");  
				 String customerName = rs.getString("customerName"); 
				 String customerContact = rs.getString("customerContact");
				 String totalAmount = Double.toString(rs.getDouble("totalAmount")); 
				 String cardNo = Integer.toString(rs.getInt("cardNo"));
				
				 
				 // Add into the html table
				 output += "<td>" + paymentID + "</td>";
				 output += "<td>" + paymentCode + "</td>";
				 output += "<td>" + paymentType + "</td>";   
				 output += "<td>" + customerName + "</td>"; 
				 output += "<td>" + customerContact + "</td>";
				 output += "<td>" + totalAmount + "</td>"; 
				 output += "<td>" + cardNo + "</td>"; 
									 
				 
				 
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						 + "<td><button class='btnRemove btn btn-danger' name='btnRemove' id ='btnRemove' value='"+ paymentID +"' >Remove</button></td></tr>";
			 } 
			 	 con.close(); 
			 	 // Complete the html table
			 	 output += "</table>"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "Error while reading the payment"; 
			 System.err.println(e.getMessage()); 
		 } 
	 	 return output; 
	 } 
		//Update payments
	 public String updatepayment(String paymentID,String paymentCode, String paymentType , String customerName, String customerContact , String totalAmount, String cardNo)
		{ 
			 String output = ""; 
			 try
			 { 
				 Connection con = connect(); 
				 if (con == null) 
				 {
					 return "Error while connecting to the database for updating."; 
				 } 
				 
				 // create a prepared statement
				 String query = "UPDATE payment SET paymentCode=? ,paymentType=? , customerName=? , customerContact=?,  totalAmount=? , cardNo=?  WHERE paymentID=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setString(1, paymentCode); 
				 preparedStmt.setString(2, paymentType); 	  
				 preparedStmt.setString(3, customerName); 
				 preparedStmt.setString(4, customerContact);
				 preparedStmt.setDouble(5, Double.parseDouble(totalAmount)); 
				 preparedStmt.setString(6, cardNo); 
				
				 preparedStmt.setInt(7, Integer.parseInt(paymentID)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 //output = "Updated successfully"; 
				 String newPayment = readpayment(); 
				 output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}"; 

			 } 
			 catch (Exception e) 
			 { 
				 //output = "Error while updating the payment."; 
				 output = "{\"status\":\"error\", \"data\": \"Error while updating the payment.\"}"; 
				 System.err.println(e.getMessage()); 
				 System.out.println(e);
			 } 
			 	return output; 
			 } 
		
		
			//Delete payments
			 public String deletepayment(String paymentID) 
			 { 
				 String output = ""; 
			 try
			 { 
				 Connection con = connect(); 
			 if (con == null) 
			 {
				 return "Error while connecting to the database for deleting."; 
			 } 
			 
			 	 // create a prepared statement
				 String query = "DELETE FROM payment WHERE paymentID=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setInt(1, Integer.parseInt(paymentID)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 //output = "Deleted successfully"; 
				 String newPayment = readpayment(); output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";

			 } 
			 catch (Exception e) 
			 { 
				 //output = "Error while deleting the payment."; 
				 output = "{\"status\":\"error\", \"data\": \"Error while deleting the payment.\"}"; 
				 System.err.println(e.getMessage()); 
				 System.out.println(e);
			 } 
			 return output;
			 }
}
