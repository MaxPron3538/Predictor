var button;
var countBottomAU;
var countTopAU;
var countBottomGD;
var countTopGD;
var marginCountBottom = -20;
var marginCountTop = 220;
var GetDeleteMarginCountBottom = -20;
var GetDeleteMarginCountTop = 120;
var condition;
var conditionGetOrDelete;
var index;

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
    if(marginCountTop > -20){
        button = document.getElementById(message);
        button.style.marginTop = marginCountTop+"px";
        marginCountTop-=20;
    }
}

function moveMarginBottom(message){
    if(marginCountBottom < 220){
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
    if(GetDeleteMarginCountTop >= 0){
        button = document.getElementById(message);
        button.style.marginTop = GetDeleteMarginCountTop+"px";
        GetDeleteMarginCountTop-=20;
    }
}


function displayFormAdd(){
    document.getElementById("getId").style.visibility = "hidden";
    document.getElementById("deleteId").style.visibility = "hidden";
    document.getElementById("form2").style.visibility = "hidden";
    document.getElementById("form1").style.visibility = "visible";

    if(GetDeleteMarginCountBottom != -20){
        if(marginCountBottom != -20){
            if(condition == true){
                if(index != true){
                    marginCountBottom = -20;
                    setInterval(moveMarginBottom,10,'update');
                }
            }
        }
        GetDeleteMarginCountTop = 120;

        if(conditionGetOrDelete == true){
            if(countTopGD == - 120){
                countBottomGD = -120;
                setInterval(moveBottomButtonGD,10,'delete',conditionGetOrDelete,0);
            }
            setInterval(moveMarginTopForGetOrDelete,10,'delete');
        }else{
            if(countBottomGD == 120){
                countTopGD = 120;
                setInterval(moveTopButtonGD,10,'delete',conditionGetOrDelete,0);
            }
            setInterval(moveMarginTopForGetOrDelete,10,'getAll');
        }
        index = true;
    }
    if(marginCountBottom != -20){
        if(condition == true){
           countBottomAU = -220;
           setInterval(moveBottomButtonAU,10,'update',condition,0);
        }
        else{
           countBottomAU = 0;
           marginCountBottom = -20;
           setInterval(moveBottomButtonAU,10,'update',condition,220);
        }
    }else{
        condition = true;
        setInterval(moveMarginBottom,10,'update');
    }
}

function displayFormUpdateId(){
    document.getElementById("form1").style.visibility = "hidden";
    document.getElementById("getId").style.visibility = "hidden";
    document.getElementById("deleteId").style.visibility = "hidden";
    document.getElementById("form2").style.visibility = "visible";

    if(GetDeleteMarginCountBottom != -20){
        if(marginCountBottom != -20){
            if(condition == false){
                if(index != true){
                    marginCountBottom = -20;
                    setInterval(moveMarginBottom,10,'get');
                }
            }
        }
        GetDeleteMarginCountTop = 120;

        if(conditionGetOrDelete == true){
            if(countTopGD == - 120){
                countBottomGD = -120;
                setInterval(moveBottomButtonGD,10,'delete',conditionGetOrDelete,0);
            }
            setInterval(moveMarginTopForGetOrDelete,10,'delete');
        }else{
            if(countBottomGD == 120){
                countTopGD = 120;
                setInterval(moveTopButtonGD,10,'delete',conditionGetOrDelete,0);
            }
            setInterval(moveMarginTopForGetOrDelete,10,'getAll');
        }
        index = true;
    }
    if(marginCountBottom != -20){
        if(condition == true){
            countTopAU = 0;
            marginCountBottom = -20;
            setInterval(moveTopButtonAU,10,'update',condition,-220);
        }
        else{
            countTopAU = 220;
            setInterval(moveTopButtonAU,10,'update',condition,0);
        }
    }
    else{
        condition = false;
        setInterval(moveMarginBottom,10,'get');
    }
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

    if(marginCountBottom != -20){
        if(GetDeleteMarginCountBottom != -20){
            if(conditionGetOrDelete == true){
                if(index != false){
                   GetDeleteMarginCountBottom = -20;
                   setInterval(moveMarginBottomForGetOrDelete,10,'delete');
                }
            }
        }
        marginCountTop = 220;

        if(condition == true){
            if(countTopAU == -220){
               countBottomAU = -220;
               setInterval(moveBottomButtonAU,10,'update',condition,0);
            }
            setInterval(moveMarginTop,10,'update');
        }else{
            if(countBottomAU == 220){
               countTopAU = 220;
               setInterval(moveTopButtonAU,10,'update',condition,0);
            }
            setInterval(moveMarginTop,10,'get');
        }
        index = false;
    }
    if(GetDeleteMarginCountBottom != -20){
        if(conditionGetOrDelete == true){
           countBottomGD = -120;
           setInterval(moveBottomButtonGD,10,'delete',conditionGetOrDelete,0);
        }
        else{
           countBottomGD = 0;
           GetDeleteMarginCountBottom = -20;
           setInterval(moveBottomButtonGD,10,'delete',conditionGetOrDelete,120);
        }
    }
    else{
        conditionGetOrDelete = true;
        setInterval(moveMarginBottomForGetOrDelete,10,'delete');
    }
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

    if(marginCountBottom != -20){
        if(GetDeleteMarginCountBottom != -20){
            if(conditionGetOrDelete == false){
                if(index != false){
                   GetDeleteMarginCountBottom = -20;
                   setInterval(moveMarginBottomForGetOrDelete,10,'getAll');
                }
            }
        }
        marginCountTop = 220;

        if(condition == true){
            if(countTopAU == -220){
               countBottomAU = -220;
               setInterval(moveBottomButtonAU,10,'update',condition,0);
            }
            setInterval(moveMarginTop,10,'update');
        }else{
            if(countBottomAU == 220){
               countTopAU = 120;
               setInterval(moveTopButtonAU,10,'update',condition,0);
            }
            setInterval(moveMarginTop,10,'get');
        }
        index = false;
    }
    if(GetDeleteMarginCountBottom != -20){

        if(conditionGetOrDelete == true){
           countTopGD = 0;
           GetDeleteMarginCountBottom = -20;
           setInterval(moveTopButtonGD,10,'delete',conditionGetOrDelete,-120);
        }
        else{
           countTopGD = 120;
           setInterval(moveTopButtonGD,10,'delete',conditionGetOrDelete,0);
        }
    }else{
        conditionGetOrDelete = false;
        setInterval(moveMarginBottomForGetOrDelete,10,'getAll');
    }

}

function displayFormDelete(){
   var id =  document.getElementById("id-delete").value;
   document.getElementById("deleteId").action+=id;
}