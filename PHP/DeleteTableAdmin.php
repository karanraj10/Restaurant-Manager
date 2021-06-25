<?php 
   
    $tno = $_REQUEST['tno'];
 
	include("config.php");
	
	$query =  mysqli_query($con,"DELETE FROM `tables` WHERE  Tno='$tno'");
	
	if(mysqli_affected_rows($con)!=0&&$query)
	{
		echo "Table Deleted Successfully!";
	}
	else
	{
		if($query)
		{
			echo "Something Went Wrong!";
		}
		else
		{
			echo "Table Is In Use,Cannot Delete!";
		}
	}
?>
