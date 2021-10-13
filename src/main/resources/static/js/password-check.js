function checkField() {
    password = document.signUp.password.value;
    confirmPassword = document.signUp.confirmPassword.value;

    if (password == null || confirmPassword == null) {
        alert ("\nกรอกข้อมูลไม่ครบ กรุณาตรวจสอบใหม่อีกครั้ง")
        return false;
    }else if(password != confirmPassword){
        alert("\nรหัสผ่านไม่ตรง กรุณาตรวจสอบใหม่อีกครั้ง")
        return false;
    }
    else{
        return true;
    }
}