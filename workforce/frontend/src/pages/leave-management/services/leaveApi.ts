export const getLeaveBalance = async () => {
  const response = await api.get("/employees/leave-balance");
  return response.data;
};
