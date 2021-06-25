<?php
	$email=$_REQUEST['email'];
	$message=$_REQUEST['message'];
	
	/*Mail Function*/
		
		date_default_timezone_set('Etc/UTC');
		
		require '/usr/share/php/libphp-phpmailer/class.phpmailer.php';
		require '/usr/share/php/libphp-phpmailer/class.smtp.php';
		
		$mail = new PHPMailer;
		$mail->setFrom('qmuzwncy@gmail.com');
		$mail->addAddress('mihirchauhan756@gmail.com');
		$mail->addAddress('rajkaran5478@gmail.com');
		$mail->addAddress('akashrana251299@gmail.com');
		$mail->Subject = 'Message sent by '.$email;
		$mail->Body = $message;
		$mail->IsSMTP();
		$mail->SMTPSecure = 'tls';
		$mail->Host = 'smtp.gmail.com';
		$mail->SMTPAuth = true;
		$mail->Port = 587;
		
		$mail->Username = '';
		$mail->Password = '';
		$mail->send();	
		
		echo "Send Mail Successfully";
		
?>
