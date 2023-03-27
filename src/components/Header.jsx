import React from 'react';
import { NavLink } from 'react-router-dom';
import PFP from "../assets/profile.svg";
import "./Header.css";



export default function Header() {
	return (
		<div className="Header">
			<NavLink className="logo" to={'/'}>
				BiCo-Reuse
			</NavLink>

			<div className="RightLinks">
				<NavLink className="CreateButton" to={'/create'}>
					<h3>+ New Post</h3>
				</NavLink>
				
				{/* <NavLink to={'/profile'}>
					<img className="pfp" src={PFP} alt="PFP" />
				</NavLink>  */}
			</div>
			
		</div>
	)
}
