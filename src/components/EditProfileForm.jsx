import React from 'react';
import { Route, Routes, useLocation } from 'react-router-dom';


export default function EditProfileForm() {
    const location= useLocation();
	const { state }= location;
	const userId = state.id;

    const requestUrl = `http://localhost:3000/update?id=${userId}`;

	return (
		<html className="EditForm"> 
            <form action={requestUrl} method="post">
            Name: <input name="name"/>  <p> </p>
            College: <input name="college"/> <p> </p>
            Email: <input name="email"/> <p> </p>
            Phone Number: <input name="phone"/> <p> </p>
            <input type="submit" value="Submit form!"/>
            </form>
        </html>
	)
}