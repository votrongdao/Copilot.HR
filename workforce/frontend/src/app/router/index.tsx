import React from 'react'
import MainLayout from '../../layouts/main-layout/MainLayout'
import { createBrowserRouter } from 'react-router-dom'
import LeaveManagementPage from '../../pages/leave-management/LeaveManagementPage'

export const router = createBrowserRouter([
  {
    path: "/",
    element: <MainLayout/>,
    children: [
        {
            path: "leave-management",
            element: <LeaveManagementPage/>
        }
    ]
  }
])


