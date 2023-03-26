import React from 'react';
import { useState, useEffect } from 'react';
import ProfileInfo from './ProfileInfo';

export default function Profile() {
	const [user, setUser] = useState([]);

	useEffect( () => {
		fetch('http://localhost:3000/profile?id=64186a1d476ee3e3a7151640') // TODO: dynamic id
		.then((res) => res.json())
		.then((user) => {
			setUser(user);
		});
	}, []);

	return (
		<div className="Profile}">
			<ProfileInfo user={user} />
		</div>
	)
}
