<?php

	//List Of All Food Items For Customer
	//Module : Customer

	include("config.php");
	$rid = $_REQUEST["rid"];
	
	$query = mysqli_query($con,"SELECT * FROM items WHERE Rid='$rid'");
   	
	while($row = mysqli_fetch_assoc($query))
	{
		$image = base64_encode( $row["Image"] ); 
		$array[] = array("ino"=>$row["Ino"],"name"=> $row["Name"],"price" => $row["Price"],"image"=>$image);
	}
	  
	   echo json_encode($array);
?>
