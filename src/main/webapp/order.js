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

        if (role === "EMPLOYEE") {
            if (order.orderStatus === true) {
                statusTdEl.textContent = 'Shipped';
            } else {
                const inputNaEl = document.createElement("input");
                inputNaEl.setAttribute("type","checkbox");
                inputNaEl.dataset.orderId = order.id;
                inputNaEl.classList.add("status-checkbox");
                inputNaEl.setAttribute("name","checkbox-order-status");
                inputNaEl.addEventListener('change', onChangeStatus);
                statusTdEl.appendChild(inputNaEl);
            }
        } else {
            if (order.orderStatus === true) {
                statusTdEl.textContent = 'Shipped';
            } else {
                statusTdEl.textContent = 'Has not been shipped yet';
            }
        }
        //statusTdEl.textContent = order.orderStatus;


        const trEl = document.createElement('tr');
        trEl.setAttribute('id', 'row-product-id-' + order.id);

        trEl.appendChild(nameTdEl);
        trEl.appendChild(priceTdEl);
        trEl.appendChild(statusTdEl);
        tbodyEl.appendChild(trEl);
    }
    return tbodyEl;
}

function onChangeStatus() {
    const orderId = this.dataset.orderId;
    const params = new URLSearchParams();
    params.append('orderId', orderId);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onChangedStatusLoad);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/orders');
    xhr.send(params);
}

function onChangedStatusLoad() {
    if (this.status === OK) {
        const response = JSON.parse(this.responseText);
        alert(response.message);
        onAllOrdersClicked();
    } else {
        onOtherResponse(allOrdersContentDivEl, this);
    }
}

function onAllOrderClickedResponse() {
    if (this.status === OK) {
        const orderDto = JSON.parse(this.responseText);
        onShowAllOrderDetails(orderDto);
    } else {
        onOtherResponse(allOrdersContentDivEl, this);
    }
}

function onShowAllOrderDetails(orderDto) {
    removeAllChildren(allOrdersContentDivEl);
    const tableEl = document.createElement('table');
    tableEl.setAttribute('id', 'all-order-table');
    const theadEl = createMyOrderTableHead();
    const orderList = orderDto.productsInOrder
    const tbodyEl = createMyOrderTableBody(orderList);
    tableEl.appendChild(theadEl);
    tableEl.appendChild(tbodyEl);
    allOrdersContentDivEl.appendChild(tableEl);
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
    allOrdersContentDivEl.appendChild(pStatusEl);
    allOrdersContentDivEl.appendChild(pEl);
    const buttonBackEl = createBackButton();
    buttonBackEl.addEventListener('click', onAllOrdersClicked);
    allOrdersContentDivEl.appendChild(buttonBackEl);
}
