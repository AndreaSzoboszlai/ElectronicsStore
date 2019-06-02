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
