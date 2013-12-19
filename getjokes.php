<?php 
    $con=mysqli_connect("db.timbloeme.nl","md137126db280854","iYPIzNwH","md137126db280854");
    if(isset($_POST['id']))
        $id = urldecode($_POST['id']);
    else
        $id = "0";
    $ids = explode(",",$id);
    $nr_ids = count($ids);
    
    $jsonData      = array();
    $jsonTempData  = array();
    
    if($ids[0]=="0"){
        $result = mysqli_query($con,"SELECT * FROM jokes ORDER BY id DESC LIMIT 10");
        while($row = mysqli_fetch_array($result)){
            $result_name = mysqli_query($con,"SELECT name FROM users WHERE id=" . $row[1]);
            $row_name = mysqli_fetch_array($result_name);
            $jsonTempData = array();
            $jsonTempData['id']    = $row[0];
            $jsonTempData['uid']   = $row[1];
            $jsonTempData['title'] = $row[2];
            $jsonTempData['joke']  = $row[3];
            $jsonTempData['user']  = $row_name[0];
            
            
            $jsonData[] = $jsonTempData; 
        }
    }else{
        for($i=0; $i<$nr_ids; $i++){
            $result = mysqli_query($con,"SELECT * FROM jokes WHERE id =" . $ids[0]);
            $row = mysqli_fetch_array($result);
            $jsonTempData = array();
            $jsonTempData['id']    = $row[0];
            $jsonTempData['uid']   = $row[1];
            $jsonTempData['title'] = $row[2];
            $jsonTempData['joke']  = $row[3];
             
            $jsonData[] = $jsonTempData;
        }
    }
     
    $outputArr = array();
    $outputArr['Android'] = $jsonData;
      
     // Encode Array To JSON Data
     print_r( json_encode($outputArr));
 ?>