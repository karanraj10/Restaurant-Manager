<?php

	// List Of Online Order List (Pending orders))
	// Order list For Accepting order
	// Module : Admin 
 
 include("config.php");
 $rid = $_REQUEST['Rid'];
 
 $query = mysqli_query($con,"SELECT * FROM `order` WHERE Paid='1' AND Cid!='' AND Rid='$rid'");


 while($row = mysqli_fetch_assoc($query))
 {
	  
	$ono = $row['Ono'];
	$cid = $row['Cid'];
	
	$query3 = mysqli_query($con,"SELECT Address FROM `customer` WHERE Cid='$cid'");
	$row4 = mysqli_fetch_assoc($query3);
	
	$query4 = mysqli_query($con,"SELECT * FROM `order_items` WHERE Ono='$ono'");
 
	while($row2 = mysqli_fetch_assoc($query4))
	{
		$ino = $row2['Ino'];
		$query2 = mysqli_query($con,"SELECT Name FROM `items` WHERE Ino='$ino'");
		$row3 = mysqli_fetch_assoc($query2);
		$name = $row3['Name'];
		$qty = $row2['Quantity'];
		$items[] = array("Name"=>$name,"Qty"=>$qty);
	}
	
	$array[] = array("Cid"=>$cid,"Ono"=>$row['Ono'],"Address"=>$row4['Address'],"Items"=>$items);
	
	$items = null;
 } 
 
  echo json_encode($array);
  
?>
