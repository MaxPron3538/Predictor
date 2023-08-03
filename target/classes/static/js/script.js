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
    if(marginCountBottom < 380){
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
    if(GetDeleteMarginCountBottom > -20){
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
            marginCountBottom = 380;
            clearInterval(firstInterval);

            if(condition == false){
               countBottomAU = -380;
               firstInterval = setInterval(moveMarginTop,10,'get');
               stopInterval = setInterval(moveBottomButtonAU,10,'update',true,0);
            }else{
               firstInterval = setInterval(moveMarginTop,10,'update');
            }
            setTimeout(setHiddenForm1,100);
        }
        else if(condition == true){
           countBottomAU = -380;
           stopInterval = setInterval(moveBottomButtonAU,10,'update',condition,0);
        }
        else{
           countBottomAU = 0;
           stopInterval = setInterval(moveBottomButtonAU,10,'update',condition,380);
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
            marginCountBottom = 380;
            clearInterval(firstInterval);

            if(condition == true){
               countBottomAU = -380;
               firstInterval = setInterval(moveMarginTop,10,'update');
               stopInterval = setInterval(moveBottomButtonAU,10,'update',condition,0);
            }else{
               firstInterval = setInterval(moveMarginTop,10,'get');
            }
            setTimeout(setHiddenForm2,100);
        }
        else if(condition == true){
            countTopAU = 0;
            stopInterval = setInterval(moveTopButtonAU,10,'update',condition,-380);
        }
        else{
            countTopAU = 380;
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
            if(countTopAU == -380){
               countBottomAU = -380;
               stopInterval = setInterval(moveBottomButtonAU,10,'update',condition,0);
            }
            firstInterval = setInterval(moveMarginTop,10,'update');
        }else{
            if(countBottomAU == 380){
               countTopAU = 380;
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
            if(countTopAU == -380){
               countBottomAU = -380;
               stopInterval = setInterval(moveBottomButtonAU,10,'update',condition,0);
            }
            firstInterval = setInterval(moveMarginTop,10,'update');
        }else{
            if(countBottomAU == 380){
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
var id = document.getElementById("id-transaction-get").value;
document.getElementById("getId").action+=id;
}
function displayFormDelete(){
var id = document.getElementById("id-transaction-delete").value;
document.getElementById("deleteId").action+=id;
}


var opacity = 1;
var interval;
var count = 0;

function lowOpacity()
{
    if(opacity >= 0)
    {
      document.getElementById("select").style.opacity = opacity-0.1;
      opacity=opacity-0.1;
    }else{
      clearInterval(interval);
      interval = setInterval(highOpacity,20);
    }
}

function highOpacity()
{
    if(opacity <= 1)
    {
      document.getElementById("select").style.opacity = opacity+0.1
      opacity=opacity+0.1;
    }else{
      clearInterval(interval);
    }
}

function doSelect()
{   interval = setInterval(lowOpacity,10);
    if(count % 2 == 0){
         intervalButtonLeft = setInterval(moveLeft,10,"predict");
         intervalButtonFirst = setInterval(moveLeftFirst,10,"predict-for-month");
         intervalButtonSecond = setInterval(moveLeftSecond,10,"predict-for-two-month");
         intervalButtonLeft2 = setInterval(moveLeft2,10,"Clasify-transactions");
       }
   else{
         intervalButtonLeft = setInterval(moveRight,10,"predict");
         intervalButtonFirst = setInterval(moveRightFirst,10,"predict-for-month");
         intervalButtonSecond = setInterval(moveRightSecond,10,"predict-for-two-month");
         intervalButtonLeft2 = setInterval(moveRight2,10,"Clasify-transactions");
         document.getElementById("predict-for-month").style.opacity = 0;
         document.getElementById("predict-for-two-month").style.opacity = 0;
   }
   count++;
}


var countLeft = 1720;
var intervalButtonLeft;
var button;
var opacityButton = 0;

function moveLeft(message){

   if(countLeft >= 1660){
     button = document.getElementById(message);
     button.style.left = countLeft+"px";
     button.style.opacity = opacityButton;
     opacityButton+=1;
     countLeft-=20;
   }
   else{
     clearInterval(intervalButtonLeft);
   }
}

var countLeft2 = 1720;
var intervalButtonLeft2;
var button2;
var opacityButton2 = 0;

function moveLeft2(message){

   if(countLeft2 >= 1660){
     button2 = document.getElementById(message);
     button2.style.left = countLeft2+"px";
     button2.style.opacity = opacityButton2;
     opacityButton2+=1;
     countLeft2-=20;
   }
   else{
     clearInterval(intervalButtonLeft2);
   }
}

function moveRight(message){

   if(countLeft <= 1720){
     button = document.getElementById(message);
     button.style.left = countLeft+"px";
     button.style.opacity = opacityButton;
     opacityButton=0;
     countLeft+=20;
   }
   else{
     clearInterval(intervalButtonLeft);
   }
}

function moveRight2(message){

   if(countLeft2 <= 1720){
     button2 = document.getElementById(message);
     button2.style.left = countLeft2+"px";
     button2.style.opacity = opacityButton2;
     opacityButton2=0;
     countLeft2+=20;
   }
   else{
     clearInterval(intervalButtonLeft2);
   }
}

var countLeftFirst = 1730;
var intervalButtonFirst;
var buttonFirst;
var opacityButtonFirst = 0;

function moveLeftFirst(message){

   if(countLeftFirst >= 1680){
     buttonFirst = document.getElementById(message);
     buttonFirst.style.left = countLeftFirst+"px";
     countLeftFirst-=10;
   }
   else{
     clearInterval(intervalButtonFirst);
   }
}

function moveRightFirst(message){

   if(countLeftFirst <= 1730){
     buttonFirst = document.getElementById(message);
     buttonFirst.style.left = countLeftFirst+"px";
     countLeftFirst+=20;
   }
   else{
     clearInterval(intervalButtonFirst);
   }
}

var countLeftSecond = 1730;
var intervalButtonSecond;
var buttonSecond;

function moveLeftSecond(message){

   if(countLeftSecond >= 1680){
     buttonSecond = document.getElementById(message);
     buttonSecond.style.left = countLeftSecond+"px";
     countLeftSecond-=10;
   }
   else{
     clearInterval(intervalButtonSecond);
   }
}

function moveRightSecond(message){
   if(countLeftSecond <= 1730){
     buttonSecond = document.getElementById(message);
     buttonSecond.style.left = countLeftSecond+"px";
     countLeftSecond+=20;
   }
   else{
     clearInterval(intervalButtonSecond);
   }
}

function selectPredictor(){
    document.getElementById("predict-for-month").style.opacity = 1;
    document.getElementById("predict-for-two-month").style.opacity = 1;
}

var marginCountBottomGraph = -550;

function moveMarginBottomGraph(message){
    if(marginCountBottomGraph < 250){
        button = document.getElementById(message);
        button.style.marginTop = marginCountBottomGraph+"px";
        marginCountBottomGraph+=5;
    }
}

var opacityGraph = 0;

function moveButtomGraph(){
   setInterval(moveMarginBottomGraph,10,"Graph");
   setTimeout(changeVisibility,100);
   setInterval(showGraph,100);
   getRequestToPredictedBalance();
}

function showGraph(){
    if(opacityGraph <= 1)
    {
      document.getElementById("Graph").style.opacity = opacityGraph+0.1;
      opacityGraph=opacityGraph+0.1;
    }
}

function changeVisibility(){
    document.getElementById("Graph").style.visibility = "visible";
}

var marginCountBottomDiagram = -500;
var buutonDiagram;
var opacityDiagram = 0;

function moveMarginBottomDiagram(message){
    if(marginCountBottomDiagram < 0){
        buutonDiagram = document.getElementById(message);
        buutonDiagram.style.marginTop = marginCountBottomDiagram+"px";
        marginCountBottomDiagram+=5;
    }
}

function moveButtomDiagram(){
   setInterval(moveMarginBottomDiagram,10,"Diagram");
   changeVisibilityDiagram();
   setTimeout(changeVisibilityDiagram,600);
   setInterval(showDiagram,50);
}

function showDiagram(){
    if(opacityDiagram <= 1)
    {
      document.getElementById("Diagram").style.opacity = opacityDiagram+0.1;
      opacityDiagram=opacityDiagram+0.1;
    }
}

function changeVisibilityDiagram(){
    document.getElementById("Diagram").style.visibility = "visible";
}
