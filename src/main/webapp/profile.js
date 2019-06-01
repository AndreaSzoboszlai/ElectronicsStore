let role;

function onProfileLoad(user) {
    clearMessages();
    role = user.userRole;
    if (user.userRole === "EMPLOYEE") {
        showContentById('link-add-product');
        showContentById('link-coupons');
        showMenu();
    } else {
        hideContentById('link-add-product');
        hideContentById('link-coupons')
        showMenu();
    }
    showContents(['container', 'login-form', 'logout-content']);
    loginTitleEl.textContent = 'Logged in';
}

function onProfileClicked() {
    console.log('profile');
}
