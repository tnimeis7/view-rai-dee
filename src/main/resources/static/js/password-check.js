function checkField() {
    password = document.signUp.password.value;
    confirmPassword = document.signUp.confirmPassword.value;

    if (password != confirmPassword) {
        alert ("\nPassword Didn't Match: Please try again")
        return false;
    }
    else{
        return true;
    }
}