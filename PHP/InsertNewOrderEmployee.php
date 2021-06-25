<?php
	include("config.php");

    $ono = $_REQUEST['orderNo'];
    $order1 = $_REQUEST['orderName'];
	$order2 = $_REQUEST['orderQty'];
	$date = date("Y-m-d");
	$email = $_REQUEST['orderEmail'];
	$tno = $_REQUEST['orderTno'];
	$tax = $_REQUEST['orderTax'];
	$rid = $_REQUEST['Rid'];
	$discount = $_REQUEST['orderDiscount'];
	
	$names = (array) json_decode($order1);
	$quantitys = (array) json_decode($order2);
	$amount=doubleval(0);

	
	for($i=0;$i<count($names);$i++)
	{
		 $value=$names[$i];
		 $query = mysqli_query($con,"SELECT Ino,Price FROM `items` WHERE Name='$value'");
		 $row = mysqli_fetch_assoc($query);
		 $ino[$i] = $row['Ino'];
		 $price = doubleval($row['Price']);
		 $qty[$i]= $quantitys[$i];
		 $amount += $price * $qty[$i];
		 $item_amm[$i] = $price * $qty[$i];
	}
  
	if($ono=="0")
	{
	  $query = mysqli_query($con,"SELECT Ono FROM `order` ORDER BY `order`.`Ono`  DESC ");
	  
	  if($row = mysqli_fetch_array($query))
	  {
		$ono = $row[0]+1;
	  }
	  else
	  {
		$ono = 1;
	  }

		 $query =  mysqli_query($con,"SELECT Eid FROM `employee` WHERE Email='$email'");
		 $eid = mysqli_fetch_array($query);
		 $eid = $eid[0];

		 $query =  mysqli_query($con,"INSERT INTO `order` VALUES('$ono', '$date', '$amount','$tax', '$discount', '$amount', NULL, '$tno','$eid','0','$rid')");
		 
		  if($query)
		  {
			 echo json_encode(array("oid"=>$ono,"result"=>"pass"));
		  }
		  else
		  {
			 echo json_encode(array("result"=>"fail"));
		  }
	}
	 
	   for($i=0;$i<count($names);$i++)
	  {
		 $inoDB=$ino[$i];
		 $qtyDB=$qty[$i];
		 $item_ammDB=$item_amm[$i];
		 
		 if($qtyDB>0)
		 {
			 $query2 = mysqli_query($con,"SELECT * FROM `order_items` WHERE Ino='$inoDB' AND Ono='$ono'");
			 if($row = mysqli_fetch_assoc($query2))
			 {
				$shiftDB=$row['Quantity'];
				$oldqty = $row['OldQuantity']+$qty[$i];
				$qtyDB=$shiftDB+$qtyDB;
				$item_ammDB=$row['Amount']+$item_ammDB;
				$status = $row['Status'];
				if($status==1)
				{
					$query1 = mysqli_query($con,"UPDATE `order_items` SET OldQuantity='$qty[$i]',Quantity='$qtyDB',Amount='$item_ammDB',Status='0' WHERE Ino='$inoDB' AND Ono='$ono'");
				}
				else if($stauts==0)
				{
					$query1 = mysqli_query($con,"UPDATE `order_items` SET OldQuantity='$oldqty',Quantity='$qtyDB',Amount='$item_ammDB',Status='0' WHERE Ino='$inoDB' AND Ono='$ono'");
				}
			 }
			 else
			 {
				$query1 = mysqli_query($con,"INSERT INTO `order_items` VALUES('$ono','$inoDB','$qtyDB','$item_ammDB','$qtyDB',0)");
			 }
		 }
	  }
	 
	  if($query1)
	  {
		 echo json_encode(array("oid"=>$ono,"result"=>"pass"));
	  }
	  else
	  {
		 echo json_encode(array("result"=>"fail"));
	  }
	
?>
