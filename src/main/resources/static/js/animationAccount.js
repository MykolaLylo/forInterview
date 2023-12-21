var indexValue = 0;
const img = document.querySelectorAll('.img-discount');
function slideshow(){
 setTimeout(slideshow,2500);
 for (var x=0; x<img.length; x++){
     img[x].style.display="none";
 }
 indexValue++;
 if (indexValue>img.length){
     indexValue = 1;
 }
 img[indexValue -1].style.display = "block"
}
slideshow();