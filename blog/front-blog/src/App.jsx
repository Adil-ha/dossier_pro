import { Link, NavLink, Outlet, useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { accountService } from './Services/accountServices';
import { logout } from './features/LoginSlice';

function App() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const isLoggedIn = accountService.isLogged();

  const handleLogout = () => {
    console.log(accountService.getToken());
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
            <ul className="navbar-nav me-auto">
              <li className="nav-item dropdown">
                <NavLink 
                  className="nav-link dropdown-toggle" 
                  to="#"
                  role="button" 
                  data-bs-toggle="dropdown" 
                  aria-haspopup="true" 
                  aria-expanded="false">
                  Categorie
                </NavLink>
                <div className="dropdown-menu">
                  <NavLink className="dropdown-item" to="#">Action</NavLink>
                  <NavLink className="dropdown-item" to="#">Another action</NavLink>
                  <NavLink className="dropdown-item" to="#">Something else here</NavLink>
                </div>
              </li>
            </ul>
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
              <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse justify-content-end" id="navbarColor01">
              <form className="d-flex">
                {isLoggedIn ? (
                  <button className="btn btn-primary" onClick={handleLogout}>Déconnexion</button>
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



