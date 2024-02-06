function orderDetailInitialize() {
    getAllOrders();
}

function getAllOrders() {
    $('#tbody-orders').empty();

    $.ajax({
        url : "http://localhost:8080/app/orderDetail?function=GetAll",
        method : "GET",
        success : function (orderList) {
            for (let order of orderList) {
                let row = `<tr>
            <th>${order.orderId}</th>
            <td>${order.cusId}</td>
            <td>${order.orderDate}</td>
            <td>${order.total}</td>
        </tr>`
                $('#tbody-orders').append(row);
            }
        },
        error : function (jqxhr, textStatus, error) {
            console.log("getAllOrders() = "+jqxhr.status);
            console.log(jqxhr)
        }
    })
}

$('#btnOrderClear').click(function () {
    getAllOrders();
    $('#txtSearchOrder').val("");
});

$('#btnOrderSearch').click(function () {
    if ($('#txtSearchOrder').val() != ""){
        let option = $('#cmbOrderSearch').val();
        if (option == "OrderID"){
            searchOrderByOrderId($('#txtSearchOrder').val());

        } else {
            searchOrderByCustomerId($('#txtSearchOrder').val());

        }
    } else {
        swal("Error", "Please input Order ID or Customer ID!", "error");
    }
});

/*
function addSearchDataToTable(orderList) {
    $('#tbody-orders').empty();
    for (let order of orderList) {
        console.log(order.orderId)
        let row = `<tr>
            <th>${order.orderId}</th>
            <td>${order.cusId}</td>
            <td>${order.orderDate}</td>
            <td>${order.total}</td>
        </tr>`
        $('#tbody-orders').append(row);
    }
}
*/

function searchOrderByOrderId(orderId) {
    $.ajax({
        url : "http://localhost:8080/app/orderDetail?function=GetByOrderID&orderId="+orderId,
        method : "GET",
        success: function (list) {
            if (list.length != 0){
                $('#tbody-orders').empty();
                for (let order of list) {
                    let row = `<tr>
                        <th>${order.orderId}</th>
                        <td>${order.cusId}</td>
                        <td>${order.orderDate}</td>
                        <td>${order.total}</td>
                    </tr>`

                    $('#tbody-orders').append(row);
                }
            }else {
                swal("Error", "Invalid Order ID!", "error");
            }
        },
        error : function (jqxhr, textStatus, error) {
            console.log("searchOrderByOrderId(orderId) = "+jqxhr.status);
            console.log(jqxhr)
        }
    })
}

function searchOrderByCustomerId(customerId) {
    $.ajax({
        url : "http://localhost:8080/app/orderDetail?function=GetByCusID&customerId="+customerId,
        method : "GET",
        success: function (list) {
            if (list.length != 0){
                $('#tbody-orders').empty();
                for (let order of list) {
                    let row = `<tr>
                        <th>${order.orderId}</th>
                        <td>${order.cusId}</td>
                        <td>${order.orderDate}</td>
                        <td>${order.total}</td>
                    </tr>`

                    $('#tbody-orders').append(row);
                }
            }else {
                swal("Error", "Invalid Customer ID!", "error");
            }
        },
        error : function (jqxhr, textStatus, error) {
            console.log("searchOrderByOrderId(orderId) = "+jqxhr.status);
            console.log(jqxhr)
        }
    })
}

