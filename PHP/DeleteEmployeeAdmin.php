<?php 
   
    $username = $_REQUEST['username'];
 
	include("config.php");
	
	$query =  mysqli_query($con,"DELETE FROM `employee` WHERE  Username = '$username'");
	
	if($query)
	{
		echo "Employee Deleted Successfully!";
	}
	else
	{
		echo "Something Went Wrong!";
	}
?>
