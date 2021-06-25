<?php 
   
    $name = $_REQUEST['name'];
 
	include("config.php");
	
	$query =  mysqli_query($con,"DELETE FROM `items` WHERE  Name='$name'");
	
	if(mysqli_affected_rows($con)!=0&&$query)
	{
		echo "Item Deleted Successfully!";
	}
	else
	{
		if($query)
		{
			echo "Something Went Wrong!";
		}
		else
		{
			echo "Item Is In Use,Cannot Delete!";
		}
	}
?>
