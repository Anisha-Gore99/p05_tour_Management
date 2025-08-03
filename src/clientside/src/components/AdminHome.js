import { Link } from 'react-router-dom';


export default function AdminHome(){
    return(
         <div>
           <header>Admin Dashboard</header>

            <nav>
                <Link to="/adminhome">Home</Link>
                <Link to="/approveagency">Approve Agency</Link>
                <Link to="/logout">Logout</Link>
            </nav>

            <section className="hero">
                <h1>Welcome, Admin!</h1>
                <p>Manage tours, approve agencies, and oversee the platform.</p>
            </section>

            <section>
                <h2>Quick Actions</h2>
                <div className="cards">
                    <div className="card">
                        <h4>Approve Agencies</h4>
                        <p>Verify and approve new travel agencies.</p>
                    </div>
                    <div className="card">
                        <h4>View Reports</h4>
                        <p>See system usage and tour booking reports.</p>
                    </div>
                    <div className="card">
                        <h4>Manage Users</h4>
                        <p>Oversee tourist and agency user accounts.</p>
                    </div>
                </div>
            </section>
        </div>
    )
}