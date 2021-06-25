<?php

	// List Of All Employee From Employee Table
	// Module : Admin 
 
	include("config.php");
	$rid = $_REQUEST["Rid"];
 
	$query = mysqli_query($con,"SELECT * FROM employee WHERE Rid='$rid'");
 
	while($row = mysqli_fetch_assoc($query))
	{
		$array[] = array("username"=>$row['Username'],"name"=>$row['Name'],"email"=>$row['Email'],"salary"=>$row['Salary'],"contact"=>$row['Contact'],"joindate"=>$row['JoinDate']);
	}
	if($array==null)
	{
		echo "NULL";
	}
	else
	{
		echo json_encode($array);
	}
?>