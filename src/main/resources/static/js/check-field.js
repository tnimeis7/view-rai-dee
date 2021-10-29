function checkFieldSignUp() {
    username = document.signUp.username.value;
    email = document.signUp.email.value;
    password = document.signUp.password.value;
    confirmPassword = document.signUp.confirmPassword.value;

    if(username == "" || email == "" || password == "" || confirmPassword == "") {
        Swal("\nกรอกข้อมูลไม่ครบ กรุณาตรวจสอบใหม่อีกครั้ง")
        return false;
    }else if(password !== confirmPassword){
        Swal("\nรหัสผ่านไม่ตรง กรุณาตรวจสอบใหม่อีกครั้ง")
        return false;
    }else{
        return true;
    }
}

function checkFieldLogin() {
    username = document.login.username.value;
    password = document.login.password.value;

    if(username == "" || password == ""){
        Swal("\nกรอกข้อมูลไม่ครบ กรุณาตรวจสอบใหม่อีกครั้ง")
        return false;
    }else{
        return true;
    }
}