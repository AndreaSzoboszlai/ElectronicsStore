let role;

function onProfileLoad(user) {
    clearMessages();
    role = user.userRole;
    profileContentDivEl.style.display = 'block';
    if (user.userRole === "EMPLOYEE") {
        showContentById('link-add-product-li');
        document.getElementById('link-add-product-li').style.display = 'inline-block';
        showContentById('link-coupons-li');
        document.getElementById('link-coupons-li').style.display = 'inline-block';
        hideContentById('link-cart-li');
        hideContentById('link-orders-li');
        showContentById('link-all-orders-li');
        document.getElementById('link-all-orders-li').style.display = 'inline-block';
        showMenu();
    } else {
        hideContentById('link-add-product-li');
        hideContentById('link-coupons-li')
        showContentById('link-cart-li');
        document.getElementById('link-cart-li').style.display = 'inline-block';
        showContentById('link-orders-li');
        document.getElementById('link-orders-li').style.display = 'inline-block';
        hideContentById('link-all-orders-li');
        showMenu();
    }
    showContents(['container', 'login-form', 'logout-content']);
    loginTitleEl.textContent = 'Logged in';
}

function onProfileClicked() {
    console.log('profile');
}
