//Модальное окно DELETE

let deleteForm = document.forms["deleteForm"]
let modalDelete = document.querySelector('#deleteModal');

async function deleteModal(id) {
    await modal(deleteForm, modalDelete, id);
    loadRolesForDelete();
}

function deleteUser() {
    deleteForm.addEventListener("submit", ev => {
        ev.preventDefault();
        fetch(urlAdmin + deleteForm.id.value, {
            method: "DELETE",
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(() => {
            alert('Пользователь успешно удален')
            fillUsersTable();
        });
    });
}

//Приведение ролей к виду JS
function loadRolesForDelete() {
    let selectDelete = document.getElementById("delete-role");
    selectDelete.innerHTML = "";

    fetch(urlRoles + deleteForm.id.value)
        .then(res => res.json())
        .then(data => {
            data.forEach(role => {
                let option = document.createElement("option");
                option.value = role.id;
                option.text = role.name.toString().replace('ROLE_', '');
                selectDelete.appendChild(option);
            });
        })
        .catch(error => console.error(error));
}

window.addEventListener("load", loadRolesForDelete);

deleteUser();