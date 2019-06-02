
function onAllProductsClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onAllProductsLoad);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/products');
    xhr.send();
}

function onAllProductsLoad() {

    showContents(['all-products-content']);
    if (this.status === OK) {
        const products = JSON.parse(this.responseText);
        if (role === 'CUSTOMER') {
            createProductCustomerTable(products);
        } else {
            creteaProductEmployeeTable(products);
        }
    } else {
        onOtherResponse(allProductsEl, this);
    }
}

function createProductCustomerTable(products) {
    mainContentEl.style.display = 'none';
    removeAllChildren(allProductsEl);
    removeAllChildren(addProductContentEl);
    const tableEl = document.createElement('table');
    tableEl.setAttribute('id', 'all-product-customer-table');
    const theadEl = createProductCustomerTableHeader();
    const tbodyEl = createProductCustomerTableBody(products);
    tableEl.appendChild(theadEl);
    tableEl.appendChild(tbodyEl);
    allProductsEl.appendChild(tableEl);
}

function createProductCustomerTableHeader() {
    const nameTdEl = document.createElement('th');
    nameTdEl.textContent = 'Name';

    const priceTdEl = document.createElement('th');
    priceTdEl.textContent = 'Price'

    const stockTdEl = document.createElement('th');
    stockTdEl.textContent = 'In stock';

    const buttonCartTdEl = document.createElement('th');
    buttonCartTdEl.textContent = 'Put in a cart';

    const trTdEl = document.createElement('tr');
    trTdEl.appendChild(nameTdEl);
    trTdEl.appendChild(priceTdEl);
    trTdEl.appendChild(stockTdEl);
    trTdEl.appendChild(buttonCartTdEl);

    const theadEl = document.createElement('thead');
    theadEl.appendChild(trTdEl);
    return theadEl;
}

function createProductCustomerTableBody(products) {
    const tbodyEl = document.createElement('tbody');

    for (let i = 0; i < products.length; i++) {
        const product = products[i];
        const nameTdEl = document.createElement('td');
        const nameAEl = document.createElement('a');
        nameAEl.href = 'javascript:void(0)';
        nameAEl.dataset.productId = product.id;
        nameAEl.onclick = onProductNameClicked;
        nameAEl.textContent = product.name;
        nameAEl.setAttribute('id', 'product-id' + product.id);
        nameTdEl.appendChild(nameAEl);

        const priceTdEl = document.createElement('td');
        priceTdEl.textContent = '$ ' + product.price;

        const stockTdEl = document.createElement('td');
        stockTdEl.textContent = product.productInStock;

        const buttonCartTdEl = document.createElement('i');
        buttonCartTdEl.classList.add('icon-shopping-cart')
        buttonCartTdEl.dataset.productCart = product.id;
        buttonCartTdEl.addEventListener('click', onProductCartClicked);
        const buttonTdEl = document.createElement('td');
        buttonTdEl.appendChild(buttonCartTdEl);
        buttonTdEl.setAttribute('id', 'product-update-button-' + product.id);

        const trEl = document.createElement('tr');
        trEl.setAttribute('id', 'row-product-id-' + product.id);

        trEl.appendChild(nameTdEl);
        trEl.appendChild(priceTdEl);
        trEl.appendChild(stockTdEl);
        trEl.appendChild(buttonTdEl);
        tbodyEl.appendChild(trEl);
    }
    return tbodyEl;
}

