<?php

	// Insert New Food Item From Admin Tools
	// Module : Admin 
	
	$image = $_REQUEST['image'];
	$price = $_REQUEST['price'];
	$name = $_REQUEST['name'];
	$rid = $_REQUEST['Rid'];
	
	$decoded = base64_decode($image);

	include("config.php");
	
	$query = mysqli_query($con,"INSERT INTO `items` VALUES(NULL,'$name','$price','".addslashes($decoded)."','$rid')");
	
	if($query)
	{
		echo json_encode(array("result"=>"pass"));
	}
	else
	{
		echo json_encode(array("result"=>"fail"));
	}
   	
?> 