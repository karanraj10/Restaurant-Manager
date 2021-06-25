<?php
 $ono = $_REQUEST['ono'];
 
  include("config.php");;
 
 $query = mysqli_query($con,"UPDATE `order` SET Paid='2' WHERE Ono='$ono'");
 
 if($query)
 {
	echo "Order is Ready!";
 }
 else
 {
	echo "Something Went Wrong!";
 }

?>
