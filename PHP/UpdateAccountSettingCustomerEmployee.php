<?php
	
	$email = $_REQUEST['email'];
	$name = $_REQUEST['name'];
	$mobile= $_REQUEST['mobile'];
	$post=$_REQUEST['post'];
	
	
	if($post=="Customer"){
		
		include("config.php");
		$query =  mysqli_query($con,"UPDATE customer SET Name='$name',Contact='$mobile' WHERE Email='$email'");
		
		if($query){
			echo "Updated Successfully";
		}
		else{
			echo "Something Went Wrong!";
		}
	}
	else{
		
		include("config.php");
		$query =  mysqli_query($con,"UPDATE employee SET Name='$name',Contact='$mobile' WHERE Email='$email'");
		
		if($query){
			echo "Updated Successfully";
		}
		else{
			echo "Something Went Wrong!";
		}
	}
?>
