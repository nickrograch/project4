$(document).ready(function () {
    getTable();


});


// $(document).ready(function(){
//     $("#editUserBtn").click(function () {
//         $("#myModal").modal('show');
//     });
// });

function getTable() {
    $.ajax({
        type: 'Get',
        url: "/rest/userlist",
        contentType: 'application/json;',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        async: true,
        cache: false,
        dataType: 'JSON',
        success: function (listUsers) {

            let htmlContentTable = `<table class="table table-striped container-white" id="tableUser">
                                        <thead>
                                            <tr class="text-center">
                                            <th>ID</th>
                                            <th>Role</th>
                                            <th>Login</th>
                                            <th>Password</th>
                                            <th>Email</th>
                                            </tr>
                                        </thead>
                                    </table>`;
            let htmlTable = "";
            for (let i = 0; i < listUsers.length; i++) {

                let htmlRole = listUsers[i].roles[0].role;

                htmlTable += `<tr id="list">`;
                htmlTable += `<td id="tableId">${listUsers[i].id}</td>`;
                htmlTable += `<td id="tableName">${listUsers[i].name}</td>`;
                htmlTable += `<td id="tablePass">${listUsers[i].password}</td>`;
                htmlTable += `<td id="tableRole">${htmlRole}</td>`;
                htmlTable += `<td id="tableEmail">${listUsers[i].email}</td>`;
                htmlTable += `<td><button type="button" class="btn btn-primary active btn-custom"
                data-toggle="modal" data-target="#myModal${listUsers[i].id}" aria-pressed="true" name="edit">Edit </button>
                 <form id="updateForm">
                    <div class="modal fade" id="myModal${listUsers[i].id}'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4>Edit user ${listUsers[i].name}</h4>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                     </button>
                                </div>
                                <div class="modal-body">
                                    <div class="container-add">
                                        <h4 class="text-black-50" style="text-align: center">ID</h4>
                                        <input type="number" id ="updateId' + listUsers[i].id + '" class="form-control"
                                               value="${listUsers[i].id}" style="width: 400px" disabled />
                                        <h4 class="text-black-50" style="text-align: center">Email</h4>
                                        <input type="email" id ="updateEmail${listUsers[i].id}" class="form-control"
                                                value="${listUsers[i].email}" />
                                        <h4 class="text-black-50" style="text-align: center">Login</h4>
                                        <input type="text" id ="updateName${listUsers[i].id}" class="form-control"
                                                value="${listUsers[i].name}" />
                                        <h4 class="text-black-50" style="text-align: center">Password</h4>
                                        <input type="password" id ="updatePassword${listUsers[i].id}" class="form-control"/>
                                        <h4 class="text-black-50" style="text-align: center">Role</h4>
                                        <select id ="updateRole${listUsers[i].id}" class="form-control" required="">
                                                <option>USER</option>
                                                <option>ADMIN</option>
                                        </select>
                                    </div>
                                    <div class="modal-footer">
                                        <button onclick="updateForm(${listUsers[i].id})" data-dismiss="modal" type="button" id ="editUserButton" class="btn btn-primary">Edit user</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                     </div>
                  </form>
                  <form>
                  <button onclick="deleteUser(${listUsers[i].id})" type="button" id = "delete" class="btn btn-primary active btn-custom" value="${listUsers[i].id}"
                            role="button" aria-pressed="true" name="id">Удалить</button>
                  <input type="hidden"  value="${listUsers[i].id}" id="id" name="id">
                  </form></td>`;
                htmlTable += ('</tr>');

            }
            $('#tableUser').remove();
            $('#contentTable ').append(htmlContentTable);
            $('#tableUser ').append(htmlTable);

        }
    })
};