function onProductNameClicked() {
    const productId = this.dataset.productId;
    const params = new URLSearchParams();
    params.append('productId', productId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onProductNameResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/product?' + params.toString());
    xhr.send();
}
function onProductNameResponse() {
    if (this.status === OK) {
        const product = JSON.parse(this.responseText);
        onProductDescriptionResponse(product);
    } else {
        onOtherResponse(allProductsEl, this);
    }
}

function onProductDescriptionResponse(product) {
    removeAllChildren(allProductsEl);

    const h2El = document.createElement('h2');
    h2El.classList.add('small-title');
    h2El.textContent = product.name;
    hideContentById('all-products-content-title');
    const pDEl = document.createElement('p');
    pDEl.textContent = "Product description: " + product.description;
    const buttonBackEl = createBackButton();
    buttonBackEl.addEventListener('click', onAllProductsClicked);
    allProductsEl.appendChild(h2El);
    allProductsEl.appendChild(pDEl);
    allProductsEl.appendChild(buttonBackEl);
}

function createBackButton() {
    const buttonEl = document.createElement('button');
    buttonEl.classList.add('form-button');
    buttonEl.textContent = 'Back';
    return buttonEl;
}

function creteaProductEmployeeTable(products) {
    mainContentEl.style.display = 'none';
    removeAllChildren(allProductsEl);
    removeAllChildren(addProductContentEl);
    const tableEl = document.createElement('table');
    tableEl.setAttribute('id', 'all-product-employee-table');
    const theadEl = createProductEmployeeTableHeader();
    const tbodyEl = createProductEmployeeTableBody(products);
    tableEl.appendChild(theadEl);
    tableEl.appendChild(tbodyEl);
    allProductsEl.appendChild(tableEl);
}

function createProductEmployeeTableHeader() {
    const nameTdEl = document.createElement('th');
    nameTdEl.textContent = 'Name';

    const priceTdEl = document.createElement('th');
    priceTdEl.textContent = 'Price'

    const stockTdEl = document.createElement('th');
    stockTdEl.textContent = 'In stock';

    const buttonUpTdEl = document.createElement('th');
    buttonUpTdEl.textContent = 'Edit';

    const buttonDelTdEl = document.createElement('th');
    buttonDelTdEl.textContent = 'Delete';

    const trTdEl = document.createElement('tr');
    trTdEl.appendChild(nameTdEl);
    trTdEl.appendChild(priceTdEl);
    trTdEl.appendChild(stockTdEl);
    trTdEl.appendChild(buttonUpTdEl);
    trTdEl.appendChild(buttonDelTdEl);
    const theadEl = document.createElement('thead');
    theadEl.appendChild(trTdEl);
    return theadEl;
}

function createProductEmployeeTableBody(products) {
    const tbodyEl = document.createElement('tbody');

    for (let i = 0; i < products.length; i++) {
        const product = products[i];
        const nameTdEl = document.createElement('td');
        const nameAEl = document.createElement('a');
        nameAEl.href = 'javascript:void(0)';
        nameAEl.dataset.productId = product.id;
        nameAEl.onclick = onProductNameClicked;
        nameAEl.textContent = product.name;
        nameAEl.setAttribute('id', 'product-id' + product.id);
        nameTdEl.appendChild(nameAEl);

        const priceTdEl = document.createElement('td');
        priceTdEl.textContent = '$ ' + product.price;

        const stockTdEl = document.createElement('td');
        stockTdEl.textContent = product.productInStock;

        const buttonUpTdEl = document.createElement('i');
        buttonUpTdEl.classList.add('icon-edit')
        buttonUpTdEl.dataset.productEdit = product.id;
        buttonUpTdEl.addEventListener('click', onProductEditClicked);
        const buttonUpdateTdEl = document.createElement('td');
        buttonUpdateTdEl.appendChild(buttonUpTdEl);
        buttonUpdateTdEl.setAttribute('id', 'product-update-button-' + product.id);

        const buttonDelTdEl = document.createElement('i');
        buttonDelTdEl.classList.add('icon-trash')
        buttonDelTdEl.dataset.productDel = product.id;
        buttonDelTdEl.addEventListener('click', onProductDelClicked);
        const buttonDeleteTdEl = document.createElement('td');
        buttonDeleteTdEl.appendChild(buttonDelTdEl);
        buttonDeleteTdEl.setAttribute('id', 'product-delete-button-' + product.id);

        const trEl = document.createElement('tr');
        trEl.setAttribute('id', 'row-product-id-' + product.id);

        trEl.appendChild(nameTdEl);
        trEl.appendChild(priceTdEl);
        trEl.appendChild(stockTdEl);
        trEl.appendChild(buttonUpdateTdEl);
        trEl.appendChild(buttonDeleteTdEl);
        tbodyEl.appendChild(trEl);
    }
    return tbodyEl;
}
function onProductCartClicked() {
    const productId = this.dataset.productCart;
    const params = new URLSearchParams();
    params.append('product-id', productId);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onCartPutClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/cart');
    xhr.send(params);
}

function onCartPutClicked() {
    if (this.status === OK) {
        const message = JSON.parse(this.responseText);
        alert(message.message);
        onAllProductsClicked();
    } else {
        onOtherResponse(allProductsEl, this);
    }
}

function onProductEditClicked() {
    const productEdit = this.dataset.productEdit;
    const params = new URLSearchParams();
    params.append('productId', productEdit);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onProductEditResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/product?' + params.toString());
    xhr.send();
}

function onProductEditResponse() {
    if (this.status === OK) {
        const product = JSON.parse(this.responseText);
        createProductEditForm(product);
    } else {
        onOtherResponse(allProductsEl, this);
    }
}

function createProductEditForm(product) {
    removeAllChildren(allProductsEl);
    const formEl = document.createElement('form');
    formEl.setAttribute('id','new-product-form');
    formEl.classList.add('menu-form');
    formEl.onSubmit = 'return false;';

    const inputidEl = document.createElement("input"); //input element, text
    inputidEl.setAttribute("type","text");
    inputidEl.classList.add("text-input");
    inputidEl.value = product.id;
    inputidEl.readOnly = true;
    inputidEl.setAttribute("name","product-id");

    const inputNaEl = document.createElement("input"); //input element, text
    inputNaEl.setAttribute("type","text");
    inputNaEl.classList.add("text-input");
    inputNaEl.value = product.name;
    inputNaEl.setAttribute("name","product-name");

    const inputPrEl = document.createElement("input"); //input element, text
    inputPrEl.setAttribute("type","text");
    inputPrEl.classList.add("text-input");
    inputPrEl.value = product.price;
    inputPrEl.setAttribute("name","product-price");

    const inputDEl = document.createElement("input"); //input element, text
    inputDEl.setAttribute("type","text");
    inputDEl.classList.add("text-input");
    inputDEl.value = product.description;
    inputDEl.setAttribute("name","product-description");

    const inputStEl = document.createElement("input"); //input element, text
    inputStEl.setAttribute("type","text");
    inputStEl.classList.add("text-input");
    inputStEl.value = product.productInStock;
    inputStEl.setAttribute("name","product-stock");

    const brEl = document.createElement("br");
    const sEl = createEditSubmitButton();
    sEl.addEventListener('click', onUpdateProductSubmit);
    formEl.appendChild(inputidEl);
    formEl.appendChild(inputNaEl);
    formEl.appendChild(inputPrEl);
    formEl.appendChild(inputDEl);
    formEl.appendChild(inputStEl);
    formEl.appendChild(brEl);
    formEl.appendChild(sEl);

    allProductsEl.appendChild(formEl);
}

function createEditSubmitButton() {
    const buttonEl = document.createElement('button');
    buttonEl.setAttribute('id', 'new-edit-product-button');
    buttonEl.setAttribute('type', 'button');
    buttonEl.classList.add('form-button');
    buttonEl.textContent = 'Edit product';
    return buttonEl;
}

function onUpdateProductSubmit() {
    const newProductFormEl = document.forms['new-product-form'];
    const idInput = newProductFormEl.querySelector('input[name="product-id"]');
    const nameInput = newProductFormEl.querySelector('input[name="product-name"]');
    const priceInput = newProductFormEl.querySelector('input[name="product-price"]');
    const descInput = newProductFormEl.querySelector('input[name="product-description"]');
    const stockInput = newProductFormEl.querySelector('input[name="product-stock"]');
    removeAllChildren(addProductContentEl);
    const id =idInput.value;
    const name = nameInput.value;
    const price = priceInput.value;
    const desc = descInput.value;
    const stock = stockInput.value;

    const data = {};
    data.id = id;
    data.name = name;
    data.price = price;
    data.description = desc;
    data.productInStock = stock;
    const json = JSON.stringify(data);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSubmittedUpdatedProduct);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('PUT', 'protected/product');
    xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
    xhr.send(json);
}

function onSubmittedUpdatedProduct() {
    if (this.status === OK) {
        const product = JSON.parse(this.responseText);
        alert(product.message);
        onAllProductsClicked();
    } else {
        onOtherResponse(addProductContentEl, this);
    }
}

function onProductDelClicked() {
    const productDel = this.dataset.productDel;

    const params = new URLSearchParams();
    params.append('del-id', productDel);

    const xhr = new XMLHttpRequest()
    xhr.addEventListener('load', onProductDeleteResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/product?' + params.toString());
    xhr.send();
}

function onProductDeleteResponse() {
    if (this.status === OK) {
        const response = JSON.parse(this.responseText);
        alert(response.message);
        onAllProductsClicked();
    } else {
        onOtherResponse(allProductsEl, this);
    }
}
