import React from 'react';
import { NavLink } from 'react-router-dom';
import './User.css';


export default function User({user, index}) {
    
  return (
    <div className="User">
			<h3>{index}) </h3>
      <NavLink className="UserLink" 
				to={'/profile'}
				state={{userId: user._id}}>
				<h3>{user.name}</h3>
			</NavLink>
    </div>
  )
}
