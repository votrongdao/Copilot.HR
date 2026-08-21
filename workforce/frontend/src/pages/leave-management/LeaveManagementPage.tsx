import React from 'react'
import LeaveBalance from './components/LeaveBalance'
import TeamLeaveCalendar from './components/TeamLeaveCalendar'
import RecentLeaveManagement from './components/RecentLeaveManagement'

const LeaveManagementPage = () => {
  return (
    <div>
      <h1>Leave Management</h1>
      <p> View Balances, team schedules, and manage leave requests.</p>
      <div>
        <LeaveBalance/>
        <TeamLeaveCalendar/>
      </div>
      <RecentLeaveManagement/>
    </div>
  )
}

export default LeaveManagementPage
