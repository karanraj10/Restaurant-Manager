<?php
	//Dashboard Employee Tables
	include("config.php");
	$rid = $_REQUEST['Rid'];
	
	$query =  mysqli_query($con,"SELECT Tno FROM tables WHERE Rid='$rid'");
	
	while($row=mysqli_fetch_assoc($query))
	{
		$tno=$row['Tno'];
		
		$query1= mysqli_query($con,"SELECT Ono FROM `order` WHERE Tno='$tno' AND Rid='$rid' AND Paid='0'");
		if($row1 = mysqli_fetch_assoc($query1))
		{
			$array[]=array("tno"=>$row['Tno'],"ono"=>$row1['Ono']);
		}
		else
		{
			$array[]=array("tno"=>$row['Tno'],"ono"=>"0");
		}
	}	
	
	echo json_encode($array);
	
?>