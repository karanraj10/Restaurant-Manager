<?php

	//List Of All Restaurant For Customer
	//Module : Customer

	include("config.php");
	
	$query = mysqli_query($con,"SELECT * FROM restaurant");
   	
	while($row = mysqli_fetch_assoc($query)){
		$image = base64_encode( $row["Image"] ); 
		$array[] = array("rid"=> $row["Rid"],"name" => $row["Name"],"city"=>$row["City"],"address"=> $row["Address"],"contact"=> $row["Contact"],"roption"=> $row["Roption"],"starttime"=> $row["StartTime"],"endtime"=> $row["EndTime"],"image"=>$image);
	   }
	   
	   echo json_encode($array);
?>
