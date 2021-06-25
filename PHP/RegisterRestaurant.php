<?php
	// Registration Of Restaurant
	// Module : Base 
	$username = $_REQUEST['username'];
	$password = $_REQUEST['password'];
	$email = $_REQUEST['email'];
	$name = $_REQUEST['name'];
	$contact = $_REQUEST['contact'];
	$address = $_REQUEST['address'];
	$location = $_REQUEST['location'];
	$gstin = $_REQUEST['GSTIN'];
	$image = $_REQUEST['image'];
	$roption = $_REQUEST['roption'];
	$start = $_REQUEST['starttime'];
	$end = $_REQUEST['endtime'];

	
	$decoded = base64_decode($image);
	
	include("config.php");
	

	$check = "select Username from customer where Username='$username'";       // Search Customers
	$check1 = "select Username from employee where Username='$username'";      // Search Employee
	$check2 = "select Username from restaurant where Username='$username'";    // Search Restaurant    For Already Existing Users..
	
	$query = mysqli_query($con,$check);
	$query1 = mysqli_query($con,$check1);
	$query2 = mysqli_query($con,$check2);
	
	if($username==null || $email==null || $name==null || $contact==null || $address==null || $password==null)              // If Username & Password Is Empty...
	{
		$array = array("error"=> 0);     //Please Provide Details..
	}
	elseif(mysqli_num_rows($query) > 0)
	{
		$array = array("error"=> 3);     //User Already Exists...
	}
	elseif(mysqli_num_rows($query1) > 0)
	{
		$array = array("error"=> 3);     //User Already Exists...
	}
	elseif(mysqli_num_rows($query2) > 0)
	{
		$array = array("error"=> 3);    //User Already Exists...
	}
	else                                           // If Not Exist Then...
	{
	
		$insert =  mysqli_query($con,"INSERT INTO `restaurant` VALUES(NULL,'$name','$email','$username','$password','$contact','$address','$gstin','$location','".addslashes($decoded)."','$roption','$start','$end')");
		
		if($insert)
		{
			$array = array("error"=> 1);
		}
		else
		{
			$array = array("error"=> 2);
		}
	}
	echo json_encode($array);
	mysqli_close($con);

?>