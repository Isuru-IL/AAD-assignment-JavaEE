function customerInitialize() {
    getAllCustomers();
}
getAllCustomers();

$('#btnCustomerSave').click(function () {
    if (checkAllCustomers()) {
        saveCustomer();
    } else {
        //alert("Please check the input fields");
        swal("Error", "Please check the input fields!", "error");
    }
});

$("#btnCustomerUpdate").click(function () {
    let id = $("#txtCustomerID").val();
    if (checkAllCustomers()) {
        updateCustomer(id);
    } else {
        swal("Error", "Please check the input fields!", "error");
    }
});

$("#btnCustomerDelete").click(function () {
    let id = $("#txtCustomerID").val();

    if (checkAllCustomers()) {
        deleteCustomer(id);
    } else {
        swal("Error", "Please check the input fields!", "error");
    }
});

$("#btnCustomerClear").click(function () {
    clearCustomerInputFields()
});

$("#btnCustomerSearch").click(function () {
    if ($("#txtCustomerSearch").val() != "") {
        let cusId = $("#txtCustomerSearch").val();
        $.ajax({
            url: "http://localhost:8080/app/customer?function=GetByID&id="+cusId,
            method: "GET",
            dataType: "json",
            success: function (resp, textStatus, jqxhr) {
                if (resp == null) {
                    swal("Error", "Invalid ID!", "error");
                    return;
                }
                setCustomerTextFieldValues(resp.id, resp.name, resp.address, resp.salary);

            },
            error: function (jqxhr, textStatus, error) {
                console.log("searchCustomerByID(id) = "+jqxhr);
            }
        })

    } else {
        //alert("Please input ID or Name")
        swal("Error", "Please input Customer ID!", "error");
    }

});

function saveCustomer() {
    let customerID = $("#txtCustomerID").val();
    let customerName = $("#txtCustomerName").val();
    let customerAddress = $("#txtCustomerAddress").val();
    let customerSalary = $("#txtCustomerSalary").val();

    let cusObj = {
        id: customerID,
        name: customerName,
        address: customerAddress,
        salary: customerSalary
    }
    let jsonObj = JSON.stringify(cusObj);
    $.ajax({
        url: "http://localhost:8080/app/customer",
        method: "POST",
        data: jsonObj,
        contentType: "application/json",
        success: function (resp, textStatus, jqxhr) {
            if (jqxhr.status == 201) {
                swal("Saved", "Customer saved successfully!", "success");
                clearCustomerInputFields()
                getAllCustomers();
            }
        },
        error: function (jqxhr, textStatus, error) {
            if (jqxhr.status == 409) {
                swal("Error", "Customer already exits!", "error");
            } else {
                swal("Error", "Something went wrong!", "error")
            }
        }
    })
}

function updateCustomer(id) {
    let name = $("#txtCustomerName").val();
    let address = $("#txtCustomerAddress").val();
    let salary = $("#txtCustomerSalary").val();

    let cusObj = {
        id: id,
        name: name,
        address: address,
        salary: salary
    }

    let jsonObj = JSON.stringify(cusObj);
    $.ajax({
        url: "http://localhost:8080/app/customer",
        method: "PUT",
        data: jsonObj,
        contentType: "application/json",
        success: function (resp, textStatus, jqxhr) {
            if (jqxhr.status == 204) {
                getAllCustomers();
                clearCustomerInputFields()
                swal("Updated", "Customer updated successfully!", "success");
            }
        },
        error: function (jqxhr, textStatus, error) {
           /* console.log(textStatus)
            console.log(jqxhr.status)*/
            if (jqxhr.status == 500) {
                swal("Error", "Customer does not exits!", "error");
            } else {
                swal("Error", "Something went wrong!", "error")
            }
        }
    })
}

function deleteCustomer(id) {
    $.ajax({
        url: "http://localhost:8080/app/customer?id=" +id,
        method: "DELETE",
        success: function (resp, textStatus, jqxhr) {
            if (jqxhr.status == 204){
                clearCustomerInputFields()
                getAllCustomers();
                //alert("Customer Deleted");
                swal("Deleted", "Customer deleted successfully!", "success");
            }
        },
        error: function (jqxhr, textStatus, error) {
            swal("Error", "Customer Not Removed. Invalid Customer!", "error");
        }
    })
}

function bindEvents() {
    $('#tbody-customer>tr').click(function () {

        let row = $(this);

        let customerID = row.children().eq(0).text();
        let customerName = row.children().eq(1).text();
        let address = row.children().eq(2).text();
        let salary = row.children().eq(3).text();

        setCustomerTextFieldValues(customerID, customerName, address, salary);
        $("#btnCustomerDelete").prop('disabled', false);
    });
}

function getAllCustomers(){
    $('#tbody-customer').empty();
    $.ajax({
        url : "http://localhost:8080/app/customer?function=GetAll",
        method : "GET",
        success : function (cusList) {
            for (let cus of cusList) {
                /*console.log("successCusId : "+ cus.id);*/
                let row = `<tr>
                <th>${cus.id}</th>
                <td>${cus.name}</td>
                <td>${cus.address}</td>
                <td>${cus.salary}</td>
            </tr>`;

                $('#tbody-customer').append(row);
                bindEvents();
            }
        },
        error : function (error) {
            console.log("error : "+ error);
        }
    })
    bindEvents();
}

function setCustomerTextFieldValues(id, name, address, salary){
    $('#txtCustomerID').val(id);
    $('#txtCustomerName').val(name);
    $('#txtCustomerAddress').val(address);
    $('#txtCustomerSalary').val(salary);
}
