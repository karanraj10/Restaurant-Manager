<?php

 /* Getting Email */
  $username = $_REQUEST['username'];
  $email = $_REQUEST['email'];
  
  include("config.php");
  
  $query=mysqli_query($con,"SELECT Email FROM customer WHERE Username='$username'");
 
  if($emailDB=mysqli_fetch_assoc($query))
  {
	  $emailDB=$emailDB['Email'];
	  if($email==$emailDB)
	  {
		  /* Database Work*/
		  $query="SELECT Email FROM customer WHERE Email='$emailDB'";
		  $ex=mysqli_query($con,$query);
		  
		  if($row=mysqli_fetch_assoc($ex)!=null)
		  {
			  $flag=true;
			   echo json_encode(array("result"=>"pass","post"=>"customer"));
		  }
		  else
		  {
			$query="SELECT Email FROM employee WHERE Email='$email'";
			$ex=mysqli_query($con,$query);
			if($row=mysqli_fetch_assoc($ex)!=null){
				$flag=true;
			   echo json_encode(array("result"=>"pass","post"=>"employee"));
			}
			else{
			   echo json_encode(array("result"=>"fail"));
			   $flag=false;
			}
		  }
	  }
	  else
	  {
		  echo json_encode(array("result"=>"fail_email"));
	  }
  }
  else
  {
	  echo json_encode(array("result"=>"fail_user"));
  }
?>
