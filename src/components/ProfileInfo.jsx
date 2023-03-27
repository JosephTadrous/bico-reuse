import React from 'react';


export default function ProfileInfo({user}) {
	return (
		<div className="ProfileInfo">
			<h1>{user.name}</h1>
            <h3>College: {user.college}</h3>
            <h3>Email: {user.email}</h3>
            <h3>Phone: {user.phone}</h3>
		</div>
	)
}