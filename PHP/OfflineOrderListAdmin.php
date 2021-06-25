<?php
 
 include("config.php");
 
 $rid = $_REQUEST['Rid'];
 $query = mysqli_query($con,"SELECT * FROM `order` WHERE Paid='1' AND Tno!=' ' AND Rid='$rid'");

 while($row = mysqli_fetch_assoc($query))
 {
	$array[] = array("Tno"=>$row['Tno'],"Ono"=>$row['Ono']);
 } 
 
  echo json_encode($array);
  
?>