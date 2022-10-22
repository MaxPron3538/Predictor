var button;
var count = -20;
var countTop = 220;
var GetDeleteCount = -20;
var GetDeleteCountTop = 120;

function moveBottomForGetOrDelete(message){
    if(GetDeleteCount < 120){
        button = document.getElementById(message);
        button.style.marginTop = GetDeleteCount+"px";
        GetDeleteCount+=20;
    }
}

function moveTopForGetOrDelete(message){
    if(GetDeleteCountTop >= 0){
        button = document.getElementById(message);
        button.style.marginTop = GetDeleteCountTop+"px";
        GetDeleteCountTop-=20;
    }
}

function moveBottom(message){
    if(count < 220){
        button = document.getElementById(message);
        button.style.marginTop = count+"px";
        count+=20;
    }
}

function moveTop(message){
    if(countTop > -20){
        button = document.getElementById(message);
        button.style.marginTop = countTop+"px";
        countTop-=20;
    }
}

function displayFormAdd(){
    document.getElementById("getId").style.visibility = "hidden";
    document.getElementById("deleteId").style.visibility = "hidden";
    document.getElementById("form2").style.visibility = "hidden";
    document.getElementById("form1").style.visibility = "visible";

    if(count == -20){
        countTop = -20;
    }else{
        countTop = 220;
        count = -20;
    }
    if(GetDeleteCount != -20){
       GetDeleteCountTop = 120;
       setInterval(moveTopForGetOrDelete,10,'getAll');
       setInterval(moveTopForGetOrDelete,10,'deleteAll');
    }
    setInterval(moveTop,10,'get');
    setInterval(moveBottom,10,'update');
}

function displayFormUpdateId(){
    document.getElementById("form1").style.visibility = "hidden";
    document.getElementById("getId").style.visibility = "hidden";
    document.getElementById("deleteId").style.visibility = "hidden";
    document.getElementById("form2").style.visibility = "visible";


    //GetDeleteCountTop = 120;
    //setInterval(moveTopForGetOrDelete,10,'get');

    if(count == -20){
        countTop = -20;
    }else{
        countTop = 220;
        count = -20;
    }
    if(GetDeleteCount != -20){
       GetDeleteCountTop = 120;
       setInterval(moveTopForGetOrDelete,10,'getAll');
       setInterval(moveTopForGetOrDelete,10,'deleteAll');
    }
    setInterval(moveTop,10,'update');
    setInterval(moveBottom,10,'get');
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

    if(GetDeleteCount == -20){
        GetDeleteCountTop = -20;
    }else{
        GetDeleteCountTop = 120;
        GetDeleteCount = -20;
    }

    if(count != -20){
        countTop = 220;
        setInterval(moveTop,10,'update');
    }
    setInterval(moveBottomForGetOrDelete,10,'getAll');
    setInterval(moveTopForGetOrDelete,10,'deleteAll');
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

    if(GetDeleteCount == -20){
        GetDeleteCountTop = -20;
    }else{
        GetDeleteCountTop = 120;
        GetDeleteCount = -20;
    }

    if(count != -20){
        countTop = 220;
        setInterval(moveTop,10,'update');
    }
    setInterval(moveTopForGetOrDelete,10,'getAll');
    setInterval(moveBottomForGetOrDelete,10,'deleteAll');
}

function displayFormDelete(){
   var id =  document.getElementById("id-delete").value;
   document.getElementById("deleteId").action+=id;
}