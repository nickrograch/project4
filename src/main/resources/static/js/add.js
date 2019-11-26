function addUser() {
    let user = {
        'name': $('#addName').val(),
        'password': $('#addPassword').val(),
        'email': $('#addEmail').val()
    };
    let role = $('#addRole').val();

    $.ajax({
        type: 'POST',
        url: "/rest/add/" + role,
        contentType: 'application/json;',
        data: JSON.stringify(user),
        async: false,
        cache: false,
        success: function () {
            $('[href="#tab1"]').tab('show');
            getTable();
        },
        error: function (error) {
            console.log(error);
        }
    })};