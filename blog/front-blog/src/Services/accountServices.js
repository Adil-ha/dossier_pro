let saveToken = (token) => {
  localStorage.setItem("token", token);
};

let logout = () => {
  localStorage.removeItem("token");
};

let isLogged = () => {
  let token = localStorage.getItem("token");
  return !!token;
};

let getToken = () => {
  let token = localStorage.getItem("token");
  const headers = {};

  if (token) {
    headers["Authorization"] = "Bearer " + token;
  }
  return headers;
};

export const accountService = {
  saveToken,
  logout,
  isLogged,
  getToken,
};
