<?php	

	//Check Reservation Details 
	//Check Available Tables And StartTime And Date..
	//Module : Customer
	$starttime = $_REQUEST['StartTime']; 
	$endttime = $_REQUEST['EndTime'];
	$date = $_REQUEST['Date'];
	$guest = $_REQUEST['Guest'];
	$rid = $_REQUEST['Rid'];

	if($starttime==null || $endttime==null || $date==null || $guest==null)
	{
		echo "error";
	}	
	else
	{
		include("config.php");
		$query = mysqli_query($con,"SELECT Tno,Capacity FROM tables where Rid='$rid'");
		
		if(mysqli_num_rows($query)>0)
		{
					
			while($tables=mysqli_fetch_assoc($query))
			{
				$array[] = array("Tno"=>$tables['Tno'],"Capacity"=>$tables['Capacity']);
			}
			
			$result;
			$flag=true;
			for($i=0;$i<count($array);$i++)
			{
				$item=$array[$i];
				if($item['Capacity']>=$guest && $flag==true)
				{
					$tno=$item['Tno'];
					$query = mysqli_query($con,"SELECT Tno FROM reservation WHERE Rdate='$date' AND StartTime='$starttime' AND Tno='$tno' AND Rid='$rid'");
					
					if(!$row=mysqli_fetch_assoc($query))
					{
						$result=$tno;
						$flag=false;
					}
					else{
						$result="error";
					}
				}
				
			}
			if($flag==true)
			{
				$result="error";
			}
		}
		else
		{
			$result = "error";
		}
				
		echo $result;
    }

?>