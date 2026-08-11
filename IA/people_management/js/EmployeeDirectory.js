/**
 * HR Platform - Employee Directory Logic
 */
document.addEventListener('DOMContentLoaded', function () {
    console.log('EmployeeDirectory JS initialized');

    // Data Store
    const employees = [
        { id: 'EMP-8021', name: 'Lucia Doan', role: 'HR Director', dept: 'Human Resources', email: 'lucia.doan@copilot.hr', status: 'Active', date: '15 Jan 2021', avatar: 'LD' },
        { id: 'EMP-8022', name: 'Duong Vo Duy Khang', role: 'Senior Frontend Engineer', dept: 'Engineering', email: 'khang.duong@copilot.hr', status: 'Active', date: '01 Mar 2022', avatar: 'DK' },
        { id: 'EMP-8023', name: 'Dao Vo', role: 'Chief Technology Officer (CTO)', dept: 'Engineering', email: 'dao.vo@copilot.hr', status: 'Active', date: '10 Oct 2020', avatar: 'DV' },
        { id: 'EMP-8024', name: 'Emily Chen', role: 'Senior Product Designer', dept: 'Product & Design', email: 'emily.chen@copilot.hr', status: 'Active', date: '05 Jun 2022', avatar: 'EC' },
        { id: 'EMP-8025', name: 'David Miller', role: 'DevOps Lead', dept: 'Engineering', email: 'david.miller@copilot.hr', status: 'Probation', date: '15 Jul 2026', avatar: 'DM' },
        { id: 'EMP-8026', name: 'Sarah Jenkins', role: 'Talent Acquisition Lead', dept: 'Human Resources', email: 'sarah.j@copilot.hr', status: 'Active', date: '12 Nov 2021', avatar: 'SJ' },
        { id: 'EMP-8027', name: 'Mark Vance', role: 'Backend Developer', dept: 'Engineering', email: 'mark.vance@copilot.hr', status: 'On Leave', date: '20 Aug 2023', avatar: 'MV' },
        { id: 'EMP-8028', name: 'Alex Lee', role: 'UI/UX Designer', dept: 'Product & Design', email: 'alex.lee@copilot.hr', status: 'Active', date: '02 Feb 2024', avatar: 'AL' }
    ];

    const tableBody = document.getElementById('directoryTableBody');
    const searchInput = document.getElementById('dirSearchInput');
    const deptSelect = document.getElementById('deptFilterSelect');
    const statusSelect = document.getElementById('statusFilterSelect');

    function renderTable(data) {
        if (!tableBody) return;
        tableBody.innerHTML = '';

        if (data.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="7" style="text-align:center; padding:32px; color:var(--text-muted);">No employees found matching criteria.</td></tr>`;
            return;
        }

        data.forEach(emp => {
            const tr = document.createElement('tr');
            tr.style.cursor = 'pointer';
            tr.setAttribute('data-id', emp.id);
            tr.innerHTML = `
                <td><strong>${emp.id}</strong></td>
                <td>
                    <div style="display:flex; align-items:center; gap:10px;">
                        <div class="avatar-tiny" style="width:30px; height:30px; background:#09090b; color:#fff; border-radius:6px; font-weight:700; font-size:11px; display:flex; align-items:center; justify-content:center;">${emp.avatar}</div>
                        <div>
                            <strong>${emp.name}</strong>
                            <div style="font-size:11px; color:var(--text-muted);">${emp.role}</div>
                        </div>
                    </div>
                </td>
                <td>${emp.dept}</td>
                <td>${emp.email}</td>
                <td>
                    <span class="status-pill ${emp.status.toLowerCase().replace(' ', '-')}">${emp.status}</span>
                </td>
                <td>${emp.date}</td>
                <td style="text-align:right;">
                    <button class="btn-wireframe btn-view-profile" data-id="${emp.id}" style="padding:4px 8px; font-size:12px;">View Profile</button>
                </td>
            `;

            // Row click event -> Navigate to full-page Employee Profile Detail screen
            tr.addEventListener('click', function (e) {
                window.location.href = `EmployeeProfileDetail.html?id=${emp.id}`;
            });

            tableBody.appendChild(tr);
        });
    }

    // Filter Handler
    function filterEmployees() {
        const query = (searchInput ? searchInput.value : '').toLowerCase();
        const dept = deptSelect ? deptSelect.value : 'ALL';
        const status = statusSelect ? statusSelect.value : 'ALL';

        const filtered = employees.filter(emp => {
            const matchesQuery = emp.name.toLowerCase().includes(query) ||
                emp.id.toLowerCase().includes(query) ||
                emp.email.toLowerCase().includes(query) ||
                emp.role.toLowerCase().includes(query);
            const matchesDept = (dept === 'ALL' || emp.dept.toLowerCase().includes(dept.toLowerCase()));
            const matchesStatus = (status === 'ALL' || emp.status.toLowerCase() === status.toLowerCase());
            return matchesQuery && matchesDept && matchesStatus;
        });

        renderTable(filtered);
    }

    if (searchInput) searchInput.addEventListener('input', filterEmployees);
    if (deptSelect) deptSelect.addEventListener('change', filterEmployees);
    if (statusSelect) statusSelect.addEventListener('change', filterEmployees);

    // Profile Drawer Logic
    const drawerBackdrop = document.getElementById('drawerBackdrop');
    const profileDrawer = document.getElementById('profileDetailDrawer');
    const closeBtn = document.getElementById('btnCloseDrawer');
    const closeFooterBtn = document.getElementById('btnCloseDrawerFooter');

    function openProfileDrawer(emp) {
        if (!emp || !profileDrawer) return;

        const nameEl = document.getElementById('drawerUserName');
        const roleEl = document.getElementById('drawerUserRoleDept');
        const avatarEl = document.getElementById('drawerAvatar');

        if (nameEl) nameEl.innerHTML = `${emp.name} <span class="status-pill active">${emp.status}</span>`;
        if (roleEl) roleEl.innerText = `${emp.role} • ${emp.dept} (${emp.id})`;
        if (avatarEl) avatarEl.innerText = emp.avatar;

        if (profileDrawer) profileDrawer.classList.remove('hidden');
        if (drawerBackdrop) drawerBackdrop.classList.remove('hidden');
    }

    function closeProfileDrawer() {
        if (profileDrawer) profileDrawer.classList.add('hidden');
        if (drawerBackdrop) drawerBackdrop.classList.add('hidden');
    }

    if (closeBtn) closeBtn.addEventListener('click', closeProfileDrawer);
    if (closeFooterBtn) closeFooterBtn.addEventListener('click', closeProfileDrawer);
    if (drawerBackdrop) drawerBackdrop.addEventListener('click', closeProfileDrawer);

    // Profile Drawer Tabs
    const drawerTabs = document.querySelectorAll('.drawer-tab');
    const drawerPanes = document.querySelectorAll('.drawer-pane');

    drawerTabs.forEach(tab => {
        tab.addEventListener('click', function () {
            const targetPane = tab.getAttribute('data-tab');

            drawerTabs.forEach(t => t.classList.remove('active'));
            tab.classList.add('active');

            drawerPanes.forEach(pane => {
                if (pane.id === `pane-${targetPane}`) {
                    pane.classList.add('active');
                } else {
                    pane.classList.remove('active');
                }
            });
        });
    });

    // Add Employee Modal Logic
    const btnAddEmployee = document.getElementById('btnAddEmployee');
    const addModalBackdrop = document.getElementById('addEmployeeModalBackdrop');
    const btnCloseAddModal = document.getElementById('btnCloseAddEmployeeModal');
    const btnCancelAddEmp = document.getElementById('btnCancelAddEmp');
    const addEmployeeForm = document.getElementById('addEmployeeForm');

    function openAddModal() {
        if (addModalBackdrop) addModalBackdrop.classList.remove('hidden');
    }

    function closeAddModal() {
        if (addModalBackdrop) addModalBackdrop.classList.add('hidden');
    }

    if (btnAddEmployee) btnAddEmployee.addEventListener('click', openAddModal);
    if (btnCloseAddModal) btnCloseAddModal.addEventListener('click', closeAddModal);
    if (btnCancelAddEmp) btnCancelAddEmp.addEventListener('click', closeAddModal);

    if (addEmployeeForm) {
        addEmployeeForm.addEventListener('submit', function (e) {
            e.preventDefault();
            const name = document.getElementById('newEmpName').value;
            const empId = document.getElementById('newEmpId').value;
            const email = document.getElementById('newEmpEmail').value;
            const dept = document.getElementById('newEmpDept').value;
            const role = document.getElementById('newEmpRole').value;

            const initials = name.split(' ').map(n => n[0]).join('').toUpperCase().substring(0, 2) || 'EM';

            const newEmp = {
                id: empId || `EMP-${Math.floor(8000 + Math.random() * 900)}`,
                name: name,
                role: role,
                dept: dept,
                email: email,
                status: 'Active',
                date: 'Today',
                avatar: initials
            };

            employees.unshift(newEmp);
            renderTable(employees);
            closeAddModal();
            addEmployeeForm.reset();

            // Show Toast Alert
            showToast(`✅ Employee profile for "${name}" created successfully!`);
        });
    }

    function showToast(message) {
        const toastContainer = document.getElementById('toastContainer');
        if (!toastContainer) return;
        const toast = document.createElement('div');
        toast.className = 'toast';
        toast.style.cssText = 'background:#09090b; color:#ffffff; padding:12px 20px; border-radius:8px; font-weight:600; font-size:13px; margin-top:10px; box-shadow:0 4px 12px rgba(0,0,0,0.15);';
        toast.innerText = message;
        toastContainer.appendChild(toast);
        setTimeout(() => {
            toast.remove();
        }, 3500);
    }

    // Initial render
    renderTable(employees);
});
