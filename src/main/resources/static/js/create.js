// Создание нового пользователя
const urlAdmin = 'http://localhost:8080/admin/api/create'
const urlRoles = 'http://localhost:8080/admin/api/roles/'

let newUserForm = document.forms["newUserForm"];

function createNewUser() {
    newUserForm.addEventListener("submit", ev => {
        ev.preventDefault();


        let rolesForNewUser = [];
        let options = document.querySelector('#newRoles').options //added
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected)
                rolesForNewUser.push({
                    id: options[i].value,
                    role: "ROLE_" + options[i].text
                });
        }

        fetch(urlAdmin, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: document.getElementById('newUsername').value,
                secondname: document.getElementById('newSecondname').value,
                age: document.getElementById('newAge').value,
                email: document.getElementById('newEmail').value,
                password: document.getElementById('newPassword').value,
                role: rolesForNewUser
            })
        })
            .then(() => {
                newUserForm.reset();
                alert('Пользователь успешно добавлен')
                fillUsersTable();
            });
    });
}

// Приведение загруженных ролей в формате java к виду JS
function loadRolesForNewUser() {
    let selectAdd = document.getElementById("newRoles");

    selectAdd.innerHTML = "";

    fetch(urlRoles)
        .then(res => res.json())
        .then(data => {
            data.forEach(role => {
                let option = document.createElement("option");
                option.value = role.id;
                option.text = role.name.toString().replace('ROLE_', '');
                selectAdd.appendChild(option);
            });
        })
        .catch(error => console.error(error));
}

window.addEventListener("load", loadRolesForNewUser);

createNewUser();