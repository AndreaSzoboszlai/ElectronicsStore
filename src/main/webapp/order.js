function onAllOrdersClicked() {
    const xhr = new XMLHttpRequest()
    xhr.addEventListener('load', onDisplayAllOrder);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/orders');
    xhr.send();
}

function onDisplayAllOrder() {
    hideContentById('main-content');
    showContents(['all-orders-content']);
    removeAllChildren(allOrdersContentDivEl);
    if ( this.status === OK) {
        const orders = JSON.parse(this.responseText);
        displayOrders(orders);
    } else {
        onOtherResponse(allOrdersContentDivEl, this);
    }
}

function displayOrders(orders) {
    const tableEl = document.createElement('table');
    tableEl.setAttribute('id', 'all-orders-table');
    const theadEl = createOrdersTableHead();
    const tbodyEl = createOrdersTableBody(orders);
    tableEl.appendChild(theadEl);
    tableEl.appendChild(tbodyEl);
    allOrdersContentDivEl.appendChild(tableEl);
}

function createOrdersTableHead() {
    const numTdEl = document.createElement('th');
    numTdEl.textContent = 'Order number';

    const priceTdEl = document.createElement('th');
    priceTdEl.textContent = 'Total Price'

    const statusTdEl = document.createElement('th');
    statusTdEl.textContent = 'Has shipped';

    const trTdEl = document.createElement('tr');
    trTdEl.appendChild(numTdEl);
    trTdEl.appendChild(priceTdEl);
    trTdEl.appendChild(statusTdEl);

    const theadEl = document.createElement('thead');
    theadEl.appendChild(trTdEl);
    return theadEl;
}

function createOrdersTableBody(orders) {
    const tbodyEl = document.createElement('tbody');

    for (let i = 0; i < orders.length; i++) {
        const order = orders[i];
        const nameTdEl = document.createElement('td');
        const nameAEl = document.createElement('a');
        nameAEl.href = 'javascript:void(0)';
        nameAEl.dataset.orderId = order.id;
        nameAEl.onclick = onOrderNumberClicked;
        nameAEl.textContent = order.id;
        nameAEl.setAttribute('id', 'orders-id-' + order.id);
        nameTdEl.appendChild(nameAEl);

        const priceTdEl = document.createElement('td');
        priceTdEl.textContent = '$ ' + order.totalPrice;

        const statusTdEl = document.createElement('td');
        statusTdEl.textContent = order.orderStatus;

        const trEl = document.createElement('tr');
        trEl.setAttribute('id', 'row-product-id-' + order.id);

        trEl.appendChild(nameTdEl);
        trEl.appendChild(priceTdEl);
        trEl.appendChild(statusTdEl);
        tbodyEl.appendChild(trEl);
    }
    return tbodyEl;
}

function onOrderNumberClicked() {
    const orderId = this.dataset.orderId;
    console.log(orderId);
}
