function checkFieldSignUp() {
    username = document.signUp.username.value;
    email = document.signUp.email.value;
    password = document.signUp.password.value;
    confirmPassword = document.signUp.confirmPassword.value;

    if(username == "" || email == "" || password == "" || confirmPassword == "") {
        alert("\nกรอกข้อมูลไม่ครบ กรุณาตรวจสอบใหม่อีกครั้ง")
        return false;
    }else if(password !== confirmPassword) {
        alert ("\nรหัสผ่านไม่ตรง กรุณาตรวจสอบใหม่อีกครั้ง")
        return false;
    }else{
        return true;
    }

}

function checkFieldLogin() {
    username = document.login.username.value;
    password = document.login.password.value;

    if(username == "" || password == "") {
        alert("\nกรอกข้อมูลไม่ครบ กรุณาตรวจสอบใหม่อีกครั้ง")
        return false;
    }else{
        return true;
    }
}

function checkPassword() {
    current = document.getElementById("currentPasswordField");
    password = document.getElementById("passwordField");
    confirm = document.getElementById("confirmPasswordField");

    if(current == "" || password == "" || confirm == "") {
        alert("กรุณากรอกข้อมูลให้ครบ");
        return false;
    }else if(password != confirm) {
        alert("รหัสผ่านไม่ตรงกัน โปรดตรวจสอบอีกครั้ง");
        return false;
    }else {
        return true;
    }

}