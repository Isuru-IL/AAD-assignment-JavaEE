function itemInitialize() {
    getAllItems();
}
getAllItems();

$('#btnItemSave').click(function () {
    if (checkAll()) {
        saveItem();
    } else {
        swal("Error", "Please check the input fields!", "error");
    }
});

$("#btnItemUpdate").click(function () {
    let code = $("#txtItemCode").val();
    if (checkAll()) {
        updateItem(code);
    } else {
        swal("Error", "Please check the input fields!", "error");
    }
});

$("#btnItemDelete").click(function () {
    let code = $("#txtItemCode").val();

    if (checkAll()) {
        deleteItem(code);
    } else {
        swal("Error", "Please check the input fields!", "error");
    }
});

$("#btnItemClear").click(function () {
    clearItemInputFields();
});

function updateItem(code) {
    let itemName = $("#txtItemName").val();
    let qtyOnHand = $("#txtItemQtyOnHand").val();
    let unitPrice = $("#txtItemPrice").val();

    let itemObj = {
        itemCode: code,
        itemName: itemName,
        qtyOnHand: qtyOnHand,
        unitPrice: unitPrice
    }
    let jsonObj = JSON.stringify(itemObj);
    console.log(jsonObj.itemName)
    $.ajax({
        url: "http://localhost:8080/app/item",
        method: "PUT",
        data: jsonObj,
        contentType: "application/json",
        success: function (resp, textStatus, jqxhr) {
            if (jqxhr.status == 204) {
                swal("Saved", "Item update successfully!", "success");
                clearItemInputFields()
                getAllItems();
            }
        },
        error: function(jqxhr, textStatus, error) {
            console.log("updateItem() = "+jqxhr.status);
            console.log(jqxhr)
            if (jqxhr.status == 500) {
                swal("Error", "Item does not exits!", "error");
            } else {
                swal("Error", "Something went wrong!", "error")
            }
        }
    })
}

function saveItem() {
    let itemCode = $("#txtItemCode").val();
    let itemName = $("#txtItemName").val();
    let qtyOnHand = $("#txtItemQtyOnHand").val();
    let unitPrice = $("#txtItemPrice").val();

    let itemObj = {
        itemCode: itemCode,
        itemName: itemName,
        qtyOnHand: qtyOnHand,
        unitPrice: unitPrice
    }
    let jsonObj = JSON.stringify(itemObj);
    console.log(jsonObj.itemName)
    $.ajax({
        url: "http://localhost:8080/app/item",
        method: "POST",
        data: jsonObj,
        contentType: "application/json",
        success: function (resp, textStatus, jqxhr) {
            if (jqxhr.status == 201) {
                swal("Saved", "Item saved successfully!", "success");
                clearItemInputFields()
                getAllItems();
            }
        },
        error: function(jqxhr, textStatus, error) {
            console.log("saveItem() = "+jqxhr.status);
            console.log(jqxhr)
            if (jqxhr.status == 409) {
                swal("Error", "Item already exits!", "error");
            } else {
                swal("Error", "Something went wrong!", "error")
            }
        }
    })
}

function deleteItem(code) {
    $.ajax({
        url: "http://localhost:8080/app/item?code=" +code,
        method: "DELETE",
        success: function (resp, textStatus, jqxhr) {
            if (jqxhr.status == 204){
                clearItemInputFields()
                getAllItems();
                //alert("Customer Deleted");
                swal("Deleted", "Item deleted successfully!", "success");
            }
        },
        error: function (jqxhr, textStatus, error) {
            console.log("deleteItem() = "+jqxhr.status);
            console.log(jqxhr)
            swal("Error", "Item Not Removed. Invalid Item!", "error");
        }
    })
}

$("#btnItemSearch").click(function () {
    if ($("#txtItemSearch").val() != "") {
        let code = $("#txtItemSearch").val();
        $.ajax({
            url: "http://localhost:8080/app/item?function=GetByCode&code="+code,
            method: "GET",
            dataType: "json",
            success: function (resp, textStatus, jqxhr) {
                if (resp == null) {
                    swal("Error", "Invalid ID!", "error");
                    return;
                }
                setItemTextFieldValues(resp.itemCode, resp.itemName, resp.qtyOnHand, resp.unitPrice);

            },
            error: function (jqxhr, textStatus, error) {
                console.log("searchItem() = "+jqxhr.status);
                console.log(jqxhr)
            }
        })

    } else {
        swal("Error", "Please input Item code !", "error");
    }

});

function getAllItems() {
    $("#tbody-item").empty();
    $.ajax({
        url : "http://localhost:8080/app/item?function=GetAll",
        method : "GET",
        success : function (itemList) {
            for (let item of itemList) {
                let row = `<tr>
                <th>${item.itemCode}</th>
                <td>${item.itemName}</td>
                <td>${item.qtyOnHand}</td>
                <td>${item.unitPrice}</td>
            </tr>`;

                $('#tbody-item').append(row);
                bindItemEvents();
            }
        },
        error : function (error) {
            console.log("error : "+ error);
        }
    })
    bindItemEvents();
}

function bindItemEvents() {
    $('#tbody-item>tr').click(function () {

        let itemRow = $(this);

        let itemCode = itemRow.children().eq(0).text();
        let itemName = itemRow.children().eq(1).text();
        let qtyOnHand = itemRow.children().eq(2).text();
        let unitPrice = itemRow.children().eq(3).text();

        setItemTextFieldValues(itemCode, itemName, qtyOnHand, unitPrice);
        $("#btnItemDelete").prop('disabled', false);
    });
}

function setItemTextFieldValues(code, name, qtyOnHand, unitPrice){
    $('#txtItemCode').val(code);
    $('#txtItemName').val(name);
    $('#txtItemQtyOnHand').val(qtyOnHand);
    $('#txtItemPrice').val(unitPrice);
}