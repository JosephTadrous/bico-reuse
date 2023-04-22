import React from 'react';
import { NavLink } from 'react-router-dom';


export default function User({user}) {
    
  return (
    <div className="User">
      <NavLink 
				to={'/profile'}
				state={{userId: user._id}}>
				<h3>{user.name}</h3>
			</NavLink>
    </div>
  )
}
