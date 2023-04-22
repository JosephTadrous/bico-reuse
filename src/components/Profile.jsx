import React from 'react';
import { useState, useEffect } from 'react';
import { Route, Routes, useLocation } from 'react-router-dom';
import ProfileInfo from './ProfileInfo';
import {NavLink} from 'react-router-dom';
import "./Profile.css"


export default function Profile() {
	const [user, setUser] = useState(null);

	const location= useLocation();
	const { state }= location;
	const userId = state.userId;
	
	useEffect( () => {
		console.log(userId);
		fetch('http://localhost:3000/profile?id=' + userId) // TODO: dynamic id
		.then((res) => res.json())
		.then((user) => {
			setUser(user);
		});
	}, [location]);

	// do not render if user is not there yet
	if (!user) return <></>
	return (
		<div className="Profile">
			<ProfileInfo user={user} />
			
			<div className="Buttons">
				<form action="http://localhost:3000/delete_user" method="post">
					<input type="hidden" name="id" value={user._id} />
					<input type="hidden" name="user_posts" 
						value={ user.history.map((post) => post._id) } 
					/>
					<button className= "DeleteButton">Delete User</button>
				</form>
				<NavLink
					to={'/editProfile'}
					state={{user: user}}>
					<button className= "EditButton">Edit User</button>
				</NavLink>
			</div>
		</div>
	)
}
