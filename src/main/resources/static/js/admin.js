fillUsersTable();

// Заполнение таблицы всех пользователей
function fillUsersTable() {
    fetch("/admin/api/users").then(
        res => {
            res.json().then(
                data => {
                    data.forEach((u) => {
                        let rolesHtml = ""; // Переменная для хранения HTML-кода ролей пользователя
                        document.getElementById('navbar-head').innerHTML =
                            `<strong>${u.email} </strong> with roles:  ${rolesHtml}`;
                        u.roles.forEach((role, index) => {
                            let roleName = role.name ? role.name : role.toString();
                            roleName = roleName.replace('ROLE_', ''); // Убираем префикс 'ROLE_'
                            if (index > 0) {
                                rolesHtml += " "; // Добавляем пробел между ролями
                            }
                            rolesHtml += `${roleName}`; // Добавляем имя роли

                        });
                        let rowHtml = `<tr id=tr_${u.id}>
                            <td>${u.id}</td>
                            <td>${u.username}</td>
                            <td>${u.secondname}</td>
                            <td>${u.age}</td>
                            <td>${u.email}</td>
                            <td>${rolesHtml}</td>
                            
                            <!-- Кнопка Edit -->
                             <td class="text-center" ">
                                <div class="btn-toolbar" id="btn-toolbar-${u.id}">
                                    <!-- Ссылка для открытия модального окна редактирования -->
                                    <button type="button"
                                        class="btn btn-primary editUserModal"
                                        data-bs-toogle="modal"
                                        data-bs-target="#editModal"
                                        onclick="editModal(${u.id})">
                                            Edit
                                    </button>
                                </div>
                            </td>
                            
                            <!-- Кнопка Delete -->
                           <td class="text-center">
                                <!-- Ссылка для открытия модального окна удаления -->
                                <button type="button"
                                    class="btn btn-danger delete-button"
                                    data-toggle="modal"
                                    data-target="#deleteModal"
                                    onclick="deleteModal(${u.id})">
                                        Delete
                                </button>
                                
                                <!-- Модальное окно удаления -->
                                <div th:replace="delete-modal-content :: content"></div>
                            </td>
                        </tr>`;

                        document.getElementById("full").insertAdjacentHTML('beforeend', rowHtml);
                    });
                })
        }
    )
}

// Заполнение модальных окон
async function getUserById(id) {

    try {
        let response = await fetch(urlAdmin + id);
        console.log(response);
        return await response.json();
    } catch (ex) {
        console.log(ex.message);
    }
}

async function modal(form, modal, id) {
    modal.show();
    let user = await getUserById(id);
    console.log(user);
    form.id.value = user.id;
    console.log(form.id.value);
    form.username.value = user.username;
    form.secondname.value = user.secondname;
    form.age.value = user.age;
    form.email.value = user.email;
    form.role.value = user.role;
}
