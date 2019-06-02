function onCartClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onCartLoad);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/cart');
    xhr.send();
}

function onCartLoad() {
    if (this.status === OK) {
        hideContentById('main-content');
        showContents(['my-cart-content']);
        removeAllChildren(cartContentDivEl);
        const cartDto = JSON.parse(this.responseText);
        createCartDisplay(cartDto);
    } else {
        onOtherResponse(cartContentDivEl, this);
    }
}

function createCartDisplay(cartDto) {
    const tableEl = document.createElement('table');
    tableEl.setAttribute('id', 'my-cart-table');
    const theadEl = createCartTableHead();
    const tbodyEl = createCartTableBody(cartDto);
    tableEl.appendChild(theadEl);
    tableEl.appendChild(tbodyEl);
    cartContentDivEl.appendChild(tableEl);
    createCouponForm();
    const buttonTdEl = createNewOrderButton();
    //buttonTdEl.addEventListener('click', onSubmitOrder);
    cartContentDivEl.appendChild(buttonTdEl);
}
function createNewOrderButton() {
    const buttonEl = document.createElement('button');
    buttonEl.classList.add('form-button');
    buttonEl.textContent = 'Order';
    return buttonEl;
}

function createCartTableHead() {
    const nameTdEl = document.createElement('th');
    nameTdEl.textContent = 'Name';

    const priceTdEl = document.createElement('th');
    priceTdEl.textContent = 'Price';

    const quantityTdEl = document.createElement('th');
    quantityTdEl.textContent = 'Quantity';

    const tPriceTdEl = document.createElement('th');
    tPriceTdEl.textContent = 'Total Price';

    const buttonCartTdEl = document.createElement('th');
    buttonCartTdEl.textContent = 'Delete From Cart';

    const trTdEl = document.createElement('tr');
    trTdEl.appendChild(nameTdEl);
    trTdEl.appendChild(priceTdEl);
    trTdEl.appendChild(quantityTdEl);
    trTdEl.appendChild(tPriceTdEl);
    trTdEl.appendChild(buttonCartTdEl);

    const theadEl = document.createElement('thead');
    theadEl.appendChild(trTdEl);
    return theadEl;
}

function createCartTableBody(cartDto) {
    const tbodyEl = document.createElement('tbody');
    for (let i = 0; i < cartDto.length; i++) {
        product = cartDto[i];
        const nameTdEl = document.createElement('td');
        nameTdEl.textContent = product.name;

        const priceTdEl = document.createElement('td');
        priceTdEl.textContent = '$ ' + product.price;

        const quantityTdEl = document.createElement('td');
        quantityTdEl.textContent = product.quantity;

        const tPriceTdEl = document.createElement('td');
        tPriceTdEl.textContent = '$ ' + product.totalPrice;

        const buttonCartTdEl = document.createElement('i');
        buttonCartTdEl.classList.add('icon-trash');
        buttonCartTdEl.dataset.productCart = product.id;
        //buttonCartTdEl.addEventListener('click', onDeleteFromCart);
        const buttonTdEl = document.createElement('td');
        buttonTdEl.appendChild(buttonCartTdEl);
        buttonTdEl.setAttribute('id', 'cart-delete-button-' + product.id);

        const trEl = document.createElement('tr');
        trEl.setAttribute('id', 'row-cart-id-' + product.id);

        trEl.appendChild(nameTdEl);
        trEl.appendChild(priceTdEl);
        trEl.appendChild(quantityTdEl);
        trEl.appendChild(tPriceTdEl);
        trEl.appendChild(buttonTdEl);
        tbodyEl.appendChild(trEl);
    }

    return tbodyEl;
}

function createCouponForm() {
    const formEl = document.createElement('form');
    formEl.setAttribute('id','add-coupon-form');
    formEl.classList.add('menu-form');
    formEl.onSubmit = 'return false;';

    const inputNaEl = document.createElement("input"); //input element, text
    inputNaEl.setAttribute("type","text");
    inputNaEl.classList.add("text-input");
    inputNaEl.placeholder = "Coupon code";
    inputNaEl.setAttribute("name","coupon-name");

    const brEl = document.createElement("br");

    const sEl = createNewCartSubmitButton();
    //sEl.addEventListener('click', onCouponUse);
    formEl.appendChild(inputNaEl);
    formEl.appendChild(brEl);
    formEl.appendChild(sEl);

    cartContentDivEl.appendChild(formEl);
}

function createNewCartSubmitButton() {
    const buttonEl = document.createElement('button');
    buttonEl.setAttribute('id', 'add-cart-button');
    buttonEl.setAttribute('type', 'button');
    buttonEl.classList.add('form-button');
    buttonEl.textContent = 'Add coupon to cart';
    return buttonEl;
}
