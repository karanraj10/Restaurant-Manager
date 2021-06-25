<?php 

	$tno = $_REQUEST['tno'];
	$capacity = $_REQUEST['capacity'];
   
	include("config.php");
	
	$query =  mysqli_query($con,"UPDATE `tables` SET Capacity='$capacity' WHERE Tno='$tno'");
	
	if($query)
	{
		echo json_encode(array("result"=>"pass"));
	}
	else
	{
		echo json_encode(array("result"=>"fail"));
	}
	
?>
