<?php 
	//List Of All Tables From Table(Database)
	//List Of All Table For Mange : Admin
	//List Of All Tables For Take Order : Employee
	//Module : Admin & Employee
	include("config.php");
	$rid = $_REQUEST['Rid'];
	
	$query =  mysqli_query($con,"SELECT * FROM tables WHERE Rid='$rid'");
	

	if(mysqli_num_rows($query)!=0)
	{
		while($row=mysqli_fetch_assoc($query))
		{
			$array[]=array("tno"=>$row['Tno'],"capacity"=>$row['Capacity'],"error"=>1);
		}
	}
	else
	{
		$array[]=array("error"=>0);
	}
	
	echo json_encode($array);
?>