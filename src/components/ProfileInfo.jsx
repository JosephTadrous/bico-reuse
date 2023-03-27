import React from 'react';
import HomePostList from './HomePostList';

export default function ProfileInfo({user}) {
	return (
		<>
			<div className="ProfileInfo">
				<h1>{user.name}</h1>
				<h3>College: {user.college}</h3>
				<h3>Email: {user.email}</h3>
				<h3>Phone: {user.phone}</h3>
				<h2>User's Post History: </h2>
			</div>

			{(user.history) ? ((user.history.length !== 0) ? 
				<HomePostList posts={user.history} /> :
				<h2>User does not have any posts</h2>) :
			 <></>}
		</>
	)
}