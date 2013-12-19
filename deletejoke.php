<?php
    $con=mysqli_connect("db.timbloeme.nl","md137126db280854","iYPIzNwH","md137126db280854");
    
    $id = urldecode($_POST['id']);
    
    $result = mysqli_query($con,"DELETE FROM jokes WHERE id=" . $id);
?>