<?php 

	// Insert New Table From Admin Tools
	// Module : Admin 

	$capacity = $_REQUEST['capacity'];
	$rid = $_REQUEST['Rid'];
   
	include("config.php");
	
	$query =  mysqli_query($con,"INSERT INTO `tables` (`Tno`, `Capacity`, `Rid`) VALUES (NULL, '$capacity','$rid');");
	
	if($query)
	{
		echo json_encode(array("result"=>"pass"));
	}
	else
	{
		echo json_encode(array("result"=>"fail"));
	}
	
?>