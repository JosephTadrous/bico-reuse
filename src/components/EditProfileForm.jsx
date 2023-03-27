import React from 'react';
import { Route, Routes, useLocation } from 'react-router-dom';


export default function EditProfileForm() {
    const location= useLocation();
	const { state }= location;
	const user = state.user;

    const requestUrl = `http://localhost:3000/update?id=${user._id}`;

	return (
		<html className="EditForm"> 
            <form action={requestUrl} method="post">
            Name: <input name="name" defaultValue={user.name}/>  <p> </p>
            College: <input name="college" defaultValue={user.college}/> <p> </p>
            Email: <input name="email" defaultValue={user.email}/> <p> </p>
            Phone Number: <input name="phone" defaultValue={user.phone}/> <p> </p>
            <input type="submit" value="Submit form!"/>
            </form>
        </html>
	)
}