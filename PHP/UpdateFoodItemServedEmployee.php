
<?php 
   
    $ono = $_REQUEST['ono'];
	$name = $_REQUEST['name'];
 
	include("config.php");
	
	$query = mysqli_query($con,"SELECT Ino FROM `items` WHERE Name='$name'");
	$row = mysqli_fetch_assoc($query);
	$ino = $row['Ino'];

	
	$query = mysqli_query($con,"UPDATE `order_items` SET Status='1' WHERE Ino='$ino' AND Ono='$ono'");
			 
?>
