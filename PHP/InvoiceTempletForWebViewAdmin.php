<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Example 1</title>
    <style>
			.clearfix:after {
		  content: "";
		  display: table;
		  clear: both;
		}

		a {
		  color: #5D6975;
		  text-decoration: underline;
		}

		body {
		  position: relative;
		  width: 100%; 
		  height: 15cm; 
		  margin: 0 auto; 
		  color: #001028;
		  background: #FFFFFF; 
		  font-family: Arial, sans-serif; 
		  font-size: 12px; 
		  font-family: Arial;
		}

		header {
		  padding: 10px 0;
		  margin-bottom: 30px;
		}

		#logo {
		  text-align: center;
		  margin-bottom: 10px;
		}

		#logo img {
		  width: 90px;
		}

		h1 {
		  border-top: 1px solid  #5D6975;
		  border-bottom: 1px solid  #5D6975;
		  color: #5D6975;
		  font-size: 2.4em;
		  line-height: 1.4em;
		  font-weight: normal;
		  text-align: center;
		  margin: 0 0 20px 0;
		  background: url(dimension.png);
		}

		#project {
		  float: left;
		}

		#project span {
		  color: #5D6975;
		  text-align: right;
		  width: 52px;
		  margin-right: 10px;
		  display: inline-block;
		  font-size: 0.8em;
		}

		#company {
		  float: right;
		  text-align: right;
		}

		#project div,
		#company div {
			text-align:left;
		  white-space: nowrap;        
		}

		table {
		  width: 100%;
		  border-collapse: collapse;
		  border-spacing: 0;
		  margin-bottom: 20px;
		}

		table tr:nth-child(2n-1) td {
		  background: #F5F5F5;
		}

		table th,
		table td {
		  text-align: center;
		}

		table th {
		  padding: 5px 20px;
		  color: #5D6975;
		  border-bottom: 1px solid #C1CED9;
		  white-space: nowrap;        
		  font-weight: normal;
		}

		table .service,
		table .desc {
		  text-align: left;
		}

		table td {
		font-size: 1.0em;
		  padding: 9px;
		  text-align: right;
		}

		table td.service,
		table td.desc {
		  vertical-align: top;
		}

		table td.unit,
		table td.qty,
		table td.total {
		  font-size: 1.0em;
		  text-align: center;
		}

		table td.grand {
		text-align:right;
		  border-top: 1px solid #5D6975;;
		}

		#notices .notice {
		  color: #5D6975;
		  font-size: 1.0em;
		}

		footer {
		  color: #5D6975;
		  width: 100%;
		  height: 30px;
		  position: absolute;
		  bottom: 0;
		  border-top: 1px solid #C1CED9;
		  padding: 8px 0;
		  text-align: center;
		}
			
	</style>
  </head>
  <body>
   <?php 
	
	$ono = $_REQUEST['ono'];
	$email = $_REQUEST['email'];
	
	 include("config.php");

	$query = mysqli_query($con,"SELECT * FROM `order` WHERE Ono='$ono'");
	
	$row = mysqli_fetch_assoc($query);
	
	$amount = doubleval($row['Amount']);
	$tax = intval($row['Tax']);
	$discount = intval($row['Discount']);
	$taxtotal = $amount + ($amount*$tax)/100;
	$discountotal = $taxtotal - ($amount*$discount)/100;
	$grandtotal = $row['TotalAmount'];
	
	$query = mysqli_query($con,"SELECT * FROM `order_items` WHERE Ono='$ono'");

?>
  
    <header class="clearfix">
      <div id="logo">
        <img src="UmrOawf.png"/>
      </div>
      <h1>Restaurant Manager</h1>
      <div id="project">
        <div><span>Order</span> <?php echo $ono; ?></div>
        <div><span>Email</span> <a href="<?php echo $email;?>"><?php echo $email;?></a></div>
        <div><span>Date</span> <?php  echo date("Y-m-d"); ?></div>
      </div>
    </header>
    <main>
      <table>
        <thead>
          <tr>
            <th style=" font-size: 0.9em; font-family: DejaVu Sans, sans-serif; text-align:left;">Item Name</th>
            <th style=" font-size: 0.9em; font-family: DejaVu Sans, sans-serif;">PRICE</th>
            <th style=" font-size: 0.9em; font-family: DejaVu Sans, sans-serif;">QTY</th>
            <th style=" font-size: 0.9em; font-family: DejaVu Sans, sans-serif;">TOTAL</th>
          </tr>
        </thead>
        <tbody>
		<?php 
		while($row1=mysqli_fetch_assoc($query))
		{		
			$ino = $row1['Ino'];
			$query1 = mysqli_query($con,"SELECT Name,Price FROM `items` WHERE Ino='$ino'");
			$row2 = mysqli_fetch_assoc($query1);
			$name = $row2['Name'];
			$price = $row2['Price'];
			
		?>
          <tr>
            <td style=" font-size: 0.7em; font-family: DejaVu Sans, sans-serif; text-align: left;"><?php echo $name; ?></td>
            <td style=" font-size: 0.7em; font-family: DejaVu Sans, sans-serif; text-align: center;"><?php echo "₹".intval($price); ?></td>
            <td style=" font-size: 0.7em; font-family: DejaVu Sans, sans-serif; text-align: center;"><?php echo $row1['Quantity']; ?></td>
            <td style=" font-size: 0.7em; font-family: DejaVu Sans, sans-serif; text-align: center;"><?php  echo "₹".floatval($row['Amount']); ?></td>
          </tr>
		  <?php } ?>
          <tr>
            <td colspan="3" style=" font-size: 0.7em; font-family: DejaVu Sans, sans-serif;; text-align: left;"><b>SUBTOTAL</b></td>
            <td style=" font-size: 0.7em; font-family: DejaVu Sans, sans-serif; text-align: center;"><?php echo "₹".$amount; ?></td>
          </tr>
          <tr>
            <td colspan="3" style=" font-size: 0.7em; font-family: DejaVu Sans, sans-serif; text-align: left;"><b>TAX <?php echo $tax; ?>%</b></td>
            <td style=" font-size: 0.7em; font-family: DejaVu Sans, sans-serif; text-align: center;"><?php echo "₹".$taxtotal; ?> </td>
          </tr>
		  <tr>
            <td colspan="3" style=" font-size: 0.7em; font-family: DejaVu Sans, sans-serif; text-align: left;"><b>Discount <?php  echo $discount; ?>%</b></td>
            <td colspan="3" style=" font-size: 0.7em; font-family: DejaVu Sans, sans-serif; text-align: center;"><?php echo "₹".$discountotal; ?></td>
          </tr>
          <tr>
            <td colspan="3" style=" font-size: 0.8em; font-family: DejaVu Sans, sans-serif; text-align: left; border-top: 1px solid #5D6975;"><b>GRAND TOTAL</b></td>
            <td class="total" style="font-size: 0.8em; font-family: DejaVu Sans, sans-serif; text-align: center; border-top: 1px solid #5D6975;"><?php echo "₹".$grandtotal; ?></td>
          </tr>
        </tbody>
      </table>
      <div id="notices">
        <div>NOTICE:</div>
        <div class="notice"> Invoice was created on a computer and is valid without the signature and seal.</div>
      </div>
    </main>
  </body>
</html>
<?php
$query = mysqli_query($con,"UPDATE `order` SET Paid='2' WHERE Ono='$ono'");
?>
