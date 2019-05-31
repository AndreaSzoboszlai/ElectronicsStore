

function onProfileLoad(user) {
    clearMessages();
    console.log(user.userRole);
    if (user.userRole === "EMPLOYEE") {
      showContentById('link-add-product');
      showMenu();
    } else {
      hideContentById('link-add-product');
      showMenu();
    }
    showContents(['container', 'login-form', 'logout-content']);
    loginTitleEl.textContent = 'Logged in';


}
