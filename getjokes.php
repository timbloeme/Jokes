<?php 
    $con=mysqli_connect("db.timbloeme.nl","md137126db280854","iYPIzNwH","md137126db280854");
    
    $id = urldecode($_POST['id']);
    $ids = explode(",",id);
    $nr_ids = count(ids)
    // Get Post Data
    $q = "SELECT * FROM jokes " 
       
    $jsonData      = array();
    $jsonTempData  = array();
      
     for($i=0; $i<nr_ids; $i++){
          $result = mysqli_query($con,"SELECT * FROM jokes WHERE id ="+ids[0]);
          
          $jsonTempData['tittle']         = $results[2];
          $jsonTempData['joke']       = $results[3];
          $jsonTempData['uid']   = $results[1];
           
          $jsonData[] = $jsonTempData;
       }
     
     $outputArr = array();
     $outputArr['Android'] = $jsonData;
      
     // Encode Array To JSON Data
     print_r( json_encode($outputArr));
      
 
 ?>