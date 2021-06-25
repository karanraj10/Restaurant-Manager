<?php
	$email=$_REQUEST['email'];
	
	include("config.php");
    $query=mysqli_query($con,"SELECT Username,Name FROM customer WHERE Email='$email'");
  
	$row = mysqli_fetch_assoc($query);
	$name = $row['Name'];
	$username = $row['Username'];
				
	date_default_timezone_set('Etc/UTC');
		
	require '/usr/share/php/libphp-phpmailer/class.phpmailer.php';
	require '/usr/share/php/libphp-phpmailer/class.smtp.php';
		
	$mail = new PHPMailer;
	$mail->setFrom('hirestaurantmanager@gmail.com','Restaurant Manager');
	$mail->addAddress($email);
	$mail->Subject = 'Welcome To Restaurant';
	$mail->Body = file_get_contents("http://34.93.138.114/TempletForWelcomeMailCustomer.php?name=$name&username=$username"); 
	$mail->IsSMTP();
	$mail->SMTPSecure = 'tls';
	$mail->Host = 'smtp.gmail.com';
	$mail->SMTPAuth = true;
	$mail->Port = 587;
		
	$mail->isHTML(true);
		
	$mail->Username = 'hirestaurantmanager@gmail.com';	
	$mail->Password = 'Database@123';
		
	$mail->send();	
	
?>