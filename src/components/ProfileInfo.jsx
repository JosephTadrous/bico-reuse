import React from 'react';


export default function ProfileInfo({user}) {
	return (
		<div className="ProfileInfo">
			<h1>{user.name}</h1>
            <p>College: {user.college}</p>
			<h2>Contact Information:</h2>
            <p>Email: {user.email}</p>
            <p>Phone: {user.phone}</p>
		</div>
	)
}