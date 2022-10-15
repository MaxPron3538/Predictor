function displayFormAdd(){
   document.getElementById("getId").style.visibility = "hidden";
   document.getElementById("deleteId").style.visibility = "hidden";
   document.getElementById("form2").style.visibility = "hidden";
   document.getElementById("form1").style.visibility = "visible";

    var button;
    var form1;
    var count = 0;

    function move2left(){
       if(count < window.screen.height*0.2){
         button = document.getElementById('update');
         button.style.marginTop = count+"px";
         count+=30;
       }
    }
    setInterval(move2left,10);
}

function displayFormUpdateId(){
   document.getElementById("form1").style.visibility = "hidden";
   document.getElementById("getId").style.visibility = "hidden";
   document.getElementById("deleteId").style.visibility = "hidden";
   document.getElementById("form2").style.visibility = "visible";
}

function displayFormUpdate(){
   var id = document.getElementById("id-update").value;
   document.getElementById("form2").action+=id;
}

function displayFormGetId(){
   document.getElementById("form1").style.visibility = "hidden";
   document.getElementById("form2").style.visibility = "hidden";
   document.getElementById("deleteId").style.visibility = "hidden";
   document.getElementById("getId").style.visibility = "visible";
}

function displayFormGet(){
   var id = document.getElementById("id-get").value;
   document.getElementById("getId").action+=id;
}

function displayFormDeleteId(){
   document.getElementById("form1").style.visibility = "hidden";
   document.getElementById("form2").style.visibility = "hidden";
   document.getElementById("getId").style.visibility = "hidden";
   document.getElementById("deleteId").style.visibility = "visible";
}

function displayFormDelete(){
   var id =  document.getElementById("id-delete").value;
   document.getElementById("deleteId").action+=id;
}