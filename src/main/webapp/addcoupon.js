function onAddCouponClicked() {
    hideContentById('main-content');
    removeAllChildren(couponContentDivEl);
    showContents(['all-coupon-content']);
    createNewCouponForm();
}

function createNewCouponForm() {
    const formEl = document.createElement('form');
    formEl.setAttribute('id','new-coupon-form');
    formEl.classList.add('menu-form');
    formEl.onSubmit = 'return false;';

    const inputNaEl = document.createElement("input"); //input element, text
    inputNaEl.setAttribute("type","text");
    inputNaEl.classList.add("text-input");
    inputNaEl.placeholder = "Name";
    inputNaEl.setAttribute("name","coupon-name");

    const inputPrEl = document.createElement("input"); //input element, text
    inputPrEl.setAttribute("type","text");
    inputPrEl.classList.add("text-input");
    inputPrEl.placeholder = "Percentage(numbers 1-99)";
    inputPrEl.setAttribute("name","coupon-percentage");

    const brEl = document.createElement("br");

    const sEl = createNewSubmitButton();
    sEl.addEventListener('click', onSubmitNewProduct);

    formEl.appendChild(inputNaEl);
    formEl.appendChild(inputPrEl);
    formEl.appendChild(brEl);
    formEl.appendChild(sEl);

    couponContentDivEl.appendChild(formEl);
}

function createNewSubmitButton() {
    const buttonEl = document.createElement('button');
    buttonEl.setAttribute('id', 'new-coupon-button');
    buttonEl.setAttribute('type', 'button');
    buttonEl.classList.add('form-button');
    buttonEl.textContent = 'Create new coupon';
    return buttonEl;
}

function onSubmitNewProduct() {
    const newProductFormEl = document.forms['new-coupon-form'];
    const nameInput = newProductFormEl.querySelector('input[name="coupon-name"]');
    const percentageInput = newProductFormEl.querySelector('input[name="coupon-percentage"]');

    removeAllChildren(addProductContentEl);
    const name = nameInput.value;
    const percentage = percentageInput.value;
    const params = new URLSearchParams();

    params.append('coupon-name', name);
    params.append('coupon-percentage', percentage);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSubmittedCoupon);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/coupons');
    xhr.send(params);
}

function onSubmittedCoupon() {
    if (this.status === OK) {
        const product = JSON.parse(this.responseText);
        alert(product.message);
        onCouponsClicked();
    } else {
        onOtherResponse(addProductContentEl, this);
    }
}
