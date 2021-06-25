<?php

 $ono = $_REQUEST['ono'];

 include("config.php");
 
 $query = mysqli_query($con,"SELECT * FROM order_items WHERE Ono='$ono'");
 
 while($row=mysqli_fetch_assoc($query))
 {
	 $ino=$row['Ino'];
	 $status=$row['Status'];
	 $query1 = mysqli_query($con,"SELECT Name FROM items WHERE Ino='$ino' ");
	 $name = mysqli_fetch_assoc($query1);
	 $name = $name['Name'];
	 $quantity=$row['Quantity'];
	 $oldquantity=$row['OldQuantity'];
	 
	
	$array[]=array("name"=>$name,"qty"=>$oldquantity,"status"=>$status);
	 
 }
 
 echo json_encode($array);

?>
