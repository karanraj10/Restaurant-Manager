<?php
	$email = $_REQUEST['email'];
        
    include("config.php");
	
	$query = mysqli_query($con,"SELECT Cid FROM customer WHERE Email='$email'");
	$cid = mysqli_fetch_assoc($query);
	$cid = $cid['Cid'];
	
	$query1 = mysqli_query($con, "SELECT * FROM `reservation` WHERE Cid = '$cid'");
	while($row = mysqli_fetch_assoc($query1))
	{
		$rid = $row["Rid"];
		$x =  mysqli_query($con,"SELECT * FROM `restaurant` WHERE Rid='$rid'");
		$get = mysqli_fetch_assoc($x);
		$resName = $get["Name"];

		$array[] = array("tno"=>$row['Tno'],"rdate"=> $row['Rdate'],"starttime" => $row['StartTime'],"endtime" => $row['EndTime'],"deposit" => $row['DepositAmount'],"guest" => $row['Guests'],"rname"=>$resName);
	}
	echo json_encode($array);
?>
