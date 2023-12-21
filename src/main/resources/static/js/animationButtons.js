let offSet= 0;
const imgContainer= document.querySelector('.img-container');

document.querySelector('.left-direction').addEventListener('click',function (){
    offSet+= 343;
    if(offSet>1029){
        offSet=0;
    }
    imgContainer.style.left = -offSet+'px';
})
document.querySelector('.right-direction').addEventListener('click',function (){
    offSet-= 343;
    if(offSet<0){
        offSet=1029;
    }
    imgContainer.style.left = -offSet+'px';
})