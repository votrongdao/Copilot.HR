/**
 * HR Platform - Employee Request Management Logic
 */
document.addEventListener('DOMContentLoaded', function () {
    console.log('RequestManagement JS initialized');

    const statusTabs = document.querySelectorAll('#statusTabs .tab-btn');
    const tableRows = document.querySelectorAll('#requestsTableBody tr');

    statusTabs.forEach(tab => {
        tab.addEventListener('click', function () {
            const statusFilter = tab.getAttribute('data-status');

            statusTabs.forEach(t => t.classList.remove('active'));
            tab.classList.add('active');

            tableRows.forEach(row => {
                const rowStatus = row.getAttribute('data-status');
                if (statusFilter === 'all' || rowStatus === statusFilter) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        });
    });

    // Quick Approve Action
    const approveBtns = document.querySelectorAll('.btn-quick-approve');
    approveBtns.forEach(btn => {
        btn.addEventListener('click', function (e) {
            e.stopPropagation();
            const row = btn.closest('tr');
            if (row) {
                const statusPill = row.querySelector('.status-pill');
                if (statusPill) {
                    statusPill.className = 'status-pill status-approved';
                    statusPill.innerHTML = '<span class="status-dot"></span> Approved';
                }
                row.setAttribute('data-status', 'approved');
                btn.remove();
            }
        });
    });
});
