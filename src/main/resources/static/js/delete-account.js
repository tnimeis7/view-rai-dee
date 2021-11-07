function confirmDelete() {
    username = document.profile.username.value;

    if (confirm("ต้องการลบบัญชี " + username + " ?") == true) {
    	return true;
    } else {
    	return false;
    }
}