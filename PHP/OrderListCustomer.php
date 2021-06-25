<?php

	$username = $_REQUEST['username'];

include("config.php");
	
	$query = mysqli_query($con,"SELECT Cid FROM customer WHERE Username='$username'");
	$cid = mysqli_fetch_assoc($query);
	$cid = $cid['Cid'];
	

	$query = mysqli_query($con,"SELECT * FROM `order` WHERE Cid='$cid'");
	
	
	while($row  = mysqli_fetch_assoc($query))
	{
		
		$oid = $row['Ono'];
		$rid = $row['Rid'];
	
		$x =  mysqli_query($con,"SELECT * FROM `restaurant` WHERE Rid='$rid'");
		$get = mysqli_fetch_assoc($x);
		$resName = $get["Name"];
					
		$query1 = mysqli_query($con,"SELECT Ino,Quantity FROM `order_items` WHERE Ono='$oid'");
		
		while($row1  = mysqli_fetch_assoc($query1))
		{
			$ino = $row1['Ino'];
			$qty = $row1['Quantity'];
			$query2 = mysqli_query($con,"SELECT Name FROM `items` WHERE Ino='$ino'");
			
			$row2= mysqli_fetch_assoc($query2);
			
			$arrayItem[] = array("item"=>$row2['Name'],"qty"=>$qty);
		}
		
		$array[] = array("ono"=> $row['Ono'],"date" => $row['Date'],"totalamount" => $row['TotalAmount'],"items"=>$arrayItem,"rname"=>$resName);
		$arrayItem = null;
	}
	
	echo json_encode($array);
	
	
	
	
	
?>

