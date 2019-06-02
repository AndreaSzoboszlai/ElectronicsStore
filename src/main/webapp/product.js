
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
        //buttonCartTdEl.addEventListener('click', onProductCartClicked);
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
        //buttonCartTdEl.addEventListener('click', onProductEditClicked);
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
