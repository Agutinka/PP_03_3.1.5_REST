fetch("/user/api").then(
    res => {
        res.json().then(
            data => {
                let rolesHtml = ""; // Переменная для хранения HTML-кода ролей пользователя
                data.roles.forEach((role, index) => {
                    let roleName = role.name ? role.name : role.toString();
                    roleName = roleName.replace('ROLE_', ''); // Убираем префикс 'ROLE_'

                    if (index > 0) {
                        rolesHtml += " "; // Добавляем пробел между ролями, кроме первой
                    }
                    rolesHtml += `${roleName}`; // Добавляем имя роли
                    document.getElementById('navbar-head').innerHTML =
                        `<strong>${data.email} </strong> with roles:  ${rolesHtml}`;
                });

                document.getElementById("table").innerHTML = `
                        <tr>
                        <td>${data.id}</td>
                        <td>${data.username}</td>
                        <td>${data.secondname}</td>
                        <td>${data.age}</td>
                        <td>${data.email}</td>
                        <td>${rolesHtml}</td></tr>`;
            }
        )
    }
);