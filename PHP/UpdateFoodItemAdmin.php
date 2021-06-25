<?php 

	$name = $_REQUEST['name'];
	$price = $_REQUEST['price'];
	$image = $_REQUEST['image'];
	
	$decoded = base64_decode($image);
   
	include("config.php");
	
	$query =  mysqli_query($con,"UPDATE `items` SET Price='$price',Image='".addslashes($decoded)."' WHERE Name='$name'");
	
	if(mysqli_affected_rows($con)!=0&&$query)
	{
		echo "Item Modified Successfully!";
	}
	else
	{
		if($query)
		{
			echo "Something Went Wrong!";
		}
		else
		{
			echo "Item Is In Use,Cannot Modify!";
		}
	}
	
?>

