<?php
    $con=mysqli_connect("db.timbloeme.nl","md137126db280854","iYPIzNwH","md137126db280854");
    
    $id = urldecode($_POST['id']);
    $title = urldecode($_POST['title']);
    $joke = urldecode($_POST['joke']);
    
    $result = mysqli_query($con,"UPDATE jokes SET name=" . $title . ", joke=" . $joke . " WHERE id=" . $id);
?>