/**
 * HR Platform - Organization & Department Logic
 */
document.addEventListener('DOMContentLoaded', function () {
    console.log('OrgDepartment JS initialized');

    // View Switcher (Tree View vs Table View)
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

    // Zoom Canvas Control
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
});
