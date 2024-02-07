//Модальное окно EDIT

let editForm = document.forms["editForm"];
let modalEdit = document.querySelector('#editModal');

async function editModal(id) {
    await modal(editForm, modalEdit, id);
    loadRolesForEdit();
}

function editUser() {
    editForm.addEventListener("submit", ev => {
        ev.preventDefault();

        //Приведение ролей из вида js к виду java
        let rolesForEdit = [];
        for (let i = 0; i < editForm.role.options.length; i++) {
            if (editForm.role.options[i].selected) rolesForEdit.push({
                id: editForm.role.options[i].value,
                name: "ROLE_" + editForm.role.options[i].text
            });
        }

        fetch(urlAdmin + editForm.id.value, {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: editForm.id.value,
                username: editForm.username.value,
                secondname: editForm.secondname.value,
                age: editForm.age.value,
                email: editForm.email.value,
                password: editForm.password.value,
                role: rolesForEdit
            })
        }).then(() => {
            alert('Пользователь успешно изменен')
            fillUsersTable();
        });
    });
}

//Приведение ролей к виду JS
function loadRolesForEdit() {
    let selectEdit = document.getElementById("edit-role");
    selectEdit.innerHTML = "";

    fetch(urlRoles)
        .then(res => res.json())
        .then(data => {
            data.forEach(role => {
                let option = document.createElement("option");
                option.value = role.id;
                option.text = role.name.toString().replace('ROLE_', '');
                selectEdit.appendChild(option);
            });
        })
        .catch(error => console.error(error));
}

window.addEventListener("load", loadRolesForEdit);

editUser();
