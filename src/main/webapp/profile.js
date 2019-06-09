let role;

function onProfileLoad(user) {
    clearMessages();
    role = user.userRole;
    profileContentDivEl.style.display = 'block';
    showProfileDetails(user);
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

function showProfileDetails(user) {
    removeAllChildren(profileContentDivEl);
    const pNameEl = document.createElement('p');
    const nNameEl = document.createTextNode("Name: " + user.name);
    pNameEl.appendChild(nNameEl);

    const pEmailEl = document.createElement('p');
    const nEmailEl = document.createTextNode("Email: " + user.email);
    pEmailEl.appendChild(nEmailEl);

    const pRoleEl = document.createElement('p');
    const nRoleEl = document.createTextNode("Role: " + user.userRole);
    pRoleEl.appendChild(nRoleEl);

    const editProfileButtonEl = createEditProfileButton();
    editProfileButtonEl.dataset.userId = user.id;
    editProfileButtonEl.addEventListener('click', onProfileEditClicked);

    profileContentDivEl.appendChild(pNameEl);
    profileContentDivEl.appendChild(pEmailEl);
    profileContentDivEl.appendChild(pRoleEl);
    profileContentDivEl.appendChild(editProfileButtonEl);
}

function createEditProfileButton() {
    const buttonEl = document.createElement('button');
    buttonEl.setAttribute('id', 'edit-profile-button');
    buttonEl.setAttribute('type', 'button');
    buttonEl.classList.add('form-button');
    buttonEl.textContent = 'Edit profile';
    return buttonEl;
}

function onProfileEditClicked() {
    const userId = this.dataset.userId;
    const params = new URLSearchParams();
    params.append('user-id', userId);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onProfileEditLoad);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/profile?' + params.toString());
    xhr.send();
}

function onProfileEditLoad() {
    if (this.status === OK) {
        const user = JSON.parse(this.responseText);
        onEditFormLoad(user);
    } else {
        onOtherResponse(profileContentDivEl, this);
    }
}

function onEditFormLoad(user) {
    removeAllChildren(profileContentDivEl);
    const formEl = document.createElement('form');
    formEl.setAttribute('id','edit-profile-form');
    formEl.classList.add('menu-form');
    formEl.onSubmit = 'return false;';

    const inputidEl = document.createElement("input"); //input element, text
    inputidEl.setAttribute("type","text");
    inputidEl.classList.add("text-input-readonly");
    inputidEl.value = user.id;
    inputidEl.readOnly = true;
    inputidEl.setAttribute("name","user-id");

    const inputNaEl = document.createElement("input"); //input element, text
    inputNaEl.setAttribute("type","text");
    inputNaEl.classList.add("text-input");
    inputNaEl.value = user.name;
    inputNaEl.setAttribute("name", "user-name");

    const inputEmEl = document.createElement("input"); //input element, text
    inputEmEl.setAttribute("type","text");
    inputEmEl.classList.add("text-input");
    inputEmEl.value = user.email;
    inputEmEl.setAttribute("name","user-email");

    const inputPassEl = document.createElement("input"); //input element, text
    inputPassEl.setAttribute("type","password");
    inputPassEl.classList.add("text-input");
    inputPassEl.value = "*****";
    inputPassEl.setAttribute("name","user-password");

    const inputRoleEl = document.createElement("input"); //input element, text
    inputRoleEl.setAttribute("type","text");
    inputRoleEl.classList.add("text-input-readonly");
    inputRoleEl.value = user.userRole;
    inputRoleEl.readOnly = true;
    inputRoleEl.setAttribute("name","user-role");

    const brEl = document.createElement("br");
    const sEl = createProfileEditSubmitButton();
    sEl.addEventListener('click', onUpdateUserProfile);
    formEl.appendChild(inputidEl);
    formEl.appendChild(document.createTextNode("Name: "));
    formEl.appendChild(inputNaEl);
    formEl.appendChild(document.createTextNode("Email: "));
    formEl.appendChild(inputEmEl);
    formEl.appendChild(document.createTextNode("Password: "));
    formEl.appendChild(inputPassEl);
    formEl.appendChild(inputRoleEl);
    formEl.appendChild(brEl);
    formEl.appendChild(sEl);

    profileContentDivEl.appendChild(formEl);
}

function createProfileEditSubmitButton() {
    const buttonEl = document.createElement('button');
    buttonEl.setAttribute('id', 'new-edit-profile-button');
    buttonEl.setAttribute('type', 'button');
    buttonEl.classList.add('form-button');
    buttonEl.textContent = 'Edit profile';
    return buttonEl;
}

function onUpdateUserProfile() {
    const newProductFormEl = document.forms['edit-profile-form'];
    const idInput = newProductFormEl.querySelector('input[name="user-id"]');
    const nameInput = newProductFormEl.querySelector('input[name="user-name"]');
    const emailInput = newProductFormEl.querySelector('input[name="user-email"]');
    const passwordInput = newProductFormEl.querySelector('input[name="user-password"]');
    const roleInput = newProductFormEl.querySelector('input[name="user-role"]');
    removeAllChildren(addProductContentEl);
    const id =idInput.value;
    const name = nameInput.value;
    const email = emailInput.value;
    const password = passwordInput.value;
    const role = roleInput.value;

    const data = {};
    data.id = id;
    data.name = name;
    data.email = email;
    data.password = password;
    data.userRole = role;
    const json = JSON.stringify(data);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSubmittedNewProfileData);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('PUT', 'protected/profile');
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send(json);
}

function onSubmittedNewProfileData() {
    if (this.status === OK) {
        const user = JSON.parse(this.responseText);
        alert("Profile data successfully updated.");
        showProfileDetails(user);
    } else {
        onOtherResponse(profileContentDivEl, this);
    }
}
