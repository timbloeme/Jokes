<?php
    $con=mysqli_connect("db.timbloeme.nl","md137126db280854","iYPIzNwH","md137126db280854");
    
    $uid = urldecode($_POST['uid']);
    $title = urldecode($_POST['title']);
    $joke = urldecode($_POST['joke']);
    
    $result = mysqli_query($con,"INSERT INTO jokes (uid, name, joke) VALUES (1,'test','test joke')";
    mysqli_close($con);
?>