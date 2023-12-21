function showPassword(){
    const password = document.getElementsByName('password');
    if (password.type ==='password'){
        password.type="text";
    }else {
        password.type='password';
    }
}