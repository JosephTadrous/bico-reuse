import React from 'react';
import { useState, useEffect } from 'react';
import { Route, Routes, useLocation } from 'react-router-dom';
import ProfileInfo from './ProfileInfo';
import {NavLink} from 'react-router-dom';
import "./Profile.css"


export default function Profile() {
	const [user, setUser] = useState([]);

	const location= useLocation();
	const { state }= location;
	const userId = state.sellerId;

	useEffect( () => {
		console.log(userId);
		fetch('http://localhost:3000/profile?id=' + userId) // TODO: dynamic id
		.then((res) => res.json())
		.then((user) => {
			setUser(user);
		});
	}, [location]);

	return (
		<div className="Profile">
			<ProfileInfo user={user} />
			<NavLink 
				to={'/editProfile'}
				state={{user: user}}>
				<button className= "EditButton">Edit User</button>
			</NavLink>
			
		</div>
	)
}
