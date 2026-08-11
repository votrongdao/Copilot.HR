/**
 * HR Platform - Unified Sidebar Navigation Component
 */
(function () {
    if (!document.getElementById('hr-sidebar-styles')) {
        const style = document.createElement('style');
        style.id = 'hr-sidebar-styles';
        style.textContent = `
            .sidebar {
                width: 260px; background: #ffffff; border-right: 1px solid #e4e4e7;
                padding: 20px 16px; display: flex; flex-direction: column; justify-content: space-between;
                flex-shrink: 0; height: 100vh; position: sticky; top: 0; z-index: 100; box-sizing: border-box;
                font-family: 'Inter', -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
            }
            .sidebar-top { display: flex; flex-direction: column; overflow-y: auto; padding-right: 2px; }
            .sidebar-top::-webkit-scrollbar { width: 4px; }
            .sidebar-top::-webkit-scrollbar-thumb { background: #e4e4e7; border-radius: 4px; }
            .sidebar-logo { font-size: 17px; font-weight: 700; color: #09090b; margin-bottom: 24px; padding: 0 8px; display: flex; align-items: center; gap: 12px; letter-spacing: -0.3px; user-select: none; cursor: pointer; }
            .sidebar-logo-icon { width: 34px; height: 34px; border: 2px solid #09090b; border-radius: 8px; display: flex; align-items: center; justify-content: center; font-weight: 800; font-size: 13px; background: #ffffff; color: #09090b; box-shadow: 0 1px 2px rgba(0,0,0,0.05); }
            .sidebar-menu { list-style: none; padding: 0; margin: 0; display: flex; flex-direction: column; gap: 4px; }
            .sidebar-menu-group { display: flex; flex-direction: column; }
            .sidebar-menu-item { padding: 10px 12px; border-radius: 10px; cursor: pointer; color: #52525b; font-weight: 500; font-size: 13.5px; transition: all 0.15s ease; display: flex; align-items: center; justify-content: space-between; user-select: none; text-decoration: none; border: 1px solid transparent; }
            .sidebar-menu-item-left { display: flex; align-items: center; gap: 12px; }
            .sidebar-menu-item svg { width: 18px; height: 18px; stroke: #71717a; stroke-width: 2; fill: none; transition: stroke 0.15s ease; flex-shrink: 0; }
            .sidebar-menu-item:hover { background: #f4f4f5; color: #09090b; }
            .sidebar-menu-item:hover svg { stroke: #09090b; }
            .sidebar-menu-item.active { background: #09090b; color: #ffffff; font-weight: 600; }
            .sidebar-menu-item.active svg { stroke: #ffffff; }
            .sidebar-menu-item.active .sidebar-chevron { stroke: #ffffff; }
            .sidebar-chevron { width: 14px; height: 14px; stroke: #71717a; fill: none; stroke-width: 2; transition: transform 0.2s ease; margin-left: auto; }
            .sidebar-menu-group.collapsed .sidebar-submenu { display: none; }
            .sidebar-menu-group.collapsed .sidebar-chevron { transform: rotate(-90deg); }
            .sidebar-submenu { list-style: none; padding-left: 18px; margin: 4px 0 8px 0; display: flex; flex-direction: column; gap: 3px; position: relative; }
            .sidebar-submenu::before { content: ''; position: absolute; left: 12px; top: 4px; bottom: 4px; width: 1.5px; background: #e4e4e7; }
            .sidebar-submenu-item { padding: 8px 12px; border-radius: 6px; font-size: 12.5px; font-weight: 500; color: #52525b; text-decoration: none; display: flex; align-items: center; gap: 10px; transition: all 0.15s ease; position: relative; }
            .sidebar-submenu-item svg { width: 15px; height: 15px; stroke: #71717a; fill: none; stroke-width: 2; flex-shrink: 0; }
            .sidebar-submenu-item:hover { color: #09090b; background: #f4f4f5; }
            .sidebar-submenu-item:hover svg { stroke: #09090b; }
            .sidebar-submenu-item.active { color: #09090b; font-weight: 700; background: #f4f4f5; box-shadow: inset 3px 0 0 #09090b; }
            .sidebar-submenu-item.active svg { stroke: #09090b; }

            .sidebar-footer { position: relative; padding-top: 14px; border-top: 1px solid #e4e4e7; margin-top: 12px; }
            .user-profile-bar { display: flex; align-items: center; gap: 10px; padding: 8px 10px; border-radius: 10px; border: 1px solid #e4e4e7; cursor: pointer; transition: all 0.15s ease; background: #ffffff; user-select: none; }
            .user-profile-bar:hover { background: #f4f4f5; border-color: #d4d4d8; }
            .user-avatar-sq { width: 36px; height: 36px; background: #09090b; color: #ffffff; border-radius: 8px; font-weight: 700; font-size: 13px; display: flex; align-items: center; justify-content: center; flex-shrink: 0; }
            .user-profile-info { display: flex; flex-direction: column; overflow: hidden; }
            .user-profile-name { font-size: 13px; font-weight: 600; color: #09090b; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
            .user-profile-role { font-size: 11px; color: #71717a; }
            .user-profile-bar .profile-chevron { width: 14px; height: 14px; stroke: #71717a; fill: none; stroke-width: 2; margin-left: auto; flex-shrink: 0; }
            .user-dropdown-menu { position: absolute; bottom: 60px; left: 0; right: 0; background: #ffffff; border: 1px solid #e4e4e7; border-radius: 10px; box-shadow: 0 4px 16px rgba(0,0,0,0.1); padding: 6px; display: none; flex-direction: column; gap: 2px; z-index: 120; }
            .user-dropdown-menu.show { display: flex; }
            .user-dropdown-item { padding: 8px 12px; font-size: 12.5px; color: #09090b; border-radius: 6px; cursor: pointer; display: flex; align-items: center; gap: 8px; transition: background 0.15s; text-decoration: none; }
            .user-dropdown-item:hover { background: #f4f4f5; }
            .user-dropdown-item.danger { color: #ef4444; }
            .user-dropdown-item.danger:hover { background: #fef2f2; }
            .sidebar-toast-msg { position: fixed; bottom: 24px; right: 24px; background: #09090b; color: #ffffff; padding: 12px 18px; border-radius: 8px; font-size: 13px; font-weight: 500; box-shadow: 0 4px 14px rgba(0,0,0,0.18); z-index: 9999; display: flex; align-items: center; gap: 10px; opacity: 0; transform: translateY(10px); transition: all 0.25s ease; pointer-events: none; }
            .sidebar-toast-msg.show { opacity: 1; transform: translateY(0); }
        `;
        document.head.appendChild(style);
    }

    function initSidebar() {
        const container = document.getElementById('sidebarContainer') || document.querySelector('.sidebar');
        if (!container) return;

        const activeMain = container.getAttribute('data-active') || 'People';
        const activeSubpage = container.getAttribute('data-subpage') || '';
        
        let isSubfolder = container.getAttribute('data-is-subfolder');
        if (isSubfolder === null) {
            isSubfolder = window.location.pathname.includes('/IA/people_management/');
        } else {
            isSubfolder = (isSubfolder === 'true');
        }

        const basePath = isSubfolder ? './' : './IA/people_management/';
        const homePath = isSubfolder ? '../../index.html' : './index.html';

        const routes = {
            'Home': homePath,
            'Employee Directory': `${basePath}EmployeeDirectory.html`,
            'Organization & Department': `${basePath}OrgDepartment.html`,
            'Request': `${basePath}RequestManagement.html`
        };

        const isPeopleActive = activeMain === 'People' || activeMain === 'People Management';
        const isOrgDomain = ['Organization & Department', 'Team Management', 'Position Management', 'Reporting Lines'].includes(activeSubpage);

        container.innerHTML = `
            <div class="sidebar-top">
                <div class="sidebar-logo" id="sidebarLogo" title="Go to Dashboard">
                    <div class="sidebar-logo-icon">HR</div>
                    <span>HR Platform</span>
                </div>

                <ul class="sidebar-menu">
                    <!-- 1. Home -->
                    <li>
                        <a href="${routes['Home']}" class="sidebar-menu-item ${activeMain === 'Home' ? 'active' : ''}">
                            <div class="sidebar-menu-item-left">
                                <svg viewBox="0 0 24 24"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/><polyline points="9 22 9 12 15 12 15 22"/></svg>
                                <span>Home</span>
                            </div>
                        </a>
                    </li>

                    <!-- 2. Recruitment -->
                    <li>
                        <a href="javascript:void(0)" class="sidebar-menu-item ${activeMain === 'Recruitment' ? 'active' : ''}" data-module="Recruitment">
                            <div class="sidebar-menu-item-left">
                                <svg viewBox="0 0 24 24"><circle cx="12" cy="12" r="10"/><circle cx="12" cy="12" r="6"/><circle cx="12" cy="12" r="2"/></svg>
                                <span>Recruitment</span>
                            </div>
                        </a>
                    </li>

                    <!-- 3. Onboard / Offboard (Group with subitems Onboarding, Offboarding) -->
                    <li class="sidebar-menu-group ${activeMain === 'Onboard / Offboard' ? '' : 'collapsed'}">
                        <div class="sidebar-menu-item ${activeMain === 'Onboard / Offboard' ? 'active' : ''}" class="toggle-group-btn">
                            <div class="sidebar-menu-item-left">
                                <svg viewBox="0 0 24 24"><path d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="8.5" cy="7" r="4"/><line x1="20" y1="8" x2="20" y2="14"/><line x1="17" y1="11" x2="23" y2="11"/></svg>
                                <span>Onboard / Offboard</span>
                            </div>
                            <svg class="sidebar-chevron" viewBox="0 0 24 24"><polyline points="6 9 12 15 18 9"/></svg>
                        </div>
                        <ul class="sidebar-submenu">
                            <li>
                                <a href="javascript:void(0)" class="sidebar-submenu-item ${activeSubpage === 'Onboarding' ? 'active' : ''}">
                                    <svg viewBox="0 0 24 24"><polyline points="9 11 12 14 22 4"/><path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"/></svg>
                                    <span>Onboarding</span>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0)" class="sidebar-submenu-item ${activeSubpage === 'Offboarding' ? 'active' : ''}">
                                    <svg viewBox="0 0 24 24"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><polyline points="16 17 21 12 16 7"/><line x1="21" y1="12" x2="9" y2="12"/></svg>
                                    <span>Offboarding</span>
                                </a>
                            </li>
                        </ul>
                    </li>

                    <!-- 4. People (Group with subitems Employee Directory, Org & Department, Request) -->
                    <li class="sidebar-menu-group ${isPeopleActive ? '' : 'collapsed'}">
                        <div class="sidebar-menu-item ${isPeopleActive ? 'active' : ''}" class="toggle-group-btn">
                            <div class="sidebar-menu-item-left">
                                <svg viewBox="0 0 24 24"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>
                                <span>People</span>
                            </div>
                            <svg class="sidebar-chevron" viewBox="0 0 24 24"><polyline points="6 9 12 15 18 9"/></svg>
                        </div>
                        
                        <ul class="sidebar-submenu">
                            <li>
                                <a href="${routes['Employee Directory']}" class="sidebar-submenu-item ${activeSubpage === 'Employee Directory' ? 'active' : ''}">
                                    <svg viewBox="0 0 24 24"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
                                    <span>Employee Directory</span>
                                </a>
                            </li>

                            <li>
                                <a href="${routes['Organization & Department']}" class="sidebar-submenu-item ${isOrgDomain ? 'active' : ''}">
                                    <svg viewBox="0 0 24 24"><rect x="2" y="7" width="20" height="14" rx="2" ry="2"/><path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"/></svg>
                                    <span>Org & Department</span>
                                </a>
                            </li>

                            <li>
                                <a href="${routes['Request']}" class="sidebar-submenu-item ${activeSubpage === 'Request Management' || activeSubpage === 'Request' ? 'active' : ''}">
                                    <svg viewBox="0 0 24 24"><polyline points="9 11 12 14 22 4"/><path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"/></svg>
                                    <span>Request</span>
                                </a>
                            </li>
                        </ul>
                    </li>

                    <!-- 5. Workforce -->
                    <li>
                        <a href="javascript:void(0)" class="sidebar-menu-item ${activeMain === 'Workforce' ? 'active' : ''}" data-module="Workforce">
                            <div class="sidebar-menu-item-left">
                                <svg viewBox="0 0 24 24"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"/><line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/><line x1="3" y1="10" x2="21" y2="10"/></svg>
                                <span>Workforce</span>
                            </div>
                        </a>
                    </li>

                    <!-- 6. Project -->
                    <li>
                        <a href="javascript:void(0)" class="sidebar-menu-item ${activeMain === 'Project' ? 'active' : ''}" data-module="Project">
                            <div class="sidebar-menu-item-left">
                                <svg viewBox="0 0 24 24"><polygon points="12 2 2 7 12 12 22 7 12 2"/><polyline points="2 17 12 22 22 17"/><polyline points="2 12 12 17 22 12"/></svg>
                                <span>Project</span>
                            </div>
                        </a>
                    </li>

                    <!-- 7. Performance -->
                    <li>
                        <a href="javascript:void(0)" class="sidebar-menu-item ${activeMain === 'Performance' ? 'active' : ''}" data-module="Performance">
                            <div class="sidebar-menu-item-left">
                                <svg viewBox="0 0 24 24"><line x1="18" y1="20" x2="18" y2="10"/><line x1="12" y1="20" x2="12" y2="4"/><line x1="6" y1="20" x2="6" y2="14"/></svg>
                                <span>Performance</span>
                            </div>
                        </a>
                    </li>

                    <!-- 8. Payroll -->
                    <li>
                        <a href="javascript:void(0)" class="sidebar-menu-item ${activeMain === 'Payroll' ? 'active' : ''}" data-module="Payroll">
                            <div class="sidebar-menu-item-left">
                                <svg viewBox="0 0 24 24"><rect x="1" y="4" width="22" height="16" rx="2" ry="2"/><line x1="1" y1="10" x2="23" y2="10"/></svg>
                                <span>Payroll</span>
                            </div>
                        </a>
                    </li>

                    <!-- 9. Integration -->
                    <li>
                        <a href="javascript:void(0)" class="sidebar-menu-item ${activeMain === 'Integration' ? 'active' : ''}" data-module="Integration">
                            <div class="sidebar-menu-item-left">
                                <svg viewBox="0 0 24 24"><circle cx="12" cy="12" r="3"/><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"/></svg>
                                <span>Integration</span>
                            </div>
                        </a>
                    </li>
                </ul>
            </div>

            <!-- Footer Avatar Box & Dropdown Menu (Update, Help, Log Out) -->
            <div class="sidebar-footer">
                <div class="user-profile-bar" id="sidebarUserProfileBar" title="Click to view profile options">
                    <div class="user-avatar-sq">DK</div>
                    <div class="user-profile-info">
                        <span class="user-profile-name">Duong Khang</span>
                        <span class="user-profile-role">Dev</span>
                    </div>
                    <svg class="profile-chevron" viewBox="0 0 24 24"><polyline points="6 9 12 15 18 9"/></svg>
                </div>

                <div class="user-dropdown-menu" id="sidebarUserDropdown">
                    <a href="javascript:void(0)" class="user-dropdown-item" id="userMenuUpdate">
                        <svg viewBox="0 0 24 24" width="15" height="15" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 20h9"/><path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z"/></svg>
                        <span>Update</span>
                    </a>
                    <a href="javascript:void(0)" class="user-dropdown-item" id="userMenuHelp">
                        <svg viewBox="0 0 24 24" width="15" height="15" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M9.09 9a3 3 0 0 1 5.83 1c0 2-3 3-3 3"/><line x1="12" y1="17" x2="12.01" y2="17"/></svg>
                        <span>Help</span>
                    </a>
                    <a href="javascript:void(0)" class="user-dropdown-item danger" id="userMenuLogout">
                        <svg viewBox="0 0 24 24" width="15" height="15" fill="none" stroke="currentColor" stroke-width="2"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><polyline points="16 17 21 12 16 7"/><line x1="21" y1="12" x2="9" y2="12"/></svg>
                        <span>Log Out</span>
                    </a>
                </div>
            </div>
        `;

        // Logo Click Navigation
        const logoBtn = container.querySelector('#sidebarLogo');
        if (logoBtn) {
            logoBtn.addEventListener('click', function () {
                window.location.href = routes['Home'];
            });
        }

        // Accordion Groups Toggle (Onboard / Offboard, People)
        const menuGroups = container.querySelectorAll('.sidebar-menu-group');
        menuGroups.forEach(group => {
            const toggleItem = group.querySelector('.sidebar-menu-item');
            if (toggleItem) {
                toggleItem.addEventListener('click', function (e) {
                    e.stopPropagation();
                    group.classList.toggle('collapsed');
                });
            }
        });

        // User Avatar Dropdown Toggle Logic
        const userProfileBar = container.querySelector('#sidebarUserProfileBar');
        const userDropdown = container.querySelector('#sidebarUserDropdown');

        if (userProfileBar && userDropdown) {
            userProfileBar.addEventListener('click', function (e) {
                e.stopPropagation();
                userDropdown.classList.toggle('show');
            });

            document.addEventListener('click', function (e) {
                if (!userProfileBar.contains(e.target) && !userDropdown.contains(e.target)) {
                    userDropdown.classList.remove('show');
                }
            });
        }

        // Toast Notifications Helper
        function showToast(msg) {
            let toast = document.getElementById('sidebarToastMsg');
            if (!toast) {
                toast = document.createElement('div');
                toast.id = 'sidebarToastMsg';
                toast.className = 'sidebar-toast-msg';
                document.body.appendChild(toast);
            }
            toast.innerHTML = `<svg viewBox="0 0 24 24" width="16" height="16" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><line x1="12" y1="16" x2="12" y2="12"/><line x1="12" y1="8" x2="12.01" y2="8"/></svg> ${msg}`;
            toast.classList.add('show');
            setTimeout(() => { toast.classList.remove('show'); }, 2500);
        }

        const updateBtn = container.querySelector('#userMenuUpdate');
        if (updateBtn) updateBtn.addEventListener('click', () => showToast('Update profile clicked'));

        const helpBtn = container.querySelector('#userMenuHelp');
        if (helpBtn) helpBtn.addEventListener('click', () => showToast('Help center opened'));

        const logoutBtn = container.querySelector('#userMenuLogout');
        if (logoutBtn) logoutBtn.addEventListener('click', () => showToast('Logging out...'));
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initSidebar);
    } else {
        initSidebar();
    }
})();
