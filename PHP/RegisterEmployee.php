<?php
	// Registration Of Employee From Admin Tools
	// Module : Admin 

	use PHPMailer\PHPMailer\PHPMailer;
		use PHPMailer\PHPMailer\SMTP;
		use PHPMailer\PHPMailer\Exception;
		
		//Load Composer's autoloader
		require 'vendor/autoload.php';
	
	include("config.php");

	$username = $_REQUEST["username"];
	$name = $_REQUEST["name"];
	$email = $_REQUEST["email"];
	$contact = $_REQUEST["contact"];
	$address = $_REQUEST["address"];
	$birthday = $_REQUEST["birthday"];
	$salary = $_REQUEST["salary"];
	$rid = $_REQUEST["Rid"];
	$joindate = date("Y-m-d");
	
	
	$check = "select Username from customer where Username = '$username'";       // Search Customers
	$check1 = "select Username from employee where Username = '$username'";      // Search Employee
	$check2 = "select Username from restaurant where Username = '$username'";    // Search Restaurant    For Already Existing Users..
	
	$query = mysqli_query($con,$check);
	$query1 = mysqli_query($con,$check1);
	$query2 = mysqli_query($con,$check2);
	
	
	if($username==null || $email==null || $name==null || $contact==null || $address==null || $birthday==null || $salary==null)              // If Username & Password Is Empty...
	{
		$array = array("error"=> 0);     //Please Provide Details..
	}
	elseif(mysqli_num_rows($query) > 0)
	{
		$array = array("error"=> 3);     //User Already Exists...
	}
	elseif(mysqli_num_rows($query1) > 0)
	{
		$array = array("error"=> 3);     //User Already Exists...
	}
	elseif(mysqli_num_rows($query2) > 0)
	{
		$array = array("error"=> 3);    //User Already Exists...
	}
	else                                             // If Not Exist Then...
	{
		$n=10;
		$characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
		$generatedpassword= '';

		for ($i = 0; $i < $n; $i++) {
			$index = rand(0, strlen($characters) - 1);
			$generatedpassword .= $characters[$index];
		}
		
		$password=$generatedpassword;
		
		$query3 =  mysqli_query($con,"INSERT INTO `employee` VALUES(NULL,'$username','$name','$email','$contact','$address','$password','$birthday','$joindate','$salary','$rid')");
		
		if($query3)
		{
			$mail = new PHPMailer(true);

			
				//Server settings
				$mail->SMTPDebug = SMTP::DEBUG_SERVER;                      //Enable verbose debug output
				$mail->isSMTP();                                            //Send using SMTP
				$mail->Host       = 'ssl://smtp.gmail.com';                     //Set the SMTP server to send through
				$mail->SMTPAuth   = true;                                   //Enable SMTP authentication
				$mail->Username   = 'hellorestaurant1010@gmail.com';                     //SMTP username
				$mail->Password   = 'helloRestaurant';                               //SMTP password
				$mail->SMTPSecure = PHPMailer::ENCRYPTION_SMTPS;            //Enable implicit TLS encryption
				$mail->Port       = 465;                                    //TCP port to connect to; use 587 if you have set `SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS`

				//Recipients
				$mail->setFrom('from@example.com', 'Mailer');
				$mail->addAddress('joe@example.net', 'Joe User');     //Add a recipient
				$mail->addAddress($email);               //Name is optional

				//Content
				$mail->isHTML(true);                                  //Set email format to HTML
				$mail->Subject = 'Request For New Password';
				$mail->Body = "Your New Password is : ".$password.""; 
			

				$mail->send();
				$array = array("error"=> 1);
		}
		else
		{
			$array = array("error"=> 2);
		}
	}
	 echo json_encode($array);
	
	mysqli_close($con);
	
	//Error=>0  : Please Provide Details..
	//Error=>1  : User Find Sucessufully OR Login Sucessfully..
	//Error=>2  : Invalid Login Details OR No Such User Exists..
	//Error=>3  : User Already Exists...
	
?>
