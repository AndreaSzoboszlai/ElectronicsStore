function onOrdersClicked() {
    const xhr = new XMLHttpRequest()
    xhr.addEventListener('load', onDisplayOrder);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/order');
    xhr.send();
}

function onDisplayOrder() {
    if (this.status === OK) {
        hideContentById('main-content');
        showContents(['my-orders-content']);
        removeAllChildren(myOrdersContentDivEl);
        const orders = JSON.parse(this.responseText);
        displayMyOrders(orders);
    } else {
        onOtherResponse(myOrdersContentDivEl, this);
    }
}

function displayMyOrders(orders) {
    const tableEl = document.createElement('table');
    tableEl.setAttribute('id', 'my-orders-table');
    const theadEl = createOrdersTableHead();
    const tbodyEl = createOrdersTableBody(orders);
    tableEl.appendChild(theadEl);
    tableEl.appendChild(tbodyEl);
    myOrdersContentDivEl.appendChild(tableEl);
}

function onOrderNumberClicked() {
    removeAllChildren(myOrdersContentDivEl);
    const orderId = this.dataset.orderId;
    const params = new URLSearchParams();
    params.append('orderId', orderId);
    const xhr = new XMLHttpRequest();
    if (role === 'CUSTOMER') {
        xhr.addEventListener('load', onOrderClickedResponse);
    } else {
        xhr.addEventListener('load', onAllOrderClickedResponse);
    }
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/order');
    xhr.send(params);
}

function onOrderClickedResponse() {
    if (this.status === OK) {
        const orderDto = JSON.parse(this.responseText);
        console.log(orderDto);
        onShowOrderDetails(orderDto);
    } else {
        onOtherResponse(myOrdersContentDivEl, this);
    }
}

function onShowOrderDetails(orderDto) {
    removeAllChildren(myOrdersContentDivEl);
    const tableEl = document.createElement('table');
    tableEl.setAttribute('id', 'my-order-table');
    const theadEl = createMyOrderTableHead();
    const orderList = orderDto.productsInOrder
    const tbodyEl = createMyOrderTableBody(orderList);
    tableEl.appendChild(theadEl);
    tableEl.appendChild(tbodyEl);
    myOrdersContentDivEl.appendChild(tableEl);
    const pEl = document.createElement('p');
    const nEl = document.createTextNode("Total Price: $ " + orderDto.totalOrderCost);
    pEl.appendChild(nEl);
    const pStatusEl = document.createElement('p');
    var nStatusEl = null;
    if (orderDto.status === true) {
        nStatusEl = document.createTextNode("Your order has been shipped");
    } else {
        nStatusEl = document.createTextNode("Your order has not been shipped yet");
    }
    pStatusEl.appendChild(nStatusEl);
    myOrdersContentDivEl.appendChild(pStatusEl);
    myOrdersContentDivEl.appendChild(pEl);
    const buttonBackEl = createBackButton();
    buttonBackEl.addEventListener('click', onOrdersClicked);
    myOrdersContentDivEl.appendChild(buttonBackEl);
}

function createMyOrderTableHead() {
    const nameTdEl = document.createElement('th');
    nameTdEl.textContent = 'Name';

    const priceTdEl = document.createElement('th');
    priceTdEl.textContent = 'Price';

    const quantityTdEl = document.createElement('th');
    quantityTdEl.textContent = 'Quantity';

    const tPriceTdEl = document.createElement('th');
    tPriceTdEl.textContent = 'Total Price';

    const trTdEl = document.createElement('tr');
    trTdEl.appendChild(nameTdEl);
    trTdEl.appendChild(priceTdEl);
    trTdEl.appendChild(quantityTdEl);
    trTdEl.appendChild(tPriceTdEl);

    const theadEl = document.createElement('thead');
    theadEl.appendChild(trTdEl);
    return theadEl;
}

function createMyOrderTableBody(orderDto) {
    const tbodyEl = document.createElement('tbody');
    for (let i = 0; i < orderDto.length; i++) {
        product = orderDto[i];
        const nameTdEl = document.createElement('td');
        nameTdEl.textContent = product.name;

        const priceTdEl = document.createElement('td');
        priceTdEl.textContent = '$ ' + product.price;

        const quantityTdEl = document.createElement('td');
        quantityTdEl.textContent = product.quantity;

        const tPriceTdEl = document.createElement('td');
        tPriceTdEl.textContent = '$ ' + product.totalPrice;

        const trEl = document.createElement('tr');
        trEl.setAttribute('id', 'row-order-id-' + product.productId);

        trEl.appendChild(nameTdEl);
        trEl.appendChild(priceTdEl);
        trEl.appendChild(quantityTdEl);
        trEl.appendChild(tPriceTdEl);
        tbodyEl.appendChild(trEl);
    }

    return tbodyEl;
}
