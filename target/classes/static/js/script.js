var button;
var countBottomAU;
var countTopAU;
var countBottomGD;
var countTopGD;
var marginCountBottom = -20;
var GetDeleteMarginCountBottom = -20;
var condition;
var conditionGetOrDelete;
var pointer;
var firstInterval;
var secondInterval;
var stopInterval;

function moveBottomButtonAU(message,condition,fullStep){
   if(condition == true){
      if(countBottomAU <= fullStep){
         moveBottomAU(message);
      }
   }else{
      if(countBottomAU < fullStep){
         moveBottomAU(message);
      }
   }
}

function moveTopButtonAU(message,condition,fullStep){
   if(condition == true){
      if(countTopAU > fullStep){
         moveTopAU(message);
      }
   }else{
      if(countTopAU >= fullStep){
         moveTopAU(message);
      }
   }
}

function moveBottomButtonGD(message,condition,fullStep){
   if(condition == true){
      if(countBottomGD <= fullStep){
         moveBottomGD(message);
      }
   }else{
      if(countBottomGD < fullStep){
         moveBottomGD(message);
      }
   }
}

function moveTopButtonGD(message,condition,fullStep){
   if(condition == true){
      if(countTopGD > fullStep){
         moveTopGD(message);
      }
   }else{
      if(countTopGD >= fullStep){
         moveTopGD(message);
      }
   }
}

function moveBottomAU(message){
   button = document.getElementById(message);
   button.style.top = countBottomAU+"px";
   countBottomAU+=20;
}

function moveTopAU(message){
   button = document.getElementById(message);
   button.style.top = countTopAU+"px";
   countTopAU-=20;
}

function moveBottomGD(message){
    button = document.getElementById(message);
    button.style.top = countBottomGD+"px";
    countBottomGD+=20;
}

function moveTopGD(message){
    button = document.getElementById(message);
    button.style.top = countTopGD+"px";
    countTopGD-=20;
}

function moveMarginTop(message){
    if(marginCountBottom > -20){
        button = document.getElementById(message);
        button.style.marginTop = marginCountBottom+"px";
        marginCountBottom-=20;
    }
}

function moveMarginBottom(message){
    if(marginCountBottom < 200){
        button = document.getElementById(message);
        button.style.marginTop = marginCountBottom+"px";
        marginCountBottom+=20;
    }
}


function moveMarginBottomForGetOrDelete(message){
    if(GetDeleteMarginCountBottom < 120){
        button = document.getElementById(message);
        button.style.marginTop = GetDeleteMarginCountBottom+"px";
        GetDeleteMarginCountBottom+=20;
    }
}

function moveMarginTopForGetOrDelete(message){
    if(GetDeleteMarginCountBottom >= 0){
        button = document.getElementById(message);
        button.style.marginTop = GetDeleteMarginCountBottom+"px";
        GetDeleteMarginCountBottom-=20;
    }
}

function setHiddenForm1(){
   document.getElementById("form1").style.visibility = "hidden";
}

function setHiddenForm2(){
   document.getElementById("form2").style.visibility = "hidden";
}

function setHiddenGetId(){
   document.getElementById("getId").style.visibility = "hidden";
}
function setHiddenDeleteId(){
   document.getElementById("deleteId").style.visibility = "hidden";
}

function displayFormAdd(){
    document.getElementById("getId").style.visibility = "hidden";
    document.getElementById("deleteId").style.visibility = "hidden";
    document.getElementById("form2").style.visibility = "hidden";
    document.getElementById("form1").style.visibility = "visible";
    clearInterval(stopInterval);

    if(GetDeleteMarginCountBottom != -20){
        clearInterval(secondInterval);


        if(conditionGetOrDelete == true){
            if(countTopGD == - 120){
                countBottomGD = -120;
                stopInterval = setInterval(moveBottomButtonGD,10,'delete',conditionGetOrDelete,0);
            }
            secondInterval = setInterval(moveMarginTopForGetOrDelete,10,'delete');
        }else{
            if(countBottomGD == 120){
                countTopGD = 120;
                stopInterval = setInterval(moveTopButtonGD,10,'delete',conditionGetOrDelete,0);
            }
            secondInterval = setInterval(moveMarginTopForGetOrDelete,10,'getAll');
        }
    }
    if(marginCountBottom != -20){
        if(pointer == 0){
            marginCountBottom = 200;
            clearInterval(firstInterval);

            if(condition == false){
               countBottomAU = -200;
               firstInterval = setInterval(moveMarginTop,10,'get');
               stopInterval = setInterval(moveBottomButtonAU,10,'update',true,0);
            }else{
               firstInterval = setInterval(moveMarginTop,10,'update');
            }
            setTimeout(setHiddenForm1,100);
        }
        else if(condition == true){
           countBottomAU = -200;
           stopInterval = setInterval(moveBottomButtonAU,10,'update',condition,0);
        }
        else{
           countBottomAU = 0;
           stopInterval = setInterval(moveBottomButtonAU,10,'update',condition,200);
        }
    }else{
        condition = true;
        clearInterval(firstInterval);
        firstInterval = setInterval(moveMarginBottom,10,'update');
    }
    pointer = 0;
}

