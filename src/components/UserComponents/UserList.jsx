import React from 'react';
import User from './User';
import './UserList.css';

export default function UserList({users}) {
  return (
  <ul className="UserList">
		{
			users.map((user) => {
				return <li key={user._id}> 
					<User user={user}/>
				</li>
			})
		}
	</ul>
  )
}
