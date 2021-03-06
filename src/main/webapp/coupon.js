function onCouponsClicked() {
    hideContentById('main-content');
    showContents(['all-coupon-content']);
    removeAllChildren(couponContentDivEl);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onCouponsLoad);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/coupons');
    xhr.send();
}

function onCouponsLoad() {
    if (this.status === OK) {
        const coupons = JSON.parse(this.responseText);
        createCouponsTable(coupons);
    } else {
        onOtherResponse(couponContentDivEl, this);
    }
}

function createCouponsTable(coupons) {
    const tableEl = document.createElement('table');
    const theader = createCouponHeader();
    const tbody = createCouponTable(coupons);
    const createBtn = createNewCouponButton()
    createBtn.addEventListener('click', onAddCouponClicked);
    tableEl.appendChild(theader);
    tableEl.appendChild(tbody);
    couponContentDivEl.appendChild(tableEl);
    couponContentDivEl.appendChild(createBtn);
}

function createNewCouponButton() {
    const buttonEl = document.createElement('button');
    buttonEl.classList.add('form-button');
    buttonEl.textContent = 'Create new Coupon';
    return buttonEl;
}


function createCouponHeader() {
    const nameThEl = document.createElement('th');
    nameThEl.textContent = 'Name';

    const codeThEl = document.createElement('th');
    codeThEl.textContent = 'Code';

    const percentageThEl = document.createElement('th');
    percentageThEl.textContent = 'Percentage'

    const deleteThEl = document.createElement('th');
    deleteThEl.textContent = 'Delete'

    const trTdEl = document.createElement('tr');
    trTdEl.appendChild(nameThEl);
    trTdEl.appendChild(codeThEl);
    trTdEl.appendChild(percentageThEl);
    trTdEl.appendChild(deleteThEl);

    const theadEl = document.createElement('thead');
    theadEl.appendChild(trTdEl);
    return theadEl;
}

function createCouponTable(coupons) {
    const tbodyEl = document.createElement('tbody');
    for (let i = 0; i < coupons.length; i++) {
        coupon = coupons[i];

        const nameTdEl = document.createElement('td');
        nameTdEl.textContent = coupon.name;
        const codeTdEl = document.createElement('td');
        codeTdEl.textContent = coupon.code;
        const percentageTdEl = document.createElement('td');
        percentageTdEl.textContent = coupon.percentage;
        const buttonDeleteEl = document.createElement('i');
        buttonDeleteEl.classList.add('icon-trash');
        buttonDeleteEl.dataset.couponEditId = coupon.id;
        buttonDeleteEl.addEventListener('click', onCouponDeleteClicked);
        const buttonDeleteTdEl = document.createElement('td');
        buttonDeleteTdEl.appendChild(buttonDeleteEl);
        const trEl = document.createElement('tr');
        trEl.setAttribute('id', 'row-coupon-id-' + coupon.id);

        trEl.appendChild(nameTdEl);
        trEl.appendChild(codeTdEl);
        trEl.appendChild(percentageTdEl);
        trEl.appendChild(buttonDeleteTdEl);

        tbodyEl.appendChild(trEl);
    }
    return tbodyEl;
}

function onCouponDeleteClicked() {
    const couponEditId = this.dataset.couponEditId;

    const params = new URLSearchParams();
    params.append('coupon-id', couponEditId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onDeleteResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/coupon?' + params.toString());
    xhr.send();
}

function onDeleteResponse() {
    if (this.status === OK) {
        const response = JSON.parse(this.responseText);
        alert(response.message);
        onCouponsClicked();
    } else {
        onOtherResponse(couponContentDivEl, this);
    }
}
