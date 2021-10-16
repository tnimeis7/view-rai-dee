$(function() {
    $("#editPhoto").change(function(event) {
      var x = URL.createObjectURL(event.target.files[0]);
       $("#photo").attr("sre",x);
       console.log(event);
       });
    })