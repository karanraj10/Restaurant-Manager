<?php 

	//New Reservation Customer After Payment
	//Module : Customer
	
	$starttime = $_REQUEST['StartTime']; 
	$endttime = $_REQUEST['EndTime'];
	$date = $_REQUEST['Date'];
	$guest = $_REQUEST['Guest'];
	$email = $_REQUEST['Email'];
	$tno= $_REQUEST['Tno'];
	$rid = $_REQUEST['Rid'];
	$dateToday = date("Y-m-d");
	
	include("config.php");
	$query = mysqli_query($con,"SELECT Cid FROM customer WHERE Email='$email'");
	$cid = mysqli_fetch_assoc($query);
	$cid = $cid['Cid'];

	$query = mysqli_query($con,"SELECT Rno FROM `reservation` ORDER BY `reservation`.`Rno`  DESC ");
	if($row = mysqli_fetch_array($query))
	{
		$rno = $row[0]+1;
	}
	else
	{
		$rno = 1;
	}
					
	$query = mysqli_query($con,"INSERT INTO reservation VALUES 
			('$rno','$dateToday','$date','$starttime','$endttime','$guest','100','$cid','$tno','$rid')");
						 
	if($query)
	{	
		echo "Table Book!";
	}
	else
	{
		echo "Table Not Available At This Time";
	}
	
?>