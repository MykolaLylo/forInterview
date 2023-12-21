const error = document.getElementById('errorMassage');


function confirmation(event) {
    const formData = new FormData(event.target);
    const  userObject = {};

    formData.forEach((value, key)=>{
        userObject[key] = value;
    });
    console.log(userObject);

    if(userObject.password !== userObject.passwordConfirm){
        error.style.visibility = 'visible';
        return event.preventDefault();
    }
}

function showPassword(){
    const password = document.getElementById('password');
    if (password.type ==='password'){
        password.type="text";
    }else {
        password.type='password';
    }
}
function showConfirm(){
    const password = document.getElementById('passwordConfirm');
    if (password.type ==='password'){
        password.type="text";
    }else {
        password.type='password';
    }
}