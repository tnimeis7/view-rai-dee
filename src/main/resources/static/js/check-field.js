function checkFieldSignUp() {
    username = document.signUp.username.value;
    email = document.signUp.email.value;
    password = document.signUp.password.value;
    confirmPassword = document.signUp.confirmPassword.value;

    if(username == "" || email == "" || password == "" || confirmPassword == "") {
        alert("\nกรอกข้อมูลไม่ครบ กรุณาตรวจสอบใหม่อีกครั้ง")
        return false;
    }else if(password !== confirmPassword){
        alert("\nรหัสผ่านไม่ตรง กรุณาตรวจสอบใหม่อีกครั้ง")
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
    var current = document.getElementById('currentPasswordField').value;
    var password = document.getElementById('passwordField').value;
    confirm = document.changePassword.confirmPassword.value;

    console.log(current);
    console.log(password);
    console.log(confirm);

    if(current == "" || password == "" || confirm == "") {
        alert("\nกรุณากรอกข้อมูลให้ครบ");
        return false;
    }else if(password != confirm) {
        alert("\nรหัสผ่านไม่ตรงกัน โปรดตรวจสอบอีกครั้ง");
        return false;
    }else {
        return true;
    }
}

function checkFeedback() {
    feedback = document.getElementById("feedback").value;

    console.log("asdasc0"+feedback);
    if(feedback == "") {
        alert("\nกรุณากรอกข้อความก่อนส่ง");
        return false;
    }else {
        alert("\nส่ง feedback ให้ผู้พัฒนาแล้ว");
        return true;
    }
}

function checkComment(){
    comment = document.getElementById("ContentField").value;

    if(comment == "") {
        alert("กรุณากรอกเนื้อหาที่จะแสดงความเห็น");
        return false;
    }else {
        alert("คุณได้แสดงความเห็นแล้ว");
        return true;
    }
}