function displayFormUpdateId(){
    document.getElementById("form1").style.visibility = "hidden";
    document.getElementById("getId").style.visibility = "hidden";
    document.getElementById("deleteId").style.visibility = "hidden";
    document.getElementById("form2").style.visibility = "visible";
    clearInterval(stopInterval);

    if(GetDeleteMarginCountBottom != -20){
        clearInterval(secondInterval);

        if(conditionGetOrDelete == true){
            if(countTopGD == - 120){
                countBottomGD = -120;
                stopInterval = setInterval(moveBottomButtonGD,10,'delete',conditionGetOrDelete,0);
            }
            secondInterval = setInterval(moveMarginTopForGetOrDelete,10,'delete');
        }else{
            if(countBottomGD == 120){
                countTopGD = 120;
                stopInterval = setInterval(moveTopButtonGD,10,'delete',conditionGetOrDelete,0);
            }
            secondInterval = setInterval(moveMarginTopForGetOrDelete,10,'getAll');
        }
    }
    if(marginCountBottom != -20){
        if(pointer == 1){
            marginCountBottom = 200;
            clearInterval(firstInterval);

            if(condition == true){
               countBottomAU = -200;
               firstInterval = setInterval(moveMarginTop,10,'update');
               stopInterval = setInterval(moveBottomButtonAU,10,'update',condition,0);
            }else{
               firstInterval = setInterval(moveMarginTop,10,'get');
            }
            setTimeout(setHiddenForm2,100);
        }
        else if(condition == true){
            countTopAU = 0;
            stopInterval = setInterval(moveTopButtonAU,10,'update',condition,-200);
        }
        else{
            countTopAU = 200;
            stopInterval = setInterval(moveTopButtonAU,10,'update',condition,0);
        }
    }
    else{
        condition = false;
        clearInterval(firstInterval);
        firstInterval = setInterval(moveMarginBottom,10,'get');
    }
    pointer = 1;
}

function displayFormGetId(){
    document.getElementById("form1").style.visibility = "hidden";
    document.getElementById("form2").style.visibility = "hidden";
    document.getElementById("deleteId").style.visibility = "hidden";
    document.getElementById("getId").style.visibility = "visible";
    clearInterval(stopInterval);

    if(marginCountBottom != -20){
        clearInterval(firstInterval);

        if(condition == true){
            if(countTopAU == -200){
               countBottomAU = -200;
               stopInterval = setInterval(moveBottomButtonAU,10,'update',condition,0);
            }
            firstInterval = setInterval(moveMarginTop,10,'update');
        }else{
            if(countBottomAU == 200){
               countTopAU = 200;
               stopInterval = setInterval(moveTopButtonAU,10,'update',condition,0);
            }
            firstInterval = setInterval(moveMarginTop,10,'get');
        }
    }
    if(GetDeleteMarginCountBottom != -20){
        if(pointer == 2){
            GetDeleteMarginCountBottom = 120;
            clearInterval(secondInterval);

            if(conditionGetOrDelete == false){
               countBottomGD = -120;
               secondInterval = setInterval(moveMarginTopForGetOrDelete,10,'getAll');
               stopInterval = setInterval(moveBottomButtonGD,10,'delete',true,0);
            }else{
               secondInterval = setInterval(moveMarginTopForGetOrDelete,10,'delete');
            }
            setTimeout(setHiddenGetId,100);
        }
        else if(conditionGetOrDelete == true){
           countBottomGD = -120;
           stopInterval = setInterval(moveBottomButtonGD,10,'delete',conditionGetOrDelete,0);
        }
        else{
           countBottomGD = 0;
           stopInterval = setInterval(moveBottomButtonGD,10,'delete',conditionGetOrDelete,120);
        }
    }
    else{
        conditionGetOrDelete = true;
        clearInterval(secondInterval);
        secondInterval = setInterval(moveMarginBottomForGetOrDelete,10,'delete');
    }
    pointer = 2;
}

function displayFormDeleteId(){
    document.getElementById("form1").style.visibility = "hidden";
    document.getElementById("form2").style.visibility = "hidden";
    document.getElementById("getId").style.visibility = "hidden";
    document.getElementById("deleteId").style.visibility = "visible";
    clearInterval(stopInterval);

    if(marginCountBottom != -20){
        clearInterval(firstInterval);

        if(condition == true){
            if(countTopAU == -200){
               countBottomAU = -200;
               stopInterval = setInterval(moveBottomButtonAU,10,'update',condition,0);
            }
            firstInterval = setInterval(moveMarginTop,10,'update');
        }else{
            if(countBottomAU == 200){
               countTopAU = 120;
               stopInterval = setInterval(moveTopButtonAU,10,'update',condition,0);
            }
            firstInterval = setInterval(moveMarginTop,10,'get');
        }
    }
    if(GetDeleteMarginCountBottom != -20){
        if(pointer == 3){
           GetDeleteMarginCountBottom = 120;
            clearInterval(secondInterval);

            if(conditionGetOrDelete == true){
               countBottomGD = -120;
               secondInterval = setInterval(moveMarginTopForGetOrDelete,10,'delete');
               stopInterval = setInterval(moveBottomButtonGD,10,'delete',conditionGetOrDelete,0);
            }else{
               secondInterval = setInterval(moveMarginTopForGetOrDelete,10,'getAll');
            }
            setTimeout(setHiddenDeleteId,100);
        }
        else if(conditionGetOrDelete == true){
           countTopGD = 0;
           stopInterval = setInterval(moveTopButtonGD,10,'delete',conditionGetOrDelete,-120);
        }
        else{
           countTopGD = 120;
           stopInterval = setInterval(moveTopButtonGD,10,'delete',conditionGetOrDelete,0);
        }
    }else{
        conditionGetOrDelete = false;
        clearInterval(secondInterval);
        secondInterval = setInterval(moveMarginBottomForGetOrDelete,10,'getAll');
    }
    pointer = 3;
}

function displayFormGet(){
var id = document.getElementById("id-product-get").value;
document.getElementById("getId").action+=id;
}
function displayFormDelete(){
var id = document.getElementById("id-product-delete").value;
document.getElementById("deleteId").action+=id;
}

function displayFormUpdate(){
alert("Hello");
var id = document.getElementById("id-product-update").value;
document.getElementById("form2").action+=id;
alert(document.getElementById("form2").action);
}