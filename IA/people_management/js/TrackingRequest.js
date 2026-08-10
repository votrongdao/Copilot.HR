/**
 * HR Platform - Tracking Request Workflow Logic
 */
document.addEventListener('DOMContentLoaded', function () {
    console.log('TrackingRequest JS initialized');

    const btnApprove = document.getElementById('btnApproveReq');
    const btnReject = document.getElementById('btnRejectReq');
    const btnUrge = document.getElementById('btnUrgeReminder');
    const currentStageText = document.getElementById('currentStageText');

    if (btnApprove) {
        btnApprove.addEventListener('click', function () {
            if (confirm('Are you sure you want to approve this request?')) {
                if (currentStageText) currentStageText.innerText = 'Completed (Approved)';
                alert('Request REQ-1092 has been approved successfully.');
            }
        });
    }

    if (btnReject) {
        btnReject.addEventListener('click', function () {
            const reason = prompt('Please enter reason for rejection:');
            if (reason) {
                if (currentStageText) currentStageText.innerText = 'Rejected';
                alert('Request REQ-1092 has been rejected.');
            }
        });
    }

    if (btnUrge) {
        btnUrge.addEventListener('click', function () {
            alert('Reminder notification email sent to HR Approver.');
        });
    }
});
