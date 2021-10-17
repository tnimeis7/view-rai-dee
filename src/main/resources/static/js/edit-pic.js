const imgDiv = document.getElementsByClassName("profile-pic-div")
const img = document.getElementById("photo");
const file = document.getElementById("editPhoto");
const uploadBtn = document.getElementById("uploadBtn");

console.log(imgDiv);
console.log(uploadBtn)

imgDiv.addEventListener('mouseover', function(){
    uploadBtn.style.display = "block"
});

imgDiv.addEventListener('mouseout', function() {
    uploadBtn.style.display = "none"
});

file.addEventListener('change', function(){
    const chooseFile = this.files[0];
    if(chooseFile) {
        const reader = new FileReader();
        reader.addEventListener('load', function() {
            img.setAttribute('src', reader.result);
        });
        reader.readAsDataURL(chooseFile);

    }
});