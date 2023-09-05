import Home from "../pages/Home";
import {Navigate} from "react-router-dom";
import Athletes from "../pages/Athletes";
import Events from "../pages/Events";
import AthletesNew from "../pages/AthletesNew";
import Login from "../components/auth/Login";
import Register from "../components/auth/Register";
import EventRaces from "../pages/EventRaces";
import Results from "../pages/Results";
import Leaderboard from "../pages/Leaderboard";
import CreatingTeam from "../pages/CreatingTeam";
import TeamOverview from "../pages/TeamOverview";
import AdminPanel from "../pages/admin-pages/AdminPanel";
import UploadRace from "../pages/admin-pages/UploadRace";
import UsersManage from "../pages/admin-pages/UsersManage";
import EventCreateForm from "../components/common/event/EventCreateForm";

export const guestRoutes = [
    {path: '/', element: <Home/>, exact: false},
    {path: '/athletes', element: <Athletes/>, exact: false},
    {path: '/events', element: <Events/>, exact: false},
    {path: '/athletess', element: <AthletesNew/>, exact: false},
    {path: '/register', element: <Register/>, exact: false},
    {path: '/event-races/:id', element: <EventRaces/>, exact: true},
    {path: '/results/:id', element: <Results/>, exact: true},
    {path: '/leaderboard/:id', element: <Leaderboard/>, exact: true},
    {path: '/team/:id', element: <TeamOverview/>, exact: true}
    //{path: '', element: <Navigate to="/posts"/>, exact: false, index: true},
];

export const userRoutes = [
    ...guestRoutes,
    {path: '/create-team/:id', element: <CreatingTeam/>, exact: true}
];

export const adminRoutes = [
    ...userRoutes,
    {path: '/admin-panel', element: <AdminPanel/>, exact: false},
    {path: '/admin/upload', element: <UploadRace/>, exact: false},
    {path: '/admin/users', element: <UsersManage/>, exact: false},
    {path: '/admin/create-event', element: <EventCreateForm/>, exact: false}
];