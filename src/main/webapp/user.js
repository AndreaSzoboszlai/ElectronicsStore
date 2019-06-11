function onAddUser() {
    hideContentById('main-content');
    showContents(['add-user-content']);
    removeAllChildren(addUserContentDivEl);
    createNewUserForm();
}

function createNewUserForm() {
    const formEl = document.createElement('form');
    formEl.setAttribute('id','new-user-form');
    formEl.classList.add('menu-form');
    formEl.onSubmit = 'return false;';

    const inputNaEl = document.createElement("input"); //input element, text
    inputNaEl.setAttribute("type","text");
    inputNaEl.classList.add("text-input");
    inputNaEl.placeholder = "Name";
    inputNaEl.setAttribute("name","user-name");

    const inputEmEl = document.createElement("input"); //input element, text
    inputEmEl.setAttribute("type","text");
    inputEmEl.classList.add("text-input");
    inputEmEl.placeholder = "Email";
    inputEmEl.setAttribute("name","user-email");

    const inputPassEl = document.createElement("input"); //input element, text
    inputPassEl.setAttribute("type","text");
    inputPassEl.classList.add("text-input");
    inputPassEl.placeholder = "Password";
    inputPassEl.setAttribute("name","user-password");

    const inputRoleSelectEl = document.createElement("select"); //input element, text
    inputRoleSelectEl.setAttribute('id', 'role-drop-down');
    const roles = ["admin","employee","customer"];
    for (let i = 0; i < roles.length; i++) {
        const dropRoleEl = document.createElement("option");
        dropRoleEl.value = roles[i];
        dropRoleEl.text = roles[i];
        inputRoleSelectEl.appendChild(dropRoleEl);
    }
    const brEl = document.createElement("br");
    const sEl = createNewUserSubmitButton();
    sEl.addEventListener('click', onSubmitNewUser);

    formEl.appendChild(inputNaEl);
    formEl.appendChild(inputEmEl);
    formEl.appendChild(inputPassEl);
    formEl.appendChild(inputRoleSelectEl);
    formEl.appendChild(brEl);
    formEl.appendChild(sEl);

    addUserContentDivEl.appendChild(formEl);
}

function createNewUserSubmitButton() {
    const buttonEl = document.createElement('button');
    buttonEl.setAttribute('id', 'add-user-button');
    buttonEl.setAttribute('type', 'button');
    buttonEl.classList.add('form-button');
    buttonEl.textContent = 'Add user';
    return buttonEl;
}

function onSubmitNewUser() {
    const newProductFormEl = document.forms['new-user-form'];
    const nameInput = newProductFormEl.querySelector('input[name="user-name"]');
    const emailInput = newProductFormEl.querySelector('input[name="user-email"]');
    const passwordInput = newProductFormEl.querySelector('input[name="user-password"]');
    const roleInput = document.getElementById('role-drop-down');
    removeAllChildren(addProductContentEl);
    const name = nameInput.value;
    const email = emailInput.value;
    const password = passwordInput.value;
    const role = roleInput.options[roleInput.selectedIndex].value;
    const params = new URLSearchParams();
    params.append('name', name);
    params.append('email', email);
    params.append('password', password);
    params.append('role', role);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSubmittedNewUserData);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/user');
    xhr.send(params);
}

function onSubmittedNewUserData() {
    if (this.status === OK) {
        const response = JSON.parse(this.responseText);
        alert(response.message);
        onAddUser();
    } else {
        onOtherResponse(addUserContentDivEl, this);
    }
}
