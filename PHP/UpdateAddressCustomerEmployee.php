<?php
	
	$email = $_REQUEST['email'];
	$address = $_REQUEST['address'];
	$post=$_REQUEST['post'];
	
	if($post=="Customer"){
		
		include("config.php");
		$query =  mysqli_query($con,"UPDATE customer SET Address='$address' WHERE Email='$email'");
		
		if($query){
			echo "Updated Successfully";
		}
		else{
			echo "Something Went Wrong!";
		}
	}
	else{
		
		include("config.php");
		$query =  mysqli_query($con,"UPDATE employee SET Address='$address' WHERE Email='$email'");
		
		if($query){
			echo "Updated Successfully";
		}
		else{
			echo "Something Went Wrong!";
		}
	}
?>