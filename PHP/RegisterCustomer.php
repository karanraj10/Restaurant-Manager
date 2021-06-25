<?php
	$username = $_REQUEST["username"];
	$password = $_REQUEST["password"];
	$email = $_REQUEST['email'];
	$name = $_REQUEST['name'];
	$contact = $_REQUEST['contact'];
	$address = $_REQUEST['address'];
	
	include("config.php");

	$check = "select Username from customer where Username = '$username'";       // Search Customers
	$check1 = "select Username from employee where Username = '$username'";      // Search Employee
	$check2 = "select Username from restaurant where Username = '$username'";    // Search Restaurant    For Already Existing Users..
	
	$query = mysqli_query($con,$check);
	$query1 = mysqli_query($con,$check1);
	$query2 = mysqli_query($con,$check2);
	
	$check = "select Email from customer where Email = '$email'";       // Search Customers
	$check1 = "select Email from employee where Email = '$email'";      // Search Employee
	$check2 = "select Email from restaurant where Email = '$email'";    // Search Restaurant    For Already Existing Users..
	
	$query3 = mysqli_query($con,$check);
	$query4 = mysqli_query($con,$check1);
	$query5 = mysqli_query($con,$check2);
	
	if(mysqli_num_rows($query)+mysqli_num_rows($query1)+mysqli_num_rows($query2) > 0)
	{
		$array = array("error"=> 3);     //User Already Exists...
	}
	elseif(mysqli_num_rows($query3)+mysqli_num_rows($query4)+mysqli_num_rows($query5) > 0)
	{
		$array = array("error"=> 5);     //Email Already Exists...
	}
	else                                            // If Not Exist Then...
	{
		
		if($query3)
		{
		
			// date_default_timezone_set('Etc/UTC');
					
			// require '/usr/share/php/libphp-phpmailer/class.phpmailer.php';
			// require '/usr/share/php/libphp-phpmailer/class.smtp.php';
				
			// $mail = new PHPMailer;
			// $mail->setFrom('hirestaurantmanager@gmail.com','Restaurant Manager');
			// $mail->addAddress($email);
			// $mail->Subject = 'Welcome To Restaurant';
			// $mail->Body = file_get_contents("http://34.93.41.224/welcome.php?name=$name&username=$username"); 
			// $mail->IsSMTP();
			// $mail->SMTPSecure = 'tls';
			// $mail->Host = 'smtp.gmail.com';
			// $mail->SMTPAuth = true;
			// $mail->Port = 587;
					
			// $mail->isHTML(true);
					
			// $mail->Username = 'kapscooper10@gmail.com';	
			// $mail->Password = 'kapsCooper10#10';
				
				
				$query3 = mysqli_query($con,"insert into customer values(NULL,'$username','$name','$email','$contact','$address','$password')");
					
				if($query3)
				{
					$array = array("error"=> 1);
				}
				else
				{
					$array = array("error"=> 2);
				}	
			
		}
		else
		{	
			$array = array("error"=> 2);
		}
		
	}
	
	echo json_encode($array);
	mysqli_close($con);
	
?>

