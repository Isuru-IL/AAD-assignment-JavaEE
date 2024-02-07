let password = "";

function checkLogInUserDetails() {
    /*if ($('#txtLogInUserName').val() == "" || $('#txtLogInPassword').val() == ""){
        swal("Error!", "Please fill the input fields!", "error");
        return Promise.resolve(false);
    }*/
    return searchInvalidUserName($('#txtLogInUserName').val())
        .then(isValidUser => {
            if (isValidUser) {
                if ($('#txtLogInPassword').val() == password) {
                    clearLogInInputFields();
                    swal("Login!", "Login Successfully!", "success");
                    return true; // Return true if login is successful
                } else {
                    swal("Error!", "Invalid Password! Please try again.", "error");
                    return false; // Return false if password is invalid
                }
            } else {
                swal("Error!", "Invalid User Name! Please try again.", "error");
                return false; // Return false if user name is invalid
            }
        })
        .catch(error => {
            console.error("Error occurred while checking username:", error);
            swal("Error!", "An error occurred while checking the username. Please try again later.", "error");
            return false; // Return false if an error occurs
        });
}

function searchInvalidUserName(userName) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: "http://localhost:8080/app/logIn",
            method: "GET",
            success: function (userList) {
                if (userList.length != 0){
                    for (let user of userList) {
                        if (user.userName == userName) {
                            password = user.password;
                            resolve(true);
                            return;
                        }
                    }
                    resolve(false);
                } else {
                    resolve(false);
                }
            },
            error: function (jqxhr, textStatus, error) {
                console.log("searchInvalidUserName logIn() = "+jqxhr.status);
                console.log(jqxhr);
                reject(error);
            }
        });
    });
}

function clearLogInInputFields() {
    $('#txtLogInUserName').val("");
    $('#txtLogInPassword').val("");
}