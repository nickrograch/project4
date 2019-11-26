

function deleteUser(id) {
    $.ajax({
        type: 'Delete',
        url: "/rest/delete/" + id,
        contentType: 'application/json;',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        async: true,
        cache: false,
        success: function () {
            getTable();
        },
        error: function (error) {
            console.log(error);
        }
})}