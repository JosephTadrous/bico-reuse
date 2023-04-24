import React from 'react';
import {useState, useEffect } from 'react';
import UserList from './UserList';
import './UserPage.css';

export default function UserPage() {
  const [users, setUsers]= useState([]);

  useEffect( () => {
		fetch('http://localhost:3000/users')
		.then((res) => res.json())
		.then((users) => {
			setUsers(users);
		});
	}, []);

  return (
		
    <div className="UserPage">
			<h1>Users</h1>
			<UserList users={users} />
		</div>
  )
}
