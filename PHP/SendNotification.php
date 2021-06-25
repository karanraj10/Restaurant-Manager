<?php
$url = 'https://fcm.googleapis.com/v1/projects/hackathon-76501/messages:send';

$access_token=file_get_contents('http://localhost:3030/');
 
$topic = $_REQUEST['topic'];
$message =  $_REQUEST['message'];

$options = array(
    'http' => array(
        'header'  => "Authorization: Bearer ".$access_token."\r\n"."Content-type: application/json\r\n",
        'method'  => 'POST',
        'content' =>  '{
   "message":{
      "topic":"'.$topic.'",
      "notification":{
        "body":"'.$message.'",
        "title":"Restaurant Manager"
      }
   }
}')
);

$context  = stream_context_create($options);
$result = file_get_contents($url,false, $context);


echo $result;
?>
