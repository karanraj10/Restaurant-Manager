<?php
		use PHPMailer\PHPMailer\PHPMailer;
		use PHPMailer\PHPMailer\SMTP;
		use PHPMailer\PHPMailer\Exception;
		
		//Load Composer's autoloader
		require 'vendor/autoload.php';
		
	$email=$_REQUEST['email'];
	$post=$_REQUEST['post'];
	
	include("config.php");

    $n=10;
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $generatedpassword= '';

    for ($i = 0; $i < $n; $i++) {
    $index = rand(0, strlen($characters) - 1);
    $generatedpassword .= $characters[$index];
    }

    $secure=$generatedpassword;

    if($post=="customer"){
    $addtodatabase="UPDATE customer SET Password='$secure' WHERE Email='$email'";
    $ex = mysqli_query($con,$addtodatabase);
    }
    else if($post=="employee"){
    $addtodatabase="UPDATE employee SET Password='$secure' WHERE Email='$email'";
    $ex = mysqli_query($con,$addtodatabase);
    }
				
		// date_default_timezone_set('Etc/UTC');
		
		// require '/usr/share/php/libphp-phpmailer/class.phpmailer.php';
		// require '/usr/share/php/libphp-phpmailer/class.smtp.php';
		
		// $mail = new PHPMailer;
		// $mail->setFrom('qmuzwncy@gmail.com','Restaurant Manager');
		// $mail->addAddress($email);
		// $mail->Subject = 'Request For New Password';
		// $mail->Body = file_get_contents("http://34.93.41.224/TempletForResetPassowrd.php?password=$generatedpassword"); 
		// $mail->IsSMTP();
		// $mail->SMTPSecure = 'tls';
		// $mail->Host = 'smtp.gmail.com';
		// $mail->SMTPAuth = true;
		// $mail->Port = 587;
		
		// $mail->isHTML(true);                                  // Set email format to HTML
		
		// $mail->Username = 'hirestaurantmanager@gmail.com';
		// $mail->Password = 'Database@123';
		
		// $mail->send();	



//Create an instance; passing `true` enables exceptions
$mail = new PHPMailer(true);

try {
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

	$mail->Body = "Your New Password is : ".$secure.""; 
   

    $mail->send();
    echo 'Message has been sent';
} catch (Exception $e) {
    echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
}
	
		
		
?>


			