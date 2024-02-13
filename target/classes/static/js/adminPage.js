const adminurl = '/api/admins';

const currentUser = fetch(adminurl).then(response => response.json())
currentUser.then(user => {

        let roles = ''
        user.roles.forEach(role => {
            let formattedRoleName = role.name.replace('ROLE_', '');
            roles += ' ';
            roles += formattedRoleName;
        })
        document.getElementById("navbar-email").innerHTML = user.email
        document.getElementById("navbar-roles").innerHTML = roles
    }
)

async function getAdminPage() {
    let page = await fetch(adminurl);

    if(page.ok) {
        let listAllUser = await  page.json();
        loadTableData(listAllUser);
    } else {
        alert(`Error, ${page.status}`)
    }
}
function loadTableData(listAllUser) {
    const tableBody = document.getElementById('admintbody');
    let dataHtml = '';
    for (let user of listAllUser) {
        let roles = [];
        user.roles.forEach(role => {
            let formattedRoleName = role.name.replace('ROLE_', '');
            roles += ' ';
            roles += formattedRoleName;
        })
        dataHtml +=
`<tr>
    <td>${user.id}</td>
    <td>${user.username}</td>
    <td>${user.lastName}</td>
    <td>${user.age}</td>
    <td>${user.email}</td>
    <td>${roles}</td>
    <td>
        <button type="button" class="btn btn-primary" data-bs-toogle="modal"
        data-bs-target="#editModal" 
        onclick="loadDataForEditModal(${user.id})">Edit</button>
    </td>
        
    <td>
        <button class="btn btn-danger" data-bs-toogle="modal"
        data-bs-target="#deleteModal" 
        onclick="deleteModalData(${user.id})">Delete</button>
    </td>
   
</tr>`
    }
    tableBody.innerHTML = dataHtml;
}
getAdminPage().then(r => {});
getUserPage().then(r => {});