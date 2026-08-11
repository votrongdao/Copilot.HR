/**
 * HR Platform - Organization & Department Logic
 */
document.addEventListener('DOMContentLoaded', function () {
    console.log('OrgDepartment JS initialized');

    // 1. View Switcher (Tree View vs Table View)
    const viewTabs = document.querySelectorAll('#viewModeTabs .tab-btn');
    const treePane = document.getElementById('treeView');
    const tablePane = document.getElementById('tableView');

    viewTabs.forEach(tab => {
        tab.addEventListener('click', function () {
            const targetView = tab.getAttribute('data-view');

            viewTabs.forEach(t => t.classList.remove('active'));
            tab.classList.add('active');

            if (targetView === 'treeView') {
                if (treePane) treePane.classList.add('active');
                if (tablePane) tablePane.classList.remove('active');
            } else {
                if (tablePane) tablePane.classList.add('active');
                if (treePane) treePane.classList.remove('active');
            }
        });
    });

    // 2. Zoom Canvas Control
    let currentZoom = 1;
    const treeContent = document.getElementById('orgTreeContent');
    const btnZoomIn = document.getElementById('btnZoomIn');
    const btnZoomOut = document.getElementById('btnZoomOut');

    if (btnZoomIn && treeContent) {
        btnZoomIn.addEventListener('click', function () {
            currentZoom = Math.min(currentZoom + 0.1, 1.5);
            treeContent.style.transform = `scale(${currentZoom})`;
            treeContent.style.transformOrigin = 'top center';
        });
    }

    if (btnZoomOut && treeContent) {
        btnZoomOut.addEventListener('click', function () {
            currentZoom = Math.max(currentZoom - 0.1, 0.6);
            treeContent.style.transform = `scale(${currentZoom})`;
            treeContent.style.transformOrigin = 'top center';
        });
    }

    // 3. Add Department Drawer / Modal Logic
    const btnCreateDept = document.getElementById('btnCreateDept');
    const deptDrawerOverlay = document.getElementById('deptDrawerOverlay');
    const btnCloseDrawer = document.getElementById('btnCloseDrawer');
    const btnCancelDrawer = document.getElementById('btnCancelDrawer');
    const deptForm = document.getElementById('deptForm');

    function openDeptDrawer() {
        if (deptDrawerOverlay) deptDrawerOverlay.classList.remove('hidden');
    }

    function closeDeptDrawer() {
        if (deptDrawerOverlay) deptDrawerOverlay.classList.add('hidden');
    }

    if (btnCreateDept) btnCreateDept.addEventListener('click', openDeptDrawer);
    if (btnCloseDrawer) btnCloseDrawer.addEventListener('click', closeDeptDrawer);
    if (btnCancelDrawer) btnCancelDrawer.addEventListener('click', closeDeptDrawer);

    if (deptForm) {
        deptForm.addEventListener('submit', function (e) {
            e.preventDefault();
            const deptName = document.getElementById('deptNameInput').value;
            const parentDept = document.getElementById('parentDeptSelect').options[document.getElementById('parentDeptSelect').selectedIndex].text;
            const deptLead = document.getElementById('deptLeadSelect').value;
            const location = document.getElementById('locationSelect').value;

            closeDeptDrawer();
            deptForm.reset();

            // Append to table if table exists
            const tbody = document.getElementById('rosterTableBody');
            if (tbody) {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>
                        <div class="dept-name-cell">
                            <div class="dept-icon-box">
                                <svg viewBox="0 0 24 24" fill="none" stroke-width="2">
                                    <rect x="2" y="7" width="20" height="14" rx="2" ry="2" />
                                    <path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16" />
                                </svg>
                            </div>
                            <span>${deptName}</span>
                        </div>
                    </td>
                    <td>${deptLead}</td>
                    <td>${parentDept}</td>
                    <td>${location}</td>
                    <td>1 Employee</td>
                    <td><span class="status-pill active">Active</span></td>
                    <td>
                        <div class="table-actions">
                            <button class="btn-wireframe btn-edit-dept">Edit</button>
                        </div>
                    </td>
                `;
                tbody.prepend(tr);
            }

            showToast(`✅ Department "${deptName}" created successfully!`);
        });
    }

    // 4. Drag & Drop in Org Tree -> PopUp Director Approval Modal
    const treeNodes = document.querySelectorAll('.tree-node');
    const approvalModalOverlay = document.getElementById('approvalModalOverlay');
    const btnCloseApprovalModal = document.getElementById('btnCloseApprovalModal');
    const approvalTitle = document.getElementById('approvalTitle');
    const approvalDesc = document.getElementById('approvalDesc');

    let draggedNodeTitle = '';

    treeNodes.forEach(node => {
        // Enable HTML5 Draggable
        node.setAttribute('draggable', 'true');
        node.style.cursor = 'grab';

        node.addEventListener('dragstart', function (e) {
            const titleEl = node.querySelector('.node-title');
            draggedNodeTitle = titleEl ? titleEl.innerText.trim() : 'Team Member / Position';
            e.dataTransfer.setData('text/plain', draggedNodeTitle);
            node.style.opacity = '0.5';
        });

        node.addEventListener('dragend', function () {
            node.style.opacity = '1';
        });

        node.addEventListener('dragover', function (e) {
            e.preventDefault();
            node.style.border = '2px dashed #09090b';
        });

        node.addEventListener('dragleave', function () {
            node.style.border = '';
        });

        node.addEventListener('drop', function (e) {
            e.preventDefault();
            node.style.border = '';

            const targetTitleEl = node.querySelector('.node-title');
            const targetNodeTitle = targetTitleEl ? targetTitleEl.innerText.trim() : 'Target Department';

            // Show Director Approval Pending Modal
            if (approvalTitle) {
                approvalTitle.innerText = 'Org Restructure Request Submitted';
            }
            if (approvalDesc) {
                approvalDesc.innerHTML = `
                    The personnel / position transfer of <strong>"${draggedNodeTitle}"</strong> to <strong>"${targetNodeTitle}"</strong> has been submitted successfully.<br><br>
                    Status: <strong>Pending approval from Director / CEO (Luu Duong)</strong>.
                `;
            }

            if (approvalModalOverlay) {
                approvalModalOverlay.classList.remove('hidden');
            }

            showToast(`📩 Transfer of "${draggedNodeTitle}" submitted for Director approval.`);
        });
    });

    if (btnCloseApprovalModal && approvalModalOverlay) {
        btnCloseApprovalModal.addEventListener('click', function () {
            approvalModalOverlay.classList.add('hidden');
        });
    }

    // Toast Notification Helper
    function showToast(message) {
        const toastContainer = document.getElementById('toastContainer');
        if (!toastContainer) return;
        const toast = document.createElement('div');
        toast.className = 'toast';
        toast.style.cssText = 'background:#09090b; color:#ffffff; padding:12px 20px; border-radius:8px; font-weight:600; font-size:13px; margin-top:10px; box-shadow:0 4px 12px rgba(0,0,0,0.15); z-index:9999;';
        toast.innerText = message;
        toastContainer.appendChild(toast);
        setTimeout(() => {
            toast.remove();
        }, 3500);
    }
});
