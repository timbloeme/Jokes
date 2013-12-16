<?php 
    $con=mysqli_connect("db.timbloeme.nl","md137126db280854","iYPIzNwH","md137126db280854");
    
    $id = urldecode($_POST['id']);
    $ids = explode(",",id);
    $nr_ids = count(ids)
    
    $jsonData      = array();
    $jsonTempData  = array();
    
    $q = "SELECT * FROM jokes " 
    
    if(ids[0]=="0"){
        $result = mysqli_query($con,"SELECT * FROM jokes ORDER BY id DESC LIMIT 10");
        while($row = mysqli_fetch_array($result)){
            $jsonTempData['tittle']= $row[2];
            $jsonTempData['joke']  = $row[3];
            $jsonTempData['uid']   = $row[1];
        }
         
        $jsonData[] = $jsonTempData;    
    }else{
        for($i=0; $i<nr_ids; $i++){
            $result = mysqli_query($con,"SELECT * FROM jokes WHERE id ="+ids[0]);
            $row = mysqli_fetch_array($result)
            
            $jsonTempData['tittle']= $row[2];
            $jsonTempData['joke']  = $row[3];
            $jsonTempData['uid']   = $row[1];
             
            $jsonData[] = $jsonTempData;
        }
    }
     
    $outputArr = array();
    $outputArr['Android'] = $jsonData;
      
     // Encode Array To JSON Data
     print_r( json_encode($outputArr));
      
 
 ?>