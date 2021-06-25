<?php

 $tno = $_REQUEST['tno'];
 
 include("config.php");
 
 $query = mysqli_query($con,"SELECT * FROM `order` WHERE Tno='$tno' AND Paid='0'");
 $row = mysqli_fetch_assoc($query);
 $ono = $row['Ono'];
 
 $query = mysqli_query($con,"SELECT Amount FROM `order_items` WHERE Ono='$ono'");

 $amount = doubleval(0);
 while($row = mysqli_fetch_assoc($query))
 {
	$amount += $row['Amount'];
 }
 
 $query = mysqli_query($con,"SELECT Tax,Discount FROM `order` WHERE Ono='$ono'");
 $row = mysqli_fetch_assoc($query);
 
 $tax = $row['Tax'];
 $discount = $row['Discount'];
 
 $totalamount = ($amount + ($tax * $amount)/100) - (($discount * $amount)/100);
 
 $query = mysqli_query($con,"UPDATE `order` SET Amount='$amount',TotalAmount='$totalamount',Paid='1' WHERE Tno='$tno' AND Paid='0'");
 
 if($query)
 {
	 echo "Now It's Time For Admin,You Are Free! 😜";
 }
 else
 {
	echo "Something Went Wrong!";
 }
?>