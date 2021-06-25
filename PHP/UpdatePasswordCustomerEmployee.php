<?php

	$email = $_REQUEST['email'];
	$post = $_REQUEST['post'];
	$password= $_REQUEST['password'];
	
	
	/*Hash*/
			
	$salt="qscefbthm";
		
	$secure=sha1($password,$salt);
	
	include("config.php");
	if($post == "Customer")
	{
		$query =  mysqli_query($con,"UPDATE customer SET Password='$secure' WHERE Email='$email'");	
	}
	else
	{
		$query =  mysqli_query($con,"UPDATE employee SET Password='$secure' WHERE Email='$email'");
	}
	
	if($query && $email!=null && $password!=null){
		echo "Password Updated Succesfully";
	}
	else{
		echo "Something Went Wrong!";
	}
	
?>