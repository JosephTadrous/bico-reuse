import React from 'react';
import {useState, useEffect } from 'react';
import UserList from './UserList';

export default function UserPage() {
  const [users, setUsers]= useState([]);

  useEffect( () => {
		fetch('http://localhost:3000/users')
		.then((res) => res.json())
		.then((users) => {
			setUsers(users);
		});
	});

  return (
    <UserList users={users} />
  )
}
