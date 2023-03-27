import React from 'react';
import {useNavigate} from 'react-router-dom';


export default function CreatePostForm({post}) {

	const navigate = useNavigate();
	const clickHandler = () => {
			navigate("/");
	}

	return (
		<div className="EditForm"> 
        <form action="http://localhost:3000/create_post" method="post" onsubmit='return false'>
        User ID: <input name="user_id"/> <p> </p>
        Title: <input name="title"/>  <p> </p>
        Description: <input name="description"/> <p> </p>
        Price: <input name="price"/> <p> </p>
        Image: <input name="photo"/> <p> </p>
				<div className="Buttons">
					<button onClick={clickHandler} className="EditButton" type="submit">Cancel</button>
					<input className="EditButton" type="submit" value="Submit form!" href="http://localhost:5173"/>
				</div>
        </form>
    </div>
	)
}
