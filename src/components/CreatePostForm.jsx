import React from 'react';
import {useNavigate} from 'react-router-dom';
import './CreatePostForm.css';
import { useState, useEffect } from 'react';


export default function CreatePostForm({post}) {

	const navigate = useNavigate();
	const clickHandler = () => {
			navigate("/");
	}

	return (
		<div className="EditForm">
			<h1>Create Post</h1>
			<form className="InputForm" action="http://localhost:3000/create_post" method="post">
        <div className="UserIdInput"><div className="TextContainer">User ID:</div> <input name="user_id"/></div> 
        <div className="TitleInput"><div className="TextContainer">Title:</div> <input name="title"/></div>  
        <div className="DescriptionInput"><div className="TextContainer">Description:</div> <input name="description"/></div> 
        <div className="PriceInput"><div className="TextContainer">Price:</div> <input name="price"/></div> 
        <div className="PhotoInput"><div className="TextContainer">Image:</div> <input name="photo"/></div> 
				<div className="Buttons">
					<button onClick={clickHandler} className="CancelButton" type="submit">Cancel</button>
					<input className="EditButton" type="submit" value="Submit" href="http://localhost:5173"/>
				</div>
			</form>
    </div>
	)
}
