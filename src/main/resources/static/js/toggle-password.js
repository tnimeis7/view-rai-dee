function showPwd1() {
  var x = document.getElementById("passwordInput");
  var y = document.getElementById("hide1");
  var z = document.getElementById("hide2");
  if (x.type === "password") {
    x.type = "text";
    y.style.display = "block";
    z.style.display = "none";
  } else {
    x.type = "password";
    y.style.display = "none";
    z.style.display = "block";
  }
}

function showPwd2() {
  var x = document.getElementById("confirmPasswordInput");
  var y = document.getElementById("hide1");
  var z = document.getElementById("hide2");
  if (x.type === "password") {
    x.type = "text";
    y.style.display = "block";
    z.style.display = "none";
  } else {
    x.type = "password";
    y.style.display = "none";
    z.style.display = "block";
  }
}