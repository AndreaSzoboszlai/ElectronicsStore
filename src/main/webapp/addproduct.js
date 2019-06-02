function onAddProductClicked() {
    hideContentById('main-content');
    removeAllChildren(addProductContentEl);
    showContents(['add-products-content']);
    createNewProductForm();
}

function createNewProductForm() {
    const formEl = document.createElement('form');
    formEl.setAttribute('id','new-product-form');
    formEl.classList.add('menu-form');
    formEl.onSubmit = 'return false;';

    const inputNaEl = document.createElement("input"); //input element, text
    inputNaEl.setAttribute("type","text");
    inputNaEl.classList.add("text-input");
    inputNaEl.placeholder = "Name";
    inputNaEl.setAttribute("name","product-name");

    const inputPrEl = document.createElement("input"); //input element, text
    inputPrEl.setAttribute("type","text");
    inputPrEl.classList.add("text-input");
    inputPrEl.placeholder = "Price";
    inputPrEl.setAttribute("name","product-price");

    const inputDEl = document.createElement("input"); //input element, text
    inputDEl.setAttribute("type","text");
    inputDEl.classList.add("text-input");
    inputDEl.placeholder = "Description";
    inputDEl.setAttribute("name","product-description");

    const inputStEl = document.createElement("input"); //input element, text
    inputStEl.setAttribute("type","text");
    inputStEl.classList.add("text-input");
    inputStEl.placeholder = "Stock";
    inputStEl.setAttribute("name","product-stock");

    const brEl = document.createElement("br");
    const sEl = createNewPrSubmitButton();
    sEl.addEventListener('click', onSubmitNewProduct);

    formEl.appendChild(inputNaEl);
    formEl.appendChild(inputPrEl);
    formEl.appendChild(inputDEl);
    formEl.appendChild(inputStEl);
    formEl.appendChild(brEl);
    formEl.appendChild(sEl);

    addProductContentEl.appendChild(formEl);
}

function createNewPrSubmitButton() {
    const buttonEl = document.createElement('button');
    buttonEl.setAttribute('id', 'new-product-button');
    buttonEl.setAttribute('type', 'button');
    buttonEl.classList.add('form-button');
    buttonEl.textContent = 'Create new product';
    return buttonEl;
}

function onSubmitNewProduct() {
    const newProductFormEl = document.forms['new-product-form'];
    const nameInput = newProductFormEl.querySelector('input[name="product-name"]');
    const priceInput = newProductFormEl.querySelector('input[name="product-price"]');
    const descInput = newProductFormEl.querySelector('input[name="product-description"]');
    const stockInput = newProductFormEl.querySelector('input[name="product-stock"]');
    removeAllChildren(addProductContentEl);
    const name = nameInput.value;
    const price = priceInput.value;
    const desc = descInput.value;
    const stock = stockInput.value;

    const params = new URLSearchParams();

    params.append('product-name', name);
    params.append('product-price', price);
    params.append('product-description', desc);
    params.append('product-stock', stock);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSubmittedProduct);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/product');
    xhr.send(params);
}

function onSubmittedProduct() {
    if (this.status === OK) {
        const product = JSON.parse(this.responseText);
        alert(product.message);
        onAddProductClicked();
    } else {
        onOtherResponse(addProductContentEl, this);
    }
}
