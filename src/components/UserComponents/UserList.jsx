import React from 'react';
import User from './User';

export default function UserList({users}) {
  return (
  <ul className="PostLinks">
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
