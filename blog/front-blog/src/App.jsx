import React from 'react';
import { Link, NavLink, Outlet, useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { accountService } from './Services/accountServices';
import { logout } from './features/LoginSlice';

function App() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const isLoggedIn = accountService.isLogged();
  const currentUser = accountService.getCurrentUser(); // Ajout de cette ligne

  const handleLogout = () => {
    dispatch(logout());
    accountService.logout();
    navigate('/');
  };

  return (
    <>
      <header>
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
          <div className="container-fluid">
            <Link className="navbar-brand" to="/">Blog</Link>
          
            <div className="collapse navbar-collapse justify-content-end" id="navbarColor01">
              <div className="navbar-text me-3">
                {isLoggedIn && currentUser ? `Bienvenue, ${currentUser.name}` : ''}
              </div>
              <form className="d-flex">
                {isLoggedIn ? (
                  <>
                    <button className="btn btn-primary me-2" onClick={handleLogout}>Déconnexion</button>
                  </>
                ) : (
                  <>
                    <NavLink className="btn btn-primary me-2" to="/login">Login</NavLink>
                    <NavLink className="btn btn-primary" to="/register">Register</NavLink>
                  </>
                )}
              </form>
            </div>
          </div>
        </nav>
      </header>
      <main>
        <Outlet />
      </main>
    </>
  );
}

export default App;




