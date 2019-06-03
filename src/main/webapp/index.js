const OK = 200;
const BAD_REQUEST = 400;
const UNAUTHORIZED = 401;
const NOT_FOUND = 404;
const INTERNAL_SERVER_ERROR = 500;

let loginContentDivEl;
let couponContentDivEl;
let menuListEl;
let mainContentEl;
let loginTitleEl;
let allProductsEl;
let addProductContentEl;
let allOrdersContentDivEl;
let myOrdersContentDivEl;

function newInfo(targetEl, message) {
    newMessage(targetEl, 'info', message);
}

function newError(targetEl, message) {
    newMessage(targetEl, 'error', message);
}

function newMessage(targetEl, cssClass, message) {
    clearMessages();

    const pEl = document.createElement('p');
    pEl.classList.add('message');
    pEl.classList.add(cssClass);
    pEl.textContent = message;

    targetEl.appendChild(pEl);
}

function clearMessages() {
    const messageEls = document.getElementsByClassName('message');
    for (let i = 0; i < messageEls.length; i++) {
        const messageEl = messageEls[i];
        messageEl.remove();
    }
}

function showContents(ids) {
    const contentEls = document.getElementsByClassName('content-box');
    for (let i = 0; i < contentEls.length; i++) {
        const contentEl = contentEls[i];
        if (ids.includes(contentEl.id)) {
            contentEl.style.display = 'block';
        } else {
            contentEl.style.display = 'none';
        }
    }
}

function removeAllChildren(el) {
    while (el.lastChild) {
      if (el.lastChild.className !== 'content-header') {
        el.lastChild.remove();
      } else if (el.lastChild.className === 'content-header') {
        break;
      }
    }
}

function onNetworkError(response) {
    document.body.remove();
    const bodyEl = document.createElement('body');
    document.appendChild(bodyEl);
    newError(bodyEl, 'Network error, please try reloaing the page');
}

function onOtherResponse(targetEl, xhr) {
    if (xhr.status === NOT_FOUND) {
        newError(targetEl, 'Not found');
        console.error(xhr);
    } else {
        const json = JSON.parse(xhr.responseText);
        if (xhr.status === INTERNAL_SERVER_ERROR) {
            newError(targetEl, `Server error: ${json.message}`);
        } else if (xhr.status === UNAUTHORIZED || xhr.status === BAD_REQUEST) {
            newError(targetEl, json.message);
        } else {
            newError(targetEl, `Unknown error: ${json.message}`);
        }
    }
}

function showMenu() {
    menuListEl.style.display = 'block';
}

function hideMenu() {
    menuListEl.style.display = 'none';
}

function showContentById(id) {
    const contentEl = document.getElementById(id);
    contentEl.style.display = 'block';
}

function hideContentById(id) {
    const contentEl = document.getElementById(id);
    contentEl.style.display = 'none';
}

function hasAuthorization() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/coupon');
    xhr.send();
    if (xhr.status === OK) {
        return true;
    }
}

function setAuthorization(user) {
    return localStorage.setItem('user', JSON.stringify(user));
}

function getAuthorization() {
    return JSON.parse(localStorage.getItem('user'));
}

function setUnauthorized() {
    return localStorage.removeItem('user');
}

function onLoad() {
    navDivEl = document.getElementById('menu');
    menuListEl = document.getElementById('menu-list');
    mainContentEl = document.getElementById('main-content');
    loginContentDivEl = document.getElementById('login-content');
    addProductContentEl = document.getElementById('add-products-content');

    loginTitleEl = document.getElementById('login-title');

    loginRedirectEl = document.getElementById('login-redirect');
    loginRedirectEl.addEventListener('click', onLoginRedirectClicked);

    registerRedirectEl = document.getElementById('register-redirect');
    registerRedirectEl.addEventListener('click', onRegisterRedirectClicked);

    allProductsEl = document.getElementById('all-products-content');
    couponContentDivEl = document.getElementById('all-coupon-content');
    allOrdersContentDivEl = document.getElementById('all-orders-content');
    myOrdersContentDivEl = document.getElementById('my-orders-content');
    cartContentDivEl = document.getElementById('my-cart-content');

    const loginButtonEl = document.getElementById('login-button');
    loginButtonEl.addEventListener('click', onLoginButtonClicked);

    const registerButtonEl = document.getElementById('register-button');
    registerButtonEl.addEventListener('click', onRegisterButtonClicked);

    if (hasAuthorization()) {
        onProfileLoad(getCurrentUser());
    } else {
        hideMenu();
        showContents(['container', 'login-content']);
    }
}

document.addEventListener('DOMContentLoaded', onLoad);
