<?php
        
    include("config.php");
	$rid = $_REQUEST['Rid'];

	$query1 = mysqli_query($con, "SELECT * FROM `reservation` WHERE Rid='$rid'");
	while($row = mysqli_fetch_assoc($query1))
	{
		$array[] = array("tno"=>$row['Tno'],"rdate"=> $row['Rdate'],"starttime" => $row['StartTime'],"endtime" => $row['EndTime'],"deposit" => $row['DepositAmount'],"guest" => $row['Guests']);
	}
	echo json_encode($array);
?>

