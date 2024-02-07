let txtUserName = $('#txtSignUpUserName');
let txtPassword = $('#txtSignUpPassword');
let txtConfirmPassword = $('#txtSignUpConfirmPassword');

function checkSignUpUserDetails() {
    if (txtUserName.val()=="" || txtPassword.val()=="" || txtConfirmPassword.val()=="") {
        swal("Error!", "Please fill the input fields!", "error");
    } else {
        searchValidUserName(txtUserName.val())
            .then(isValid => {
                if (isValid) {
                    if (txtPassword.val() == txtConfirmPassword.val()) {
                        addNewUserName(txtUserName.val(), txtPassword.val());
                        return true;
                    } else {
                        swal("Error!", "Password not same. Check the password again!", "error");
                    }
                } else {
                    swal("Error!", "This User Name already exists! Please try again.", "error");
                }
            })
            .catch(error => {
                console.error("Error occurred while checking username:", error);
                swal("Error!", "An error occurred while checking the username. Please try again later.", "error");
            });
    }
    return false
}

function searchValidUserName(userName) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: "http://localhost:8080/app/signUp",
            method: "GET",
            success: function(userList) {
                if (userList.length != 0) {
                    for (let user of userList) {
                        console.log(user.userName);
                        if (user.userName === userName) {
                            resolve(false);
                            return;
                        }
                    }
                    resolve(true);
                } else {
                    console.log("searchValidUserName");
                    resolve(true);
                }
            },
            error: function(jqxhr, textStatus, error) {
                console.log("searchValidUserName signUp() = " + jqxhr.status);
                console.log(jqxhr);
                reject(error);
            }
        });
    });
}

function addNewUserName(userName, password) {
    let userObj = {
        userName: userName,
        password: password
    }
    let jsonObj = JSON.stringify(userObj);
    $.ajax({
        url: "http://localhost:8080/app/signUp",
        method: "POST",
        data: jsonObj,
        contentType: "application/json",
        success: function (resp, textStatus, jqxhr) {
            if (jqxhr.status == 201) {
                clearSignUpInputFields();
                swal("Sign Up!", "New User add successfully!", "success");
            }
        },
        error: function(jqxhr, textStatus, error) {
            console.log("addNewUserName() = "+jqxhr.status);
            console.log(jqxhr)
        }
    })
}

function clearSignUpInputFields() {
    $('#txtSignUpUserName').val("");
    $('#txtSignUpPassword').val("");
    $('#txtSignUpConfirmPassword').val("");
}
