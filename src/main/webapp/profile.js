let role;

function onProfileLoad(user) {
    clearMessages();
    role = user.userRole;
    if (user.userRole === "EMPLOYEE") {
        showContentById('link-add-product');
        showContentById('link-coupons');
        hideContentById('link-cart');
        hideContentById('link-orders');
        showContentById('link-all-orders');
        showMenu();
    } else {
        hideContentById('link-add-product');
        hideContentById('link-coupons')
        showContentById('link-cart');
        showContentById('link-orders');
        hideContentById('link-all-orders');
        showMenu();
    }
    showContents(['container', 'login-form', 'logout-content']);
    loginTitleEl.textContent = 'Logged in';
}

function onProfileClicked() {
    console.log('profile');
}
