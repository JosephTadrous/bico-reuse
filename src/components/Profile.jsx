import React from 'react';
import { useState, useEffect } from 'react';
import { Route, Routes, useLocation } from 'react-router-dom';
import ProfileInfo from './ProfileInfo';
import {NavLink} from 'react-router-dom';


export default function Profile() {
	const [user, setUser] = useState([]);

	const location= useLocation();
	const { state }= location;
	const userId = state.sellerId;

	useEffect( () => {
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
				state={{id: user._id}}>
				<button>Edit User</button>
			</NavLink>
			
		</div>
	)
}
