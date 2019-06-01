
function onAllProductsClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onAllProductsLoad);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/products');
    xhr.send();
}

function onAllProductsLoad() {
    allProductsEl.style.display = 'block';
    if (this.status === OK) {
        const products = JSON.parse(this.responseText);
        console.log(products);
        creteProductTable(products);
    } else {
        onOtherResponse(allProductsEl, this);
    }
}

function creteProductTable(products) {
    //hideContentById(mainContentEl);
    removeAllChildren(allProductsEl);
    const tableEl = document.createElement('table');
    tableEl.setAttribute('id', 'all-product-table');
    const theadEl = createProductTableHeader();
    const tbodyEl = createProductTableBody(products);
    tableEl.appendChild(theadEl);
    tableEl.appendChild(tbodyEl);
    allProductsEl.appendChild(tableEl);
}

function createProductTableHeader() {
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

function createProductTableBody(products) {
    const tbodyEl = document.createElement('tbody');

    for (let i = 0; i < products.length; i++) {
        const product = products[i];
        const nameTdEl = document.createElement('td');
        const nameAEl = document.createElement('a');
        nameAEl.href = 'javascript:void(0)';
        nameAEl.dataset.productId = product.id;
        //nameAEl.onclick = onProductClicked;
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
        //buttonCartTdEl.addEventListener('click', onCartClicked);
        const buttonTdEl = document.createElement('td');
        buttonTdEl.appendChild(buttonCartTdEl);
        buttonTdEl.setAttribute('id', 'product-update-button-' + product.id);

        const trEl = document.createElement('tr');
        trEl.setAttribute('id', 'row-product-id-' + product.id);

        trEl.appendChild(nameAEl);
        trEl.appendChild(priceTdEl);
        trEl.appendChild(stockTdEl);
        trEl.appendChild(buttonTdEl);
        tbodyEl.appendChild(trEl);
    }
    return tbodyEl;
}
