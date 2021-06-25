<?php

	// Login Of Employee, Restaurant, Customer
	// Module : Base 

	$username = $_REQUEST["username"];
	$password = $_REQUEST["password"];
	
	include("config.php");
	
	$findCust = "select * from customer where Username='$username' and Password='$password'";        //Find Customer
	$findRes = "select * from restaurant where Username='$username' and Password='$password'";     //Find Restaurant 
	$findEmp = "select * from employee where Username='$username' and Password='$password'";       //Find Employee
	
	$queryCust = mysqli_query($con,$findCust);
	$queryRes = mysqli_query($con,$findRes);
	$queryEmp = mysqli_query($con,$findEmp);
	
	
	if($username==null || $password==null)      // If Username & Password is Empty Then...
	{
		$array = array("error"=> 0);       
	}
	elseif(mysqli_num_rows($queryCust) > 0)        // If Username Found in Customer Then...
	{
		//....CUSTOMER.....
		$row = mysqli_fetch_assoc($queryCust);    // Fetch Customer Details.. 
		
		$array = array("id"=>$row["Cid"],"name"=> $row["Name"],"email"=>$row["Email"],"post"=> "Customer","mobile"=>$row["Contact"],"address"=>$row["Address"],"error"=>1);
			
	}
	elseif(mysqli_num_rows($queryRes) > 0)        // If Username Found in Restaurant Then...
	{
		//....Restaurant.....
		$row = mysqli_fetch_assoc($queryRes);     // Fetch Restaurant Details..
		
		$array = array("name"=> $row["Name"],"email"=>$row["Email"],"post"=> "Admin","mobile"=>$row["Contact"],"address"=>$row["Address"],"location"=>$row["City"],"id"=>$row["Rid"],"error"=>1);
			
	}
	elseif(mysqli_num_rows($queryEmp) > 0)        // If Username Found in Employee Then...
	{
		//....Employee.....
		$row = mysqli_fetch_assoc($queryEmp);     // Fetch Employee Details..
		
		$array = array("id"=>$row["Eid"],"name"=> $row["Name"],"email"=>$row["Email"],"post"=> "Employee","mobile"=>$row["Contact"],"address"=>$row["Address"],"rid"=>$row["Rid"],"error"=>1);
	}
	else
	{
		$array = array("error"=> 2);
	}
	echo json_encode($array);
	mysqli_close($con);
	
	
	//Error=>0  : Please Provide Details..
	//Error=>1  : User Find Sucessufully OR Login Sucessfully..
	//Error=>2  : Invalid Login Details OR No Such User Exists..
?>
