<?php

	$email = $_REQUEST['email'];
	$ono = $_REQUEST['ono'];
	
	require 'vendor/autoload.php';
	use Dompdf\Dompdf;
	
	// instantiat and use the dompdf class
	$dompdf = new Dompdf();
	echo $dompf;
	$dompdf->loadHtml(file_get_contents("http://localhost/InvoiceTempletForMailAdmin.php?ono=$ono&email=$email"));


	// (Optional) Setup the paper size and orientation
	$dompdf->setPaper('A5', 'portrait');

	// Render the HTML as PDF

		$dompdf->render();
	
		date_default_timezone_set('Etc/UTC');
		
		require '/usr/share/php/libphp-phpmailer/class.phpmailer.php';
		require '/usr/share/php/libphp-phpmailer/class.smtp.php';
		
		$mail = new PHPMailer;
		$mail->addAddress($email);
		$mail->Subject = 'Message sent by Restaurant Manager';
		$mail->Body = "Invoice";
		$mail->IsSMTP();
		$mail->SMTPSecure = 'tls';
		$mail->Host = 'smtp.gmail.com';
		$mail->SMTPAuth = true;
		$mail->Port = 587;

		$pdfString = $dompdf->output();
		
		$mail->addStringAttachment($pdfString, 'invoice.pdf');
		
		$mail->Username = 'hirestaurantmanager@gmail.com';
		$mail->Password = 'Database@123';
		
		$mail->send();
		
		
	
?>

