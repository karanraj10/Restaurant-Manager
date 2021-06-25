<?php

	// Place New Order From Customer
	// Module : Customer 

	$email = $_REQUEST['email'];
	$totalamount = $_REQUEST['totalamount'];
	$amount = $_REQUEST['amount'];
	$tax = $_REQUEST['tax'];
	$discount = $_REQUEST['discount'];
	$rid = $_REQUEST['Rid'];
	$date = date("Y-m-d");
	
	$itemsDB = $_REQUEST['itemname'];
	$quantityDB = $_REQUEST['quantity'];
	
	 include("config.php");
	$db=mysqli_select_db($con,"project");
	$query = mysqli_query($con,"SELECT Cid FROM customer WHERE Email='$email'");
	$cid = mysqli_fetch_assoc($query);
	$cid = $cid['Cid'];
	
	$query1 =  mysqli_query($con,"INSERT INTO `order` VALUES (NULL, '$date', '$amount', '$tax', '$discount', '$totalamount', '$cid', NULL, NULL,'1','$rid')");
	
	$query2 = mysqli_query($con,"SELECT * FROM `order` ORDER BY `order`.`Ono`  DESC ");
	$row = mysqli_fetch_array($query2);
	$ono = $row[0];
	
	$items=json_decode($itemsDB,true);
	$quantitys=json_decode($quantityDB,true);
	
	foreach(array_combine($items,$quantitys)as $item=>$quantity){
			$item=trim($item);
			$quantity=intval($quantity);
			
			$query3 = mysqli_query($con,"SELECT * FROM items WHERE Name='$item'");
			$row = mysqli_fetch_assoc($query3);
			$price = $row['Price'];
			$ino = $row['Ino'];
			
		if($quantity!=null){
			$amount = floatval($price) * intval($quantity);
			$query4 = mysqli_query($con,"INSERT INTO `order_items` (`Ono`, `Ino`, `Quantity`, `Amount`) VALUES ('$ono', '$ino', '$quantity', '$amount')");
		}
	}
	
	if($query4)
	{
		echo "Order Placed Successfully!";
	}
	else
	{
		echo "Something Went Wrong!";
	}

	
?>